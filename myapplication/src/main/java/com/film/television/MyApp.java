package com.film.television;

import android.app.Application;

import com.bytedance.sdk.openadsdk.TTCustomController;
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InitCallback;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdvertisementManager.getInstance().init(this, "5670955", "终端测试软件", new InitCallback() {
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        }, getTTCustomController());
        AdvertisementManager.getInstance().initConfig("297ca176-e8ad-40e7-b39e-0926e16bf166", "192.168.1.0");
    }

    private TTCustomController getTTCustomController() {
        return new TTCustomController() {
            @Override
            public boolean isCanUseLocation() {  //是否授权位置权限
                return true;
            }

            @Override
            public boolean isCanUsePhoneState() {  //是否授权手机信息权限
                return true;
            }

            @Override
            public boolean isCanUseWifiState() {  //是否授权wifi state权限
                return true;
            }

            @Override
            public boolean isCanUseWriteExternal() {  //是否授权写外部存储权限
                return true;
            }

            @Override
            public boolean isCanUseAndroidId() {  //是否授权Android Id权限
                return true;
            }

            @Override
            public MediationPrivacyConfig getMediationPrivacyConfig() {
                return new MediationPrivacyConfig() {
                    @Override
                    public boolean isLimitPersonalAds() {  //是否限制个性化广告
                        return false;
                    }

                    @Override
                    public boolean isProgrammaticRecommend() {  //是否开启程序化广告推荐
                        return true;
                    }
                };
            }
        };
    }
}
