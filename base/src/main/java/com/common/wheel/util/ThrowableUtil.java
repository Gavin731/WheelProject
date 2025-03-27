package com.common.wheel.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.common.wheel.R;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;


public class ThrowableUtil {


    public void handleThrowable(Throwable throwable, Context context, String errorMsg) {
		/*if (throwable instanceof NoWorkException) {
			return;
		}*/
        if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException
                || throwable instanceof ConnectException) {
            Log.e("", context.getResources().getString(R.string.time_connect_out));
        } else if (throwable instanceof JsonSyntaxException) {
            Log.e("", context.getResources().getString(R.string.json_error));
        }
//        else if (throwable instanceof UndeliverableException) {
//            RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
//                public void accept(Throwable throwable) throws Exception {
//                    Log.e("", "UndeliverableException异常:" + ExceptionUtil.getStackTrace(throwable));
//                }
//            });
//        }
        else {
            if (!TextUtils.isEmpty(errorMsg)) {
                Log.e("", errorMsg);
            }
        }
    }
}
