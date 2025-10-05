package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InitCallback;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.SplashPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISplashView;

public class SplashActivity extends MvpActivity<SplashPresenter> implements ISplashView {

    FrameLayout splashContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        MyApp.getInstance().isSplash = true;
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
        // 获取信息是否可以上报
        ADUtil.getKey(this, new InitCallback() {
            @Override
            public void success() {
                LogUtils.e("aaaaa_开始获取开屏广告");
                ADUtil.showOpenScreenAd(SplashActivity.this, ConstantConfig.AD_SPLASH, splashContainer, ScreenUtils.getScreenWidth(), 0, new OpenScreenAdCallBack() {
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

            @Override
            public void error() {
                LogUtils.e("aaaaa_初始化失败");
                openMain();
            }
        });
    }

    public void openMain() {
        MyApp.getInstance().isSplash = false;
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

}
