package com.rzm.socialsecurity.presenter;

import android.widget.TextView;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ISBFunctionDetailView;

import java.util.ArrayList;
import java.util.List;

public class SBFunctionDetailPresenter extends MvpPresenter<ISBFunctionDetailView> {
    @Override
    public void initView() {
        getView().initView();
    }

    public String getTitle(int type){
        String result ="";
        switch (type){
            case 1:
                result = "养老保险作用";
                break;
            case 2:
                result = "医疗保险作用";
                break;
            case 3:
                result = "失业保险作用";
                break;
            case 4:
                result = "工伤保险作用";
                break;
            case 5:
                result = "生育保险作用";
                break;
            case 6:
                result = "医保问答";
                break;
            case 7:
                result = "报销标准";
                break;
            case 8:
                result = "生育津贴";
                break;
        }
        return result;
    }

}
