package com.common.wheel.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.common.wheel.http.api.BaseApi;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apis {

    private static final int DEFAULT_CONNECT_TIMEOUT = 30;   //连接超时
    private static final int DEFAULT_READ_TIMEOUT = 12;     //读取超时
    private static final int DEFAULT_WRITE_TIMEOUT = 12;   //写超时
    private static BaseApi baseApi;

    /**
     * 不带生命周期绑定的 获取单例模式的api
     *
     * @return
     */
    public static BaseApi getBaseApi() {
        if (baseApi == null) {
            synchronized (BaseApi.class) {
                if (baseApi == null) {
                    baseApi = getApi(BaseApi.class);
                }
            }
        }
        return baseApi;
    }

    /**
     * 生成api实例
     *
     * @param clazz class
     * @param <T>
     * @return 实例
     */
    private static <T> T getApi(Class<T> clazz) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 支持HTTPS
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)); //明文Http与比较新的Https
		builder.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer your_token") // 静态 Token
                        .build();
                return chain.proceed(newRequest);
            }
        });  //添加公共参数
        //添加logcat信息 只在debug上看
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("", "请求的url:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
//		builder.addInterceptor(new NetWorkInterceptor());  //网络拦截器
        builder.addInterceptor(loggingInterceptor);  //日志拦截器
        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BaseUrl.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(clazz);
    }
}
