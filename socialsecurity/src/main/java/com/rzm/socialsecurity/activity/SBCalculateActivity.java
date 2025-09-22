package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.SBCalculatePresenter;
import com.rzm.socialsecurity.view.ISBCalculateView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 社保计算页面
 */
public class SBCalculateActivity extends MvpActivity<SBCalculatePresenter>  implements ISBCalculateView {
    public ImageView ivBack;
    public TextView tv_month_money,tv_sb_money, tv_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public SBCalculatePresenter createPresenter() {
        return new SBCalculatePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sbjs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tv_month_money = findViewById(R.id.tv_month_money);
        tv_sb_money = findViewById(R.id.tv_sb_money);
        tv_money = findViewById(R.id.tv_money);

        float monthMoney=getIntent().getFloatExtra(ConstantConfig.monthMoney, 0);
        float sbgrMoney=getIntent().getFloatExtra(ConstantConfig.sbgrMoney, 0);
        BigDecimal bg1=new BigDecimal(monthMoney).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg2=new BigDecimal(sbgrMoney).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg3 = bg1.subtract(bg2).setScale(2, RoundingMode.HALF_UP);
        tv_month_money.setText(String.valueOf(bg1));
        tv_sb_money.setText(String.valueOf(bg2));
        tv_money.setText(String.valueOf(bg3));
    }
}
