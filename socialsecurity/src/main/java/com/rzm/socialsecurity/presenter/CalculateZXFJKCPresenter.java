package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ICalculateZXFJKCView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculateZXFJKCPresenter extends MvpPresenter<ICalculateZXFJKCView> {

    private List<String> sylrData = new ArrayList<>();//赡养老人
    private float sylrAmount = 0;
    private List<String> zfzjData = new ArrayList<>();//租房租金
    private float zfzjAmount = 0;
    private List<String> zfdklxData = new ArrayList<>();//住房贷利息
    private float zfdklxAmount = 0;
    private List<String> jxjyData = new ArrayList<>();//继续教育
    private float jxjyAmount = 0;

    private List<String> znjyData = new ArrayList<>();//继续教育
    private float znjyAmount = 0;
    private List<String> yyrzhData = new ArrayList<>();//继续教育
    private float yyrzhAmount = 0;

    @Override
    public void initView() {
        getView().initView();
    }

    public void showSylrDialog(){
        sylrData.clear();
        sylrData.add("独生子女");
        sylrData.add("非独生，2个兄弟姐妹");
        sylrData.add("非独生，3个兄弟姐妹");
        sylrData.add("非独生，4个兄弟姐妹");
        sylrData.add("非独生，5个兄弟姐妹");
        sylrData.add("清空选项");
        getView().showSylrDialog(sylrData);
    }
    public void calculateSylrAmount(int position){
        switch (position){
            case 0:
                sylrAmount = 3000;
                break;
            case 1:
                sylrAmount = 1500;
                break;
            case 2:
                sylrAmount = 1000;
                break;
            case 3:
                sylrAmount = 750;
                break;
            case 4:
                sylrAmount = 600;
                break;
            case 5:
                sylrAmount = 0;
                break;
        }
        getView().showSylrAmount(sylrAmount);
        calculateTotalAmount();
    }

    public void showZfzjDialog(){
        zfzjData.clear();
        zfzjData.add("直辖市、省会、其他城市");
        zfzjData.add("市辖区户籍人口超过100万");
        zfzjData.add("市辖区户籍人口不超过100万");
        zfzjData.add("清空选项");
        getView().showZfzjDialog(zfzjData);
    }
    public void calculateZfzjAmount(int position){
        switch (position){
            case 0:
                zfzjAmount = 1500;
                break;
            case 1:
                zfzjAmount = 1100;
                break;
            case 2:
                zfzjAmount = 800;
                break;
            case 3:
                zfzjAmount = 0;
                break;
        }
        getView().showZfzjAmount(zfzjAmount);
        calculateTotalAmount();
    }

    public void showZfdklxDialog(){
        zfdklxData.clear();
        zfdklxData.add("本人或者配偶首套房贷款且全部由我扣除");
        zfdklxData.add("本人或者配偶首套房贷款且夫妻平分扣除");
        zfdklxData.add("清空选项");
        getView().showZfdklxDialog(zfdklxData);
    }
    public void calculateZfdklxAmount(int position){
        switch (position){
            case 0:
                zfdklxAmount = 1000;
                break;
            case 1:
                zfdklxAmount = 500;
                break;
            case 2:
                zfdklxAmount = 0;
                break;
        }
        getView().showZfdklxAmount(zfdklxAmount);
        calculateTotalAmount();
    }

    public void showJxjyDialog(){
        jxjyData.clear();
        jxjyData.add("本人在学历教育期间");
        jxjyData.add("本人取得职业资格继续教育证书");
        jxjyData.add("清空选项");
        getView().showJxjyDialog(jxjyData);
    }
    public void calculateJxjyAmount(int position){
        switch (position){
            case 0:
                jxjyAmount = 400;
                break;
            case 1:
                jxjyAmount = 300;
                break;
            case 2:
                jxjyAmount = 0;
                break;
        }
        getView().showJxjyAmount(jxjyAmount);
        calculateTotalAmount();
    }
    public void showZnjyDialog(){
        znjyData.clear();
        znjyData.add("1个子女");
        znjyData.add("2个子女");
        znjyData.add("3个子女");
        znjyData.add("4个子女");
        znjyData.add("5个子女");
        znjyData.add("清空选项");
        getView().showZnjyDialog(znjyData);
    }
    public void calculateZnjyAmount(int position){
        switch (position){
            case 0:
                znjyAmount = 2000;
                break;
            case 1:
                znjyAmount = 4000;
                break;
            case 2:
                znjyAmount = 6000;
                break;
            case 3:
                znjyAmount = 8000;
                break;
            case 4:
                znjyAmount = 10000;
                break;
            case 5:
                znjyAmount = 0;
                break;
        }
        getView().showZnjyAmount(znjyAmount);
        calculateTotalAmount();
    }

    public void showYyrzhDialog(){
        yyrzhData.clear();
        yyrzhData.add("1个宝宝");
        yyrzhData.add("2个宝宝");
        yyrzhData.add("3个宝宝");
        yyrzhData.add("4个宝宝");
        yyrzhData.add("5个宝宝");
        yyrzhData.add("清空选项");
        getView().showYyrzhDialog(yyrzhData);
    }
    public void calculateYyrzhAmount(int position){
        switch (position){
            case 0:
                yyrzhAmount = 2000;
                break;
            case 1:
                yyrzhAmount = 4000;
                break;
            case 2:
                yyrzhAmount = 6000;
                break;
            case 3:
                yyrzhAmount = 8000;
                break;
            case 4:
                yyrzhAmount = 10000;
                break;
            case 5:
                yyrzhAmount = 0;
                break;
        }
        getView().showYyrzhAmount(yyrzhAmount);
        calculateTotalAmount();
    }

    public void calculateTotalAmount(){
        BigDecimal bg1= new BigDecimal(sylrAmount).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal bg2= new BigDecimal(zfzjAmount).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal bg3= new BigDecimal(zfdklxAmount).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal bg4= new BigDecimal(jxjyAmount).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal bg5= new BigDecimal(znjyAmount).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal bg6= new BigDecimal(yyrzhAmount).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal totalAmount = bg1.add(bg2).add(bg3).add(bg4).add(bg5).add(bg6).setScale(2, RoundingMode.HALF_UP);;
        getView().showTotalAmount(totalAmount);
    }
}
