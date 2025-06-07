package com.common.wheel.admanager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.ComplianceInfo;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.mediation.MediationConstant;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationExpressRenderListener;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationSplashRequestInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationNativeManager;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;


public class InformationFlowManager implements TTAdNative.FeedAdListener, MediationExpressRenderListener {

    private static volatile InformationFlowManager instance;
    private final TTAdNative mTTAdNative;
    private TTFeedAd mTTFeedAd;

    private WeakReference<Activity> weakRef;
    private FrameLayout splashContainer;

    private String projectId;

    private InformationFlowAdCallback callback;

    protected static InformationFlowManager getInstance() {
        if (instance == null) {
            synchronized (InformationFlowManager.class) {
                if (instance == null) {
                    instance = new InformationFlowManager();
                }
            }
        }
        return instance;
    }

    private InformationFlowManager() {
        mTTAdNative = AdvertisementManager.getInstance().getTTAdNative();
    }

    private AdSlot buildNativeAdslot(String codeId, int width, int height) {
        MediationSplashRequestInfo csjSplashRequestInfo = new MediationSplashRequestInfo(
                MediationConstant.ADN_PANGLE, // 穿山甲
                codeId, // adn开屏广告代码位Id，注意不是聚合广告位Id
                projectId,   // adn应用id，注意要跟初始化传入的保持一致
                ""   // adn没有appKey时，传入空即可
        ) {
        };

        return new AdSlot.Builder()
                .setCodeId(codeId) //广告位ID
                /**
                 * 注：
                 *  1:单位为px
                 *  2:如果是信息流自渲染广告，设置广告图片期望的图片宽高 ，不能为0
                 *  2:如果是信息流模板广告，宽度设置为希望的宽度，高度设置为0(0为高度选择自适应参数)
                 */
                .setImageAcceptedSize(width, height)
                .setAdCount(1)//请求广告数量为1到3条 （优先采用平台配置的数量）
                .setMediationAdSlot(// 聚合广告请求配置
                        new MediationAdSlot.Builder()
                                //将自定义兜底对象设置给AdSlot
                                .setMediationSplashRequestInfo(csjSplashRequestInfo)
                                .setMuted(true)
                                .build())
                .build();
    }

    protected void loadNativeAd(Activity act, String appId, String codeId, FrameLayout splashContainer, int width, int height, InformationFlowAdCallback callback) {
        this.projectId = appId;
        this.weakRef = new WeakReference<>(act);
        this.splashContainer = splashContainer;
        this.callback = callback;
        AdSlot adSlot = buildNativeAdslot(codeId, width, height);
        mTTAdNative.loadFeedAd(adSlot, this);
    }

    @Override
    public void onError(int i, String s) {
        Log.e("", "ad load error：" + s);
        if(callback!=null){
            callback.onError();
        }
    }

    @Override
    public void onFeedAdLoad(List<TTFeedAd> list) {
        if(callback!=null){
            callback.onFeedAdLoad();
        }
        //如果是自渲染下载类广告可以通过以下api获取下载六要素
        if (list != null && list.size() > 0) {
            mTTFeedAd = list.get(0);
            ComplianceInfo complianceInfo = mTTFeedAd.getComplianceInfo();
            if (complianceInfo != null) {
                String appName = complianceInfo.getAppName(); //应用名称
                String appVersion = complianceInfo.getAppVersion(); //应用版本号
                String developerName = complianceInfo.getDeveloperName(); //开发者名称
                String privacyUrl = complianceInfo.getPrivacyUrl(); //隐私协议Url
                Map<String, String> permissionsMap = complianceInfo.getPermissionsMap(); //权限名称及权限描述列表
                String permissionUrl = complianceInfo.getPermissionUrl(); //权限列表url
                String functionDescUrl = complianceInfo.getFunctionDescUrl(); //应用功能url
            } else {
                //非下载类广告
            }
            mTTFeedAd.uploadDislikeEvent("mediation_dislike_event");
            /** 5、展示广告 */
            MediationNativeManager manager = mTTFeedAd.getMediationManager();
            if (manager != null) {
                if (manager.isExpress()) { // --- 模板feed流广告
                    mTTFeedAd.setExpressRenderListener(this);
                    mTTFeedAd.render(); // 调用render方法进行渲染，在onRenderSuccess中展示广告
                } else {                   // --- 自渲染feed流广告

                    // 自渲染广告返回的是广告素材，开发者自己将其渲染成view
//                            View feedView = FeedAdUtils.getFeedAdFromFeedInfo(mTTFeedAd, this, null, mAdInteractionListener);
//                            if (feedView != null) {
//                                UIUtils.removeFromParent(feedView);
//                                mFeedContainer.removeAllViews();
//                                mFeedContainer.addView(feedView);
//                            }
                }
            }
        }
    }

    @Override
    public void onRenderFail(View view, String s, int i) {
        if(callback!=null){
            callback.onRenderFail();
        }
    }

    @Override
    public void onAdClick() {
        if(callback!=null){
            callback.onAdClick();
        }
    }

    @Override
    public void onAdShow() {

    }

    @Override
    public void onRenderSuccess(View view, float v, float v1, boolean b) {
        Log.i("","信息流广告获取成功");
        if(weakRef == null || weakRef.get() == null){
            return;
        }
        Activity activity = weakRef.get();
        if(callback!=null){
            callback.onRenderSuccess();
        }
        if (mTTFeedAd != null) {
            MediationAdEcpmInfo item = mTTFeedAd.getMediationManager().getShowEcpm();
            ViewHelper.showAdUploadInfo(activity, item, "FEEDS");

            View expressFeedView = mTTFeedAd.getAdView(); // *** 注意不要使用onRenderSuccess参数中的view ***
            ViewHelper.renderInfoView(activity, splashContainer, expressFeedView, mTTFeedAd);
        }
    }
}
