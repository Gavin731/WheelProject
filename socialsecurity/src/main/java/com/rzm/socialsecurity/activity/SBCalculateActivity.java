package com.rzm.socialsecurity.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.presenter.SBCalculatePresenter;
import com.rzm.socialsecurity.view.ISBCalculateView;

/**
 * 社保计算页面
 */
public class SBCalculateActivity extends MvpActivity<SBCalculatePresenter>  implements ISBCalculateView {
    public ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public SBCalculatePresenter createPresenter() {
        return new SBCalculatePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sbjs;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
    }
}
