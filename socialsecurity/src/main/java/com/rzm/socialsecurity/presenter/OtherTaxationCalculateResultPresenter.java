package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.IOtherTaxationCalculateResultView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OtherTaxationCalculateResultPresenter extends MvpPresenter<IOtherTaxationCalculateResultView> {

    public List<List<String>> tableData = new ArrayList<>();

    @Override
    public void initView() {
        getView().initView();
        initData();
    }

    public void initData() {
        int type = getView().getType();
        if (type == 1) {
            initLaowu();
        }else if (type == 2) {
            initNzj();
        }else if (type == 3) {
            initGxfh();
        }else if (type == 4) {
            initGtjy();
        }


    }

    /**
     * 劳务数据
     */
    public void initLaowu() {
        List<String> titles = new ArrayList<>();
        titles.add("级数");
        titles.add("劳务报酬所得\n应税部分");
        titles.add("税率(%)");
        titles.add("速算\n扣除数");

        tableData.clear();
        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("不超过\n20000元的");
        row1.add("20");
        row1.add("0");
        tableData.add(row1);

        List<String> row2 = new ArrayList<>();
        row2.add("2");
        row2.add("超过20000元至50000元的");
        row2.add("30");
        row2.add("2000");
        tableData.add(row2);

        List<String> row3 = new ArrayList<>();
        row3.add("3");
        row3.add("超过50000元的");
        row3.add("40");
        row3.add("7000");
        tableData.add(row3);

        getView().initData(titles, tableData);
    }

    /**
     * 劳务税率
     *
     * @param amount
     * @return
     */
    public List<String> getLaywuData(BigDecimal amount) {
        BigDecimal total = amount;
        if (total.floatValue() <= 20000) {
            return tableData.get(0);
        } else if (total.floatValue() > 20000 && total.floatValue() <= 50000) {
            return tableData.get(1);
        } else {
            return tableData.get(2);
        }
    }
    /**
     * 年终奖数据
     */
    public void initNzj() {
        List<String> titles = new ArrayList<>();
        titles.add("级数");
        titles.add("全年应纳税\n所得额");
        titles.add("税率(%)");
        titles.add("速算\n扣除数");

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

    /**
     * 年终奖税率
     *
     * @param amount
     * @return
     */
    public List<String> getNzjData(BigDecimal amount) {
        BigDecimal total = amount;
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
     * 股息分红
     */
    public void initGxfh() {
        List<String> titles = new ArrayList<>();
        titles.add("级数");
        titles.add("股息分红所得\n应税部分");
        titles.add("税率(%)");
        titles.add("速算\n扣除数");

        tableData.clear();
        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("超过0元");
        row1.add("20");
        row1.add("0");
        tableData.add(row1);
        getView().initData(titles, tableData);
    }

    /**
     * 股息分红
     *
     * @param amount
     * @return
     */
    public List<String> getGxfhData(BigDecimal amount) {
        BigDecimal total = amount;
        return tableData.get(0);
    }

    /**
     * 个体经营数据
     */
    public void initGtjy() {
        List<String> titles = new ArrayList<>();
        titles.add("级数");
        titles.add("全年应纳税\n所得额");
        titles.add("税率(%)");
        titles.add("速算\n扣除数");

        tableData.clear();
        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("不超过\n15000元的");
        row1.add("5");
        row1.add("0");
        tableData.add(row1);

        List<String> row2 = new ArrayList<>();
        row2.add("2");
        row2.add("超过15000元至30000元的");
        row2.add("10");
        row2.add("750");
        tableData.add(row2);

        List<String> row3 = new ArrayList<>();
        row3.add("3");
        row3.add("超过30000元至60000元的");
        row3.add("20");
        row3.add("3750");
        tableData.add(row3);

        List<String> row4 = new ArrayList<>();
        row4.add("4");
        row4.add("超过60000元至100000元的");
        row4.add("30");
        row4.add("9750");
        tableData.add(row4);

        List<String> row5 = new ArrayList<>();
        row5.add("5");
        row5.add("超过100000元的");
        row5.add("35");
        row5.add("14750");
        tableData.add(row5);

        getView().initData(titles, tableData);
    }

    /**
     * 个体经营税率
     *
     * @param amount
     * @return
     */
    public List<String> getGtjyData(BigDecimal amount) {
        BigDecimal total = amount;
        if (total.floatValue() <= 15000) {
            return tableData.get(0);
        } else if (total.floatValue() > 15000 && total.floatValue() <= 30000) {
            return tableData.get(1);
        } else if (total.floatValue() > 30000 && total.floatValue() <= 60000) {
            return tableData.get(2);
        } else if (total.floatValue() > 60000 && total.floatValue() <= 100000) {
            return tableData.get(3);
        } else {
            return tableData.get(4);
        }
    }

    /**
     * 应缴税额
     *
     * @param amount 金额
     * @param cbAmount 个体经营成本
     * @return
     */
    public BigDecimal calculateSk(int type, BigDecimal amount, BigDecimal cbAmount) {
        // 劳务
        if (type == 1) {
            List<String> list = getLaywuData(amount);
            float sl = Float.parseFloat(list.get(2)) / 100;// 税率
            float kcs = Float.parseFloat(list.get(3));// 扣除数
            float sl2=0.8f;
            BigDecimal bgsl=new BigDecimal(sl).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bg1 = new BigDecimal(sl2).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bg2 = new BigDecimal(kcs);
            // 不超过4000
            if (amount.floatValue() <= 4000) {
                return amount.subtract(new BigDecimal(800)).multiply(bgsl).subtract(bg2).setScale(2, RoundingMode.HALF_UP);
            }
            return amount.multiply(bg1).multiply(bgsl).subtract(bg2).setScale(2, RoundingMode.HALF_UP);
        }else if (type == 2){ // 年终奖报酬计算
            List<String> list = getNzjData(amount);
            float sl = Float.parseFloat(list.get(2)) / 100;// 税率
            float kcs = Float.parseFloat(list.get(3));// 扣除数
            BigDecimal bg1 = new BigDecimal(sl).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bg2 = new BigDecimal(kcs);
            return amount.multiply(bg1).subtract(bg2).setScale(2, RoundingMode.HALF_UP);
        }else if (type == 3){ // 股息分红报酬计算
            List<String> list = getGxfhData(amount);
            float sl = Float.parseFloat(list.get(2)) / 100;// 税率
            float kcs = Float.parseFloat(list.get(3));// 扣除数
            BigDecimal bg1 = new BigDecimal(sl).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bg2 = new BigDecimal(kcs);
            return amount.multiply(bg1).setScale(2, RoundingMode.HALF_UP);
        }else if (type == 4){ // 个体经营税务计算
            List<String> list = getGtjyData(amount.subtract(cbAmount));
            float sl = Float.parseFloat(list.get(2)) / 100;// 税率
            float kcs = Float.parseFloat(list.get(3));// 扣除数
            BigDecimal bg1 = new BigDecimal(sl).setScale(2, RoundingMode.HALF_UP);
            BigDecimal bg2 = new BigDecimal(kcs);
            return amount.subtract(cbAmount).multiply(bg1).subtract(bg2).setScale(2, RoundingMode.HALF_UP);
        }
        return new BigDecimal(0);

    }

    public String getCalculateHint1(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "个税=税前(超过4000元)x(1-20%)*税率-速算扣除数";
                break;
            case 2:
                result = "个税=税前*税率-速算扣除数";
                break;
            case 3:
                result = "应缴税额=税前*税率(20%)";
                break;
            case 4:
                result = "应缴纳税款=(税前-成本)*税率-速算扣除数";
                break;
        }
        return result;
    }

    public String getCalculateHint2(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "个税=(税前(不超过4000元)-800元)x税率(20%)";
                break;
            case 2:
            case 3:
            case 4:
                result = "";
                break;
        }
        return result;
    }
}
