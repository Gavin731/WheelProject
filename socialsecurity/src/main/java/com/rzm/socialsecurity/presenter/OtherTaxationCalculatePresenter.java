package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IOtherTaxationCalculateView;

public class OtherTaxationCalculatePresenter extends MvpPresenter<IOtherTaxationCalculateView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
