package com.common.wheel.admanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.common.wheel.constans.ConstantsPath;
import com.common.wheel.entity.ConfigEntity;
import com.common.wheel.entity.TokenEntity;
import com.common.wheel.http.RxConsumerThrowable;
import com.common.wheel.http.RxObjectCode;
import com.common.wheel.http.RxObjectCodeFunction;
import com.common.wheel.http.entity.ResultBean;
import com.common.wheel.util.DeviceUtil;
import com.common.wheel.util.ExceptionUtil;
import com.common.wheel.util.GsonUtil;
import com.google.gson.internal.LinkedTreeMap;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class ApiService {

    @SuppressLint("CheckResult")
    protected static void requestTestHttp(Context context) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appkey", "13761304832");
        params.put("appsecret", "zt123456");
        params.put("orgClientCode", "TEST17");
        Apis.getBaseApi().addDevice(params)
                .subscribeOn(Schedulers.io())
                .map(new RxObjectCodeFunction<>(context, TokenEntity.class))
                .map(new Function<RxObjectCode<TokenEntity>, Boolean>() {
                    @Override
                    public Boolean apply(RxObjectCode<TokenEntity> machineEntityRxObjectCode) throws Exception {
                        TokenEntity result = machineEntityRxObjectCode.getObject();
                        Log.i("", "获取到的网络请求结果：" + GsonUtil.formatObjectToJson(result));
                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            Log.i("", "请求失败");
                            return;
                        }
                        Log.i("", "获取成功");
                    }
                }, new RxConsumerThrowable(context, "登录异常"));
    }

    protected static void getKey(Context context) {
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
                            AdvertisementManager.getInstance().setToken(result.getAppToken());
                            isPostEnvInfo(context);
                            requestConfig(context);
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
                    }
                });
    }

    /**
     * 是否上报信息
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void isPostEnvInfo(Context context) {
        HashMap<String, Object> params = new HashMap<>();

        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_sdk_report_config_query");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", AdvertisementManager.getInstance().getToken());
        requestParams.put("params", params);
        Apis.getBaseApi().zxzh_sdk_report_config_query(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultBean, Object>() {
                    @Override
                    public Object apply(ResultBean resultBean) throws Exception {
                        if (resultBean.getData() != null) {
                            String data = resultBean.getData().toString();
                            if("true".equals(data)){
                                postEnvInfo(context);
                            }
                        }
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
     * 手机信息上报
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void postEnvInfo(Context context) {
        boolean isUpload = Hawk.get("isUpload", false);
        if(isUpload){
            return;
        }

        boolean isRoot = DeviceUtil.isRoot();
        boolean isAdb = DeviceUtil.isAdb(context);
        boolean isDl = DeviceUtil.isDl(context);
        boolean isVpn = DeviceUtil.isVpnActive(context);
        String oaid = Hawk.get("oaid");
        String publicIP = Hawk.get("publicIP");

        HashMap<String, Object> params = new HashMap<>();
        params.put("tjType", "yxtj");
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
        requestParams.put("methodType", "zxzh_sdk_env_info");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", AdvertisementManager.getInstance().getToken());
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
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void requestConfig(Context context) {
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


        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_sdk_config_query");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", AdvertisementManager.getInstance().getToken());
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
                        return null;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "get configkey error:" + ExceptionUtil.getStackTrace(throwable));
                    }
                });
    }

    private static void writeConfig(Context context, List<ConfigEntity> configs) {
        Log.i("", "configkey:" + GsonUtil.formatObjectToJson(configs));
        ApiService.addLog(context,"info","获取的配置信息："+GsonUtil.formatObjectToJson(configs));
        for (ConfigEntity configEntity : configs) {
            switch (configEntity.getConfigKey()) {
                case ConstantsPath.global_ad_switch: // //全局广告开关
                    break;
                case ConstantsPath.splash_ad_switch: //开屏广告开关
                    break;
                case ConstantsPath.interstitial_ad_switch://插屏广告开关
                    break;
                case ConstantsPath.video_ad_switch://激励视频广告开关
                    break;
                case ConstantsPath.feeds_ad_switch://信息流广告开关
                    break;
                case ConstantsPath.interstitial_perss_ad_config://插屏广告诱导设置
                    if (configEntity.getConfigStatus()) {
                        Hawk.put(ConstantsPath.interstitial_perss_ad_config, true);
                        Hawk.put(ConstantsPath.interstitial_perss_ad_config_value, configEntity.getConfigValue());
                    } else {
                        Hawk.put(ConstantsPath.interstitial_perss_ad_config, false);
                    }
                    break;
                case ConstantsPath.splash_misclick_ad_config://开屏广告误点配置
                    break;
                case ConstantsPath.interstitial_misclick_ad_config://插屏广告误点配置
                    if (configEntity.getConfigStatus()) {
                        Hawk.put(ConstantsPath.interstitial_misclick_ad_config, true);
                        Hawk.put(ConstantsPath.interstitial_misclick_ad_config_value, configEntity.getConfigValue());
                    } else {
                        Hawk.put(ConstantsPath.interstitial_misclick_ad_config, false);
                    }
                    break;
                case ConstantsPath.video_misclick_ad_config://激励视频广告误点配置
                    break;
                case ConstantsPath.feeds_misclick_ad_config://信息流视频广告误点配置
                    if (configEntity.getConfigStatus()) {
                        Hawk.put(ConstantsPath.feeds_misclick_ad_config, true);
                        Hawk.put(ConstantsPath.feeds_misclick_ad_config_value, configEntity.getConfigValue());
                    } else {
                        Hawk.put(ConstantsPath.feeds_misclick_ad_config, false);
                    }
                    break;
                case ConstantsPath.perss_img_url:
                    if (configEntity.getConfigStatus()) {
                        Hawk.put(ConstantsPath.perss_img_url_value, configEntity.getConfigValue());
                    }
                    break;
                case ConstantsPath.union_ad_switch:
                    break;
                case ConstantsPath.valid_user_flag:
                    Hawk.put(ConstantsPath.valid_user_flag_value, configEntity.getConfigValue());
                    break;
                case ConstantsPath.is_upload_log:
                    Hawk.put(ConstantsPath.is_upload_log_value, configEntity.getConfigStatus());
                    break;
            }
        }


    }

    /**
     * 点击信息上报
     * @param context
     */
    @SuppressLint("CheckResult")
    protected static void postAdInfo(Context context, HashMap<String, String> adInfo) {
        String oaid = Hawk.get("oaid");

        Log.i("", "start postAdInfo");
        HashMap<String, String> params = new HashMap<>();
        params.put("adPlatform", adInfo.get("adPlatform")); // 广告平台（见平台枚举）
        params.put("adType", adInfo.get("adType"));// 广告类型（见类型枚举）
        params.put("ecpm", adInfo.get("ecpm"));
        params.put("oaid", oaid); // 设备OAID
        params.put("adPosition", adInfo.get("adPosition")); // 广告位标识
        params.put("clickType", adInfo.get("clickType")); // 点击类型（见点击类型枚举）
        params.put("deviceId", DeviceUtil.getUUID(context));
        params.put("userId", "");

        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_sdk_ad_click_info");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", AdvertisementManager.getInstance().getToken());
        requestParams.put("params", params);

        Log.i("", "ad upload info:" + GsonUtil.formatObjectToJson(requestParams));
        Apis.getBaseApi().zxzh_sdk_ad_click_info(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultBean, Object>() {
                    @Override
                    public Object apply(ResultBean resultBean) throws Exception {
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

    protected static void addLog(Context context, String type, String content) {
        String oaid = Hawk.get("oaid");
        boolean is_upload_log = Hawk.get(ConstantsPath.is_upload_log_value, false);
        if(!is_upload_log){
            Log.i("","上传日志配置没开，不上传");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("oaid", oaid);
        params.put("eventType", type);
        params.put("eventName", "插屏");
        params.put("eventValue", content);

        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodType", "zxzh_sdk_event_tracking");
        requestParams.put("appName", context.getPackageName());
        requestParams.put("appToken", AdvertisementManager.getInstance().getToken());
        requestParams.put("params", params);

        Apis.getBaseApi().zxzh_sdk_event_tracking(requestParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultBean, Object>() {
                    @Override
                    public Object apply(ResultBean resultBean) throws Exception {
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
}
