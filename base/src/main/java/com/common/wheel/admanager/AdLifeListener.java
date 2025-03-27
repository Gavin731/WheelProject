package com.common.wheel.admanager;

import android.content.Context;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.orhanobut.hawk.Hawk;

import java.lang.ref.WeakReference;

public class AdLifeListener implements TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
    private final WeakReference<Context> mContextRef;

    protected AdLifeListener(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    public void onAdShow() {
        Hawk.put("adCount", "1");
        if (ViewHelper.isAddView(mContextRef.get(), 1)) {
            ViewHelper.addInterstitialView();
        }
    }

    @Override
    public void onAdVideoBarClick() {
        Log.i("", "adLs click");
    }

    @Override
    public void onAdClose() {
        Log.i("", "adLs close");
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
