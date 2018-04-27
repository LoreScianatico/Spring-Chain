package com.lorescianatico.chain.util;


public class StringBuilderUtil {

    private StringBuilderUtil(){}

    public static String build(String... strings){
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

}
