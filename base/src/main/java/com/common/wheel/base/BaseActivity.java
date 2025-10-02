package com.common.wheel.base;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.common.wheel.mvp.IBaseView;
import com.common.wheel.util.ActivityManager;
import com.common.wheel.util.ImmersiveModeHelper;
import com.common.wheel.util.ToastUtil;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/12/4     zenglinggui       v1.0.0        create
 **/
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        int layoutResID = getLayoutId();
        if (layoutResID != 0) {
            setContentView(layoutResID);
            ActivityManager.getInstance().addActivity(this);
        }
        ImmersiveModeHelper.setTransparentStatusBar(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract int getLayoutId();

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(this, message);
    }
}
