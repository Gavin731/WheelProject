package com.film.television;

import android.app.Application;

import com.common.wheel.admanager.AdvertisementManager;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdvertisementManager.getInstance().init(this, "5670955", "终端测试软件");
    }
}
