package com.rzm.socialsecurity.util;

import android.app.Application;
import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

public class UMUtil {

    public static String key = "68d5fad4c0c131698b7eba30";

    public static void preInit(Application context) {
        String channel = ChannelUtils.getChannel(context);
        UMConfigure.enableImeiCollection(false);
        UMConfigure.preInit(context, key, channel);
    }

    public static void init(Context context) {
        String channel = ChannelUtils.getChannel(context);
        UMConfigure.init(context, key, channel,
                UMConfigure.DEVICE_TYPE_PHONE,
                "");
    }
}
