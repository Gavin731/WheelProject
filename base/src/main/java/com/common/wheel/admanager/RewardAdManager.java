package com.common.wheel.admanager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationBaseManager;

public class RewardAdManager {

    private static volatile RewardAdManager instance;
    private final TTAdNative mTTAdNative;

    private RewardAdCallBack listener;

    protected static RewardAdManager getInstance() {
        if (instance == null) {
            synchronized (RewardAdManager.class) {
                if (instance == null) {
                    instance = new RewardAdManager();
                }
            }
        }
        return instance;
    }

    private RewardAdManager() {
        mTTAdNative = AdvertisementManager.getInstance().getTTAdNative();
    }

    private AdSlot buildSplashAdslot(String codeId) {
        return new AdSlot.Builder()
                .setCodeId(codeId)  //广告位ID
                .setOrientation(TTAdConstant.VERTICAL)  //激励视频方向
                .setMediationAdSlot(
                        new MediationAdSlot.Builder()
                                .setMuted(false)
//                                .setRewardName("观看")
//                                .setRewardAmount(1)
                                .build()
                )
                .build();
    }

    //加载激励视频
    protected void loadRewardAd(Activity act, String codeId, RewardAdCallBack listener) {
        this.listener = listener;
        /** 这里为激励视频的简单功能，如需使用复杂功能，如gromore的服务端奖励验证，请参考demo中的AdUtils.kt类中激励部分 */
        mTTAdNative.loadRewardVideoAd(buildSplashAdslot(codeId), new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int errorCode, String errorMsg) {
                //广告加载失败
                Log.e("", errorMsg);
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                //广告加载成功
            }

            @Override
            public void onRewardVideoCached() {
                //广告缓存成功 此api已经废弃，请使用onRewardVideoCached(TTRewardVideoAd ttRewardVideoAd)
            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ttRewardVideoAd) {
                //广告缓存成功 在此回调中进行广告展示
                showRewardAd(act, ttRewardVideoAd);
            }
        });
    }

    //展示激励视频
    private void showRewardAd(Activity act, TTRewardVideoAd ttRewardVideoAd) {
        if (act == null || ttRewardVideoAd == null) {
            return;
        }

        ttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
            @Override
            public void onAdShow() {
                //广告展示
                //获取展示广告相关信息，需要再show回调之后进行获取
                MediationBaseManager manager = ttRewardVideoAd.getMediationManager();
                if (manager != null && manager.getShowEcpm() != null) {
                    MediationAdEcpmInfo showEcpm = manager.getShowEcpm();
                    String ecpm = showEcpm.getEcpm(); //展示广告的价格
                    String sdkName = showEcpm.getSdkName();  //展示广告的adn名称
                    String slotId = showEcpm.getSlotId(); //展示广告的代码位ID
                }
            }

            @Override
            public void onAdVideoBarClick() {
                if (listener != null) {
                    listener.onAdVideoBarClick();
                }
                //广告点击
                MediationBaseManager manager = ttRewardVideoAd.getMediationManager();
                if (manager != null && manager.getShowEcpm() != null) {
                    MediationAdEcpmInfo showEcpm = manager.getShowEcpm();
                    ViewHelper.logRewardEcpmInfo(act, showEcpm);
                }

            }

            @Override
            public void onAdClose() {
                //广告关闭
                if (listener != null) {
                    listener.onAdClose();
                }
            }

            @Override
            public void onVideoComplete() {
                //广告视频播放完成
                if (listener != null) {
                    listener.onVideoComplete();
                }
            }

            @Override
            public void onVideoError() {
                //广告视频错误
            }

            @Override
            public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, int errorCode, String errorMsg) {
                //奖励发放 已废弃 请使用 onRewardArrived 替代
            }

            @Override
            public void onRewardArrived(boolean isRewardValid, int rewardType, Bundle extraInfo) {
                //奖励发放
                if (isRewardValid) {
                    // 验证通过
                    // 从extraInfo读取奖励信息
                } else {
                    // 未验证通过
                }
            }

            @Override
            public void onSkippedVideo() {
                //广告跳过
            }
        });
        ttRewardVideoAd.showRewardVideoAd(act); //展示激励视频
    }
}
