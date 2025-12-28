package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.mvp.IBaseView;
import com.common.wheel.mvp.MvpActivity;
import com.common.wheel.mvp.MvpPresenter;
import com.common.wheel.util.ImmersiveModeHelper;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.SBFunctionPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISBFunctionDetailView;
import com.rzm.socialsecurity.view.ISBFunctionView;

public class ZDGZActivity extends MvpActivity<SBFunctionPresenter> implements ISBFunctionView {
    public ImageView ivBack, im_content;
    public TextView tvTitle;

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
    public SBFunctionPresenter createPresenter() {
        return new SBFunctionPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zdgz;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("");

        im_content = findViewById(R.id.im_content);
        Glide.with(this).load(R.mipmap.zdgz).into(im_content);

    }
}
