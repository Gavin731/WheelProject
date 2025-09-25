package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ISplashView;

public class SplashPresenter extends MvpPresenter<ISplashView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
