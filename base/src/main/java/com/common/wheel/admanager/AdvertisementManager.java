package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTCustomController;
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig;

public class AdvertisementManager {
    private static volatile AdvertisementManager instance;
    private static final String TAG = "AdvertisementManager";

    private static boolean sInit;
    private static boolean sStart;

    private AdvertisementManager() {
    }

    public static AdvertisementManager getInstance() {
        if (instance == null) {
            synchronized (AdvertisementManager.class) {
                if (instance == null) {
                    instance = new AdvertisementManager();
                }
            }
        }
        return instance;
    }

    public TTAdManager get() {
        return TTAdSdk.getAdManager();
    }

    public void requestPermissionIfNecessary(Context context){
        get().requestPermissionIfNecessary(context);
    }

    public void init(Context context) {
        doInit(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void doInit(Context context) {
        if (sInit) {
            LogUtils.i(TAG+"已经初始化过了");
            return;
        }

        TTAdSdk.init(context, buildConfig(context));
        TTAdSdk.start(new TTAdSdk.Callback() {
            @Override
            public void success() {
                sInit = true;
                //初始化成功
                //在初始化成功回调之后进行广告加载
                Log.e(TAG,"初始化成功");
            }

            @Override
            public void fail(int i, String s) {
                //初始化失败
                Log.e(TAG,"初始化失败");
            }
        });
    }

    /**
     * 获取配置
     *
     * @param context
     * @return
     */
    private TTAdConfig buildConfig(Context context) {

        return new TTAdConfig.Builder()
                /**
                 * 注：需要替换成在媒体平台申请的appID ，切勿直接复制
                 */
                .appId("5558135")
                .appName("剧多影视大全")
                /**
                 * 上线前需要关闭debug开关，否则会影响性能
                 */
                .debug(true)
                /**
                 * 使用聚合功能此开关必须设置为true，默认为false
                 */
                .useMediation(true)
                .customController(getTTCustomController())  //设置隐私权
//                .customController(getTTCustomController()) //如果您需要设置隐私策略请参考该api
//                .setMediationConfig(new MediationConfig.Builder() //可设置聚合特有参数详细设置请参考该api
//                        .setMediationConfigUserInfoForSegment(getUserInfoForSegment())//如果您需要配置流量分组信息请参考该api
//                        .build())
                .build();
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
