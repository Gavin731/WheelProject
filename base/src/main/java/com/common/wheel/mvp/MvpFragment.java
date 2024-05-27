package com.common.wheel.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.base.BaseFragment;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public abstract class MvpFragment<P extends IPresenter> extends BaseFragment implements IBaseView {

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.setView(this);
        getLifecycle().addObserver(presenter);
    }

    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(presenter);
    }

}
