package com.robin.personalAssistant_server.util;

public class JudgeUtil {

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static boolean isInteger(String str) {
        for (char ch : str.toCharArray()) {
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
        return true;
    }

}
