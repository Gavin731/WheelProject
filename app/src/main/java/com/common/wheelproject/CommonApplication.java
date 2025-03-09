package com.common.wheelproject;

import com.common.wheel.BaseApplication;
import com.common.wheel.admanager.AdvertisementManager;

public class CommonApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AdvertisementManager.getInstance().init(getApplicationContext());
    }
}
