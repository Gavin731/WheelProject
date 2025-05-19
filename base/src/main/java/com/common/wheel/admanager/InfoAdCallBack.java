package com.common.wheel.admanager;

/**
 * 插屏回调
 */
public interface InfoAdCallBack {

    void onAdShow();

    void onAdVideoBarClick();

    void onAdClose();

    void onVideoComplete();

    void onSkippedVideo();
}
