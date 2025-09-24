package com.rzm.socialsecurity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.GSCalculateResultPresenter;
import com.rzm.socialsecurity.presenter.OtherTaxationCalculateResultPresenter;
import com.rzm.socialsecurity.util.TablelayoutUtil;
import com.rzm.socialsecurity.view.IGSCalculateResultView;
import com.rzm.socialsecurity.view.IOtherTaxationCalculateResultView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 其他税务计算结果页面
 */
public class OtherTaxationCalculateResultActivity extends MvpActivity<OtherTaxationCalculateResultPresenter> implements IOtherTaxationCalculateResultView {

    public ImageView ivBack;
    public TableLayout tbLSl;
    public TextView tvTitle,tvMoney, tvMoney1, tvSk,tvJsgs1,tvJsgs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public OtherTaxationCalculateResultPresenter createPresenter() {
        return new OtherTaxationCalculateResultPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_other_taxation_result_calculate;
    }

    @Override
    public void initView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        tbLSl = findViewById(R.id.tbL_sl);
        tvMoney = findViewById(R.id.tv_money);
        tvMoney1 = findViewById(R.id.tv_money1);
        tvSk = findViewById(R.id.tv_sk);

        tvJsgs1=findViewById(R.id.tv_jsgs1);
        tvJsgs2=findViewById(R.id.tv_jsgs2);
    }

    @Override
    public int getType() {
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);
        return type;
    }

    @Override
    public void initData(List<String> titles, List<List<String>> tableData) {
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);
        float amount = getIntent().getFloatExtra(ConstantConfig.amount, 0);
        float cbAmount = getIntent().getFloatExtra(ConstantConfig.cbAmount, 0);
        tvTitle.setText(getHint(type));
        tvTitle.setVisibility(View.VISIBLE);
        tvJsgs1.setText(presenter.getCalculateHint1(type));
        tvJsgs2.setText(presenter.getCalculateHint2(type));

        BigDecimal bg1 = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);// 收入
        // 应纳税额
        BigDecimal bg2 = presenter.calculateSk(type, bg1, new BigDecimal(cbAmount));
        // 本月税后所得
        BigDecimal bg8 = bg1.subtract(bg2).setScale(2, RoundingMode.HALF_UP);

        tvMoney.setText(String.valueOf(bg8));
        tvMoney1.setText(String.valueOf(bg1));
        tvSk.setText(String.valueOf(bg2));

        TablelayoutUtil.addTableRow(this, tbLSl, titles,
                tableData, getColor(R.color.gray), getColor(R.color.white),
                220, 80);
    }

    public String getHint(int type) {
        String name = "";
        switch (type) {
            case 1:
                name = "税后劳务报酬计算";
                break;
            case 2:
                name = "年终奖报酬计算";
                break;
            case 3:
                name = "股息分红报酬计算";
                break;
            case 4:
                name = "个体经营税务计算";
                break;
        }
        return name;
    }
}
