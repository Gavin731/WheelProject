package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.SBFunctionPresenter;
import com.rzm.socialsecurity.view.ISBFunctionView;

/**
 * 社保的作用页面
 */
public class SBFunctionActivity  extends MvpActivity<SBFunctionPresenter> implements ISBFunctionView {
    public ImageView ivBack;
    public LinearLayout llYanglao, llYiliao, llShiye, llGongshang,llShengyu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public SBFunctionPresenter createPresenter() {
        return new SBFunctionPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sbzy;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        llYanglao = findViewById(R.id.ll_yanglao);
        llYanglao.setOnClickListener(v -> jumpDetailPage(1));
        llYiliao = findViewById(R.id.ll_yiliao);
        llYiliao.setOnClickListener(v -> jumpDetailPage(2));
        llShiye = findViewById(R.id.ll_shiye);
        llShiye.setOnClickListener(v -> jumpDetailPage(3));
        llGongshang = findViewById(R.id.ll_gongshang);
        llGongshang.setOnClickListener(v -> jumpDetailPage(4));
        llShengyu = findViewById(R.id.ll_shengyu);
        llShengyu.setOnClickListener(v -> jumpDetailPage(5));
    }

    public void jumpDetailPage(int type){
        Intent intent = new Intent(this, SBFunctionDetailActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }
}
