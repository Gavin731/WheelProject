package com.common.wheel.http;

import android.content.Context;
import android.util.Log;

import com.common.wheel.http.entity.ResultBean;
import com.common.wheel.util.GsonUtil;

import io.reactivex.functions.Function;

public class RxObjectCodeFunction<T> implements Function<ResultBean, RxObjectCode<T>> {

    public static final int RESULTOK = 200;
    private Class<T> tClass;
    private Context context;


    public RxObjectCodeFunction(Context context, Class<T> tClass) {
        this.tClass = tClass;
        this.context = context;
    }

    public RxObjectCodeFunction(Class<T> tClass) {
        this.tClass = tClass;
    }


    @Override
    public RxObjectCode apply(ResultBean resultBean) throws Exception {
        int code = resultBean.getCode();
        RxObjectCode rxObjectCode = new RxObjectCode();
        rxObjectCode.setCode(code);
        if (code != RESULTOK) {
            Log.i("", "接口返回的错误code1:" + code + " 信息：" + resultBean.getMessage());
        }
//        String result = AESUtil.getInstance().getDecodeResultData(resultBean);
//        Logger.e("接口返回的result:" + result);
//        if (TextUtils.isEmpty(result)) {
//            rxObjectCode.setObject(new Object());
//            return rxObjectCode;
//        }
        rxObjectCode.setObject(resultBean.getData());
        return rxObjectCode;
    }


}
