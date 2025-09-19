package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ISBManageOrSuperviseView;

public class SBManageOrSupervisePresenter extends MvpPresenter<ISBManageOrSuperviseView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
