package com.common.wheelproject.test.activity;


import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.wheel.base.BaseActivity;
import com.common.wheel.http.Apis;
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
        Apis.getBaseApi().addDevice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new RxObjectCodeFunction<>(this, String.class))
                .map(new Function<RxObjectCode<String>, Boolean>() {
                    @Override
                    public Boolean apply(RxObjectCode<String> machineEntityRxObjectCode) throws Exception {
                        String result = machineEntityRxObjectCode.getObject();
                        LogUtils.i("获取到的网络请求结果：" + result);
                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(!aBoolean){
                    ToastUtils.showShort("请求失败");
                }
            }
        }, new RxConsumerThrowable(this, "登录异常"));
    }
}
