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
 * 公积金计算
 */
public class GJJCalculationActivity extends MvpActivity<GJJCalculationPresenter> implements IGJJCalculationView {
    public ImageView ivBack;
    public LinearLayout tvCalculate;
    public EditText etMonthMoney, edCompanyRate1, edCompanyRate2, edPersonalRate1, edPersonalRate2;

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
        return R.layout.activity_gjjjs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        etMonthMoney = findViewById(R.id.et_month_money);
        edCompanyRate1 = findViewById(R.id.ed_company_rate1);
        edCompanyRate2 = findViewById(R.id.ed_company_rate2);
        edPersonalRate1 = findViewById(R.id.ed_personal_rate1);
        edPersonalRate2 = findViewById(R.id.ed_personal_rate2);

        tvCalculate = findViewById(R.id.tv_calculate);
        tvCalculate.setOnClickListener(v -> {

            Intent intent = new Intent(this, GJJCalculationResultActivity.class);
            String monthMoney = etMonthMoney.getText().toString().trim();
            String companyRate1 = edCompanyRate1.getText().toString().trim();
            String companyRate2 = edCompanyRate2.getText().toString().trim();
            String personalRate1 = edPersonalRate1.getText().toString().trim();
            String personalRate2 = edPersonalRate2.getText().toString().trim();
            if (TextUtils.isEmpty(monthMoney)) {
                showToast("请先填写本月工资收入");
                return;
            }
            if (TextUtils.isEmpty(companyRate1)) {
                showToast("请先填写公积金企业缴纳比例");
                return;
            }
            if (TextUtils.isEmpty(personalRate1)) {
                showToast("请先填写公积金个人缴纳比例");
                return;
            }
            float monthMoneybl = 0;
            try {
                monthMoneybl = Float.parseFloat(monthMoney);
            } catch (Exception ignored) {

            }
            float companyRate1bl = 0;
            try {
                companyRate1bl = Float.parseFloat(companyRate1);
            } catch (Exception ignored) {

            }
            if (companyRate1bl > 20) {
                showToast("比例不能超过20");
                return;
            }
            float companyRate2bl = 0;
            try {
                companyRate2bl = Float.parseFloat(companyRate2);
            } catch (Exception ignored) {

            }
            if (companyRate2bl > 20) {
                showToast("比例不能超过20");
                return;
            }
            float personalRate1bl = 0;
            try {
                personalRate1bl = Float.parseFloat(personalRate1);
            } catch (Exception ignored) {

            }
            if (personalRate1bl > 20) {
                showToast("比例不能超过20");
                return;
            }
            float personalRate2bl = 0;
            try {
                personalRate2bl = Float.parseFloat(personalRate2);
            } catch (Exception ignored) {

            }
            if (personalRate2bl > 20) {
                showToast("比例不能超过20");
                return;
            }
            intent.putExtra(ConstantConfig.monthMoney, monthMoneybl);
            intent.putExtra(ConstantConfig.companyRate1bl, companyRate1bl);
            intent.putExtra(ConstantConfig.companyRate2bl, companyRate2bl);
            intent.putExtra(ConstantConfig.personalRate1bl, personalRate1bl);
            intent.putExtra(ConstantConfig.personalRate2bl, personalRate2bl);

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
