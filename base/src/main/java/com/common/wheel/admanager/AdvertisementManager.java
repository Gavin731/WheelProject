package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;

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
        sInit = true;
        LogUtils.i(TAG+"初始化成功");
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
                .appId("5001121")
                .appName("APP测试媒体")
                /**
                 * 上线前需要关闭debug开关，否则会影响性能
                 */
                .debug(true)
                /**
                 * 使用聚合功能此开关必须设置为true，默认为false
                 */
                .useMediation(true)
//                .customController(getTTCustomController()) //如果您需要设置隐私策略请参考该api
//                .setMediationConfig(new MediationConfig.Builder() //可设置聚合特有参数详细设置请参考该api
//                        .setMediationConfigUserInfoForSegment(getUserInfoForSegment())//如果您需要配置流量分组信息请参考该api
//                        .build())
                .build();
    }


}
