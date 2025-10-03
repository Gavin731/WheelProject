package com.rzm.socialsecurity.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.SBManageOrSupervisePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISBManageOrSuperviseView;

/**
 * 社保的管理和监督
 */
public class SBManageOrSuperviseActivity extends MvpActivity<SBManageOrSupervisePresenter> implements ISBManageOrSuperviseView {

    public ImageView ivBack;
    public TextView tvSbManager,tvSbSupervise;
    public LinearLayout sbManager, sbSupervise;
    public View vSbManager, vSbSupervise;

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
    public SBManageOrSupervisePresenter createPresenter() {
        return new SBManageOrSupervisePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sb_manager_supervise;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        sbManager = findViewById(R.id.sb_manager);
        sbSupervise = findViewById(R.id.sb_supervise);

        vSbManager=findViewById(R.id.v_sb_manager);
        vSbSupervise=findViewById(R.id.v_sb_supervise);

        tvSbManager = findViewById(R.id.tv_sb_manager);
        tvSbManager.setOnClickListener(v -> {
            sbManager.setVisibility(VISIBLE);
            vSbManager.setVisibility(VISIBLE);
            sbSupervise.setVisibility(GONE);
            vSbSupervise.setVisibility(GONE);
            tvSbManager.setTextColor(getColor(R.color.color_ff333333));
            tvSbSupervise.setTextColor(getColor(R.color.color_ff999999));
        });
        tvSbSupervise = findViewById(R.id.tv_sb_supervise);
        tvSbSupervise.setOnClickListener(v -> {
            sbManager.setVisibility(GONE);
            vSbManager.setVisibility(GONE);
            sbSupervise.setVisibility(VISIBLE);
            vSbSupervise.setVisibility(VISIBLE);
            tvSbSupervise.setTextColor(getColor(R.color.color_ff333333));
            tvSbManager.setTextColor(getColor(R.color.color_ff999999));
        });
    }
}
