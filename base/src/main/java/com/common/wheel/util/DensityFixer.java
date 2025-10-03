package com.common.wheel.util;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.view.View;

public class DensityFixer {

    /**
     * 修复密度配置
     */
    public static void fixDensity(Activity activity) {
        if (activity == null) return;

        final Application app = activity.getApplication();
        final DisplayMetrics appMetrics = app.getResources().getDisplayMetrics();
        final DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();

        // 获取目标密度
        float targetDensity = appMetrics.widthPixels / 240f; // 基于 375dp 设计图
        float targetScaledDensity = targetDensity * (appMetrics.scaledDensity / appMetrics.density);
        int targetDensityDpi = (int) (targetDensity * 160);

        // 应用配置
        appMetrics.density = targetDensity;
        appMetrics.scaledDensity = targetScaledDensity;
        appMetrics.densityDpi = targetDensityDpi;

        activityMetrics.density = targetDensity;
        activityMetrics.scaledDensity = targetScaledDensity;
        activityMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 监听界面焦点变化，修复适配问题
     */
    public static void watchAndFix(final Activity activity) {
        activity.getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // 布局变化时检查并修复
                fixDensity(activity);
            }
        });
    }
}
