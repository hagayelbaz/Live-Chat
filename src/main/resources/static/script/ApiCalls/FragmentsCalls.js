import {CallsHelper} from "./CallsHelper.js";

/**
 * Class that contains all the calls to the fragments controller
 */
export class FragmentsCalls{
    static FRAGMENTS_PATH = "/api/fragments";

    static async getAllPersonMenu(){
        return await CallsHelper.get(`${this.FRAGMENTS_PATH}/person-menu`, CallsHelper.ReturnTypes.TEXT);
    }

    static async getPersonMenuById(id){
        return await CallsHelper.get(`${this.FRAGMENTS_PATH}/person-menu/${id}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async getAllPersonMenuNotInChatRoomWithUserId(userId){
        return await CallsHelper.get(`${this.FRAGMENTS_PATH}/person-menu/filter/not-friends/${userId}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async getAllUsersNotInChatRoomWithChatRoomId(chatRoomId){
        return await CallsHelper.get(`${this.FRAGMENTS_PATH}/person-menu/filter/not-friends/chatroom/${chatRoomId}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async getAllPersonMenuInChatRoomWithUserId(userId){
        return await CallsHelper.get(`${this.FRAGMENTS_PATH}/person-menu/filter/friends/${userId}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async getAllChatRoomByUserId(userId){
        return await CallsHelper.get(`${this.FRAGMENTS_PATH}/chat-room/${userId}`, CallsHelper.ReturnTypes.TEXT);
    }
}