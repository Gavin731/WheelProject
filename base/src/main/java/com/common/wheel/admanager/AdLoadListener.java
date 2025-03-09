package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;

public class AdLoadListener implements TTAdNative.FullScreenVideoAdListener {

    private Activity context;

    private TTFullScreenVideoAd mAd;

    public AdLoadListener(Activity activity) {
        context = activity;
    }

    @Override
    public void onError(int i, String s) {
        LogUtils.e(s);
    }

    @Override
    public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
        LogUtils.i("AdLoadListener  广告类型：" + getAdType(ttFullScreenVideoAd.getFullVideoAdType()));
        handleAd(ttFullScreenVideoAd);
    }

    @Override
    public void onFullScreenVideoCached() {

    }

    @Override
    public void onFullScreenVideoCached(TTFullScreenVideoAd ttFullScreenVideoAd) {
        LogUtils.i("AdLoadListener 缓存已加载");
        handleAd(ttFullScreenVideoAd);
    }

    public void handleAd(TTFullScreenVideoAd ad) {
        if (mAd != null) {
            return;
        }
        mAd = ad;
        //【必须】广告展示时的生命周期监听

        mAd.setFullScreenVideoAdInteractionListener(new AdLifeListener(context));
        //【可选】监听下载状态
//        mAd.setDownloadListener(new DownloadStatusListener());
    }

    public void showAd(TTAdConstant.RitScenes ritScenes, String scenes) {
        if (mAd == null) {
            LogUtils.e("AdLoadListener 当前广告未加载好，请先加载广告");
            return;
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
}
