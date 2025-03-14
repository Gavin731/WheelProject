package com.common.wheel.admanager;

import android.app.Activity;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;

import java.util.ArrayList;
import java.util.List;


public class InterstitialAdManager {

    private static volatile InterstitialAdManager instance;
    private final TTAdNative mTTAdNative;

    private final List<AdLoadListener> adLoadListeners = new ArrayList<>();


    protected static InterstitialAdManager getInstance() {
        if (instance == null) {
            synchronized (InterstitialAdManager.class) {
                if (instance == null) {
                    instance = new InterstitialAdManager();
                }
            }
        }
        return instance;
    }

    protected InterstitialAdManager() {
        mTTAdNative = AdvertisementManager.getInstance().getTTAdNative();
    }

    protected void loadAd(Activity activity, String codeId) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) // 广告代码位Id
                .setOrientation(TTAdConstant.VERTICAL)  //设置方向
                .setAdLoadType(TTAdLoadType.LOAD) // 本次广告用途：TTAdLoadType.LOAD实时；TTAdLoadType.PRELOAD预请求
                .setMediationAdSlot(
                        new MediationAdSlot.Builder()
                                .setMuted(false)
                                .build()
                )
                .build();

        AdLoadListener mAdLoadListener = new AdLoadListener(activity);
        mTTAdNative.loadFullScreenVideoAd(adSlot, mAdLoadListener);
        adLoadListeners.add(mAdLoadListener);
    }

    protected void showAd(Activity activity, String codeId) {
        if (!adLoadListeners.isEmpty()) {
            AdLoadListener mAdLoadListener = adLoadListeners.get(0);
            mAdLoadListener.showAd(TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
            adLoadListeners.remove(0);
            return;
        }
        loadAd(activity, codeId);
    }
}
