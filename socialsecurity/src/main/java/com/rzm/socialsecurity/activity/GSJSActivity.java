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

public class GSJSActivity extends MvpActivity<GJJCalculationPresenter> implements IGJJCalculationView {

    public ImageView ivBack;
    public EditText etGsMonthMoney, etGsSbMoney, etGsZxkcMoney;
    public LinearLayout tvGsCalculate;

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
        return R.layout.activity_gsjs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        etGsMonthMoney = findViewById(R.id.et_gs_month_money);
        etGsSbMoney = findViewById(R.id.et_gs_sb_money);
        etGsZxkcMoney = findViewById(R.id.et_gs_zxkc_money);

        tvGsCalculate = findViewById(R.id.tv_gs_calculate);
        tvGsCalculate.setOnClickListener(v -> {
            String monthMoney=etGsMonthMoney.getText().toString().trim();
            String sbMoney=etGsSbMoney.getText().toString().trim();
            String zxkcMoney=etGsZxkcMoney.getText().toString().trim();
            if(TextUtils.isEmpty(monthMoney)){
                showToast("请先填写本月工资收入");
                return;
            }
            if(TextUtils.isEmpty(sbMoney)){
                sbMoney="0";
//                showToast("请先填写五险一金");
//                return;
            }
            if(TextUtils.isEmpty(zxkcMoney)){
                zxkcMoney = "0";
//                showToast("请先填写专项附加扣除");
//                return;
            }
            float monthMoneybl = 0;
            try {
                monthMoneybl = Float.parseFloat(monthMoney);
            }catch (Exception ignored){

            }
            float sbMoneybl = 0;
            try {
                sbMoneybl = Float.parseFloat(sbMoney);
            }catch (Exception ignored){

            }
            float zxkcMoneybl = 0;
            try {
                zxkcMoneybl = Float.parseFloat(zxkcMoney);
            }catch (Exception ignored){

            }

            Intent intent = new Intent(this, GSCalculateResultActivity.class);
            intent.putExtra(ConstantConfig.monthMoney, monthMoneybl);
            intent.putExtra(ConstantConfig.sbgrMoney, sbMoneybl);
            intent.putExtra(ConstantConfig.zxkcMoney, zxkcMoneybl);

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
