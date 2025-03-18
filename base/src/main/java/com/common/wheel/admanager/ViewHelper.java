package com.common.wheel.admanager;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.R;

import java.lang.reflect.Field;

public class ViewHelper {

    protected static void clickView(ViewGroup rootView) {
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
    }

    protected static void addInterstitialView() {
        Activity currentActivity = getCurPage();
        if (currentActivity != null) {
            btnToPage(currentActivity);
        } else {
            LogUtils.e("AdLifeListener 无法获取当前页面");
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


    protected static void btnToPage(Activity activity) {
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
                ViewHelper.clickView(rootView);
            });

            // 将按钮添加到根布局中
            rootView.addView(closeImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void renderInfoView(Context context, FrameLayout splashContainer, View expressFeedView) {
        View flView = LayoutInflater.from(context).inflate(R.layout.view_info, null);
        FrameLayout flInfo = flView.findViewById(R.id.fl_info);
        View llMask = flView.findViewById(R.id.ll_mask);
        flInfo.removeAllViews();
        flInfo.addView(expressFeedView);
        llMask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ViewHelper.clickView((ViewGroup) expressFeedView);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ViewHelper.clickView((ViewGroup) expressFeedView);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        splashContainer.addView(flView);
    }
}
