package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;

public class InterstitialAdManager {

    private AdLoadListener mAdLoadListener;
    private TTAdNative mTTAdNative;

    public InterstitialAdManager(Context context) {
        TTAdManager ttAdManager = AdvertisementManager.getInstance().get();
        mTTAdNative = ttAdManager.createAdNative(context);
    }

    /**
     * 加载插屏广告
     *
     * @param codeId
     */
    public void loadAd(Activity activity, final String codeId) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) // 广告代码位Id
                .setOrientation(TTAdConstant.VERTICAL)  //设置方向
                .setAdLoadType(TTAdLoadType.LOAD) // 本次广告用途：TTAdLoadType.LOAD实时；TTAdLoadType.PRELOAD预请求
                .build();

        mAdLoadListener = new AdLoadListener(activity);

        mTTAdNative.loadFullScreenVideoAd(adSlot, mAdLoadListener);
    }

    public void showAd(){
        if (mAdLoadListener == null) {
            return;
        }

        mAdLoadListener.showAd(TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
    }
}
