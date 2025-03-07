package com.common.wheelproject.test.activity;


import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.wheel.base.BaseActivity;
import com.common.wheel.http.Apis;
import com.common.wheel.util.GsonUtil;
import com.common.wheelproject.entity.TokenEntity;
import com.common.wheel.http.RxConsumerThrowable;
import com.common.wheel.http.RxObjectCode;
import com.common.wheel.http.RxObjectCodeFunction;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/12/13     zenglinggui       v1.0.0        create
 **/
public class HttpLayoutActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HashMap<String, String> params = new HashMap<>();
        params.put("appkey", "13761304832");
        params.put("appsecret", "zt123456");
        params.put("orgClientCode", "TEST17");
        Apis.getBaseApi().addDevice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new RxObjectCodeFunction<>(this, TokenEntity.class))
                .map(new Function<RxObjectCode<TokenEntity>, Boolean>() {
                    @Override
                    public Boolean apply(RxObjectCode<TokenEntity> machineEntityRxObjectCode) throws Exception {
                        TokenEntity result = machineEntityRxObjectCode.getObject();
                        LogUtils.i("获取到的网络请求结果：" + GsonUtil.formatObjectToJson(result));
                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    ToastUtils.showShort("请求失败");
                    return;
                }
                ToastUtils.showShort("获取成功");
            }
        }, new RxConsumerThrowable(this, "登录异常"));
    }
}
