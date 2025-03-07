package com.common.wheel.http.api;

import com.common.wheel.http.entity.ResultBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BaseApi {

    /**
     * 获取机器号
     *
     * @return
     */
    @POST("k/integration/notoken/bsc/v1/login")
    Observable<ResultBean> addDevice(@Body HashMap<String, String> param);
}
