package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.mvp.MvpActivity;
import com.common.wheel.util.ImmersiveModeHelper;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.GJJCalculationPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IGJJCalculationView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GJJCalculationResultActivity extends MvpActivity<GJJCalculationPresenter> implements IGJJCalculationView {

    public ImageView ivBack;
    public TextView tvTitle, tv_total_amount, tv_company_total, tv_presonal_total, tv_company_total2, tv_presonal_total2,
            tv_presonal_rate, tv_presonal_rate2, tv_company_rate, tv_company_rate2,
            tv_company_money, tv_company_money2, tv_presonal_money, tv_presonal_money2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        ImmersiveModeHelper.setStatusBarMode(this, true);
        ADUtil.showInterstitialAd(this, ConstantConfig.AD_Interstitial, new InfoAdCallBack() {
            @Override
            public void onError() {

            }

            @Override
            public void onLoadSuccess() {

            }

            @Override
            public void onStartShow() {

            }

            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdVideoBarClick() {

            }

            @Override
            public void onAdClose() {

            }

            @Override
            public void onVideoComplete() {

            }

            @Override
            public void onSkippedVideo() {

            }
        });
    }

    @Override
    public GJJCalculationPresenter createPresenter() {
        return new GJJCalculationPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gjjjs_result;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("公积金计算结果");
        FrameLayout fl_info_ad = findViewById(R.id.fl_info_ad);
        ADUtil.showInfoFlowAd(this, ConstantConfig.AD_INFO, fl_info_ad, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
            @Override
            public void onError() {

            }

            @Override
            public void onFeedAdLoad() {

            }

            @Override
            public void onRenderSuccess() {

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onRenderFail() {

            }
        });
        tv_total_amount = findViewById(R.id.tv_total_amount);
        tv_company_total = findViewById(R.id.tv_company_total);
        tv_presonal_total = findViewById(R.id.tv_presonal_total);
        tv_company_total2 = findViewById(R.id.tv_company_total2);
        tv_presonal_total2 = findViewById(R.id.tv_presonal_total2);

        tv_presonal_rate = findViewById(R.id.tv_presonal_rate);
        tv_presonal_rate2 = findViewById(R.id.tv_presonal_rate2);
        tv_company_rate = findViewById(R.id.tv_company_rate);
        tv_company_rate2 = findViewById(R.id.tv_company_rate2);

        tv_company_money = findViewById(R.id.tv_company_money);
        tv_company_money2 = findViewById(R.id.tv_company_money2);
        tv_presonal_money = findViewById(R.id.tv_presonal_money);
        tv_presonal_money2 = findViewById(R.id.tv_presonal_money2);

        Intent intent = getIntent();
        float gongzi = intent.getFloatExtra(ConstantConfig.monthMoney, 0);
        float companyRate1bl = intent.getFloatExtra(ConstantConfig.companyRate1bl, 0);
        float companyRate2bl = intent.getFloatExtra(ConstantConfig.companyRate2bl, 0);
        float personalRate1bl = intent.getFloatExtra(ConstantConfig.personalRate1bl, 0);
        float personalRate2bl = intent.getFloatExtra(ConstantConfig.personalRate2bl, 0);

        tv_company_rate.setText("比例：" + companyRate1bl + "%");
        tv_company_rate2.setText("比例：" + companyRate2bl + "%");
        tv_presonal_rate.setText("比例：" + personalRate1bl + "%");
        tv_presonal_rate2.setText("比例：" + personalRate2bl + "%");

        BigDecimal money100 = new BigDecimal(100);
        BigDecimal money = new BigDecimal(gongzi);
        // 计算公积金
        BigDecimal companyMoney = money.multiply(new BigDecimal(companyRate1bl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_money.setText("金额：" + companyMoney);
        BigDecimal personalMoney = money.multiply(new BigDecimal(personalRate1bl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_presonal_money.setText("金额：" + personalMoney);
        //补充公积金
        BigDecimal companyMoney2 = money.multiply(new BigDecimal(companyRate2bl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_money2.setText("金额：" + companyMoney2);
        BigDecimal personalMoney2 = money.multiply(new BigDecimal(personalRate2bl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_presonal_money2.setText("金额：" + personalMoney2);

        // 全部总额
        tv_total_amount.setText(String.valueOf(companyMoney.add(personalMoney).add(companyMoney2).add(personalMoney2)));
        // 企业总额
        BigDecimal companyTotal = companyMoney.add(companyMoney2);
        tv_company_total.setText(String.valueOf(companyTotal));
        tv_company_total2.setText(String.valueOf(companyTotal));
        // 个人总额
        BigDecimal presonalTotal = personalMoney.add(personalMoney2);
        tv_presonal_total.setText(String.valueOf(presonalTotal));
        tv_presonal_total2.setText(String.valueOf(presonalTotal));
    }
}
