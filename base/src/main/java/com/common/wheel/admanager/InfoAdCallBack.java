package com.common.wheel.admanager;

/**
 * 插屏回调
 */
public interface InfoAdCallBack {

    void onError();
    void onLoadSuccess();
    void onStartShow();
    void onAdShow();

    void onAdVideoBarClick();

    void onAdClose();

    void onVideoComplete();

    void onSkippedVideo();
}
