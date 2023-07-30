import {CallsHelper} from "./CallsHelper.js";

/**
 *  This class contains all the calls related to the users
 */
export class UserCalls{
    static USERS_PATH = "/api/users"

    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    static async getAllUsers(){
        return CallsHelper.get(`${UserCalls.USERS_PATH}`, CallsHelper.ReturnTypes.JSON);
    }

    static async getUserByEmail(email){
        return CallsHelper.get(`${UserCalls.USERS_PATH}/email/${email}`, CallsHelper.ReturnTypes.JSON);
    }

    static async getUserById(id){
        return CallsHelper.get(`${UserCalls.USERS_PATH}/id/${id}`, CallsHelper.ReturnTypes.JSON);
    }

    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    static async deleteAllUsers(){
        return CallsHelper.delete(`${UserCalls.USERS_PATH}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async deleteUserByEmail(email){
        return CallsHelper.delete(`${UserCalls.USERS_PATH}/email/${email}`, CallsHelper.ReturnTypes.TEXT);
    }

    static async deleteUserById(id){
        return CallsHelper.delete(`${UserCalls.USERS_PATH}/id/${id}`, CallsHelper.ReturnTypes.TEXT);
    }
}
