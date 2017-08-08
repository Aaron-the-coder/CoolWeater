package com.dale.coolweather.util;

/**
 * Created by dale on 2017/8/8.
 */

public class UrlHelper {
    private static boolean isTestEnv = false;

    public static String getAreaUrl(){
        if (isTestEnv){
            return "http://www.guolin.tech/api/china";
        }else {
            return "http://www.guolin.tech/api/china";
        }
    }
}
