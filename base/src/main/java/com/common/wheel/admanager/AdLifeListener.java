package com.common.wheel.admanager;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;

import java.lang.ref.WeakReference;

public class AdLifeListener implements TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
    private final WeakReference<Context> mContextRef;

    protected AdLifeListener(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    public void onAdShow() {
        LogUtils.i("AdLifeListener 广告已显示");
        ViewHelper.addInterstitialView();
    }

    @Override
    public void onAdVideoBarClick() {
        LogUtils.i("AdLifeListener 广告被点击");
    }

    @Override
    public void onAdClose() {
        LogUtils.i("AdLifeListener 广告已关闭");
    }

    @Override
    public void onVideoComplete() {
        LogUtils.i("AdLifeListener 广告加载完成");
    }

    @Override
    public void onSkippedVideo() {
        LogUtils.i("AdLifeListener 广告已跳过");
    }


}
