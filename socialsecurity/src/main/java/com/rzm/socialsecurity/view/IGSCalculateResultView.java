package com.rzm.socialsecurity.view;

import java.util.List;

public interface IGSCalculateResultView extends IBView{

    void initView();
    void initData( List<String> titles, List<List<String>> tableData);
}
