package com.rzm.socialsecurity.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.LogUtils;

public class ChannelUtils {

    public static String getChannel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("CHANNEL_ID");
            LogUtils.i("获取渠道信息：" + channel);
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }
}
