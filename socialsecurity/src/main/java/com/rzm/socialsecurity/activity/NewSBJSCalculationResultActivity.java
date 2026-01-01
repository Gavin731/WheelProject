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

public class NewSBJSCalculationResultActivity extends MvpActivity<GJJCalculationPresenter> implements IGJJCalculationView {

    public ImageView ivBack;
    public TextView tvTitle, tv_total_amount, tv_company_total, tv_presonal_total, tv_company_total2, tv_presonal_total2,
            tv_company_yanglao_money, tv_presonal_yanglao_money, tv_company_yiliao_money, tv_presonal_yiliao_money,
            tv_company_shiye_money,tv_presonal_shiye_money,tv_company_gongshang_money,
            tv_company_shengyu_money, tv_company_gjj_money, tv_presonal_gjj_money;

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
        return R.layout.activity_new_sbjs_result;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("社保计算结果");
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

        tv_company_yanglao_money = findViewById(R.id.tv_company_yanglao_money);
        tv_presonal_yanglao_money = findViewById(R.id.tv_presonal_yanglao_money);

        tv_company_yiliao_money = findViewById(R.id.tv_company_yiliao_money);
        tv_presonal_yiliao_money = findViewById(R.id.tv_presonal_yiliao_money);

        tv_company_shiye_money = findViewById(R.id.tv_company_shiye_money);
        tv_presonal_shiye_money = findViewById(R.id.tv_presonal_shiye_money);

        tv_company_gjj_money = findViewById(R.id.tv_company_gjj_money);
        tv_presonal_gjj_money = findViewById(R.id.tv_presonal_gjj_money);

        tv_company_gongshang_money = findViewById(R.id.tv_company_gongshang_money);
        tv_company_shengyu_money = findViewById(R.id.tv_company_shengyu_money);


        Intent intent = getIntent();
        float gongzi = intent.getFloatExtra(ConstantConfig.monthMoney, 0);
        BigDecimal money100 = new BigDecimal(100);
        BigDecimal money = new BigDecimal(gongzi);

        // 计算养老
        float companyYlbxRate = intent.getFloatExtra(ConstantConfig.companyYlbxRate, 0);
        float personalYlbxRatenl = intent.getFloatExtra(ConstantConfig.personalYlbxRatenl, 0);

        BigDecimal companyMoney = money.multiply(new BigDecimal(companyYlbxRate)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_yanglao_money.setText(String.valueOf(companyMoney));
        BigDecimal personalMoney = money.multiply(new BigDecimal(personalYlbxRatenl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_presonal_yanglao_money.setText(String.valueOf(personalMoney));
        // 医疗
        float companyYiliaoRatebl = intent.getFloatExtra(ConstantConfig.companyYiliaoRatebl, 0);
        float personalYiliaoRatebl = intent.getFloatExtra(ConstantConfig.personalYiliaoRatebl, 0);

        BigDecimal companyYiliaoMoney = money.multiply(new BigDecimal(companyYiliaoRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_yiliao_money.setText(String.valueOf(companyYiliaoMoney));
        BigDecimal personalYiliaoMoney = money.multiply(new BigDecimal(personalYiliaoRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_presonal_yiliao_money.setText(String.valueOf(personalYiliaoMoney));
        // 失业
        float companyShiyeRatebl = intent.getFloatExtra(ConstantConfig.companyShiyeRatebl, 0);
        float personalShiyeRatebl = intent.getFloatExtra(ConstantConfig.personalShiyeRatebl, 0);

        BigDecimal companyShiyeMoney = money.multiply(new BigDecimal(companyShiyeRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_shiye_money.setText(String.valueOf(companyShiyeMoney));
        BigDecimal personalShiyeMoney = money.multiply(new BigDecimal(personalShiyeRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_presonal_shiye_money.setText(String.valueOf(personalShiyeMoney));
        // 公积金
        float companyGjjRatebl = intent.getFloatExtra(ConstantConfig.companyGjjRatebl, 0);
        float personalGjjRatebl = intent.getFloatExtra(ConstantConfig.personalGjjRatebl, 0);

        BigDecimal companyGjjMoney = money.multiply(new BigDecimal(companyGjjRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_gjj_money.setText(String.valueOf(companyGjjMoney));
        BigDecimal personalGjjMoney = money.multiply(new BigDecimal(personalGjjRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_presonal_gjj_money.setText(String.valueOf(personalGjjMoney));
        // 工伤
        float companyGongshangRatebl = intent.getFloatExtra(ConstantConfig.companyGongshangRatebl, 0);

        BigDecimal companyGongshangMoney = money.multiply(new BigDecimal(companyGongshangRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_gongshang_money.setText(String.valueOf(companyGongshangMoney));
        // 生育
        float companyShengyuRatebl = intent.getFloatExtra(ConstantConfig.companyShengyuRatebl, 0);

        BigDecimal companyShengyuMoney = money.multiply(new BigDecimal(companyShengyuRatebl)).divide(money100, 2, RoundingMode.HALF_UP);
        tv_company_shengyu_money.setText(String.valueOf(companyShengyuMoney));

        // 企业总额
        BigDecimal companyTotal = companyMoney.add(companyYiliaoMoney).add(companyShiyeMoney).add(companyGjjMoney).add(companyGongshangMoney).add(companyShengyuMoney);
        tv_company_total.setText(String.valueOf(companyTotal));
        tv_company_total2.setText(String.valueOf(companyTotal));
        // 个人总额
        BigDecimal presonalTotal = personalMoney.add(personalYiliaoMoney).add(personalShiyeMoney).add(personalGjjMoney);
        tv_presonal_total.setText(String.valueOf(presonalTotal));
        tv_presonal_total2.setText(String.valueOf(presonalTotal));

        // 全部总额
        BigDecimal total =companyTotal.add(presonalTotal);
        tv_total_amount.setText(String.valueOf(total));

    }
}
