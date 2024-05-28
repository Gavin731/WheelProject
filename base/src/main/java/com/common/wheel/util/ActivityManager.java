package com.common.wheel.util;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

	private static ActivityManager activityManager;
	private static List<Activity> activityStack = new ArrayList<Activity>();

	public static ActivityManager getInstance() {
		if (activityManager == null) {
			synchronized (ActivityManager.class) {
				if (activityManager == null) {
					activityManager = new ActivityManager();
				}
			}
		}
		return activityManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new ArrayList<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 移除指定的Activity
	 */
	public void removeActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * 获取指定的Activity
	 */
	public Activity getActivity(String activity) {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			Activity activity1 = activityStack.get(i);
			if (null != activity1 && TextUtils.equals(activity1.getClass().getSimpleName(), activity)) {
				return activity1;
			}
		}
		return null;
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (i < activityStack.size() && null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 结束指定Activity
	 */
	public void finishActivity(String activity) {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			Activity activity1 = activityStack.get(i);
			if (null != activity1 && TextUtils.equals(activity1.getClass().getSimpleName(), activity)) {
				activity1.finish();
				activityStack.remove(activity1);
				break;
			}
		}
	}

	/**
	 * 结束非MainActivity
	 */
	public void finishNoMainActivity() {
//		for (int i = 0, size = activityStack.size(); i < size; i++) {
//			Activity activity1 = activityStack.get(i);
//			if (null != activity1 && !TextUtils.equals(activity1.getClass().getSimpleName(), MainActivity.class.getSimpleName())) {
//				activity1.finish();
//				activityStack.remove(activity1);
//				i--;
//				break;
//			}
//		}
	}

}
