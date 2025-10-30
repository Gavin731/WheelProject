package com.rzm.socialsecurity.util;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.util.ExceptionUtil;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppDeviceUtil {

    private static final String[] IP_SERVICES = {
            "https://api.ipify.org",
            "https://ipinfo.io/ip",
            "https://checkip.amazonaws.com",
            "https://icanhazip.com"
    };


    public static String getIp() {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("https://httpbin.org/ip");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String externalIp = stringBuilder.toString();
            reader.close();
            JSONObject jsonObject = new JSONObject(externalIp);
            String ip = jsonObject.getString("origin");
            return ip;
        } catch (Exception e) {
            LogUtils.e(ExceptionUtil.getStackTrace(e));
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }

    public static String getIpAddress() {
        String ipAddress = AppDeviceUtil.getIp();
        if (!TextUtils.isEmpty(ipAddress)) {
            return ipAddress;
        }
        return tryNextService(0);
    }

    public static String tryNextService(int index) {
        if (index >= IP_SERVICES.length) {
            return "";
        }
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(IP_SERVICES[index]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String ip = stringBuilder.toString();
            reader.close();
            urlConnection.disconnect();
            if (TextUtils.isEmpty(ip)) {
                return tryNextService(index + 1);
            }
            return ip;
        } catch (Exception e) {
            LogUtils.e(ExceptionUtil.getStackTrace(e));
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return tryNextService(index + 1);
        }
    }
}
