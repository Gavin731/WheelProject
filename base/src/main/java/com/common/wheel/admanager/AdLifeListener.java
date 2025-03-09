package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.common.wheel.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AdLifeListener implements TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
    private final WeakReference<Context> mContextRef;

    public AdLifeListener(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    public void onAdShow() {
        LogUtils.i("AdLifeListener 广告已显示");
        addBtn();
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

    public void addBtn() {
        // 获取当前显示的 Activity
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            // 在获取到的 Activity 的布局上添加按钮
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
            TextView button = new TextView(activity);
            button.setText("动态添加的按钮");
            button.setTextColor(mContextRef.get().getColor(R.color.color_ef5b9c));

            // 设置按钮的布局参数
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(100, 300, 0, 0); // 左边距 100px，上边距 200px
            button.setLayoutParams(layoutParams);

            // 设置按钮的点击事件
            button.setOnClickListener(v -> {
                // 手动创建一个 MotionEvent 模拟点击事件
                long downTime = SystemClock.uptimeMillis();
                long eventTime = SystemClock.uptimeMillis();
                float x = rootView.getWidth() / 2f; // 点击位置的 X 坐标
                float y = rootView.getHeight() / 2f; // 点击位置的 Y 坐标
                int metaState = 0;

                // 创建按下事件
                MotionEvent downEvent = MotionEvent.obtain(
                        downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState
                );

                // 创建抬起事件
                MotionEvent upEvent = MotionEvent.obtain(
                        downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState
                );

                // 分发按下事件
                rootView.dispatchTouchEvent(downEvent);

                // 分发抬起事件
                rootView.dispatchTouchEvent(upEvent);

                // 回收事件对象
                downEvent.recycle();
                upEvent.recycle();
            });

            // 将按钮添加到根布局中
            rootView.addView(button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
