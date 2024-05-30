package com.common.wheelproject.http;

import com.common.wheelproject.CommonApplication;
import com.common.wheelproject.R;

public interface BaseUrl {

    String baseUrl = CommonApplication.getInstance().getString(R.string.BASE_URL);

}
