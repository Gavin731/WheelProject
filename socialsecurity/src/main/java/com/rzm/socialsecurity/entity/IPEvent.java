package com.rzm.socialsecurity.entity;

import com.common.wheel.admanager.InitCallback;

public class IPEvent {


    private String ipAddress;
    private InitCallback initCallback;

    public IPEvent(String ipAddress, InitCallback initCallback){
        this.ipAddress = ipAddress;
        this.initCallback=initCallback;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public InitCallback getInitCallback() {
        return initCallback;
    }

    public void setInitCallback(InitCallback initCallback) {
        this.initCallback = initCallback;
    }
}
