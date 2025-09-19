package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ISBCalculateView;

public class SBCalculatePresenter extends MvpPresenter<ISBCalculateView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
