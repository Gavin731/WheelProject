package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.GJJCalculationPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IGJJCalculationView;

/**
 * 新的社保计算
 */
public class NewSbCalculationActivity extends MvpActivity<GJJCalculationPresenter> implements IGJJCalculationView {
    public ImageView ivBack;
    public LinearLayout tvCalculate;
    public EditText etMonthMoney, edCompanyYlbxRate, edPersonalYlbxRate, edCompanyYiliaoRate, edPersonalYiliaoRate,
            edCompanyShiyeRate, edPersonalShiyeRate, edCompanyShengyuRate, edCompanyGongshangRate,
            ed_company_gjj_rate, ed_personal_gjj_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
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
        return R.layout.activity_new_sbjs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        etMonthMoney = findViewById(R.id.et_month_money);
        edCompanyYlbxRate = findViewById(R.id.ed_company_ylbx_rate);
        edPersonalYlbxRate = findViewById(R.id.ed_personal_ylbx_rate);

        edCompanyYiliaoRate = findViewById(R.id.ed_company_yiliao_rate);
        edPersonalYiliaoRate = findViewById(R.id.ed_personal_yiliao_rate);

        edCompanyShiyeRate = findViewById(R.id.ed_company_shiye_rate);
        edPersonalShiyeRate = findViewById(R.id.ed_personal_shiye_rate);

        edCompanyShengyuRate = findViewById(R.id.ed_company_shengyu_rate);
        edCompanyGongshangRate = findViewById(R.id.ed_company_gongshang_rate);

        ed_company_gjj_rate= findViewById(R.id.ed_company_gjj_rate);
        ed_personal_gjj_rate= findViewById(R.id.ed_personal_gjj_rate);

        tvCalculate = findViewById(R.id.tv_calculate);
        tvCalculate.setOnClickListener(v -> {

            Intent intent = new Intent(this, NewSBJSCalculationResultActivity.class);

            String monthMoney = etMonthMoney.getText().toString().trim();
            String companyYlbxRate = edCompanyYlbxRate.getText().toString().trim();
            String personalYlbxRate = edPersonalYlbxRate.getText().toString().trim();

            String companyYiliaoRate = edCompanyYiliaoRate.getText().toString().trim();
            String personalYiliaoRate = edPersonalYiliaoRate.getText().toString().trim();

            String companyShiyeRate = edCompanyShiyeRate.getText().toString().trim();
            String personalShiyeRate = edPersonalShiyeRate.getText().toString().trim();

            String companyShengyuRate = edCompanyShengyuRate.getText().toString().trim();
            String companyGongshangRate = edCompanyGongshangRate.getText().toString().trim();

            String companyGjjRate=ed_company_gjj_rate.getText().toString().trim();
            String personalGjjRate=ed_personal_gjj_rate.getText().toString().trim();

            if (TextUtils.isEmpty(monthMoney)) {
                showToast("请先填写缴纳基数");
                return;
            }
            if (TextUtils.isEmpty(companyYlbxRate)) {
                showToast("请先填写单位养老保险比例");
                return;
            }
            if (TextUtils.isEmpty(personalYlbxRate)) {
                showToast("请先填写个人养老保险比例");
                return;
            }
            if (TextUtils.isEmpty(companyYiliaoRate)) {
                showToast("请先填写单位医疗保险比例");
                return;
            }
            if (TextUtils.isEmpty(personalYiliaoRate)) {
                showToast("请先填写个人医疗保险比例");
                return;
            }
            if (TextUtils.isEmpty(companyShiyeRate)) {
                showToast("请先填写单位失业保险比例");
                return;
            }
            if (TextUtils.isEmpty(personalShiyeRate)) {
                showToast("请先填写个人失业保险比例");
                return;
            }
            if (TextUtils.isEmpty(companyGjjRate)) {
                showToast("请先填写单位公积金比例");
                return;
            }
            if (TextUtils.isEmpty(personalGjjRate)) {
                showToast("请先填写个人公积金比例");
                return;
            }
            if (TextUtils.isEmpty(companyShengyuRate)) {
                showToast("请先填写单位生育保险比例");
                return;
            }
            if (TextUtils.isEmpty(companyGongshangRate)) {
                showToast("请先填写单位工伤保险比例");
                return;
            }

            float monthMoneybl = 0;
            try {
                monthMoneybl = Float.parseFloat(monthMoney);
            } catch (Exception ignored) {

            }
            float companyYlbxRatebl = 0;
            try {
                companyYlbxRatebl = Float.parseFloat(companyYlbxRate);
            } catch (Exception ignored) {

            }
//            if (companyYlbxRatebl > 20) {
//                showToast("比例不能超过20");
//                return;
//            }
            float personalYlbxRatenl = 0;
            try {
                personalYlbxRatenl = Float.parseFloat(personalYlbxRate);
            } catch (Exception ignored) {

            }
            float companyYiliaoRatebl = 0;
            try {
                companyYiliaoRatebl = Float.parseFloat(companyYiliaoRate);
            } catch (Exception ignored) {

            }
            float personalYiliaoRatebl = 0;
            try {
                personalYiliaoRatebl = Float.parseFloat(personalYiliaoRate);
            } catch (Exception ignored) {

            }

            float companyShiyeRatebl = 0;
            try {
                companyShiyeRatebl = Float.parseFloat(companyShiyeRate);
            } catch (Exception ignored) {

            }

            float personalShiyeRatebl = 0;
            try {
                personalShiyeRatebl = Float.parseFloat(personalShiyeRate);
            } catch (Exception ignored) {

            }

            float companyGjjRatebl=0;
            try {
                companyGjjRatebl=Float.parseFloat(companyGjjRate);
            } catch (Exception ignored) {

            }
            float personalGjjRatebl=0;
            try {
                personalGjjRatebl=Float.parseFloat(personalGjjRate);
            }catch (Exception ignored) {

            }
            float companyShengyuRatebl = 0;
            try {
                companyShengyuRatebl = Float.parseFloat(companyShengyuRate);
            } catch (Exception ignored) {

            }

            float companyGongshangRatebl = 0;
            try {
                companyGongshangRatebl = Float.parseFloat(companyGongshangRate);
            } catch (Exception ignored) {

            }
            intent.putExtra(ConstantConfig.monthMoney, monthMoneybl);
            intent.putExtra(ConstantConfig.companyYlbxRate, companyYlbxRatebl);
            intent.putExtra(ConstantConfig.personalYlbxRatenl, personalYlbxRatenl);
            intent.putExtra(ConstantConfig.companyYiliaoRatebl, companyYiliaoRatebl);
            intent.putExtra(ConstantConfig.personalYiliaoRatebl, personalYiliaoRatebl);
            intent.putExtra(ConstantConfig.companyShiyeRatebl, companyShiyeRatebl);
            intent.putExtra(ConstantConfig.personalShiyeRatebl, personalShiyeRatebl);
            intent.putExtra(ConstantConfig.companyShengyuRatebl, companyShengyuRatebl);
            intent.putExtra(ConstantConfig.companyGongshangRatebl, companyGongshangRatebl);
            intent.putExtra(ConstantConfig.companyGjjRatebl, companyGjjRatebl);
            intent.putExtra(ConstantConfig.personalGjjRatebl, personalGjjRatebl);

            ADUtil.showRewardAd(this, ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {

                    startActivity(intent);
                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void onAdVideoBarClick() {

                }

                @Override
                public void onVideoError() {

                }

                @Override
                public void onRewardArrived() {

                }

                @Override
                public void onSkippedVideo() {

                }

                @Override
                public void onAdShow() {

                }

                @Override
                public void onError() {

                }
            });
        });
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
    }
}
