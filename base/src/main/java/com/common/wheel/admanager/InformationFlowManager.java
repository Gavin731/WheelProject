package com.common.wheel.admanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.ComplianceInfo;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationExpressRenderListener;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationNativeManager;

import java.util.List;
import java.util.Map;


public class InformationFlowManager {

    private static volatile InformationFlowManager instance;
    private final TTAdNative mTTAdNative;

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
                                .setMuted(false)
                                .build())
                .build();
    }

    protected void loadNativeAd(Activity act, String codeId, FrameLayout splashContainer, int width, int height) {
        AdSlot adSlot = buildNativeAdslot(codeId, width, height);
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
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
//                                        expressFeedView.setOnTouchListener(new View.OnTouchListener() {
//                                            @SuppressLint("ClickableViewAccessibility")
//                                            @Override
//                                            public boolean onTouch(View v, MotionEvent event) {
//                                                boolean isClick = false;
//                                                switch (event.getAction()) {
//                                                    case MotionEvent.ACTION_DOWN:
//                                                        if (!isClick) {
//                                                            isClick = true;
//                                                            expressFeedView.performClick();
////                                                            ClickViewUtil.openClick((ViewGroup) expressFeedView);
//                                                        }
//                                                        break;
//                                                    case MotionEvent.ACTION_MOVE:
//
//                                                        break;
//                                                    case MotionEvent.ACTION_UP:
//                                                        break;
//                                                }
//                                                return false; // 返回 true 表示消费事件
//                                            }
//                                        });
//                                        splashContainer.setOnTouchListener((v2, event) -> {
//                                            boolean isClick = false;
//                                            switch (event.getAction()) {
//                                                case MotionEvent.ACTION_DOWN:
//                                                    break;
//                                                case MotionEvent.ACTION_MOVE:
//                                                    if (!isClick) {
//                                                        isClick = true;
////                                                        expressFeedView.performClick();
//                                                        ClickViewUtil.openClick((ViewGroup) expressFeedView);
//                                                    }
//                                                    break;
//                                                case MotionEvent.ACTION_UP:
//                                                    break;
//                                            }
//                                            return true; // 返回 true 表示消费事件
//                                        });

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
