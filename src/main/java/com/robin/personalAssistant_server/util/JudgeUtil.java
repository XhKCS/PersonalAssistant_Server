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
        try {
            Integer.parseInt(str);
            return true;
        } catch(Exception e){
            return false;
        }
    }

}
