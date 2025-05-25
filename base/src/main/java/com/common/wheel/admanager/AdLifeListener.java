package com.common.wheel.admanager;

import android.content.Context;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.orhanobut.hawk.Hawk;

import java.lang.ref.WeakReference;

public class AdLifeListener implements TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
    private final WeakReference<Context> mContextRef;
    private TTFullScreenVideoAd mAd;
    private InfoAdCallBack callback;

    protected AdLifeListener(Context context, TTFullScreenVideoAd mAd, InfoAdCallBack callback) {
        mContextRef = new WeakReference<>(context);
        this.mAd = mAd;
        this.callback = callback;
    }

    protected void setCallBack(InfoAdCallBack callback){
        this.callback = callback;
    }

    @Override
    public void onAdShow() {
        ViewHelper.addInterstitialView(mAd);
        if(callback!=null){
            callback.onAdShow();
        }
    }

    @Override
    public void onAdVideoBarClick() {
        Log.i("", "adLs click");
        if(callback!=null){
            callback.onAdVideoBarClick();
        }
    }

    @Override
    public void onAdClose() {
        Log.i("", "adLs close");
        ViewHelper.hideView();
        if(callback!=null){
            callback.onAdClose();
        }
    }

    @Override
    public void onVideoComplete() {
        Log.i("", "adLs complete");
        if(callback!=null){
            callback.onVideoComplete();
        }
    }

    @Override
    public void onSkippedVideo() {
        Log.i("", "adLs Skip");
        if(callback!=null){
            callback.onSkippedVideo();
        }
    }


}
