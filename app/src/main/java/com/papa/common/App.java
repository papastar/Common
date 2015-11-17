package com.papa.common;

import android.app.Application;

import com.papa.library.data.DatabaseManager;


/**
 * Created by Administrator on 2015/11/12.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.init(this);
    }
}
