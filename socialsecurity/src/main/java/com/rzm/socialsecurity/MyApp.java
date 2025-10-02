package com.rzm.socialsecurity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.BaseApplication;
import com.common.wheel.admanager.InitCallback;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.activity.SplashActivity;
import com.rzm.socialsecurity.entity.SplashEventEntity;
import com.rzm.socialsecurity.entity.SplashEventEntity2;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.UMUtil;

import org.greenrobot.eventbus.EventBus;

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

        Hawk.init(this).build();
        Hawk.put("url", getResources().getString(R.string.app_url));
        // 获取信息是否可以上报
        ADUtil.getKey(this, new InitCallback() {
            @Override
            public void success() {
                setAdInit(true);
                EventBus.getDefault().post(new SplashEventEntity(true));
            }

            @Override
            public void error() {
                setAdInit(false);
                EventBus.getDefault().post(new SplashEventEntity(false));
            }
        });

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
        if(isSplash){
            return;
        }
        LogUtils.e("1开始获取开屏广告111");
        EventBus.getDefault().postSticky(new SplashEventEntity2(true));
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
