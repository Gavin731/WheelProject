package com.common.wheelproject.home.presenter;


import com.common.wheel.mvp.MvpPresenter;
import com.common.wheelproject.home.view.IAView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/12/3     zenglinggui       v1.0.0        create
 **/
public class APresenter extends MvpPresenter<IAView> {

    @Override
    public void initView() {
        getView().showToast("我是fragment");
    }
}
