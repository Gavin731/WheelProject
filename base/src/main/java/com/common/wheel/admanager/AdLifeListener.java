package com.common.wheel.admanager;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
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
        LogUtils.i("adLs click");
    }

    @Override
    public void onAdClose() {
        LogUtils.i("adLs close");
    }

    @Override
    public void onVideoComplete() {
        LogUtils.i("adLs complete");
    }

    @Override
    public void onSkippedVideo() {
        LogUtils.i("adLs Skip");
    }


}
