package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IMedicalCalculationView;

public class MedicalCalculationPresenter extends MvpPresenter<IMedicalCalculationView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
