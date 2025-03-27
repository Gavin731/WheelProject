package com.common.wheel;

import android.app.Application;
import android.content.Context;


import com.orhanobut.hawk.Hawk;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        initSharedPreferences();
        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
        });
    }

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    /**
     * 初始化SharedPreferences
     */
    private void initSharedPreferences() {
        Hawk.init(this).build();
    }
}
