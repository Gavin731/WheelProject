package com.rzm.socialsecurity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.BaseApplication;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.activity.SplashActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.IPEvent;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.UMUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MyApp extends BaseApplication {

    private int activityCount = 0;
    private static MyApp myApp;

    private boolean umIsInit=false;

    public static MyApp getMyApp(){
        return myApp;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        boolean isShowUserPrivacy = Hawk.get(ConstantConfig.isAgreeUserPrivacy, false);
        if(isShowUserPrivacy){
            initUm();
        }
        Hawk.put("url", getResources().getString(R.string.app_url));
        FileDownloader.setupOnApplicationOnCreate(this);
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
                    resetApp();
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

                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
//        AutoSizeConfig.getInstance().setCustomFragment(true).setExcludeFontScale(true);
    }

    public void resetApp() {
        if (isSplash) {
            return;
        }
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void initUm(){
        LogUtils.i("开始初始化友盟");
        if(umIsInit){
            LogUtils.i("开始初始化友盟:已经初始化过了");
            return;
        }
        UMUtil.preInit(this);
        UMUtil.init(this);
        umIsInit=true;
    }
}
