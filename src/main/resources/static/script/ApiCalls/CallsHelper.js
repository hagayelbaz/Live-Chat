/**
 * Helper class for making API calls
 * make sure the csrf token is present in the header of the page and in the body of the request
 * @type {{ReturnTypes: {JSON: string, TEXT: string, NONE: string}, get: (function(*=, *=): Promise<any>), post: (function(*=, *=, *=): Promise<any>), delete: (function(*=, *=): Promise<any>)}}
 */
export class CallsHelper{
    /**
     * Return types for the API calls using like Enum
     * @type {{JSON: string, TEXT: string, NONE: string}}
     */
    static ReturnTypes = {
        JSON: "json",
        TEXT: "text",
        NONE: "none",
    }

    /**
     * Get method for the API calls
     * @param url the url to call
     * @param type the type of the return
     * @returns {Promise<Promise<any>|Promise<string>|null>}
     */
    static async get(url, type = CallsHelper.ReturnTypes.JSON) {
        const response = await fetch(url);

        return type === CallsHelper.ReturnTypes.JSON ? response.json()
            : type === CallsHelper.ReturnTypes.TEXT ? response.text() : null;
    }

    /**
     * Post method for the API calls
     * @param url the url to call
     * @param body the body of the request
     * @param type the type of the return
     * @returns {Promise<Promise<any>|Promise<string>|null>}
     */
    static async post(url, body, type = CallsHelper.ReturnTypes.JSON) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        // add the csrf token to the body
        body._csrf = token;

        const response = await fetch(url, {
            method: "POST",
            headers: {
                [header]: token,
                "charset": "UTF-8",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        return type === CallsHelper.ReturnTypes.JSON ? response.json()
                : type === CallsHelper.ReturnTypes.TEXT ? response.text() : null;
    }

    /**
     * Delete method for the API calls
     * @param url the url to call
     * @param type the type of the return
     * @returns {Promise<Promise<any>|Promise<string>|null>}
     */
    static async delete(url, type = CallsHelper.ReturnTypes.JSON) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        const response = await fetch(url, {
            method: 'DELETE',
            headers: {
                [header]: token,
                "charset": "UTF-8",
                "Content-Type": "application/json"
            }
        });

        return type === CallsHelper.ReturnTypes.JSON ? response.json()
            : type === CallsHelper.ReturnTypes.TEXT ? response.text() : null;
    }
}
