package com.cy.dailywallpaper;


import java.util.HashMap;

/**
 * ************************************************************
 * author：cy
 * version：
 * create：2019/04/09 16:55
 * desc：
 * ************************************************************
 */

public class JniUtils {

    static {
        System.loadLibrary("native-lib");

    }
//    public static native String stringFromJNI();
    public static native HashMap<String,String> getHashMap();
//    public static native DiskInfo[] getStructArray();
}
