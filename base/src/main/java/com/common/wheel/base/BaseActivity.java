package com.common.base.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.common.wheel.mvp.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = getLayoutId();
        if (layoutResID != 0) {
            setContentView(layoutResID);
            mUnbinder = ButterKnife.bind(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            this.mUnbinder = null;
        }
    }

    public abstract int getLayoutId();

    @Override
    public void showToast(String message) {
        ToastUtils.showLong(message);
    }
}
