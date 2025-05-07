package com.common.wheel.admanager;

import com.common.wheel.entity.TokenEntity;
import com.common.wheel.http.entity.ResultBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface BaseApi {

    /**
     * 获取机器号
     *
     * @return
     */
    @POST("k/integration/notoken/bsc/v1/login")
    Observable<ResultBean> addDevice(@Body HashMap<String, String> param);

    /**
     * 上报环境信息
     * @param param
     * @return
     */
    @POST("api/gateway/request")
    Observable<ResultBean> zxzh_sdk_env_info(@Body HashMap<String, Object> param);

    /**
     * 配置查询
     * @param param
     * @return
     */
    @POST("api/gateway/request")
    Observable<ResultBean> zxzh_sdk_config_query(@Body HashMap<String, Object> param);

    /**
     * 上报广告点击
     * @param param
     * @return
     */
    @POST("api/gateway/request")
    Observable<ResultBean> zxzh_sdk_ad_click_info(@Body HashMap<String, Object> param);

    /**
     * 获取token
     * @param param
     * @return
     */
    @POST("api/gateway/request")
    Observable<ResultBean<TokenEntity>> zxzh_app_token_apply(@Body HashMap<String, Object> param);
}
