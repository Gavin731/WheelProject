package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    private boolean isLoadAdCallback =false;// 加载广告是否有回调

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
        // 延迟20秒执行，没有回调直接跳首页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isLoadAdCallback){
                    openMain();
                }
            }
        }, 20000);
        splashContainer = findViewById(R.id.splashContainer);
        // 获取信息是否可以上报
        ADUtil.getKey(this, new InitCallback() {
            @Override
            public void success() {
                LogUtils.e("aaaaa_开始获取开屏广告");
                ADUtil.showOpenScreenAd(SplashActivity.this, ConstantConfig.AD_SPLASH, splashContainer, ScreenUtils.getAppScreenWidth(), ScreenUtils.getAppScreenHeight(), new OpenScreenAdCallBack() {
                    @Override
                    public void onAdClose() {
                        isLoadAdCallback = true;
                        openMain();
                    }

                    @Override
                    public void onSplashAdClick() {

                    }

                    @Override
                    public void onSplashAdShow() {
                        isLoadAdCallback = true;
                    }

                    @Override
                    public void onSplashLoadFail() {
                        isLoadAdCallback = true;
                        openMain();
                    }

                    @Override
                    public void onSplashRenderFail() {
                        isLoadAdCallback = true;
                        openMain();
                    }
                });
            }

            @Override
            public void error() {
                LogUtils.e("aaaaa_初始化失败");
                isLoadAdCallback = true;
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
