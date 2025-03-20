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

public class OpenScreenAdManager implements TTAdNative.CSJSplashAdListener, CSJSplashAd.SplashAdListener {

    private static volatile OpenScreenAdManager instance;
    private final TTAdNative mTTAdNative;
    private OpenScreenAdCallBack callBack;
    private Activity activity;
    private FrameLayout splashContainer;
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

    private OpenScreenAdManager() {
        mTTAdNative = AdvertisementManager.getInstance().getTTAdNative();
    }

    private AdSlot buildSplashAdslot(int width, int height) {
        MediationSplashRequestInfo csjSplashRequestInfo = new MediationSplashRequestInfo(
                MediationConstant.ADN_PANGLE, // 穿山甲
                codeId, // adn开屏广告代码位Id，注意不是聚合广告位Id
                projectId,   // adn应用id，注意要跟初始化传入的保持一致
                ""   // adn没有appKey时，传入空即可
        ) {
        };


        return new AdSlot.Builder()
                .setCodeId(codeId) //广告位ID
                .setImageAcceptedSize(width, height)
                .setMediationAdSlot(
                        new MediationAdSlot.Builder()
                                //将自定义兜底对象设置给AdSlot
                                .setMediationSplashRequestInfo(csjSplashRequestInfo)
                                .build())
                .build();
    }

    protected void loadSplashAd(Activity act, String appId, String codeId, FrameLayout splashContainer, int width, int height, OpenScreenAdCallBack callBack) {
        this.projectId = appId;
        this.codeId = codeId;
        this.callBack = callBack;
        this.activity = act;
        this.splashContainer = splashContainer;
        mTTAdNative.loadSplashAd(buildSplashAdslot(width, height), this, 3500);
    }

    private void showSplashAd(CSJSplashAd splashAd, FrameLayout container) {
        if (splashAd == null || container == null) {
            return;
        }
        container.removeAllViews();
        splashAd.setSplashAdListener(this);
        splashAd.showSplashView(container);//展示开屏广告
    }

    @Override
    public void onSplashLoadSuccess(CSJSplashAd csjSplashAd) {

    }

    @Override
    public void onSplashLoadFail(CSJAdError csjAdError) {
        LogUtils.e("广告加载失败：" + csjAdError.getMsg());
    }

    @Override
    public void onSplashRenderSuccess(CSJSplashAd csjSplashAd) {
        showSplashAd(csjSplashAd, splashContainer);
    }

    @Override
    public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
        LogUtils.e("广告渲染失败:" + csjAdError.getMsg());
    }

    @Override
    public void onSplashAdShow(CSJSplashAd csjSplashAd) {
        MediationBaseManager manager = csjSplashAd.getMediationManager();
        if (manager != null && manager.getShowEcpm() != null) {
            MediationAdEcpmInfo showEcpm = manager.getShowEcpm();
            String ecpm = showEcpm.getEcpm(); //展示广告的价格
            String sdkName = showEcpm.getSdkName();  //展示广告的adn名称
            String slotId = showEcpm.getSlotId(); //展示广告的代码位ID
        }
    }

    @Override
    public void onSplashAdClick(CSJSplashAd csjSplashAd) {
        LogUtils.i("广告被点击");
    }

    @Override
    public void onSplashAdClose(CSJSplashAd csjSplashAd, int i) {
        if (callBack != null) {
            callBack.onAdClose();
        }
        csjSplashAd.getMediationManager().destroy();
    }
}
