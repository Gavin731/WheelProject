package com.common.wheel.admanager;

import android.app.Activity;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.CSJAdError;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.mediation.MediationConstant;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationSplashRequestInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationBaseManager;

public class OpenScreenAdManager {

    private static volatile OpenScreenAdManager instance;
    private final TTAdNative mTTAdNative;
    private String projectId;
    private String codeId;


    protected static OpenScreenAdManager getInstance() {
        if (instance == null) {
            synchronized (OpenScreenAdManager.class) {
                if (instance == null) {
                    instance = new OpenScreenAdManager();
                }
            }
        }
        return instance;
    }

    protected OpenScreenAdManager() {
        mTTAdNative = AdvertisementManager.getInstance().getTTAdNative();
    }

    private AdSlot buildSplashAdslot() {
        MediationSplashRequestInfo csjSplashRequestInfo = new MediationSplashRequestInfo(
                MediationConstant.ADN_PANGLE, // 穿山甲
                codeId, // adn开屏广告代码位Id，注意不是聚合广告位Id
                projectId,   // adn应用id，注意要跟初始化传入的保持一致
                ""   // adn没有appKey时，传入空即可
        ) {
        };


        return new AdSlot.Builder()
                .setCodeId(codeId) //广告位ID
//                .setImageAcceptedSize(widthPx, heightPx)
                .setMediationAdSlot(
                        new MediationAdSlot.Builder()
                                //将自定义兜底对象设置给AdSlot
                                .setMediationSplashRequestInfo(csjSplashRequestInfo)
                                .build())
                .build();
    }

    protected void loadSplashAd(Activity act, String appId, String codeId, FrameLayout splashContainer) {
        this.projectId = appId;
        this.codeId = codeId;
        mTTAdNative.loadSplashAd(buildSplashAdslot(), new TTAdNative.CSJSplashAdListener() {
            @Override
            public void onSplashLoadSuccess(CSJSplashAd csjSplashAd) {
                LogUtils.e("开屏广告加载成功");
            }

            @Override
            public void onSplashLoadFail(CSJAdError csjAdError) {
                //广告加载失败
                LogUtils.e("开屏广告加载失败：" + csjAdError.getMsg());
            }

            @Override
            public void onSplashRenderSuccess(CSJSplashAd csjSplashAd) {
                LogUtils.e("开屏广告渲染成功");
                //广告渲染成功，在此展示广告
                showSplashAd(csjSplashAd, splashContainer); //注 ：splashContainer为展示Banner广告的容器
            }

            @Override
            public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
                //广告渲染失败
                LogUtils.e("开屏广告渲染失败:" + csjAdError.getMsg());
            }
        }, 3500);
    }

    private void showSplashAd(CSJSplashAd splashAd, FrameLayout container) {
        if (splashAd == null || container == null) {
            return;
        }

        splashAd.setSplashAdListener(new CSJSplashAd.SplashAdListener() {
            @Override
            public void onSplashAdShow(CSJSplashAd csjSplashAd) {
                //广告展示
                //获取展示广告相关信息，需要再show回调之后进行获取
                MediationBaseManager manager = splashAd.getMediationManager();
                if (manager != null && manager.getShowEcpm() != null) {
                    MediationAdEcpmInfo showEcpm = manager.getShowEcpm();
                    String ecpm = showEcpm.getEcpm(); //展示广告的价格
                    String sdkName = showEcpm.getSdkName();  //展示广告的adn名称
                    String slotId = showEcpm.getSlotId(); //展示广告的代码位ID
                }
            }

            @Override
            public void onSplashAdClick(CSJSplashAd csjSplashAd) {
                //广告点击
            }

            @Override
            public void onSplashAdClose(CSJSplashAd csjSplashAd, int i) {
                //广告关闭
            }
        });
        splashAd.showSplashView(container);//展示开屏广告
    }
}
