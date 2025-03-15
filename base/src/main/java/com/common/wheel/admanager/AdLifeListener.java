package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.common.wheel.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AdLifeListener implements TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
    private final WeakReference<Context> mContextRef;

    protected AdLifeListener(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    public void onAdShow() {
        LogUtils.i("AdLifeListener 广告已显示");
        addClickView();
    }

    @Override
    public void onAdVideoBarClick() {
        LogUtils.i("AdLifeListener 广告被点击");
    }

    @Override
    public void onAdClose() {
        LogUtils.i("AdLifeListener 广告已关闭");
    }

    @Override
    public void onVideoComplete() {
        LogUtils.i("AdLifeListener 广告加载完成");
    }

    @Override
    public void onSkippedVideo() {
        LogUtils.i("AdLifeListener 广告已跳过");
    }

    public void addClickView() {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            addButtonToActivity(currentActivity);
        } else {
            LogUtils.e("AdLifeListener 无法获取当前页面");
        }
    }


    private Activity getCurrentActivity() {
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


    private void addButtonToActivity(Activity activity) {
        try {
            // 获取 Activity 的根布局
            ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);

            // 创建按钮
            ImageView closeImg = new ImageView(activity);
            closeImg.setImageDrawable(activity.getResources().getDrawable(R.mipmap.icon_close));

            // 设置按钮的布局参数
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(100, 600, 0, 0); // 左边距 100px，上边距 200px
            closeImg.setLayoutParams(layoutParams);

            // 设置按钮的点击事件
            closeImg.setOnClickListener(v -> {
                ClickViewUtil.openClick(rootView);
            });

            // 将按钮添加到根布局中
            rootView.addView(closeImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
