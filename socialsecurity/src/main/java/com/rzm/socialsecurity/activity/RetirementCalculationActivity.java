package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.github.gzuliyujiang.wheelpicker.DatePicker;
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.RetirementCalculationResultPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.DialogUtil;
import com.rzm.socialsecurity.view.IRetirementCalculationResultView;

import java.util.List;

/**
 * 退休计算
 */
public class RetirementCalculationActivity extends MvpActivity<RetirementCalculationResultPresenter> implements IRetirementCalculationResultView {

    public ImageView ivBack;
    public LinearLayout tvCalculate;
    public int selectYear, selectMonth, selectDay, selectSexTypePosition = -1;
    public TextView tvDate, tvSexType;

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
    public RetirementCalculationResultPresenter createPresenter() {
        return new RetirementCalculationResultPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tuixiujs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvCalculate = findViewById(R.id.tv_calculate);
        tvCalculate.setOnClickListener(v -> {
            if (selectYear < 1965) {
                showToast("请选择出生年月");
                return;
            }
            if (selectSexTypePosition < 0) {
                showToast("请选择性别及人员类型");
                return;
            }
            Intent intent = new Intent(this, RetirementCalculationResultActivity.class);
            intent.putExtra(ConstantConfig.year, selectYear);
            intent.putExtra(ConstantConfig.month, selectMonth);
            intent.putExtra(ConstantConfig.day, selectDay);
            intent.putExtra(ConstantConfig.sexOrType, selectSexTypePosition == 0 ? ConstantConfig.sexOrType_maleEmployee : (selectSexTypePosition == 1 ? ConstantConfig.sexOrType_femaleCadre : ConstantConfig.sexOrType_femaleEmployee));
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
//                    startActivity(intent);
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
        findViewById(R.id.ll_select_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCalendar();
            }
        });
        tvDate = findViewById(R.id.tv_date);
        findViewById(R.id.ll_select_sex_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexTypeDialog(presenter.getSexTypeData());
            }
        });
        tvSexType = findViewById(R.id.tv_sex_type);
        findViewById(R.id.tv_click_href).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetirementCalculationActivity.this, WebViewActivity.class);
                intent.putExtra(ConstantConfig.webType, 3);
                startActivity(intent);
            }
        });
    }

    public void selectCalendar() {
        // 创建日期选择器实例
        DatePicker datePicker = new DatePicker(this);
        DateWheelLayout dateWheelLayout = datePicker.getWheelLayout();
        dateWheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY);
        dateWheelLayout.setRange(DateEntity.target(1965, 1, 1), DateEntity.target(2099, 12, 31), DateEntity.today());
        dateWheelLayout.setResetWhenLinkage(false);
        datePicker.setOnDatePickedListener(new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
                LogUtils.i("选择的日期：" + year + ":" + month + ":" + day);
                selectYear = year;
                selectMonth = month;
                selectDay = day;
                refreshCalendarText();
            }
        });
        datePicker.show();
    }

    public void refreshCalendarText() {
        String month = selectMonth < 10 ? ("0" + selectMonth) : String.valueOf(selectMonth);
        String day = selectDay < 10 ? ("0" + selectDay) : String.valueOf(selectDay);
        tvDate.setText(selectYear + "-" + month + "-" + day);
    }

    public void showSexTypeDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                selectSexTypePosition = position;
                tvSexType.setText(value);
            }
        }, "性别及人员类型");
    }
}
