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

    private InterstitialAdManager() {
        mTTAdNative = AdvertisementManager.getInstance().getTTAdNative();
    }

    private void loadAd(Activity activity, String codeId, AdLoadListener.LoadSuccess loadSuccess) {
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

        AdLoadListener mAdLoadListener = new AdLoadListener(activity, loadSuccess);
        mTTAdNative.loadFullScreenVideoAd(adSlot, mAdLoadListener);
        adLoadListeners.add(mAdLoadListener);
    }

    protected void showAd(Activity activity, String codeId) {
        if (!adLoadListeners.isEmpty()) {
            AdLoadListener mAdLoadListener = adLoadListeners.get(0);
            mAdLoadListener.showAd(TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
            adLoadListeners.remove(0);
            loadAd(activity, codeId, null);
            return;
        }
        loadAd(activity, codeId, new AdLoadListener.LoadSuccess() {
            @Override
            public void loadSuccess() {
                AdLoadListener mAdLoadListener = adLoadListeners.get(0);
                mAdLoadListener.showAd(TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                adLoadListeners.remove(0);
                loadAd(activity, codeId, null);
            }
        });
    }

    /**
     * 预加载
     *
     * @param activity
     * @param codeId
     */
    protected void preload(Activity activity, String codeId) {
        loadAd(activity, codeId, null);
    }
}
