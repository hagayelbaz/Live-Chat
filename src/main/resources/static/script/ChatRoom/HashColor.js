/**
 * simple class that generate color
 * from a string.
 */
export class HashColor{
    static #hashCode(str) {
        let hash = 0;
        for (let i = 0; i < str.length; i++) {
            hash = str.charCodeAt(i) + ((hash << 5) - hash);
        }
        return hash;
    }

    static #intToRGB(i){
        const c = (i & 0x00FFFFFF)
            .toString(16)
            .toUpperCase();

        return "00000".substring(0, 6 - c.length) + c;
    }

    /**
     * generate a color from a string
     * @param name the string
     * @returns {*} the color
     */
    static getColorFromName(name) {
        return HashColor.#intToRGB(HashColor.#hashCode(name));
    }
}