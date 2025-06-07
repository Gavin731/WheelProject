package com.common.wheel.admanager;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.CSJAdError;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.mediation.MediationConstant;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationSplashRequestInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationBaseManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class OpenScreenAdManager implements TTAdNative.CSJSplashAdListener, CSJSplashAd.SplashAdListener {

    private static volatile OpenScreenAdManager instance;
    private final TTAdNative mTTAdNative;
    private OpenScreenAdCallBack callBack;
    private WeakReference<Activity> weakRef;
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
        this.weakRef = new WeakReference<>(act);
        this.splashContainer = splashContainer;
        mTTAdNative.loadSplashAd(buildSplashAdslot(width, height), this, 3500);
    }

    private void showSplashAd(CSJSplashAd splashAd, FrameLayout container) {
        if (splashAd == null || container == null) {
            return;
        }
        MediationAdEcpmInfo item = splashAd.getMediationManager().getShowEcpm();
        ViewHelper.showAdUploadInfo(weakRef.get(), item, "SPLASH");

        container.removeAllViews();
        splashAd.setSplashAdListener(this);
        splashAd.showSplashView(container);//展示开屏广告
    }

    @Override
    public void onSplashLoadSuccess(CSJSplashAd csjSplashAd) {

    }

    @Override
    public void onSplashLoadFail(CSJAdError csjAdError) {
        Log.e("", "open ad load fail：" + csjAdError.getMsg());
        if (callBack != null) {
            callBack.onSplashLoadFail();
        }
    }

    @Override
    public void onSplashRenderSuccess(CSJSplashAd csjSplashAd) {
        showSplashAd(csjSplashAd, splashContainer);
    }

    @Override
    public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
        Log.e("", "open ad render fail:" + csjAdError.getMsg());
        if (callBack != null) {
            callBack.onSplashRenderFail();
        }
    }

    @Override
    public void onSplashAdShow(CSJSplashAd csjSplashAd) {
        if (callBack != null) {
            callBack.onSplashAdShow();
        }
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
        if (callBack != null) {
            callBack.onSplashAdClick();
        }
        Log.i("", "open ad click");
        MediationBaseManager mediationManager = csjSplashAd.getMediationManager();
        if (mediationManager != null) {
            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
            if (showEcpm != null) {
                logEcpmInfo(showEcpm);
            }
        }
    }

    @Override
    public void onSplashAdClose(CSJSplashAd csjSplashAd, int i) {
        if (callBack != null) {
            callBack.onAdClose();
        }
        csjSplashAd.getMediationManager().destroy();
    }

    private void logEcpmInfo(MediationAdEcpmInfo item) {
        if(weakRef == null || weakRef.get() == null){
            return;
        }
        Activity activity = weakRef.get();
        HashMap<String, String> params = new HashMap<>();
        params.put("adPlatform", item.getChannel()); // 广告平台（见平台枚举）
        params.put("adType", "SPLASH");// 广告类型（见类型枚举）
        params.put("ecpm", item.getEcpm());
        params.put("adPosition", item.getSlotId()); // 广告位标识
        params.put("clickType", "MANUAL_CLICK"); // 点击类型（见点击类型枚举）
        params.put("userId", "");
        ApiService.postAdInfo(activity, params);
//        Log.d(Const.TAG, "EcpmInfo: \n" +
//                "SdkName: " + item.getSdkName() + ",\n" +
//                "CustomSdkName: " + item.getCustomSdkName() + ",\n" +
//                "SlotId: " + item.getSlotId() + ",\n" +
//                // 单位：分；一般情况下兜底代码位的ecpm是0，若获取到的ecpm为0的话，可优先核实是否是兜底代码位
//                "Ecpm: " + item.getEcpm() + ",\n" +
//                "ReqBiddingType: " + item.getReqBiddingType() + ",\n" +
//                "ErrorMsg: " + item.getErrorMsg() + ",\n" +
//                "RequestId: " + item.getRequestId() + ",\n" +
//                "RitType: " + item.getRitType() + ",\n" +
//                "AbTestId: " + item.getAbTestId() + ",\n" +
//                "ScenarioId: " + item.getScenarioId() + ",\n" +
//                "SegmentId: " + item.getSegmentId() + ",\n" +
//                "Channel: " + item.getChannel() + ",\n" +
//                "SubChannel: " + item.getSubChannel() + ",\n" +
//                "customData: " + item.getCustomData()
//        );
    }
}
