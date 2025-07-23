package com.common.wheel.admanager;

/**
 * 信息流回调
 */
public interface InformationFlowAdCallback {

    void onError();
    void onFeedAdLoad();
    void onRenderSuccess();
    void onAdClick();
    void onRenderFail();
}
