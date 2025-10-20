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
import com.rzm.socialsecurity.presenter.TaxGuidePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PayRecordDetailActivity extends MvpActivity<TaxGuidePresenter> implements IBView {

    public FrameLayout flInfoAdTax;
    public ImageView ivBack;
    public TextView tvTitle, tvCalendar;
    public String type = "yanglao";
    public LinearLayout llYanglao, llYiliao, llShiye, llGongshang, llShengyu;
    public EditText etMonthMoney;
    public int selectYear, selectMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeHelper.setStatusBarMode(this, true);
        initView();
    }

    @Override
    public TaxGuidePresenter createPresenter() {
        return new TaxGuidePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_record_detail;
    }

    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("记录支出");

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

        llYanglao = findViewById(R.id.ll_yanglao);
        llYanglao.setOnClickListener(v -> selectBxType("yanglao"));

        llYiliao = findViewById(R.id.ll_yiliao);
        llYiliao.setOnClickListener(v -> selectBxType("yiliao"));

        llShiye = findViewById(R.id.ll_shiye);
        llShiye.setOnClickListener(v -> selectBxType("shiye"));

        llGongshang = findViewById(R.id.ll_gongshang);
        llGongshang.setOnClickListener(v -> selectBxType("gongshang"));

        llShengyu = findViewById(R.id.ll_shengyu);
        llShengyu.setOnClickListener(v -> selectBxType("shengyu"));

        etMonthMoney = findViewById(R.id.et_month_money);

        tvCalendar = findViewById(R.id.tv_calendar);
        Calendar calendar = Calendar.getInstance();
        selectYear = calendar.get(Calendar.YEAR);
        selectMonth = calendar.get(Calendar.MONTH)+1;
        refreshCalendarText();
        findViewById(R.id.ll_save).setOnClickListener(v->{
            String monthMoney = etMonthMoney.getText().toString().trim();
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
        llYanglao.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llYiliao.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llShiye.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llGongshang.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        llShengyu.setBackground(getDrawable(R.mipmap.wdsbzd_k1));
        if ("yanglao".equals(bx)) {
            llYanglao.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("yiliao".equals(bx)) {
            llYiliao.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("shiye".equals(bx)) {
            llShiye.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("gongshang".equals(bx)) {
            llGongshang.setBackground(getDrawable(R.mipmap.jlzc_k1));
        } else if ("shengyu".equals(bx)) {
            llShengyu.setBackground(getDrawable(R.mipmap.jlzc_k1));
        }
    }

    public void selectCalendar() {
        // 创建日期选择器实例
        DatePicker datePicker = new DatePicker(this);
        DateWheelLayout dateWheelLayout = datePicker.getWheelLayout();
        dateWheelLayout.setDateMode(DateMode.YEAR_MONTH);
        dateWheelLayout.setRange(DateEntity.target(1990, 1, 1), DateEntity.target(2099, 12, 31), DateEntity.today());
        dateWheelLayout.setResetWhenLinkage(false);
        datePicker.setOnDatePickedListener(new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
                LogUtils.i("选择的日期：" + year + ":" + month + ":" + day);
                selectYear = year;
                selectMonth = month;
                refreshCalendarText();
            }
        });
        datePicker.show();
    }

    public void refreshCalendarText() {
        if (selectMonth < 10) {
            tvCalendar.setText(selectYear + "-0" + selectMonth);
        } else {
            tvCalendar.setText(selectYear + "-" + selectMonth);
        }
    }

    public void saveData(float money){
        // 查询现有数据
        String jsonData = Hawk.get(ConstantConfig.record);
        Map<String,Float> data = new HashMap<>();
        if(!TextUtils.isEmpty(jsonData)){
            data = GsonUtil.parseJsonToMap(jsonData);
        }
        String key="";
        if (selectMonth < 10) {
            key = selectYear+"-0"+selectMonth+"-"+type;
        }else{
            key = selectYear+"-"+selectMonth+"-"+type;
        }

        data.put(key, money);
        Hawk.put(ConstantConfig.record, GsonUtil.formatObjectToJson(data));
    }
}
