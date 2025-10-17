package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InitCallback;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.BuildConfig;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.IPEvent;
import com.rzm.socialsecurity.presenter.SplashPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISplashView;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.listener.OnGetOaidListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends MvpActivity<SplashPresenter> implements ISplashView {

    FrameLayout splashContainer;
    private boolean isLoadAdCallback =false;// 加载广告是否有回调
    InitCallback initCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        MyApp.getInstance().isSplash = true;
        EventBus.getDefault().register(this);
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

        initCallback = new InitCallback() {
            @Override
            public void success() {
                LogUtils.e("aaaaa_开始获取开屏广告");
                ADUtil.showOpenScreenAd(SplashActivity.this, ConstantConfig.AD_SPLASH, splashContainer, ScreenUtils.getAppScreenWidth(), ScreenUtils.getAppScreenHeight(), new OpenScreenAdCallBack() {
                    @Override
                    public void onAdClose() {
                        LogUtils.e("开屏广告被关闭，跳转首页");
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
                        LogUtils.e("开屏广告加载异常，跳转首页");
                        isLoadAdCallback = true;
                        openMain();
                    }

                    @Override
                    public void onSplashRenderFail() {
                        LogUtils.e("开屏广告渲染异常，跳转首页");
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
        };
        // 获取信息是否可以上报
        ADUtil.getKey(this, initCallback);
    }

    public void openMain() {
        MyApp.getInstance().isSplash = false;
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initAd(IPEvent ipEvent){
        ADUtil.initAd(getApplicationContext(), ipEvent.getIpAddress(), initCallback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
