package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IGSCalculateResultView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GSCalculateResultPresenter extends MvpPresenter<IGSCalculateResultView> {

    public List<List<String>> tableData=new ArrayList<>();

    @Override
    public void initView() {
        getView().initView();
        initData();
    }

    public void initData(){
        List<String> titles=new ArrayList<>();
        titles.add("级数");
        titles.add("全年应纳税\n所得额");
        titles.add("税率(%)");
        titles.add("速算扣除数");

        tableData.clear();
        List<String> row1=new ArrayList<>();
        row1.add("1");
        row1.add("不超过\n36000元的");
        row1.add("3");
        row1.add("0");
        tableData.add(row1);

        List<String> row2=new ArrayList<>();
        row2.add("2");
        row2.add("超过36000元至144000元的");
        row2.add("10");
        row2.add("2520");
        tableData.add(row2);

        List<String> row3=new ArrayList<>();
        row3.add("3");
        row3.add("超过144000元至300000元的");
        row3.add("20");
        row3.add("16920");
        tableData.add(row3);

        List<String> row4=new ArrayList<>();
        row4.add("4");
        row4.add("超过300000元至420000元的");
        row4.add("25");
        row4.add("31920");
        tableData.add(row4);

        List<String> row5=new ArrayList<>();
        row5.add("5");
        row5.add("超过420000元至660000元的");
        row5.add("30");
        row5.add("52920");
        tableData.add(row5);

        List<String> row6=new ArrayList<>();
        row6.add("6");
        row6.add("超过660000元至960000元的");
        row6.add("35");
        row6.add("85920");
        tableData.add(row6);

        List<String> row7=new ArrayList<>();
        row7.add("7");
        row7.add("超过960000元的");
        row7.add("45");
        row7.add("181920");
        tableData.add(row7);

        getView().initData(titles, tableData);
    }

    public List<String> getData(BigDecimal amount){
        BigDecimal total =amount.multiply(new BigDecimal(12));
        if(total.floatValue() <= 36000){
            return tableData.get(0);
        }else if(total.floatValue() > 36000 && total.floatValue() <= 144000){
            return tableData.get(1);
        }else if(total.floatValue() > 144000 && total.floatValue() <= 300000){
            return tableData.get(2);
        }else if(total.floatValue() > 300000 && total.floatValue() <= 420000){
            return tableData.get(3);
        }else if(total.floatValue() > 420000 && total.floatValue() <= 660000){
            return tableData.get(4);
        }else if(total.floatValue() > 660000 && total.floatValue() <= 960000){
            return tableData.get(5);
        }else{
            return tableData.get(6);
        }
    }

    /**
     * 应缴税额
     * @param amount 应纳税所得额
     * @return
     */
    public BigDecimal calculateSk(BigDecimal amount){
        List<String> list = getData(amount);
        float sl = Float.parseFloat(list.get(2))/100;// 税率
        float kcs = Float.parseFloat(list.get(3))/12;// 扣除数

        BigDecimal bg1 = new BigDecimal(sl);
        BigDecimal bg2 = new BigDecimal(kcs);
        BigDecimal bigDecimal = amount.multiply(bg1).subtract(bg2).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal;
    }
}
