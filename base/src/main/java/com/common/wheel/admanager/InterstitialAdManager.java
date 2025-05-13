package com.common.wheel.admanager;

import android.app.Activity;
import android.os.Handler;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.mediation.MediationConstant;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationSplashRequestInfo;

import java.util.ArrayList;
import java.util.List;


public class InterstitialAdManager {

    private static volatile InterstitialAdManager instance;
    private final TTAdNative mTTAdNative;

    private final List<AdLoadListener> adLoadListeners = new ArrayList<>();

    private String projectId;

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
        MediationSplashRequestInfo csjSplashRequestInfo = new MediationSplashRequestInfo(
                MediationConstant.ADN_PANGLE, // 穿山甲
                codeId, // adn开屏广告代码位Id，注意不是聚合广告位Id
                projectId,   // adn应用id，注意要跟初始化传入的保持一致
                ""   // adn没有appKey时，传入空即可
        ) {
        };

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) // 广告代码位Id
                .setOrientation(TTAdConstant.VERTICAL)  //设置方向
                .setAdLoadType(TTAdLoadType.LOAD) // 本次广告用途：TTAdLoadType.LOAD实时；TTAdLoadType.PRELOAD预请求
                .setMediationAdSlot(
                        new MediationAdSlot.Builder()
                                .setMuted(true)
                                //将自定义兜底对象设置给AdSlot
                                .setMediationSplashRequestInfo(csjSplashRequestInfo)
                                .build()
                )
                .build();

        AdLoadListener mAdLoadListener = new AdLoadListener(activity, loadSuccess);
        mTTAdNative.loadFullScreenVideoAd(adSlot, mAdLoadListener);
        adLoadListeners.add(mAdLoadListener);
    }

    protected void showAd(Activity activity,String appId, String codeId) {
        this.projectId = appId;
        if (!adLoadListeners.isEmpty()) {
            show(activity, codeId);
            return;
        }
        loadAd(activity, codeId, new AdLoadListener.LoadSuccess() {
            @Override
            public void loadSuccess() {
                show(activity, codeId);
            }
        });
    }

    private void show(Activity activity, String codeId) {
        AdLoadListener mAdLoadListener = adLoadListeners.get(0);
        mAdLoadListener.showAd(TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adLoadListeners.remove(0);
                loadAd(activity, codeId, null);
            }
        }, 500);
    }

    /**
     * 预加载
     * @param activity
     * @param appId
     * @param codeId
     */
    protected void preload(Activity activity,String appId, String codeId) {
        this.projectId = appId;
        loadAd(activity, codeId, null);
    }
}
