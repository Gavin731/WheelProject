package com.common.wheel.util;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.wheel.R;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;


public class ThrowableUtil {


	public void handleThrowable(Throwable throwable, Context context, String errorMsg) {
		/*if (throwable instanceof NoWorkException) {
			return;
		}*/
		if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException
				|| throwable instanceof ConnectException) {
			ToastUtils.showShort(R.string.time_connect_out);
		} else if (throwable instanceof JsonSyntaxException) {
			ToastUtils.showShort(R.string.json_error);
		} else if (throwable instanceof UndeliverableException) {
			RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
				public void accept(Throwable throwable) throws Exception {
					LogUtils.e(throwable, "UndeliverableException异常:");
				}
			});
		} else {
			if (!TextUtils.isEmpty(errorMsg)) {
				ToastUtils.showShort(errorMsg);
			}
		}
	}
}
