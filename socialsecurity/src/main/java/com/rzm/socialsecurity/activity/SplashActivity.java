package com.rzm.socialsecurity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.SplashEventEntity;
import com.rzm.socialsecurity.entity.SplashEventEntity2;
import com.rzm.socialsecurity.presenter.SplashPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISplashView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends MvpActivity<SplashPresenter> implements ISplashView {

    FrameLayout splashContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        EventBus.getDefault().register(this);
        MyApp.getInstance().setSplash(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销 EventBus
        EventBus.getDefault().unregister(this);
        MyApp.getInstance().setSplash(false);
    }

    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        splashContainer = findViewById(R.id.splashContainer);
//        loaOpenScreenAdd(new SplashEventEntity(false));
    }

    // 订阅事件 - 主线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loaOpenScreenAd1(SplashEventEntity entity) {
        LogUtils.e("开始获取开屏广告0");
        ADUtil.showOpenScreenAd(this, ConstantConfig.AD_SPLASH, splashContainer, 0, 0, new OpenScreenAdCallBack() {
            @Override
            public void onAdClose() {
                openMain();
            }

            @Override
            public void onSplashAdClick() {

            }

            @Override
            public void onSplashAdShow() {

            }

            @Override
            public void onSplashLoadFail() {
                openMain();
            }

            @Override
            public void onSplashRenderFail() {
                openMain();
            }
        });
    }
    // 订阅事件 - 主线程
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loaOpenScreenAd2(SplashEventEntity2 entity) {
        LogUtils.e("开始获取开屏广告");
        ADUtil.showOpenScreenAd(this, ConstantConfig.AD_SPLASH, splashContainer, 0, 0, new OpenScreenAdCallBack() {
            @Override
            public void onAdClose() {
                openMain();
            }

            @Override
            public void onSplashAdClick() {

            }

            @Override
            public void onSplashAdShow() {

            }

            @Override
            public void onSplashLoadFail() {
                openMain();
            }

            @Override
            public void onSplashRenderFail() {
                openMain();
            }
        });
    }

    public void openMain() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
