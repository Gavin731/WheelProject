package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.ComplianceInfo;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationExpressRenderListener;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationNativeManager;

import java.util.List;
import java.util.Map;

public class InformationFlowManager {

    private TTAdNative mTTAdNative;

    public InformationFlowManager(Context context) {
        TTAdManager ttAdManager = AdvertisementManager.getInstance().get();
        LogUtils.i("穿山甲sdk版本：" + ttAdManager.getSDKVersion());
        mTTAdNative = ttAdManager.createAdNative(context);
    }

    //构造信息流Adslot
    private AdSlot buildNativeAdslot() {
        return new AdSlot.Builder()
                .setCodeId("103401966") //广告位ID
                /**
                 * 注：
                 *  1:单位为px
                 *  2:如果是信息流自渲染广告，设置广告图片期望的图片宽高 ，不能为0
                 *  2:如果是信息流模板广告，宽度设置为希望的宽度，高度设置为0(0为高度选择自适应参数)
                 */
                .setImageAcceptedSize(200, 100)
                .setAdCount(1)//请求广告数量为1到3条 （优先采用平台配置的数量）
                .setMediationAdSlot(// 聚合广告请求配置
                        new MediationAdSlot.Builder()
                                .setMuted(false)
                                .build())
                .build();
    }

    //加载信息流广告
    public void loadNativeAd(Activity act, FrameLayout splashContainer) {
        mTTAdNative.loadFeedAd(buildNativeAdslot(), new TTAdNative.FeedAdListener() {
            private TTFeedAd mTTFeedAd;

            @Override
            public void onError(int erroCode, String errorMsg) {
                //广告加载失败
                LogUtils.e("信息广告加载失败：" + errorMsg);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                //广告加载成功
                //信息流广告渲染具体参考demo
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
                            mTTFeedAd.setExpressRenderListener(new MediationExpressRenderListener() {
                                @Override
                                public void onRenderSuccess(View view, float v, float v1, boolean b) {
                                    if (mTTFeedAd != null) {
                                        View expressFeedView = mTTFeedAd.getAdView(); // *** 注意不要使用onRenderSuccess参数中的view ***
//                                        UIUtils.removeFromParent(expressFeedView);
                                        splashContainer.removeAllViews();
                                        splashContainer.addView(expressFeedView);
                                    }
                                }

                                @Override
                                public void onRenderFail(View view, String s, int i) {

                                }

                                @Override
                                public void onAdClick() {

                                }

                                @Override
                                public void onAdShow() {

                                }
                            });
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
        });
    }
}
