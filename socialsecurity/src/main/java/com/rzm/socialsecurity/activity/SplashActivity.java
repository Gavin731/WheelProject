package com.rzm.socialsecurity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.presenter.SplashPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISplashView;

public class SplashActivity extends MvpActivity<SplashPresenter> implements ISplashView {

    FrameLayout splashContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
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
        ADUtil.showOpenScreenAd(this, "103656025", splashContainer, 0, 0, new OpenScreenAdCallBack() {
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

    public void openMain(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
