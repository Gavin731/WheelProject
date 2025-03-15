package com.common.wheel.util;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.DeviceUtils;

public class DeviceUtil {

    /**
     * 检查设备是否插入了 SIM 卡
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            int simState = telephonyManager.getSimState();
            return simState != TelephonyManager.SIM_STATE_ABSENT
                    && simState != TelephonyManager.SIM_STATE_UNKNOWN;
        }
        return false;
    }

    /**
     * 获取ip地址
     * @param context
     * @return
     */
    public static String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return formatIpAddress(ipAddress);
        }
        return null;
    }

    private static String formatIpAddress(int ipAddress) {
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                ((ipAddress >> 24) & 0xFF);
    }
}
