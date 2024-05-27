package com.common.wheel.mvp;

import android.os.Bundle;


/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public abstract class MvpActivity<P extends IPresenter> extends com.common.base.base.BaseActivity implements IBaseView {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.setView(this);
        getLifecycle().addObserver(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(presenter);

    }

    public abstract P createPresenter();

}
