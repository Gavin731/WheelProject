package com.rzm.socialsecurity.view;

import java.util.List;

public interface IOtherTaxationCalculateResultView extends IBView{

    void initView();
    int getType();
    void initData( List<String> titles, List<List<String>> tableData);
}
