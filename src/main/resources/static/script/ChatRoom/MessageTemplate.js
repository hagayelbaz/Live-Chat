import {MessagesCalls} from "../ApiCalls/MessagesCalls.js";


/**
 * this class is responsible for generating the HTML template for the chat room
 * it's better to generate the HTML in the client side and not in the server side
 * because the server side will generate the HTML only once, but the client side
 * will generate the HTML many times, especially for messages.
 */
export class MessageTemplate {

    /**
     * get all messages template for the chat room
     * @param chatRoomId
     * @param userId
     * @param currentLastMessage
     * @returns {Promise<string>}
     */
    static async getAllMessagesTemplate(chatRoomId, userId, currentLastMessage) {
        let isNull = chatRoomId === null || userId === null;

        const allMessages = await MessagesCalls.getMessagesByChatRoomId(chatRoomId)
            .catch(() => isNull = true);

        if (isNull || allMessages.length === 0) return "";

        const messagesTemplate = this.#allMessagesToTemplate(allMessages, currentLastMessage, userId);

        const height = $(window).height() - $(".chat-input").height() - $(".chat-header").height();

        return `
            <div id="message-container" class="d-flex flex-column-reverse p-3 overflow-auto flex-grow-1" style="overflow-y: scroll; max-height: ${height}px">
                ${messagesTemplate}
            </div>
        `;
    }

    /**
     * get the template for all messages
     * basically helper function for getAllMessagesTemplate
     * @param allMessages the list of all messages
     * @param currentLastMessage the last message in the chat room
     * @param userId the user id
     * @returns {string} the template
     */
    static #allMessagesToTemplate(allMessages, currentLastMessage, userId) {
        currentLastMessage.id = allMessages[allMessages.length - 1].id;
        allMessages.reverse();
        let messagesTemplate = "";

        for (const message of allMessages) {
            const sender = message.user.id === userId ? "me" : "other";
            const bubbleTemplate = this.getBubbleTemplate(message, sender, message.timestamp);
            messagesTemplate += bubbleTemplate;
        }
        return messagesTemplate;
    }

    /**
     * for user can't inject html to the chat room
     * and send messages like <script>alert("hello")</script>
     * @param text the text u want to escape
     * @returns {*}
     */
    static escapeHtml(text) {
        return text
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }

    /**
     * generate HTML bubble template for each message
     * @param message the message object from server
     * @param sender the sender of the message ("me" or "other") as text
     * @param date the date of the message
     * @returns {string} the html template
     */
    static getBubbleTemplate(message, sender, date) {
        const align = sender === "me" ? "align-self-end" : "align-self-start";
        const messageClasses = sender === "me" ? "bg-primary text-light" : "bg-light text-dark";
        const direction = sender === "me" ? "ltr" : "rtl";
        const bubbleClass = sender === "me" ? "bubble-me" : "bubble-other";

        date = new Date(date);
        let options = {
            hour: 'numeric',
            minute: 'numeric',
            month: 'short',
            day: 'numeric'
        };

        let formattedDate = date.toLocaleDateString('en-US', options);
        const personNameSub = message.user.firstname.substring(0, 1).toUpperCase() + message.user.lastname.substring(0, 1).toUpperCase();


        return `
           <div class="${align}" dir="${direction}">
                <div class="d-flex flex-column align-items-end p-1 ${bubbleClass}">
                     <div class="d-flex">
                        <div class="ms-4"> 
                             <div class="p-2 ps-4 pe-4 mb-1 bubble-chat ${messageClasses}" >
                                <p style="word-wrap: break-word" class="m-0" dir="ltr">${this.escapeHtml(message.text)}</p>
                             </div>
                        </div>
                        ${sender !== "me" ?
                                `
                                <div class="d-inline-flex justify-content-center align-items-center circle chat-date">
                                    <span class="">${personNameSub}</span>
                                </div> 
                                ` : ""
                        }
                    </div>
                    <p class="m-2 mt-1 text-end text-muted">${formattedDate}</p>
                </div>        
           </div>
        `;
    }
}
