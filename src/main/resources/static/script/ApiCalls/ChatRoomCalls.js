import {CallsHelper} from "./CallsHelper.js";

/**
 * Class that contains all the calls to the server related to chat rooms.
 */
export class ChatRoomCalls{
    static CHAT_ROOMS_PATH = "/api/chatroom";

    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    static async getAllChatRooms(){
        return CallsHelper.get(`${this.CHAT_ROOMS_PATH}`, CallsHelper.ReturnTypes.JSON);
    }

    static async getAllChatRoomsByUserId(userId){
        return CallsHelper.get(`${this.CHAT_ROOMS_PATH}/user/${userId}`, CallsHelper.ReturnTypes.JSON);
    }

    static async getChatRoomById(chatRoomId){
        return CallsHelper.get(`${this.CHAT_ROOMS_PATH}/${chatRoomId}`, CallsHelper.ReturnTypes.JSON);
    }

    static async getAllUsersNotInChatRoomWithUserId(userId){
        return CallsHelper.get(`${this.CHAT_ROOMS_PATH}/filter/not-friends/${userId}`, CallsHelper.ReturnTypes.JSON);
    }

    static async getAllUsersInChatRoomWithUserId(userId){
        return CallsHelper.get(`${this.CHAT_ROOMS_PATH}/filter/friends/${userId}`, CallsHelper.ReturnTypes.JSON);
    }

    //==========================================================================
    //============================== CREATE ====================================
    //==========================================================================

    static async createChatRoom(chatRoomRequest){
        return CallsHelper.post(`${this.CHAT_ROOMS_PATH}`, chatRoomRequest, CallsHelper.ReturnTypes.JSON);
    }

    static async addUserToChatRoom(chatRoomId, userId){
        return CallsHelper.post(`${this.CHAT_ROOMS_PATH}/${chatRoomId}/user/${userId}`, {}, CallsHelper.ReturnTypes.JSON);
    }

    static async createChatRoomAndAddUsers(chatRoomRequest){
        return CallsHelper.post(`${this.CHAT_ROOMS_PATH}/create-add`, chatRoomRequest, CallsHelper.ReturnTypes.JSON);
    }

    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    static async deleteAllChatRooms(){
        return CallsHelper.delete(`${this.CHAT_ROOMS_PATH}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async deleteUserFromChatRoom(chatRoomId, userId){
        return CallsHelper.delete(`${this.CHAT_ROOMS_PATH}/${chatRoomId}/user/${userId}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async deleteChatRoomById(chatRoomId){
        return CallsHelper.delete(`${this.CHAT_ROOMS_PATH}/${chatRoomId}`, CallsHelper.ReturnTypes.TEXT);
    }
}