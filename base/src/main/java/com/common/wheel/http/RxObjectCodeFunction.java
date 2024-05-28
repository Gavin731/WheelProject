package com.common.wheel.http;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.wheel.BaseApplication;
import com.common.wheel.http.entity.ResultBean;
import com.common.wheel.util.GsonUtil;

import io.reactivex.functions.Function;

public class RxObjectCodeFunction<T> implements Function<ResultBean, RxObjectCode<T>> {

    public static final int RESULTOK = 10000;
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
            LogUtils.i("接口返回的错误code1:" + code + " 信息：" + resultBean.getMessage());
            Thread thread = Thread.currentThread();
            if (TextUtils.equals(thread.getName(), "main")) {
                if (resultBean.getMessage() != null) {
                    if (context == null) {
                        Toast.makeText(BaseApplication.getInstance(), (String) resultBean.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        ToastUtils.showShort((String) resultBean.getMessage());
                    }
                }
            }
        }
//        String result = AESUtil.getInstance().getDecodeResultData(resultBean);
//        Logger.e("接口返回的result:" + result);
//        if (TextUtils.isEmpty(result)) {
//            rxObjectCode.setObject(new Object());
//            return rxObjectCode;
//        }
        rxObjectCode.setObject(GsonUtil.parseJsonToBean(resultBean.getData().toString(), tClass));
        return rxObjectCode;
    }


}
