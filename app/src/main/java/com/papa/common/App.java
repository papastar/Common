package com.papa.common;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.papa.library.data.DatabaseManager;
import com.papa.library.okhttp.OkHttpClientManager;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by Administrator on 2015/11/12.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("HTTP");
        DatabaseManager.init(this);
        initOkHttp();
    }


    private void initOkHttp() {
        OkHttpClient client = OkHttpClientManager.getInstance().getOkHttpClient();
        try {
            Class c = Class.forName("com.facebook.stetho.okhttp.StethoInterceptor");
            client.networkInterceptors().add((Interceptor) c.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
