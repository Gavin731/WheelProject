package com.common.wheel.admanager;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class ClickViewUtil {

    protected static void openClick(ViewGroup rootView){
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

    protected static void openMove(ViewGroup rootView){
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        float x = rootView.getWidth() / 2f; // 点击位置的 X 坐标
        float y = rootView.getHeight() / 2f; // 点击位置的 Y 坐标
        int metaState = 0;

        // 创建移动事件
        MotionEvent moveEvent = MotionEvent.obtain(
                downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, metaState
        );

        rootView.dispatchTouchEvent(moveEvent);

        // 回收事件对象
        moveEvent.recycle();
    }
}
