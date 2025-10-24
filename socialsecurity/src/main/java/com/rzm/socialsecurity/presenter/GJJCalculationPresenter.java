package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IGJJCalculationView;

public class GJJCalculationPresenter extends MvpPresenter<IGJJCalculationView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
