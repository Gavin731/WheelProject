package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTCustomController;
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig;
import com.common.wheel.R;
import com.common.wheel.constans.ConstantsPath;
import com.orhanobut.hawk.Hawk;

public class AdvertisementManager {
    private static volatile AdvertisementManager instance;
    private static final String TAG = "AdvertisementManager";

    private boolean sInit;
    private boolean sStart;
    private String projectId;
    private String projectName;
    private String token;
    private Context context;

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

    protected TTAdNative getTTAdNative(Activity act) {
        TTAdManager ttAdManager = get();
        return ttAdManager.createAdNative(act);
    }

    public void requestPermissionIfNecessary(Context context) {
        get().requestPermissionIfNecessary(context);
    }

    public void initConfig(String oaid, String publicIP) {
        Hawk.init(context).build();
        Hawk.put("url", context.getResources().getString(R.string.base_url));
        String token = Hawk.get("token") == null ? "" : Hawk.get("token").toString();
        Hawk.put("oaid", oaid);
        Hawk.put("publicIP", publicIP);
        if (TextUtils.isEmpty(Hawk.get("token"))) {
            ApiService.getKey(context);
        } else {
            this.token = token;
            ApiService.isPostEnvInfo(context);
            ApiService.requestConfig(context);
        }
    }

    public void init(Context context, String appId, String appName, InitCallback callback, TTCustomController customController) {
        this.projectId = appId;
        this.projectName = appName;
        this.context = context;
        doInit(callback, customController);
    }

    private void doInit(InitCallback callback, TTCustomController customController) {
        if (sInit) {
            Log.i(TAG, "已经初始化过了");
            return;
        }
        TTAdSdk.init(context, buildConfig(customController));
        TTAdSdk.start(new TTAdSdk.Callback() {
            @Override
            public void success() {
                sInit = true;
                //初始化成功
                //在初始化成功回调之后进行广告加载
                Log.e(TAG, "初始化成功");
                if(callback!=null){
                    callback.success();
                }
            }

            @Override
            public void fail(int i, String s) {
                //初始化失败
                Log.e(TAG, "初始化失败");
                if(callback!=null){
                    callback.error();
                }
            }
        });
    }

    private TTAdConfig buildConfig(TTCustomController customController) {

        return new TTAdConfig.Builder()
                /**
                 * 注：需要替换成在媒体平台申请的appID ，切勿直接复制
                 */
                .appId(this.projectId)
                .appName(this.projectName)
                /**
                 *  todo 上线前需要关闭debug开关，否则会影响性能
                 */
                .debug(false)
                /**
                 * 使用聚合功能此开关必须设置为true，默认为false
                 */
                .useMediation(true)
                .customController(customController)  //设置隐私权
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

    /**
     * 预加载插屏广告
     *
     * @param activity
     * @param codeId
     */
    public void preloadInterstitialAd(Activity activity, String codeId, InfoAdCallBack callback) {
        if (!sInit) {
            Log.i(TAG, "SDK没有初始化");
            return;
        }
        InterstitialAdManager.getInstance().preload(activity,this.projectId, codeId, callback);
    }

    /**
     * 插屏广告
     */
    public void showInterstitialAd(Activity activity, String codeId, InfoAdCallBack callback) {
        ApiService.addLog(activity,"info","准备获取插屏广告");
        if (!sInit) {
            Log.i(TAG, "SDK没有初始化");
            ApiService.addLog(activity,"error","SDK没有初始化");
            return;
        }
        if(!Hawk.isBuilt()){
            Hawk.init(context).build();
        }

        String valid_user_flag_value = Hawk.get(ConstantsPath.valid_user_flag_value, "0");
        if ("0".equals(valid_user_flag_value)) {
            if(callback != null){
                Log.i(TAG, "无效用户，获取插屏广告失败");
                ApiService.addLog(activity,"error","无效用户，获取插屏广告失败");
                callback.onAdClose();
            }
            return;
        }
        ApiService.addLog(activity,"info","开始获取插屏广告");
        InterstitialAdManager.getInstance().showAd(activity,this.projectId, codeId, callback);
    }

    /**
     * 信息流广告
     */
    public void showInfoFlowAd(Activity activity, String codeId, FrameLayout splashContainer, int width, int height, InformationFlowAdCallback callback) {
        if (!sInit) {
            Log.i(TAG, "SDK没有初始化");
            return;
        }
        if(!Hawk.isBuilt()){
            Hawk.init(context).build();
        }
        InformationFlowManager.getInstance().loadNativeAd(activity,this.projectId, codeId, splashContainer, width, height, callback);
    }

    /**
     * 开屏广告
     */
    public void showOpenScreenAd(Activity act, String codeId, FrameLayout splashContainer, int width, int height, OpenScreenAdCallBack callBack) {
        if (!sInit) {
            Log.i(TAG, "SDK没有初始化");
            return;
        }
        if(!Hawk.isBuilt()){
            Hawk.init(context).build();
        }
        OpenScreenAdManager.getInstance().loadSplashAd(act, this.projectId, codeId, splashContainer, width, height, callBack);
    }

    /**
     * 激励视频
     *
     * @param act
     * @param codeId
     * @param listener
     */
    public void showRewardAd(Activity act, String codeId, RewardAdCallBack listener) {
        if (!sInit) {
            Log.i(TAG, "SDK没有初始化");
            return;
        }
        if(!Hawk.isBuilt()){
            Hawk.init(context).build();
        }
        String valid_user_flag_value = Hawk.get(ConstantsPath.valid_user_flag_value, "0");
        if ("0".equals(valid_user_flag_value)) {
            if(listener != null){
                Log.i(TAG, "无效用户，获取激励广告失败");
                listener.onAdClose();
            }
            return;
        }

        RewardAdManager.getInstance().loadRewardAd(act, this.projectId, codeId, listener);
    }

    protected void setToken(String token) {
        this.token = token;
    }

    protected String getToken() {
        return token;
    }
}
