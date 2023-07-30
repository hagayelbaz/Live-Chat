import {FragmentsCalls} from "./ApiCalls/FragmentsCalls.js";
import {CallsHelper} from "./ApiCalls/CallsHelper.js";
import {Variables} from "./ChatRoom/Variables.js";
import {HashColor} from "./ChatRoom/HashColor.js";
import {Service} from "./ChatRoom/Service.js";
import {UserCalls} from "./ApiCalls/UsersCalls.js";
import {MessageTemplate} from "./ChatRoom/MessageTemplate.js";
import {MessagesCalls} from "./ApiCalls/MessagesCalls.js";
import {ChatRoomCalls} from "./ApiCalls/ChatRoomCalls.js";
import {ErrorManager} from "./ErrorManager.js";

/**
 * this file is the main file of the chat room
 */
(() => {

    let targetChatRoomId = null;
    let userId = null;
    const currentLastMessage = {
        id: null
    };

    /**
     * auto set the id of the user by given email from database
     * @returns {Promise<void>}
     */
    async function setUserId() {
        const currentUser = await UserCalls.getUserByEmail(Variables.currentUserEmail);
        userId = currentUser.id;
    }

    /**
     * set the selected chat room data as name and participants
     * @returns {Promise<void>}
     */
    async function setChatDetails() {
        let users = "";
        if (targetChatRoomId === null)
            return;

        ChatRoomCalls.getChatRoomById(targetChatRoomId).then((chatRoom) => {
            chatRoom.users?.forEach((user) => users += user.firstname + " " + user.lastname + ", ");
            Variables.chatRoomName.text(chatRoom.name);
            Variables.chatRoomUsers.text(users.substring(0, users.length - 2));
        }).catch();
    }

    /**
     * refresh the messages in the chat room
     * @param anyway (boolean) if true, refresh the messages also
     * if the last message is the same (useful for empty chat room, or if user click again on the same chat room)
     * @returns {Promise<void>}
     */
    async function refreshChatMessages(anyway = false) {
        if (anyway)
            Variables.chatMessages.html("");

        await setChatDetails();

        if (targetChatRoomId === null)
            return;

        let lastMessage = await MessagesCalls.getLastMessageInChatRoom(targetChatRoomId);
        if (!anyway && (lastMessage === null || (currentLastMessage !== null && lastMessage.id === currentLastMessage.id)))
            return;

        const messages = await MessageTemplate.getAllMessagesTemplate(targetChatRoomId, userId, currentLastMessage);
        Variables.chatMessages.html(messages);

        //render the css
        setTimeout(() => {
            setCircleColors();

            if(currentLastMessage.id !== null && lastMessage.id !== currentLastMessage.id)
                return;

            onSizeChange();
        }, 0);
    }

    /**
     * this function is very important!
     * the idea is to show the scroll bar with the exact height
     * for not - hidden input
     */
    function onSizeChange() {
        try {
            const height = $(window).height() - $(".chat-input").height() - $(".chat-header").height();
            $("#message-container").css("max-height", height + "px");
            const chatMessages = document.getElementById('message-container');
            chatMessages.scrollTop = chatMessages.scrollHeight;
        } catch (e) {
            //ignore, maybe the element is not rendered yet
        }
    }

    /**
     * set the available chat rooms for the user
     * @returns {Promise<void>}
     */
    async function setUserChatRooms() {
        Variables.refreshVariables();
        const res = await FragmentsCalls.getAllChatRoomByUserId(userId);
        Variables.userChatRooms.html(res);
    }

    /**
     * show the modal for user can sign other users to the chat room
     * @returns {Promise<void>}
     */
    async function createChatRoom() {
        Variables.refreshVariables();
        Variables.allUsersModal.modal('show');
        const res = await FragmentsCalls.getAllPersonMenu();
        Variables.allUsersModalData.html(res);
        setCircleColors();

        $("#all-users-modal-data li").each((user, li) => {
            li.addEventListener("click", async () => {
                Variables.refreshVariables();
                const chatName = Variables.allUsersModalValue.val();
                if(chatName === "" || chatName.length < 2) {
                    ErrorManager.addError("Chat name must be at least 2 characters");
                    return;
                }

                let userEmail = $(li).find('.user-email').text();
                await Service.assignUserToNewChatRoom(Variables.currentUserEmail, userEmail, chatName);
                Variables.allUsersModal.modal('hide');
                await fireEvents();
            })
        });
    }

    /**
     * show the modal for creating chat room with one user for now (u can add mor later)
     * @returns {Promise<void>}
     */
    async function onAddUserToChatRoom() {
        Variables.refreshVariables();
        Variables.usersNotInChatRoomModal.modal('show');
        const res = await FragmentsCalls.getAllUsersNotInChatRoomWithChatRoomId(targetChatRoomId);
        Variables.notConnectedUsers.html(res);
        setCircleColors();

        $("#not-connected-users li").each((user, li) => {
            li.addEventListener("click", async () => {
                Variables.refreshVariables();
                let userEmail = $(li).find('.user-email').text();
                let user = await UserCalls.getUserByEmail(userEmail);
                await ChatRoomCalls.addUserToChatRoom(targetChatRoomId, user.id);
                await fireEvents();
                Variables.usersNotInChatRoomModal.modal('hide');
            })
        });

    }

    /**
     * simple design function that show the little circle side of the user
     */
    function setCircleColors() {
        setTimeout(() => {
            $('.circle span').each(function () {
                const text = $(this).text();
                const color = HashColor.getColorFromName(text);
                $(this).parent().css('background-color', '#' + color);
            });
        }, 0);
    }

    /**
     * define some behavior for event like select chatroom
     * or for small screen
     */
    function chatAreaHandle() {
        Variables.refreshVariables();

        Variables.userListArea.find('li').click(async function () {
            await onChatRoomSelected($(this));
        });

        Variables.backToUsersButton.click(() => {
            Variables.refreshVariables();

            if ($(window).width() <= 768) {
                Variables.chatArea.removeClass("d-block").addClass("d-none");
                Variables.userListArea.removeClass("d-none");
            }
        });
    }

    /**
     * logout the user from the system
     */
    function onLogout() {
        CallsHelper.post("/auth/logout", {}).then()
            .catch((error) => console.log(error));
    }

    /**
     * make some refresh events
     * @returns {Promise<void>}
     */
    async function fireEvents() {
        Variables.refreshVariables();

        await refreshChatMessages(true);
        await setUserChatRooms();
        setCircleColors();
        chatAreaHandle();
    }

    /**
     * handle the send message button
     * @returns {Promise<void>}
     */
    async function sendMessageHandle() {
        const message = Variables.sendMessageInput.val();
        Variables.sendMessageInput.val("");

        if (message === "") {
            return;
        }

        await Service.sendMessage(message, Variables.currentUserEmail, targetChatRoomId);
        await fireEvents();
    }

    /**
     * handle the selected chat room event,
     * in the smaller screen there is spacial behavior.
     * also get the messages for this chat room
     * @param context
     * @returns {Promise<void>}
     */
    async function onChatRoomSelected(context) {
        Variables.refreshVariables();
        Variables.chatArea.removeClass("disabled");

        targetChatRoomId = context.find('.chat-room-id').val();
        Variables.chatMessages.html("");
        await refreshChatMessages(true);


        if ($(window).width() <= 768) {
            Variables.userListArea.addClass("d-none");
            Variables.chatArea.removeClass("d-none").addClass("d-block");
        }
    }


    document.addEventListener("DOMContentLoaded", async () => {
        Variables.refreshVariables();
        ErrorManager.resetError();
        await setUserId();//once! there is no reason to this will be changed
        await fireEvents();

        Variables.chatArea.addClass('disabled');
        Variables.logoutButton.click(onLogout);
        Variables.openAllUsersModalButton.on("click", createChatRoom);
        Variables.addUserToChatRoomButton.on("click", onAddUserToChatRoom);
        Variables.sendMessageButton.click(sendMessageHandle);

        //press enter - send the message
        Variables.sendMessageInput.keypress((e) => {
            if (e.which === 13) {
                sendMessageHandle();
            }
        });

        window.addEventListener('resize', onSizeChange, true);
        setInterval(refreshChatMessages, 1000);//the polling
    });
})();


