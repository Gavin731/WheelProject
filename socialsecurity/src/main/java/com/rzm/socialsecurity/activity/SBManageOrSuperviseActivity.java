package com.rzm.socialsecurity.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.presenter.SBManageOrSupervisePresenter;
import com.rzm.socialsecurity.view.ISBManageOrSuperviseView;

/**
 * 社保的管理和监督
 */
public class SBManageOrSuperviseActivity extends MvpActivity<SBManageOrSupervisePresenter> implements ISBManageOrSuperviseView {

    public ImageView ivBack;
    public TextView tvSbManager,tvSbSupervise;
    public LinearLayout sbManager, sbSupervise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
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

        tvSbManager = findViewById(R.id.tv_sb_manager);
        tvSbManager.setOnClickListener(v -> {
            sbManager.setVisibility(VISIBLE);
            sbSupervise.setVisibility(GONE);
        });
        tvSbSupervise = findViewById(R.id.tv_sb_supervise);
        tvSbSupervise.setOnClickListener(v -> {
            sbManager.setVisibility(GONE);
            sbSupervise.setVisibility(VISIBLE);
        });
    }
}
