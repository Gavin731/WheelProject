package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.mvp.MvpActivity;
import com.common.wheel.util.GsonUtil;
import com.common.wheel.util.ImmersiveModeHelper;
import com.github.gzuliyujiang.wheelpicker.DatePicker;
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.MedicalCalculationPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IMedicalCalculationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MedicalCalculationDetailActivity extends MvpActivity<MedicalCalculationPresenter> implements IMedicalCalculationView {
    public FrameLayout flInfoAdTax;
    public ImageView ivBack;
    public TextView tvTitle, tvCalendar;
    public String type = "menzhen";
    public LinearLayout llMenzhen, llZhuyuan, llYaofei, llQita;
    public EditText etMonthMoney;
    public int selectYear, selectMonth, selectDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeHelper.setStatusBarMode(this, true);
        initView();
    }

    @Override
    public MedicalCalculationPresenter createPresenter() {
        return new MedicalCalculationPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yiliaobxjs_result_detail;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("添加缴费记录");

        flInfoAdTax = findViewById(R.id.fl_info_ad_tax);
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
        //            flInfoAdTax.removeAllViews();
        ADUtil.showInfoFlowAd(this, ConstantConfig.AD_INFO, flInfoAdTax, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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
        findViewById(R.id.ll_calendar).setOnClickListener(v -> selectCalendar());

        llMenzhen = findViewById(R.id.ll_menzhen);
        llMenzhen.setOnClickListener(v -> selectBxType("menzhen"));

        llZhuyuan = findViewById(R.id.ll_zhuyuan);
        llZhuyuan.setOnClickListener(v -> selectBxType("zhuyuan"));

        llYaofei = findViewById(R.id.ll_yaofei);
        llYaofei.setOnClickListener(v -> selectBxType("yaofei"));

        llQita = findViewById(R.id.ll_other);
        llQita.setOnClickListener(v -> selectBxType("qita"));

        etMonthMoney = findViewById(R.id.et_month_money);

        tvCalendar = findViewById(R.id.tv_calendar);
        Calendar calendar = Calendar.getInstance();
        selectYear = calendar.get(Calendar.YEAR);
        selectMonth = calendar.get(Calendar.MONTH) + 1;
        selectDay = calendar.get(Calendar.DAY_OF_MONTH);
        refreshCalendarText();
        findViewById(R.id.ll_save).setOnClickListener(v -> {
            String monthMoney = etMonthMoney.getText().toString().trim();
            if (TextUtils.isEmpty(monthMoney)) {
                showToast("请先填写金额");
                return;
            }
            float monthMoneybl = 0;
            try {
                monthMoneybl = Float.parseFloat(monthMoney);
            } catch (Exception ignored) {

            }
            saveData(monthMoneybl);
            setResult(Activity.RESULT_OK);
            finish();
        });
    }

    public void selectBxType(String bx) {
        this.type = bx;
        llMenzhen.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llZhuyuan.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llYaofei.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llQita.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        if ("menzhen".equals(bx)) {
            llMenzhen.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("zhuyuan".equals(bx)) {
            llZhuyuan.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("yaofei".equals(bx)) {
            llYaofei.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("qita".equals(bx)) {
            llQita.setBackground(getDrawable(R.mipmap.jlzc_k1));
        }
    }

    public void selectCalendar() {
        // 创建日期选择器实例
        DatePicker datePicker = new DatePicker(this);
        DateWheelLayout dateWheelLayout = datePicker.getWheelLayout();
        dateWheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY);
        dateWheelLayout.setRange(DateEntity.target(1990, 1, 1), DateEntity.target(2099, 12, 31), DateEntity.today());
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
        String month = String.valueOf(selectMonth);
        if (selectMonth < 10) {
            month = "0" + selectMonth;
        }
        String day = String.valueOf(selectDay);
        if (selectDay < 10) {
            day = "0" + selectDay;
        }
        tvCalendar.setText(selectYear + "-" + month + "-" + day);
    }

    public void saveData(float money) {
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
        String day = String.valueOf(selectDay);
        if (selectDay < 10) {
            day = "0" + selectDay;
        }

        String key = selectYear + "-" + month + "-" + day + "-" + type;
        // 获取原有的数据
        String orgMoney = data.get(key);
        if (TextUtils.isEmpty(orgMoney)) {
            data.put(key, String.valueOf(money));
        } else {
            BigDecimal bgMoney = new BigDecimal(orgMoney).add(new BigDecimal(money)).setScale(2, RoundingMode.HALF_UP);
            data.put(key, String.valueOf(bgMoney));
        }
        Hawk.put(ConstantConfig.yiliao_record, GsonUtil.formatObjectToJson(data));
    }
}
