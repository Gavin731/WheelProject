package com.rzm.socialsecurity.view;

import java.math.BigDecimal;
import java.util.List;

public interface ICalculateZXFJKCView extends IBView{

    void initView();
    void showSylrDialog(List<String> datas);
    void showSylrAmount(float amount);
    void showZfzjDialog(List<String> datas);
    void showZfzjAmount(float amount);
    void showZfdklxDialog(List<String> datas);
    void showZfdklxAmount(float amount);
    void showJxjyDialog(List<String> datas);
    void showJxjyAmount(float amount);
    void showZnjyDialog(List<String> datas);
    void showZnjyAmount(float amount);
    void showYyrzhDialog(List<String> datas);
    void showYyrzhAmount(float amount);
    void showTotalAmount(BigDecimal amount);
}
