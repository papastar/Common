package com.papa.library.okhttp;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2015/11/21.
 */
public class OkHttpUtil {


    public static void logRequestBody(String url) {
        Logger.d(url);
    }

    public static void logRequestUrl(String url) {
        Logger.d(url);
    }

    public static void logResponse(String response) {
        Logger.d(response);
    }

    public static void logResponseFail(Exception e, String url) {
        Logger.e(e, url);
    }

}
