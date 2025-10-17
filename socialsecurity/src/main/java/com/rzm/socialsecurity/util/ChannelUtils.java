package com.rzm.socialsecurity.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ChannelUtils {

    public static String getChannel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("CHANNEL_ID");
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }
}
