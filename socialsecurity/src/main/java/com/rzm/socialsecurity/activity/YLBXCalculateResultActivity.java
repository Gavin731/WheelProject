package com.rzm.socialsecurity.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    public TextView tvBack, totalAmount, companyAmount, personalAmount,tvTitle;

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
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle=findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(getTitle(type));

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

    public String getTitle(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "养老保险计算结果";
                break;
            case 2:
                result = "医疗保险计算结果";
                break;
            case 3:
                result = "失业保险计算结果";
                break;
            case 4:
                result = "工伤保险计算结果";
                break;
            case 5:
                result = "生育保险计算结果";
                break;
        }
        return result;
    }
}
