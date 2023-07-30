/**
 * this class contains all the variables that
 * we need to use in the chat room (using JQuery)
 */
export class Variables {
    /**
     * all the chat rooms that available to the current user
     * @type {any | jQuery | HTMLElement}
     */
    static userChatRooms = null;
    static notConnectedUsers = null;
    /**
     * get the current user email from database
     * @type {String}
     */
    static currentUserEmail = null;
    /**
     * the main container of the user list
     * @type {any | jQuery | HTMLElement}
     */
    static userListArea = null;
    /**
     * the modal that show all the users u can add to chat room
     * that are not in the chat room right now
     * @type {any | jQuery | HTMLElement}
     */
    static usersNotInChatRoomModal = null;
    /**
     * the chat area full container
     * @type {any | jQuery | HTMLElement}
     */
    static chatArea = null;
    /**
     * the button u can back to see the chatroom list
     * only available on small screen
     * @type {any | jQuery }
     */
    static backToUsersButton = null;
    /**
     * logout button....
     * @type {any | jQuery}
     */
    static logoutButton = null;
    /**
     * the button that open the modal to
     * show all the users u can add to new chat-room
     * @type {any | jQuery | HTMLElement}
     */
    static openAllUsersModalButton = null;
    /**
     * email of user in list of person-menu
     * <p> object in list
     * @type {any | jQuery | HTMLElement}
     */
    static usersEmails = null;
    /**
     * name of user in list of person-menu
     * <p> object in list
     * @type {any | jQuery | HTMLElement}
     */
    static usersNames = null;
    /**
     * the text of users in chat room
     * (show all the names in the chat
     * @type {any | jQuery | HTMLElement}
     */
    static chatRoomUsers = null;
    /**
     * the name of the chat room
     * @type {any | jQuery | HTMLElement}
     */
    static chatRoomName = null;
    /**
     * the button that send message
     * @type {any | jQuery}
     */
    static sendMessageButton = null;
    /**
     * the input that user write the message in
     * @type {any | jQuery | HTMLElement}
     */
    static sendMessageInput = null;
    /**
     * the chat messages area Html
     * @type {any | jQuery | HTMLElement}
     */
    static chatMessages = null;
    /**
     * the button that add user to chat room
     * @type {any | jQuery | HTMLElement}
     */
    static addUserToChatRoomButton = null;

    /**
     * return the modal that contains all users
     * @type {any | jQuery | HTMLElement}
     */
    static allUsersModal = null;
    /**
     * the data inside the modal (users)
     * @type {any | jQuery | HTMLElement}
     */
    static allUsersModalData = null;
    /**
     * using for group name
     * @type {any | jQuery | HTMLElement}
     */
    static allUsersModalValue = null;

    /**
     * error alert div
     * @type {any | jQuery | HTMLElement}
     */
    static errorAlert = null;

    static refreshVariables() {
        Variables.userChatRooms = $("#user-chat-rooms");
        Variables.notConnectedUsers = $("#not-connected-users");
        Variables.currentUserEmail = $("#my-email").text();
        Variables.userListArea = $("#user-list-area");
        Variables.usersNotInChatRoomModal = $("#users-not-in-chatroom-modal");
        Variables.chatArea = $("#chat-area");
        Variables.backToUsersButton = $("#back-to-users");
        Variables.logoutButton = $("#logout");
        Variables.openAllUsersModalButton = $("#open-all-users-modal");
        Variables.chatRoomUsers = $("#chat-room-users");
        Variables.chatRoomName = $("#target-user-name");
        Variables.usersEmails = $(".user-email");
        Variables.usersNames = $(".user-name");
        Variables.sendMessageButton = $("#send-message-button");
        Variables.sendMessageInput = $("#send-message-input");
        Variables.chatMessages = $("#chat-messages");
        Variables.addUserToChatRoomButton = $("#add-user-to-chatroom");
        Variables.allUsersModal = $("#all-users-modal");
        Variables.allUsersModalData = $("#all-users-modal-data");
        Variables.allUsersModalValue = $("#all-users-modal-value");
        Variables.errorAlert = $(".error-alert");
    }

}