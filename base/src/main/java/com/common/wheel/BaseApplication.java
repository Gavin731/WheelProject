package com.common.wheel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.LogUtils;
import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;

import gnu.trove.THash;
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
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        initSharedPreferences();
        RxJavaPlugins.setErrorHandler(throwable -> {
            throwable.printStackTrace();
            LogUtils.e(throwable, "RxJavaPlugins");
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
