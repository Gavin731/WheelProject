package com.common.wheel.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

public class DeviceUtil {

    /**
     * 需要权限   android.permission.READ_PRIVILEGED_PHONE_STATE
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getUUID(context);
        } else {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Method method = manager.getClass().getMethod("getImei", int.class);
                String imei1 = (String) method.invoke(manager, 0);
                String imei2 = (String) method.invoke(manager, 1);
                if (TextUtils.isEmpty(imei2)) {
                    return imei1;
                }
                if (!TextUtils.isEmpty(imei1)) {
                    //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                    String imei = "";
                    if (imei1.compareTo(imei2) <= 0) {
                        imei = imei1;
                    } else {
                        imei = imei2;
                    }
                    return imei;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return manager.getDeviceId();
            }
            return "";
        }
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }

    public static String getOAId(Context context) {
        return "";
    }

    /**
     * 需要权限  android.permission.READ_PRIVILEGED_PHONE_STATE
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getMeId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return telephonyManager.getMeid();
            } else {
                return telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统
     *
     * @return
     */
    public static String getSystem() {
        return Build.BRAND;
    }

    /**
     * 获取制造商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getMac(Context context) {
        String macAddress = getMacAddress();
        if (!TextUtils.isEmpty(macAddress)) {
            return macAddress;
        }
        String macAddressFromWifiInfo = getLocalMacAddressFromWifiInfo(context);
        if (!TextUtils.isEmpty(macAddressFromWifiInfo)) {
            return macAddressFromWifiInfo;
        }

        String macAddressFromIp = getLocalMacAddressFromIp(context);
        if (!TextUtils.isEmpty(macAddressFromIp)) {
            return macAddressFromIp;
        }
        return null;
    }

    /**
     * 检查设备是否插入了 SIM 卡
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            int simState = telephonyManager.getSimState();
            return simState != TelephonyManager.SIM_STATE_ABSENT;
        }
        return false;
    }

    /**
     * 获取ip地址
     *
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
        return (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "." + ((ipAddress >> 16) & 0xFF) + "." + ((ipAddress >> 24) & 0xFF);
    }

    public static String getUUID(Context context) {

        String serial = null;

        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = getSerial(context);
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    @SuppressLint("MissingPermission")
    public static String getSerial(Context context) {
        String serial = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
        } catch (Exception e) {
            Log.i("", "获取设备序列号失败-1-" + e.toString());
        }
        if (TextUtils.isEmpty(serial) || TextUtils.equals(serial, "unknown")) {
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            } catch (Exception e) {
                Log.i("", "获取设备序列号失败-2-" + e.toString());
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(serial) || TextUtils.equals(serial, "unknown")) {
            try {
                serial = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID); // Secure.ANDROID_ID 作为可以获取唯一标识
            } catch (Exception e) {
                Log.i("", "获取设备序列号失败-3-" + e.toString());
                e.printStackTrace();
                //serial需要一个初始化
                serial = "serial";
            }
        }
        return serial;
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    @SuppressLint({"NewApi", "DefaultLocale"})
    public static String getMacAddress() {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }


    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 根据wifi信息获取本地mac
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac = winfo.getMacAddress();
        return mac;
    }

    /**
     * 根据IP获取本地Mac
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddressFromIp(Context context) {
        String mac_s = "";
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            mac_s = byte2hex(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mac_s;
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 二进制转十六进制
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    // 检查常见 Root 文件路径
    public static boolean checkRootFiles() {
        // 常见的Root相关文件路径
        String[] paths = {
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su",
                "/su/bin/su"
        };

        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    // 检查 su 命令是否存在
    public static boolean checkSuExists() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Exception e) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    // 检查 Build.TAGS 是否包含 "test-keys"
    public static boolean checkBuildTags() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    // 检查 Magisk 相关文件
    public static boolean checkMagisk() {
        // Magisk常见路径
        String[] magiskPaths = {
                "/sbin/.magisk",
                "/sbin/magisk",
                "/cache/.disable_magisk",
                "/cache/magisk.log",
                "/data/adb/magisk"
        };

        for (String path : magiskPaths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    // 尝试执行特权命令
    public static boolean checkRootCommand() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            outputStream.close();
            int exitValue = process.waitFor();
            return exitValue == 0;
        } catch (Exception e) {
            return false;
        }
    }

    // 检查系统属性
    public static boolean checkSystemProperties() {
        String[] props = {
                "ro.debuggable",
                "ro.secure",
                "service.adb.root"
        };

        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class);

            for (String prop : props) {
                String value = (String) get.invoke(null, prop);
                if ("1".equals(value)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return false;
    }

    public static boolean isDeviceRooted() {
//        return checkRootFiles() || checkSuExists() || checkBuildTags()
//                || checkMagisk() || checkRootCommand() || checkSystemProperties();
        return checkRootCommand();
    }

    /**
     * 是否开了root
     *
     * @return
     */
    public static boolean isRoot() {
        // 在应用启动时检查
        if (isDeviceRooted()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否开了vpn
     *
     * @param context
     * @return
     */
    public static boolean isVpnActive(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network activeNetwork = cm.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities caps = cm.getNetworkCapabilities(activeNetwork);
                return caps != null && caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
            }
        } else {
            // 兼容旧版本的方法
            try {
                NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
                for (NetworkInfo networkInfo : networkInfos) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_VPN) {
                        if (networkInfo.isConnectedOrConnecting()) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isAdbEnabledByProps() {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
            String adbEnabled = (String) get.invoke(null, "persist.sys.usb.config", "");
            return adbEnabled.contains("adb");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDebuggerAttached() {
        return Debug.isDebuggerConnected();
    }

    public static boolean isSystemDebuggable() {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class);
            String debuggable = (String) get.invoke(null, "ro.debuggable");
            return "1".equals(debuggable);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAdbAccessible(Context context) {
        int state = Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0);
        return state == 1;
        // 组合多种检测方法提高准确性
//        return isAdbEnabledByProps() || isSystemDebuggable() || isDebuggerAttached();
    }

    /**
     * 是否开了adb调试
     * @param context
     * @return
     */
    public static boolean isAdb(Context context) {
        // 使用示例
        if (isAdbAccessible(context)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isProxyEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network activeNetwork = cm.getActiveNetwork();
            if (activeNetwork != null) {
                LinkProperties linkProperties = cm.getLinkProperties(activeNetwork);
                return linkProperties != null && linkProperties.getHttpProxy() != null;
            }
        } else {
            // 兼容旧版本的方法
            String proxyHost = System.getProperty("http.proxyHost");
            String proxyPort = System.getProperty("http.proxyPort");
            return proxyHost != null || proxyPort != null;
        }
        return false;
    }

    public static boolean isUsingProxy() {
        String proxyHost = System.getProperty("http.proxyHost");
        String proxyPort = System.getProperty("http.proxyPort");
        return proxyHost != null || proxyPort != null;
    }

    /**
     * 是否开了代理
     * @param context
     * @return
     */
    public static boolean isDl(Context context) {
        return isProxyEnabled(context) || isUsingProxy();
    }
}
