package com.rzm.socialsecurity.fragment;

import static android.view.View.VISIBLE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpFragment;
import com.common.wheel.util.GsonUtil;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.PayRecordDetailActivity;
import com.rzm.socialsecurity.activity.TaxGuideDetailActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.TaxGuidePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 缴费记录
 */
public class PayRecordFragment extends MvpFragment<TaxGuidePresenter> implements IBView {

    private static final String ARG_C = "content";
    public boolean isShow = false;
    public FrameLayout flInfoAdTax;
    public int selectYear;
    public TextView tvYear, tvYanglaoMoney, tvYiliaoMoney, tvShiyeMoney, tvGongshangMoney, tvShengyuMoney,
            tvYanglaoRate, tvYiliaoRate, tvShiyeRate, tvGongshangRate, tvShengyuRate,
            tvData1, tvData2, tvData3, tvData4, tvData5, tvData6, tvData7, tvData8, tvData9, tvData10, tvData11, tvData12;

    public static PayRecordFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        PayRecordFragment fragment = new PayRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public TaxGuidePresenter createPresenter() {
        return new TaxGuidePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pay_record;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser ) {
            if(!isShow){
                isShow = true;
                ADUtil.showInterstitialAd(getActivity(), ConstantConfig.AD_Interstitial, new InfoAdCallBack() {
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
                showInfoAd();
                refreshYearData();
            }
            if(flInfoAdTax!=null &&  flInfoAdTax.getVisibility() == VISIBLE){
                showInfoAd();
            }
        }

    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        flInfoAdTax = view.findViewById(R.id.fl_info_ad_tax);
        view.findViewById(R.id.tv_start_calculate).setOnClickListener(v -> {
            ADUtil.showRewardAd(getActivity(), ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    someActivityResultLauncher.launch(new Intent(getActivity(), PayRecordDetailActivity.class));
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

        selectYear = Calendar.getInstance().get(Calendar.YEAR);
        tvYear = view.findViewById(R.id.tv_year);

        view.findViewById(R.id.ll_left).setOnClickListener(v -> {
            selectYear--;
            refreshYearData();
        });
        view.findViewById(R.id.ll_right).setOnClickListener(v -> {
            selectYear++;
            refreshYearData();
        });
        tvYanglaoMoney = view.findViewById(R.id.tv_yanglao_money);
        tvYiliaoMoney = view.findViewById(R.id.tv_yiliao_money);
        tvShiyeMoney = view.findViewById(R.id.tv_shiye_money);
        tvGongshangMoney = view.findViewById(R.id.tv_gongshang_money);
        tvShengyuMoney = view.findViewById(R.id.tv_shengyu_money);

        tvYanglaoRate = view.findViewById(R.id.tv_yanglao_rate);
        tvYiliaoRate = view.findViewById(R.id.tv_yiliao_rate);
        tvShiyeRate = view.findViewById(R.id.tv_shiye_rate);
        tvGongshangRate = view.findViewById(R.id.tv_gongshang_rate);
        tvShengyuRate = view.findViewById(R.id.tv_shengyu_rate);

        tvData1 = view.findViewById(R.id.tv_data1);
        tvData2 = view.findViewById(R.id.tv_data2);
        tvData3 = view.findViewById(R.id.tv_data3);
        tvData4 = view.findViewById(R.id.tv_data4);
        tvData5 = view.findViewById(R.id.tv_data5);
        tvData6 = view.findViewById(R.id.tv_data6);
        tvData7 = view.findViewById(R.id.tv_data7);
        tvData8 = view.findViewById(R.id.tv_data8);
        tvData9 = view.findViewById(R.id.tv_data9);
        tvData10 = view.findViewById(R.id.tv_data10);
        tvData11 = view.findViewById(R.id.tv_data11);
        tvData12 = view.findViewById(R.id.tv_data12);

    }

    public void refreshYearData() {
        tvYear.setText(String.valueOf(selectYear));
        // 查询现有数据
        String jsonData = Hawk.get(ConstantConfig.record);
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
        // 找出当前5中类型的数据
        BigDecimal total = new BigDecimal(0);
        BigDecimal dataYanglao = new BigDecimal(0);
        BigDecimal dataYiliao = new BigDecimal(0);
        BigDecimal dataShiye = new BigDecimal(0);
        BigDecimal dataGongshang = new BigDecimal(0);
        BigDecimal dataShengyu = new BigDecimal(0);
        for (Map.Entry<String, String> entry : yearData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            total = total.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            if (key.indexOf("yanglao") > 0) {
                dataYanglao = dataYanglao.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("yiliao") > 0) {
                dataYiliao = dataYiliao.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            }
            if (key.indexOf("shiye") > 0) {
                dataShiye = dataShiye.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            }
            if (key.indexOf("gongshang") > 0) {
                dataGongshang = dataGongshang.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            }
            if (key.indexOf("shengyu") > 0) {
                dataShengyu = dataShengyu.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            }
        }
        tvYanglaoMoney.setText(String.valueOf(dataYanglao));
        tvYiliaoMoney.setText(String.valueOf(dataYiliao));
        tvShiyeMoney.setText(String.valueOf(dataShiye));
        tvGongshangMoney.setText(String.valueOf(dataGongshang));
        tvShengyuMoney.setText(String.valueOf(dataShengyu));

        if(total.floatValue() > 0){
            BigDecimal data100 = new BigDecimal(100);
            BigDecimal yanglaoRate = dataYanglao.divide(total, 4, RoundingMode.HALF_UP).multiply(data100).setScale(2, RoundingMode.HALF_UP);
            BigDecimal yiliaoRate = dataYiliao.divide(total, 4, RoundingMode.HALF_UP).multiply(data100).setScale(2, RoundingMode.HALF_UP);
            BigDecimal shiyeRate = dataShiye.divide(total, 4, RoundingMode.HALF_UP).multiply(data100).setScale(2, RoundingMode.HALF_UP);
            BigDecimal gongshangRate = dataGongshang.divide(total, 4, RoundingMode.HALF_UP).multiply(data100).setScale(2, RoundingMode.HALF_UP);
            BigDecimal shengyuRate = dataShengyu.divide(total, 4, RoundingMode.HALF_UP).multiply(data100).setScale(2, RoundingMode.HALF_UP);
            tvYanglaoRate.setText(String.valueOf(yanglaoRate) + "%");
            tvYiliaoRate.setText(String.valueOf(yiliaoRate) + "%");
            tvShiyeRate.setText(String.valueOf(shiyeRate) + "%");
            tvGongshangRate.setText(String.valueOf(gongshangRate) + "%");
            tvShengyuRate.setText(String.valueOf(shengyuRate) + "%");
        }else{
            tvYanglaoRate.setText("0%");
            tvYiliaoRate.setText("0%");
            tvShiyeRate.setText("0%");
            tvGongshangRate.setText("0%");
            tvShengyuRate.setText("0%");
        }


        // 找出1~12月的数据
        BigDecimal data1 = new BigDecimal(0);
        BigDecimal data2 = new BigDecimal(0);
        BigDecimal data3 = new BigDecimal(0);
        BigDecimal data4 = new BigDecimal(0);
        BigDecimal data5 = new BigDecimal(0);
        BigDecimal data6 = new BigDecimal(0);
        BigDecimal data7 = new BigDecimal(0);
        BigDecimal data8 = new BigDecimal(0);
        BigDecimal data9 = new BigDecimal(0);
        BigDecimal data10 = new BigDecimal(0);
        BigDecimal data11 = new BigDecimal(0);
        BigDecimal data12 = new BigDecimal(0);
        for (Map.Entry<String, String> entry : yearData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.indexOf("-01-") > 0) {
                data1 = data1.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-02-") > 0) {
                data2 = data2.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-03-") > 0) {
                data3 = data3.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-04-") > 0) {
                data4 = data4.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-05-") > 0) {
                data5 = data5.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-06-") > 0) {
                data6 = data6.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-07-") > 0) {
                data7 = data7.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-08-") > 0) {
                data8 = data8.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-09-") > 0) {
                data9 = data9.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-10-") > 0) {
                data10 = data10.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-11-") > 0) {
                data11 = data11.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            } else if (key.indexOf("-12-") > 0) {
                data12 = data12.add(new BigDecimal(value)).setScale(2, RoundingMode.HALF_UP);
            }
        }
        tvData1.setText(String.valueOf(data1));
        tvData2.setText(String.valueOf(data2));
        tvData3.setText(String.valueOf(data3));
        tvData4.setText(String.valueOf(data4));
        tvData5.setText(String.valueOf(data5));
        tvData6.setText(String.valueOf(data6));
        tvData7.setText(String.valueOf(data7));
        tvData8.setText(String.valueOf(data8));
        tvData9.setText(String.valueOf(data9));
        tvData10.setText(String.valueOf(data10));
        tvData11.setText(String.valueOf(data11));
        tvData12.setText(String.valueOf(data12));
    }

    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        refreshYearData();
                    }
                }
            });

    public void showInfoAd(){
        LogUtils.i("------aa3333");
        ADUtil.showInfoFlowAd(getActivity(), ConstantConfig.AD_INFO, flInfoAdTax, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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
