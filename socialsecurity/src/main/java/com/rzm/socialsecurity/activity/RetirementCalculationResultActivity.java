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
import com.rzm.socialsecurity.presenter.RetirementCalculationResultPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.DateUtil;
import com.rzm.socialsecurity.view.IRetirementCalculationResultView;
import com.umeng.commonsdk.debug.D;

public class RetirementCalculationResultActivity extends MvpActivity<RetirementCalculationResultPresenter> implements IRetirementCalculationResultView {

    public ImageView ivBack;
    public TextView tvBack, tvTitle, tvDelayMonths, tvTuixiuYearMonth, tvTuixiuDate, diffYear;

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
    public RetirementCalculationResultPresenter createPresenter() {
        return new RetirementCalculationResultPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tuixiujs_result;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("计算结果");
        tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(v -> finish());

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

        Intent intent = getIntent();
        int year = intent.getIntExtra(ConstantConfig.year, 0);
        int month = intent.getIntExtra(ConstantConfig.month, 0);
        int day = intent.getIntExtra(ConstantConfig.day, 0);
        String sexOrType = intent.getStringExtra(ConstantConfig.sexOrType);

        tvDelayMonths = findViewById(R.id.tv_delayMonths);
        tvTuixiuYearMonth = findViewById(R.id.tv_tuixiu_year_month);
        tvTuixiuDate = findViewById(R.id.tv_tuixiu_date);
        diffYear = findViewById(R.id.diff_year);

        calculation(year, month, day, sexOrType);
    }

    public void calculation(int year, int month, int day, String sexOrType) {
        if (ConstantConfig.sexOrType_maleEmployee.equals(sexOrType)) {
            calculationMaleEmployee(year, month, day);
        } else if (ConstantConfig.sexOrType_femaleCadre.equals(sexOrType)) {
            calculationFemaleCadre(year, month, day);
        } else if (ConstantConfig.sexOrType_femaleEmployee.equals(sexOrType)) {
            calculationFemaleEmployee(year, month, day);
        }
    }

    public void calculationMaleEmployee(int year, int month, int day) {
        int delayMonths = 0;// 延迟月数
        int txYear = 0;//原来退休年
        int txMonth = 0;//原来退休月

        txYear = year + 60;
        txMonth = month;
        // 月份差
        int diffMonth = DateUtil.getMonthDifferenceWithCalendar(2025, 1, txYear, txMonth);
        delayMonths = (diffMonth / 4 + 1);
        if (delayMonths > 36) {
            delayMonths = 36;
        }
        tvDelayMonths.setText(delayMonths + "个月");
        // 求退休年月
        int totalMonth = txMonth + delayMonths;
        if (totalMonth > 12) {
            txYear = txYear + (totalMonth / 12);
            txMonth = totalMonth % 12;
            if (txMonth == 0) {
                txYear--;
                txMonth = 12;
            }
        } else if (totalMonth == 12) {
            txMonth = 12;
        } else {
            txMonth = totalMonth;
        }
        tvTuixiuDate.setText(txYear + "年" + txMonth + "月");
        // 求退休年龄
        int delayYear = delayMonths / 12;
        int delayMonth = delayMonths % 12;
        tvTuixiuYearMonth.setText((60 + delayYear) + "岁" + delayMonth + "月");
        // 求距离退休时间
        diffYear.setText((txYear - DateUtil.getYear()) + "年");
    }

    public void calculationFemaleCadre(int year, int month, int day) {
        int delayMonths = 0;// 延迟月数
        int txYear = 0;//原来退休年
        int txMonth = 0;//原来退休月

        txYear = year + 55;
        txMonth = month;
        // 月份差
        int diffMonth = DateUtil.getMonthDifferenceWithCalendar(2025, 1, txYear, txMonth);
        delayMonths = (diffMonth / 4 + 1);
        if (delayMonths > 36) {
            delayMonths = 36;
        }
        tvDelayMonths.setText(delayMonths + "个月");
        // 求退休年月
        int totalMonth = txMonth + delayMonths;
        if (totalMonth > 12) {
            txYear = txYear + (totalMonth / 12);
            txMonth = totalMonth % 12;
            if (txMonth == 0) {
                txYear--;
                txMonth = 12;
            }
        } else if (totalMonth == 12) {
            txMonth = 12;
        } else {
            txMonth = totalMonth;
        }
        tvTuixiuDate.setText(txYear + "年" + txMonth + "月");
        // 求退休年龄
        int delayYear = delayMonths / 12;
        int delayMonth = delayMonths % 12;
        tvTuixiuYearMonth.setText((55 + delayYear) + "岁" + delayMonth + "月");
        // 求距离退休时间
        diffYear.setText((txYear - DateUtil.getYear()) + "年");
    }

    public void calculationFemaleEmployee(int year, int month, int day) {
        int delayMonths = 0;// 延迟月数
        int txYear = 0;//原来退休年
        int txMonth = 0;//原来退休月

        txYear = year + 50;
        txMonth = month;
        // 月份差
        int diffMonth = DateUtil.getMonthDifferenceWithCalendar(2025, 1, txYear, txMonth);
        delayMonths = (diffMonth / 2 + 1);
        if (delayMonths > 60) {
            delayMonths = 60;
        }
        tvDelayMonths.setText(delayMonths + "个月");
        // 求退休年月
        int totalMonth = txMonth + delayMonths;
        if (totalMonth > 12) {
            txYear = txYear + (totalMonth / 12);
            txMonth = totalMonth % 12;
            if (txMonth == 0) {
                txYear--;
                txMonth = 12;
            }
        } else if (totalMonth == 12) {
            txMonth = 12;
        } else {
            txMonth = totalMonth;
        }
        tvTuixiuDate.setText(txYear + "年" + txMonth + "月");
        // 求退休年龄
        int delayYear = delayMonths / 12;
        int delayMonth = delayMonths % 12;
        tvTuixiuYearMonth.setText((50 + delayYear) + "岁" + delayMonth + "月");
        // 求距离退休时间
        diffYear.setText((txYear - DateUtil.getYear()) + "年");
    }
}
