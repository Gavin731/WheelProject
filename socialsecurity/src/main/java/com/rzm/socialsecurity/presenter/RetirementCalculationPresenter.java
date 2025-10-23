package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IRetirementCalculationView;

import java.util.ArrayList;
import java.util.List;

public class RetirementCalculationPresenter extends MvpPresenter<IRetirementCalculationView> {

    private List<String> sexTypeData = new ArrayList<>();//住房贷利息

    @Override
    public void initView() {
        getView().initView();
        initData();
    }

    public void initData(){
        sexTypeData.clear();
        sexTypeData.add("男职工");
        sexTypeData.add("原法定退休年龄55周岁女干部");
        sexTypeData.add("原法定退休年龄55周岁女职工");
    }

    public List<String> getSexTypeData(){
        return sexTypeData;
    }
}
