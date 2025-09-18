package com.rzm.socialsecurity.presenter;


import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IAView;
import com.rzm.socialsecurity.view.IBView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/12/3     zenglinggui       v1.0.0        create
 **/
public class BPresenter extends MvpPresenter<IBView> {

    @Override
    public void initView() {
        getView().showToast("我是fragment");
    }
}
