package com.rzm.socialsecurity.entity;

public class ShowInfoAdEvent {

    public ShowInfoAdEvent(boolean r) {
        this.isShowAd = r;
    }

    private boolean isShowAd;

    public boolean isShowAd() {
        return isShowAd;
    }

    public void setShowAd(boolean showAd) {
        isShowAd = showAd;
    }
}
