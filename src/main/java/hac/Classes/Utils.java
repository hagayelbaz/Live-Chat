package hac.Classes;

import hac.Models.User.User;

/**
 * <b>Utils class</b>
 * <p>
 *     Provide some useful functions
 * </p>
 */
public class Utils {
    /**
     * combine multiple arrays into one
     * @param arrays arrays to combine
     * @return combined array
     */
    public static String [] combineArrays(String[] ...arrays){
        int length = 0;
        for (String[] array : arrays) {
            length += array.length;
        }
        String[] result = new String[length];
        int pos = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }

        return result;
    }

}
