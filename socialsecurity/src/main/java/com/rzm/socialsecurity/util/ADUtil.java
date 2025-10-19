package com.rzm.socialsecurity.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.TTCustomController;
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.Apis;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.InformationFlowManager;
import com.common.wheel.admanager.InitCallback;
import com.common.wheel.admanager.InterstitialAdManager;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.admanager.OpenScreenAdManager;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.admanager.RewardAdManager;
import com.common.wheel.constans.ConstantsPath;
import com.common.wheel.entity.ConfigEntity;
import com.common.wheel.entity.TokenEntity;
import com.common.wheel.http.RxObjectCode;
import com.common.wheel.http.RxObjectCodeFunction;
import com.common.wheel.http.entity.ResultBean;
import com.common.wheel.util.DeviceUtil;
import com.common.wheel.util.ExceptionUtil;
import com.common.wheel.util.GsonUtil;
import com.google.gson.internal.LinkedTreeMap;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.BuildConfig;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.IPEvent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.listener.OnGetOaidListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ADUtil {

    public static String TAG = "ADUtil";

    public static void getKey(Context context, InitCallback callback) {

        String token = Hawk.get("token");
        if(!TextUtils.isEmpty(token)){
            isPostEnvInfo(context, callback);
            return;
        }
        LogUtils.e("aaaaa_开始获取token");
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_app_token_apply");
        requestParams.put("appName", context.getPackageName());
        Apis.getBaseApi().zxzh_app_token_apply(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new RxObjectCodeFunction<>(context, TokenEntity.class))
                .map(new Function<RxObjectCode<TokenEntity>, Boolean>() {
                    @Override
                    public Boolean apply(RxObjectCode<TokenEntity> tokenEntityRxObjectCode) throws Exception {
                        TokenEntity result = tokenEntityRxObjectCode.getObject();
                        if (!TextUtils.isEmpty(result.getAppToken())) {
                            Hawk.put("token", result.getAppToken());
                            isPostEnvInfo(context, callback);
                        }else{
                            LogUtils.e("aaaaa_开始获取token为空");
                            callback.error();
                        }
                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "token error:" + ExceptionUtil.getStackTrace(throwable));
//                        callback.error();
                        LogUtils.e("aaaaa_开始获取token error");
                    }
                });
    }

    /**
     * 是否上报信息
     *
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void isPostEnvInfo(Context context, InitCallback callback) {
        LogUtils.e("aaaaa_开始获取是否上报");
        String token = Hawk.get("token");
        HashMap<String, Object> params = new HashMap<>();
        params.put("appVersion", BuildConfig.VERSION_NAME);

        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_sdk_report_config_query");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", token);

        requestParams.put("params", params);

        boolean isShowUserPrivacy = Hawk.get(ConstantConfig.isAgreeUserPrivacy, false);

        Apis.getBaseApi().zxzh_sdk_report_config_query(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultBean, Object>() {
                    @Override
                    public Object apply(ResultBean resultBean) throws Exception {
                        if (resultBean.getData() != null) {
                            String data = resultBean.getData().toString();
                            Hawk.put(ConstantConfig.userEnv, data);
                            LogUtils.e("aaaaa_开始获取是否上报结果"+data);
                            if ("true".equals(data) || isShowUserPrivacy) {
                                initAdManager(context, callback);
                            }else{
                                callback.error();
                            }
                        }
                        return true;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("aaaaa_开始获取是否上报 error");
                    }
                });
    }


    /**
     * 手机信息上报
     *
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void postEnvInfo(Context context) {
        boolean isUpload = Hawk.get("isUpload", false);
        if (isUpload) {
            return;
        }

        String token = Hawk.get("token");

        boolean isRoot = DeviceUtil.isRoot();
        boolean isAdb = DeviceUtil.isAdb(context);
        boolean isDl = DeviceUtil.isDl(context);
        boolean isVpn = DeviceUtil.isVpnActive(context);
        String oaid = Hawk.get("oaid");
        String publicIP = Hawk.get("publicIP");

        HashMap<String, Object> params = new HashMap<>();
        params.put("tjType", "ystj");
        params.put("deviceId", DeviceUtil.getUUID(context));
        params.put("osVersion", DeviceUtil.getSystemVersion());
        params.put("imei", DeviceUtil.getImei(context));
        params.put("androidId", DeviceUtil.getAndroidId(context));
        params.put("oaid", oaid);
        params.put("meid", DeviceUtil.getMeId(context));
        params.put("mac", DeviceUtil.getMac(context));
        params.put("systemInfo", DeviceUtil.getSystem());
        params.put("ipAddress", TextUtils.isEmpty(publicIP) ? DeviceUtil.getWifiIpAddress(context) : publicIP);
        params.put("simState", DeviceUtil.hasSimCard(context) ? "5" : "");
        params.put("MANUFACTURER", DeviceUtil.getManufacturer());
        params.put("MODEL", Build.MODEL);
        params.put("BRAND", DeviceUtil.getSystem());
        params.put("BOARD", Build.PRODUCT);
        params.put("DEVICE", "");
        params.put("HARDWARE", Build.HARDWARE);
        params.put("OS_VERSION", "");
        params.put("SDK_INT", "");
        params.put("vpnState", isVpn ? 1 : 0);
        params.put("rootState", isRoot ? 1 : 0);
        params.put("adbState", isAdb ? 1 : 0);
        params.put("agentState", isDl ? 1 : 0);


        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_app_env_info");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", token);
        requestParams.put("params", params);
        Apis.getBaseApi().zxzh_sdk_env_info(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultBean, Object>() {
                    @Override
                    public Object apply(ResultBean resultBean) throws Exception {
                        Hawk.put("isUpload", true);
                        return null;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * 获取配置
     *
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void requestConfig(Context context) {
        String token = Hawk.get("token");
        boolean isRoot = DeviceUtil.isRoot();
        boolean isAdb = DeviceUtil.isAdb(context);
        boolean isDl = DeviceUtil.isDl(context);
        boolean isVpn = DeviceUtil.isVpnActive(context);
        String oaid = Hawk.get("oaid");
        String publicIP = Hawk.get("publicIP");

        HashMap<String, Object> params = new HashMap<>();
        params.put("imei", DeviceUtil.getImei(context));
        params.put("androidId", DeviceUtil.getAndroidId(context));
        params.put("oaid", oaid);
        params.put("meid", DeviceUtil.getMeId(context));
        params.put("mac", DeviceUtil.getMac(context));
        params.put("systemInfo", DeviceUtil.getSystem());
        params.put("ipAddress", TextUtils.isEmpty(publicIP) ? DeviceUtil.getWifiIpAddress(context) : publicIP);
        params.put("simState", DeviceUtil.hasSimCard(context) ? "5" : "");
        params.put("vpnState", isVpn ? 1 : 0);
        params.put("rootState", isRoot ? 1 : 0);
        params.put("adbState", isAdb ? 1 : 0);
        params.put("agentState", isDl ? 1 : 0);
        params.put("appVersion", BuildConfig.VERSION_NAME);


        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_app_config_query");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", token);
        requestParams.put("params", params);
        Apis.getBaseApi().zxzh_sdk_config_query(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultBean, Object>() {
                    @Override
                    public Object apply(ResultBean resultBean) throws Exception {
                        if (resultBean.getData() != null) {
                            ArrayList<LinkedTreeMap<String, Object>> config = (ArrayList<LinkedTreeMap<String, Object>>) resultBean.getData();
                            List<ConfigEntity> configs = new ArrayList<>();

                            for (LinkedTreeMap<String, Object> map : config) {
                                ConfigEntity entity = new ConfigEntity();

                                String configKey = map.get("configKey") == null ? "" : map.get("configKey").toString();
                                boolean configStatus = map.get("configStatus") != null && (boolean) map.get("configStatus");
                                String configValue = map.get("configValue") == null ? "" : map.get("configValue").toString();
                                entity.setConfigKey(configKey);
                                entity.setConfigStatus(configStatus);
                                entity.setConfigValue(configValue);

                                configs.add(entity);
                            }
                            if (configs != null && configs.size() > 0) {
                                writeConfig(context, configs);
                            }
                        }
                        return true;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "app get configkey error:" + ExceptionUtil.getStackTrace(throwable));
                    }
                });
    }

    public static void initAdManager(Context context, InitCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ipAddress = AppDeviceUtil.getIp();
                IPEvent ipEvent=new IPEvent(ipAddress, callback);
                EventBus.getDefault().post(ipEvent);
            }
        }).start();
    }

    public static void initAd(Context context, String ipAddress, InitCallback callback){
        if (!Hawk.isBuilt()) {
            Hawk.init(context).build();
        }
        LogUtils.e("IP地址是：" + ipAddress);
        String oaid = Hawk.get(ConstantConfig.oaid);
        LogUtils.e("oaid原有地址是：" + oaid);
        if(!TextUtils.isEmpty(oaid)){
            AdvertisementManager.getInstance().init(context, ConstantConfig.AD_PROJECT, ConstantConfig.PROJECT_NAME, callback, getTTCustomController());
            AdvertisementManager.getInstance().initConfig(oaid, ipAddress, context.getResources().getString(R.string.app_url), BuildConfig.VERSION_NAME);
            // 获取app配置
            requestConfig(context);
            return;
        }
        UMConfigure.getOaid(context, new OnGetOaidListener() {
            @Override
            public void onGetOaid(String s) {
                LogUtils.e("oaid地址是：" + s);
                Hawk.put(ConstantConfig.oaid, s);
                AdvertisementManager.getInstance().init(context, ConstantConfig.AD_PROJECT, ConstantConfig.PROJECT_NAME, callback, getTTCustomController());
                AdvertisementManager.getInstance().initConfig(s, ipAddress, context.getResources().getString(R.string.app_url), BuildConfig.VERSION_NAME);
//                        postEnvInfo(context);
                // 获取app配置
                requestConfig(context);
            }
        });
    }


    private static void writeConfig(Context context, List<ConfigEntity> configs) {
        Log.i("", "app configkey:" + GsonUtil.formatObjectToJson(configs));
        for (ConfigEntity configEntity : configs) {
            switch (configEntity.getConfigKey()) {
                case ConstantsPath.global_ad_switch: // //全局广告开关
                    Hawk.put(ConstantsPath.is_global_ad_switch, configEntity.getConfigStatus());
                    break;
                case ConstantsPath.splash_ad_switch: //开屏广告开关
                    Hawk.put(ConstantsPath.is_splash_ad_switch, configEntity.getConfigStatus());
                    break;
                case ConstantsPath.interstitial_ad_switch://插屏广告开关
                    Hawk.put(ConstantsPath.is_interstitial_ad_switch, configEntity.getConfigStatus());
                    break;
                case ConstantsPath.video_ad_switch://激励视频广告开关
                    Hawk.put(ConstantsPath.is_video_ad_switch, configEntity.getConfigStatus());
                    break;
                case ConstantsPath.feeds_ad_switch://信息流广告开关
                    Hawk.put(ConstantsPath.is_feeds_ad_switch, configEntity.getConfigStatus());
                    break;
            }
        }


    }

    private static TTCustomController getTTCustomController() {
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

    /**
     * 插屏广告
     */
    public static void showInterstitialAd(Activity activity, String codeId, InfoAdCallBack callback) {
        boolean is_global_ad_switch = Hawk.get(ConstantsPath.is_global_ad_switch, false);
        if (!is_global_ad_switch) {
            callback.onAdClose();
            return;
        }
        boolean is_interstitial_ad_switch = Hawk.get(ConstantsPath.is_interstitial_ad_switch, false);
        if (!is_interstitial_ad_switch) {
            callback.onAdClose();
            return;
        }
        AdvertisementManager.getInstance().showInterstitialAd(activity, codeId, callback);
    }

    /**
     * 信息流广告
     */
    public static void showInfoFlowAd(Activity activity, String codeId, FrameLayout splashContainer, int width, int height, boolean isConfig, InformationFlowAdCallback callback) {
        boolean is_global_ad_switch = Hawk.get(ConstantsPath.is_global_ad_switch, false);
        if (!is_global_ad_switch) {
            callback.onError();
            return;
        }
        boolean is_feeds_ad_switch = Hawk.get(ConstantsPath.is_feeds_ad_switch, false);
        if (isConfig && !is_feeds_ad_switch) {
            callback.onError();
            return;
        }
        AdvertisementManager.getInstance().showInfoFlowAd(activity, codeId, splashContainer, width, height, callback);
    }

    /**
     * 开屏广告
     */
    public static void showOpenScreenAd(Activity act, String codeId, FrameLayout splashContainer, int width, int height, OpenScreenAdCallBack callBack) {
        boolean is_global_ad_switch = Hawk.get(ConstantsPath.is_global_ad_switch, false);
        if (!is_global_ad_switch) {
            callBack.onAdClose();
            return;
        }
        boolean is_splash_ad_switch = Hawk.get(ConstantsPath.is_splash_ad_switch, false);
        if (!is_splash_ad_switch) {
            callBack.onAdClose();
            return;
        }
        LogUtils.e("aaaaa_开始获取开屏广告2");
        AdvertisementManager.getInstance().showOpenScreenAd(act, codeId, splashContainer, width, height, callBack);
    }

    /**
     * 激励视频
     *
     * @param act
     * @param codeId
     * @param listener
     */
    public static void showRewardAd(Activity act, String codeId, RewardAdCallBack listener) {
        boolean is_global_ad_switch = Hawk.get(ConstantsPath.is_global_ad_switch, false);
        if (!is_global_ad_switch) {
            listener.onAdClose();
            return;
        }
        boolean is_video_ad_switch = Hawk.get(ConstantsPath.is_video_ad_switch, false);
        if (!is_video_ad_switch) {
            listener.onAdClose();
            return;
        }
        AdvertisementManager.getInstance().showRewardAd(act, codeId, listener);
    }
}
