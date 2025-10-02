package com.rzm.socialsecurity.util;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppDeviceUtil {

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
            throw new RuntimeException(e);
        } finally {
            urlConnection.disconnect();
        }

    }
}
