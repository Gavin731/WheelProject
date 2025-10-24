package com.rzm.socialsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.common.wheel.util.GsonUtil;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.adapter.MedicalCalculationAdapter;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.MedicalCalculationEntity;
import com.rzm.socialsecurity.presenter.MedicalCalculationPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.DateUtil;
import com.rzm.socialsecurity.view.IMedicalCalculationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医疗账单记录
 */
public class MedicalCalculationActivity extends MvpActivity<MedicalCalculationPresenter> implements IMedicalCalculationView {

    public ImageView ivBack;
    public int selectYear, selectMonth;
    public TextView tvYear, tvMonth, tvMoney, tvDate;
    public RecyclerView rvList;
    public MedicalCalculationAdapter adapter;

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
    public MedicalCalculationPresenter createPresenter() {
        return new MedicalCalculationPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yiliaobxjs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        findViewById(R.id.tv_calculate).setOnClickListener(v -> {

            Intent intent = new Intent(this, MedicalCalculationDetailActivity.class);
            ADUtil.showRewardAd(this, ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    someActivityResultLauncher.launch(intent);
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
                    // todo zeng
                    someActivityResultLauncher.launch(intent);
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
        tvMoney = findViewById(R.id.tv_money);
        tvDate = findViewById(R.id.tv_date);
        rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicalCalculationAdapter(R.layout.view_medical);
        rvList.setAdapter(adapter);

        selectYear = Calendar.getInstance().get(Calendar.YEAR);
        selectMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        tvYear = findViewById(R.id.tv_year);
        tvMonth = findViewById(R.id.tv_month);
        refreshYearData();
        refreshMonthData();
        findViewById(R.id.ll_left).setOnClickListener(v -> {
            selectYear--;
            refreshYearData();
            refreshMonthData();
        });
        findViewById(R.id.ll_right).setOnClickListener(v -> {
            selectYear++;
            refreshYearData();
            refreshMonthData();
        });

        findViewById(R.id.ll_left_month).setOnClickListener(v -> {
            if (selectMonth == 1) {
                return;
            }
            selectMonth--;
            refreshMonthData();
        });
        findViewById(R.id.ll_right_month).setOnClickListener(v -> {
            if (selectMonth >= 12) {
                return;
            }
            selectMonth++;
            refreshMonthData();
        });
    }

    public void refreshYearData() {
        tvYear.setText(String.valueOf(selectYear));

        // 查询现有数据
        String jsonData = Hawk.get(ConstantConfig.yiliao_record);
        Map<String, String> data = new HashMap<>();
        if (!TextUtils.isEmpty(jsonData)) {
            data = GsonUtil.parseJsonToMapString(jsonData);
        }
        Map<String, String> yearData = new HashMap<>();
        // 找出当前年份的数据
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.indexOf(String.valueOf(selectYear)) == 0) {
                yearData.put(key, value);
            }
        }

        // 计算年度总费用
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<String, String> entry : yearData.entrySet()) {
            String value = entry.getValue();
            total = total.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
        }
        tvMoney.setText(String.valueOf(total));

        // 获取当日
        Calendar calendar = Calendar.getInstance();
        String strDate = DateUtil.dateToString(calendar.getTime(), "yyyy-MM-dd");
        tvDate.setText("更新至：" + strDate);
    }

    public void refreshMonthData() {
        tvMonth.setText(String.valueOf(selectMonth));
        // 查询现有数据
        String jsonData = Hawk.get(ConstantConfig.yiliao_record);
        Map<String, String> data = new HashMap<>();
        if (!TextUtils.isEmpty(jsonData)) {
            data = GsonUtil.parseJsonToMapString(jsonData);
        }
        String month = String.valueOf(selectMonth);
        if (selectMonth < 10) {
            month = "0" + selectMonth;
        }
        String orgkey = selectYear + "-" + month;
        Map<String, String> monthData = new HashMap<>();
        // 找出当前年，月份的数据
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.indexOf(orgkey) == 0) {
                monthData.put(key, value);
            }
        }

        // 计算月度总费用
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<String, String> entry : monthData.entrySet()) {
            String value = entry.getValue();
            total = total.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
        }

        List<MedicalCalculationEntity> dataList = new ArrayList<>();
        BigDecimal data100 = new BigDecimal(100);
        for (Map.Entry<String, String> entry : monthData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            MedicalCalculationEntity entity = new MedicalCalculationEntity();
            entity.setType(getType(key));
            entity.setAmount(value);
            BigDecimal rate = new BigDecimal(value).divide(total, 4, RoundingMode.HALF_UP).multiply(data100).setScale(2, RoundingMode.HALF_UP);
            entity.setRate(rate + "%");
            dataList.add(entity);
        }

        adapter.setNewData(dataList);
    }

    public String getType(String key) {
        if (key.indexOf("menzhen") >= 0) {
            return "门诊费";
        }
        if (key.indexOf("zhuyuan") >= 0) {
            return "住院费";
        }
        if (key.indexOf("yaofei") >= 0) {
            return "药费";
        }
        if (key.indexOf("qita") >= 0) {
            return "其他费用";
        }
        return "";
    }

    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        refreshYearData();
                        refreshMonthData();
                    }
                }
            });
}
