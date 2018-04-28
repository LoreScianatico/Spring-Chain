package com.lorescianatico.chain.util;


/**
 * Utility for string building
 */
public class StringBuilderUtil {

    /**
     * Private constructor
     */
    private StringBuilderUtil(){}

    /**
     * Builds an array of strings based on the order
     * @param strings the strings to be concatenated
     * @return result string
     */
    public static String build(String... strings){
        StringBuilder stringBuilder = new StringBuilder(strings[0].length());
        for (String string : strings) {
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

}
