package com.common.wheel.admanager;

/**
 * 激励广告回调
 */
public interface RewardAdCallBack {

    void onAdClose();

    void onVideoComplete();

    void onAdVideoBarClick();
    void onVideoError();
    void onRewardArrived();
    void onSkippedVideo();
    void onAdShow();
    void onError();
}
