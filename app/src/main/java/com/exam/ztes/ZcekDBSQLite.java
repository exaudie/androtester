package com.exam.ztes;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by admin on 2/12/2018.
 */

public class ZcekDBSQLite extends Application {
    private static Context cntx;

    @Override
    public void onCreate() {
        super.onCreate();
        cntx = this;
        Stetho.initializeWithDefaults(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    public static Context getCntx() {
        return cntx;
    }
}
