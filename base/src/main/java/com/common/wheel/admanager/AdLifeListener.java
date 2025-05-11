package com.common.wheel.admanager;

import android.content.Context;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.orhanobut.hawk.Hawk;

import java.lang.ref.WeakReference;

public class AdLifeListener implements TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
    private final WeakReference<Context> mContextRef;
    private TTFullScreenVideoAd mAd;

    protected AdLifeListener(Context context, TTFullScreenVideoAd mAd) {
        mContextRef = new WeakReference<>(context);
        this.mAd = mAd;
    }

    @Override
    public void onAdShow() {
        ViewHelper.addInterstitialView(mAd);
    }

    @Override
    public void onAdVideoBarClick() {
        Log.i("", "adLs click");
    }

    @Override
    public void onAdClose() {
        Log.i("", "adLs close");
        ViewHelper.hideView();
    }

    @Override
    public void onVideoComplete() {
        Log.i("", "adLs complete");
    }

    @Override
    public void onSkippedVideo() {
        Log.i("", "adLs Skip");
    }


}
