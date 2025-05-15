package com.film.television;

import android.app.Application;

import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InitCallback;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdvertisementManager.getInstance().init(this, "5670955", "终端测试软件", new InitCallback() {
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        });
        AdvertisementManager.getInstance().initConfig();
    }
}
