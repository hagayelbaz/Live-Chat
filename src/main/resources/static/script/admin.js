import {CallsHelper} from "./ApiCalls/CallsHelper.js";

/**
 * this file is for the admin page
 */
(() => {
    const adminMail = "admin-mail";
    const makeRequestButton = "make-request-button";
    const requestInput = "request-input";
    const requestResult = "request-result";
    const requestSelect = "request-select";
    const requestBody = "request-body";
    const adminContent = "admin-content";
    const adminHeader = "admin-header";
    const endpoint = "endpoint";

    const messageBody = {
        text: "text",
        timestamp: new Date(),
        userId: "userId",
        chatRoomId: "chatRoomId",
    }
    const userBody = {
        email: "email",
    }
    const chatRoomBody = {
        name: "name",
        users: [userBody, userBody],//list f userBody
    }

    /**
     * handle the request by the selected option
     * @param request the request to send
     * @param body the body of the request
     * @returns {Promise<Promise<*>|Promise<string>|null>}
     */
    function handleRequestBySelect(request, body) {
        const selectedOption = $(`#${requestSelect} option:selected`).text();
        let _body;
        try {
            _body = JSON.parse(body);
        } catch (e) {
            _body = body;
        }

        switch (selectedOption) {
            case "GET":
                return CallsHelper.get(request, CallsHelper.ReturnTypes.TEXT);
            case "POST":
                return CallsHelper.post(request, _body, CallsHelper.ReturnTypes.TEXT);
            case "DELETE":
                return CallsHelper.delete(request, CallsHelper.ReturnTypes.TEXT);
        }
    }


    /**
     * set the ace editor to be json editor
     */
    function aceEditorToJson() {
        let htmEditor;
        ace.require("ace/ext/language_tools");

        htmEditor = ace.edit("request-body");
        htmEditor.getSession().setMode("ace/mode/json");
        htmEditor.setTheme("ace/theme/merbivore");

        htmEditor.setOptions({
            enableBasicAutocompletion: true,
            enableSnippets: true
        });

        htmEditor.setShowPrintMargin(false);
        htmEditor.setHighlightActiveLine(false);

    }

    /**
     * when the send request button is clicked, send the request and show the result
     */
    function onSendRequestClick() {
        const request = $(`#${requestInput}`).val();
        const editor = ace.edit(requestBody);
        const body = editor.getValue();
        const requestType = $(`#${requestSelect} option:selected`).text();

        const startMessage = `Sending '${requestType}' request to: '${request}' with body: \n ${body}`;

        handleRequestBySelect(request, body).then((res) => {
            try {
                res = JSON.parse(res);
                res = JSON.stringify(res, null, 4);
            } catch (e) {
            }

            const message = `${startMessage} \n Result: ${res}`;
            $(`#${requestResult}`).css('color', 'green').text(message); // Set color to green

        }).catch((err) => {
            const message = `${startMessage} \n Error: ${err}}`;
            $(`#${requestResult}`).css('color', 'red').text(message); // Set color to red
        });

    }

    /**
     * when an endpoint is clicked, fill the request input with the endpoint
     */
    function onEndpointClick() {
        const text = $(this).text();
        $(`#${requestInput}`).val(text);

        //if "this" contain class named "message" then fill the body with messageBody
        if ($(this).hasClass("message-post")) {
            const editor = ace.edit(requestBody);
            editor.setValue(JSON.stringify(messageBody, null, 4));
        } else if ($(this).hasClass("chatroom-post")) {
            const editor = ace.edit(requestBody);
            editor.setValue(JSON.stringify(chatRoomBody, null, 4));
        } else {
            const editor = ace.edit(requestBody);
            editor.setValue("");
        }
    }

    /**
     * set the height of the text area to be the height of the rest of the page
     * also set the admin content max height to show scroll bar
     */
    function onSizeChange() {
        try {
            const win = $(window);
            const win_height = win.height();
            const admin_header = $(`.${adminHeader}`);
            const admin_content = $(`.${adminContent}`);
            const row_1 = $(`.row-1`);
            const row_2 = $(`.row-2`);

            admin_content.css("max-height", (win_height - admin_header.height) + "px");

            const textAreaHeight = win_height - admin_header.height() - row_1.height() - row_2.height();
            const rows = win.width() < 960 ? 7 : textAreaHeight / 30 - 1;

            $(`#${requestResult}`).attr("rows", Math.floor(rows));

        } catch (e) {
            //ignore, maybe the element is not rendered yet
        }
    }

    document.addEventListener("DOMContentLoaded", () => {
        aceEditorToJson();
        onSizeChange();

        $(`#${makeRequestButton}`).click(onSendRequestClick);
        window.addEventListener('resize', onSizeChange, true);
        $(`.${endpoint}`).click(onEndpointClick);
    });
})();












