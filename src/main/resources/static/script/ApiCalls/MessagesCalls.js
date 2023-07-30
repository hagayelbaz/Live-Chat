import {CallsHelper} from "./CallsHelper.js";

/**
 * Class that contains all the calls related to the messages
 */
export class MessagesCalls{
    static MESSAGE_PATH = "/api/messages";

    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    static async getAllMessages(){
        try {
            return await CallsHelper.get(`${this.MESSAGE_PATH}`, CallsHelper.ReturnTypes.JSON);
        } catch (e){
            return null;
        }
    }

    static async getMessagesByChatRoomId(chatRoomId){
        try {
            return await CallsHelper.get(`${this.MESSAGE_PATH}/chatroom/${chatRoomId}`, CallsHelper.ReturnTypes.JSON);
        }catch (e){
            return null;
        }
    }

    static async getLastMessageInChatRoom(chatRoomId){
        try {
            return await CallsHelper.get(`${this.MESSAGE_PATH}/chatroom/${chatRoomId}/last`, CallsHelper.ReturnTypes.JSON);
        }catch (e){
            return null;
        }
    }

    //==========================================================================
    //=============================== POST =====================================
    //==========================================================================

    static async createMessage(message){
        return await CallsHelper.post(`${this.MESSAGE_PATH}`, message, CallsHelper.ReturnTypes.JSON);
    }

    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    static async deleteMessageById(messageId){
        return CallsHelper.delete(`${this.MESSAGE_PATH}/${messageId}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async deleteMessagesByChatRoomId(chatRoomId){
        return CallsHelper.delete(`${this.MESSAGE_PATH}/chatroom/${chatRoomId}`, CallsHelper.ReturnTypes.TEXT);
    }
}