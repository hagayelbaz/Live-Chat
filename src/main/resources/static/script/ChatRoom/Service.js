import {UserCalls as UsersCalls, UserCalls} from "../ApiCalls/UsersCalls.js";
import {ChatRoomCalls} from "../ApiCalls/ChatRoomCalls.js";
import {MessagesCalls as MessageCalls} from "../ApiCalls/MessagesCalls.js";

/**
 * simple service that allow the user to send messages and create new chat rooms
 */
export class Service{
    /**
     * create a new chat room and add the current user and the registered user to it
     * @param currentUserEmail the current user email
     * @param registeredUserEmail the registered user email
     * @param chatRoomName the chat room name
     * @returns {Promise<void>} a promise that resolve when the chat room is created
     */
    static async assignUserToNewChatRoom(currentUserEmail, registeredUserEmail, chatRoomName){
        const user = await UsersCalls.getUserByEmail(registeredUserEmail);
        const currentUser = await UserCalls.getUserByEmail(currentUserEmail);

        const newUser = {
            name: chatRoomName,
            users:[
                {email: user.email},
                {email: currentUser.email}]
        }

        await ChatRoomCalls.createChatRoomAndAddUsers(newUser);
    }

    /**
     * send a message to a chat room
     * @param message the message to send
     * @param senderEmail the sender email
     * @param chatRoomId the chat room id
     * @returns {Promise<void>} a promise that resolve when the message is sent
     */
    static async sendMessage(message, senderEmail, chatRoomId){
        const sender = await UserCalls.getUserByEmail(senderEmail);

        const newMessage = {
            text: message,
            timestamp: new Date(),
            userId: sender.id,
            chatRoomId: chatRoomId,
        }

        await MessageCalls.createMessage(newMessage);
    }
}