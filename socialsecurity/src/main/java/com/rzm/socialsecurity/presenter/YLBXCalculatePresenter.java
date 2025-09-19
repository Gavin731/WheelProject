package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IYLBXCalculateView;

public class YLBXCalculatePresenter extends MvpPresenter<IYLBXCalculateView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
