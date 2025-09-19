package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ISBFunctionView;

public class SBFunctionPresenter extends MvpPresenter<ISBFunctionView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
