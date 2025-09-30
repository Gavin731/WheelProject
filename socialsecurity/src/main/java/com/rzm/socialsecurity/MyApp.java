package com.rzm.socialsecurity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.BarUtils;
import com.bytedance.sdk.openadsdk.TTCustomController;
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig;
import com.common.wheel.BaseApplication;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InitCallback;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.activity.SplashActivity;
import com.rzm.socialsecurity.util.UMUtil;
import com.umeng.commonsdk.UMConfigure;

public class MyApp extends BaseApplication {

    private int activityCount = 0;

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AdvertisementManager.getInstance().init(this, "5744513", "社保个税管家", new InitCallback() {
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        }, getTTCustomController());
        AdvertisementManager.getInstance().initConfig("297ca176-e8ad-40e7-b39e-0926e16bf166", "192.168.1.0");
        Hawk.init(this).build();
        FileDownloader.setupOnApplicationOnCreate(this);
        UMUtil.preInit(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                if (activityCount == 1) {
                    // 应用进入前台
                    Log.d("aaaaa", "App in foreground");
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    // 应用进入后台
                    resetApp();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public void resetApp() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private TTCustomController getTTCustomController() {
        return new TTCustomController() {
            @Override
            public boolean isCanUseLocation() {  //是否授权位置权限
                return true;
            }

            @Override
            public boolean isCanUsePhoneState() {  //是否授权手机信息权限
                return true;
            }

            @Override
            public boolean isCanUseWifiState() {  //是否授权wifi state权限
                return true;
            }

            @Override
            public boolean isCanUseWriteExternal() {  //是否授权写外部存储权限
                return true;
            }

            @Override
            public boolean isCanUseAndroidId() {  //是否授权Android Id权限
                return true;
            }

            @Override
            public MediationPrivacyConfig getMediationPrivacyConfig() {
                return new MediationPrivacyConfig() {
                    @Override
                    public boolean isLimitPersonalAds() {  //是否限制个性化广告
                        return false;
                    }

                    @Override
                    public boolean isProgrammaticRecommend() {  //是否开启程序化广告推荐
                        return true;
                    }
                };
            }
        };
    }
}
