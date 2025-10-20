package com.rzm.socialsecurity.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.PayRecordDetailActivity;
import com.rzm.socialsecurity.activity.TaxGuideDetailActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.TaxGuidePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

import java.util.Calendar;

/**
 * 缴费记录
 */
public class PayRecordFragment extends MvpFragment<TaxGuidePresenter> implements IBView {

    private static final String ARG_C = "content";
    public boolean isShow = false;
    public FrameLayout flInfoAdTax;
    public int selectYear;
    public TextView tvYear;

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
        if (isVisibleToUser && !isShow) {
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
                    startActivity(new Intent(getActivity(), PayRecordDetailActivity.class));
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
        refreshYearData();


        view.findViewById(R.id.ll_left).setOnClickListener(v->{
            selectYear--;
            refreshYearData();
        });
        view.findViewById(R.id.ll_right).setOnClickListener(v->{
            selectYear++;
            refreshYearData();
        });
    }

    public void refreshYearData(){
        tvYear.setText(String.valueOf(selectYear));
    }
}
