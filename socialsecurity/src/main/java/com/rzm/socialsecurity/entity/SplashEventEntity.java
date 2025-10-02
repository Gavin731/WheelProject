package com.rzm.socialsecurity.entity;

public class SplashEventEntity {

    public SplashEventEntity(boolean init) {
        this.isInitAd = init;
    }

    private boolean isInitAd;

    public boolean isInitAd() {
        return isInitAd;
    }

    public void setInitAd(boolean initAd) {
        isInitAd = initAd;
    }
}
