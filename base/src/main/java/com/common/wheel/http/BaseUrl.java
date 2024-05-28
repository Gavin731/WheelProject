package com.common.wheel.http;

import com.common.wheel.BaseApplication;
import com.common.wheel.R;

public interface BaseUrl {

	String baseUrl = BaseApplication.getInstance().getString(R.string.BASE_URL);

}
