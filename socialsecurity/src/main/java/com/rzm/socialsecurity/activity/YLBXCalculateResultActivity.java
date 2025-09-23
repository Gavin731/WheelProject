package com.rzm.socialsecurity.activity;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.YLBXCalculateResultPresenter;
import com.rzm.socialsecurity.view.IYLBXCalculateResultView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 养老保险计算结果页面
 */
public class YLBXCalculateResultActivity extends MvpActivity<YLBXCalculateResultPresenter> implements IYLBXCalculateResultView {
    public ImageView ivBack;
    public TextView tvBack, totalAmount, companyAmount, personalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public YLBXCalculateResultPresenter createPresenter() {
        return new YLBXCalculateResultPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ylbxjs_result;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(v -> finish());
        totalAmount = findViewById(R.id.total_amount);
        companyAmount = findViewById(R.id.company_amount);
        personalAmount = findViewById(R.id.personal_amount);

        Intent intent = getIntent();
        boolean isHidePersonalText = intent.getBooleanExtra(ConstantConfig.isHidePersonalText, false);
        if (isHidePersonalText) {
            findViewById(R.id.tv_personal).setVisibility(GONE);
            findViewById(R.id.ll_personal).setVisibility(GONE);
        }
        String cardinalNumberText = intent.getStringExtra(ConstantConfig.cardinalNumberText);
        String companyText = intent.getStringExtra(ConstantConfig.companyText);
        String personalText = intent.getStringExtra(ConstantConfig.personalText);
        // 计算
        BigDecimal bgCardinalNumber = new BigDecimal(cardinalNumberText);
        BigDecimal bgCompany = new BigDecimal(companyText);
        BigDecimal bgPersonal = new BigDecimal(personalText);
        BigDecimal num = new BigDecimal(100);
        // 单位缴纳
        BigDecimal company = bgCardinalNumber.multiply(bgCompany).divide(num, 2, RoundingMode.HALF_UP);
        companyAmount.setText(String.valueOf(company));
        //个人缴纳
        BigDecimal personal = bgCardinalNumber.multiply(bgPersonal).divide(num, 2, RoundingMode.HALF_UP);
        personalAmount.setText(String.valueOf(personal));
        // 总缴纳
        BigDecimal total = company.add(personal).setScale(2, RoundingMode.HALF_UP);
        totalAmount.setText(String.valueOf(total));
    }
}
