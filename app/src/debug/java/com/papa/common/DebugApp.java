package com.papa.common;

import com.facebook.stetho.Stetho;

/**
 * Created by Administrator on 2015/11/19.
 */
public class DebugApp extends App{

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
