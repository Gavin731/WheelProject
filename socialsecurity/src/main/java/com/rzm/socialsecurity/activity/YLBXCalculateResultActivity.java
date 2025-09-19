package com.rzm.socialsecurity.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.presenter.YLBXCalculateResultPresenter;
import com.rzm.socialsecurity.view.IYLBXCalculateResultView;

/**
 * 养老保险计算结果页面
 */
public class YLBXCalculateResultActivity extends MvpActivity<YLBXCalculateResultPresenter> implements IYLBXCalculateResultView {
    public ImageView ivBack;
    public TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public YLBXCalculateResultPresenter createPresenter() {
        return new YLBXCalculateResultPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ylbxjs_result;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(v -> finish());
    }
}
