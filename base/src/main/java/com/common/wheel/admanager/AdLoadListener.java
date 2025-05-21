package com.common.wheel.admanager;

import android.app.Activity;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationBaseManager;

public class AdLoadListener implements TTAdNative.FullScreenVideoAdListener {

    private final Activity context;

    private TTFullScreenVideoAd mAd;
    private AdLoadListener.LoadSuccess loadSuccess;
    private InfoAdCallBack callback;

    protected AdLoadListener(Activity activity, AdLoadListener.LoadSuccess loadSuccess, InfoAdCallBack callback) {
        context = activity;
        this.loadSuccess = loadSuccess;
        this.callback = callback;
    }

    @Override
    public void onError(int i, String s) {
        Log.e("", s);
        if(callback!=null){
            callback.onError();
        }
    }

    @Override
    public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
        Log.i("", "AdLoadL  广告类型：" + getAdType(ttFullScreenVideoAd.getFullVideoAdType()));
        handleAd(ttFullScreenVideoAd);
    }

    @Override
    public void onFullScreenVideoCached() {

    }

    @Override
    public void onFullScreenVideoCached(TTFullScreenVideoAd ttFullScreenVideoAd) {
        handleAd(ttFullScreenVideoAd);
        if (loadSuccess != null) {
            loadSuccess.loadSuccess();
        }
    }

    public void handleAd(TTFullScreenVideoAd ad) {
        if(callback!=null){
            callback.onLoadSuccess();
        }
        if (mAd != null) {
            return;
        }
        mAd = ad;
        //【必须】广告展示时的生命周期监听

        mAd.setFullScreenVideoAdInteractionListener(new AdLifeListener(context, mAd, callback));
        //【可选】监听下载状态
//        mAd.setDownloadListener(new DownloadStatusListener());
        //广告展示
        MediationBaseManager manager = mAd.getMediationManager();
        //获取展示广告相关信息，需要再show回调之后进行获取
        if (manager != null && manager.getShowEcpm() != null) {
            MediationAdEcpmInfo showEcpm = manager.getShowEcpm();
            String ecpm = showEcpm.getEcpm(); //展示广告的价格
            String sdkName = showEcpm.getSdkName();  //展示广告的adn名称
            String slotId = showEcpm.getSlotId(); //展示广告的代码位ID
        }

    }

    protected void showAd(TTAdConstant.RitScenes ritScenes, String scenes) {
        if (mAd == null) {
            Log.i("", "AdLoadL mAd is null");
            return;
        }
        if(callback!=null){
            callback.onStartShow();
        }
        mAd.showFullScreenVideoAd(context, ritScenes, scenes);
        // 广告使用后应废弃
        mAd = null;
    }

    private static String getAdType(int type) {
        switch (type) {
            case TTAdConstant.AD_TYPE_COMMON_VIDEO:
                return "普通全屏视频，type=" + type;
            case TTAdConstant.AD_TYPE_PLAYABLE_VIDEO:
                return "Playable全屏视频，type=" + type;
            case TTAdConstant.AD_TYPE_PLAYABLE:
                return "纯Playable，type=" + type;
            case TTAdConstant.AD_TYPE_LIVE:
                return "直播流，type=" + type;
        }
        return "未知类型+type=" + type;
    }

    protected interface LoadSuccess {
        void loadSuccess();
    }
}
