import {Variables} from "./ChatRoom/Variables.js";

/**
 * Class to manage errors
 * simply add error message, static, using this class,
 * the message automatically disappear after 5 seconds
 */
export class ErrorManager{
    static errorList = [];

    /**
     * Add error message to the list
     * @param error
     */
    static addError(error){
        this.errorList.push(error);
        this.manage();

        setTimeout(() => {
            this.errorList.pop();
            this.manage();
        }, 5000);
    }

    /**
     * manage if show or hide in timeout
     */
    static manage(){
        if(this.errorList.length > 0){
            this.showError();
        }else {
            this.hideError();
        }
    }

    /**
     * show error message
     */
    static showError(){
        if(Variables.errorAlert.hasClass("d-none"))
            Variables.errorAlert.removeClass("d-none")
        Variables.errorAlert.addClass("d-block");

        Variables.errorAlert.find("span").html(this.errorList[this.errorList.length - 1]);
    }

    /**
     * hide error message
     */
    static hideError(){
        if(Variables.errorAlert.hasClass("d-block"))
            Variables.errorAlert.removeClass("d-block");
        Variables.errorAlert.addClass("d-none");
    }

    /**
     * reset error list (hide and empty the list)
     */
    static resetError(){
        this.errorList = [];
        this.hideError();
    }
}