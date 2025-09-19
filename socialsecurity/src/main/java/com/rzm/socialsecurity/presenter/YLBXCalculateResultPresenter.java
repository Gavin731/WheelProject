package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IYLBXCalculateResultView;

public class YLBXCalculateResultPresenter extends MvpPresenter<IYLBXCalculateResultView> {
    @Override
    public void initView() {
        getView().initView();
    }
}
