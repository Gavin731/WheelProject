package com.common.wheel.http;

import android.content.Context;

import com.common.wheel.util.ThrowableUtil;

import io.reactivex.functions.Consumer;

public class RxConsumerThrowable implements Consumer<Throwable> {

    private Context context;
    private String tag;
    private String errorMsg;

    public RxConsumerThrowable(Context context, String tag) {
        this(context, tag, tag);
    }

    public RxConsumerThrowable(Context context, String tag, String errorMsg) {
        this.context = context;
        this.tag = tag;
        this.errorMsg = errorMsg;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        new ThrowableUtil().handleThrowable(throwable, context, errorMsg);
    }
}
