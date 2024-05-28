package com.common.wheelproject.home.presenter;


import com.common.wheel.mvp.MvpPresenter;
import com.common.wheelproject.home.view.IMainView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public class MainPresenter extends MvpPresenter<IMainView> {

    @Override
    public void initView() {
        getView().showToast(getView().getResourcesHint());
        getView().initNavigation();
    }
}
