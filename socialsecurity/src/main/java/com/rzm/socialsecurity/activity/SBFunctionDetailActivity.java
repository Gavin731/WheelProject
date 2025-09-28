package com.rzm.socialsecurity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.SBFunctionDetailPresenter;
import com.rzm.socialsecurity.view.ISBFunctionDetailView;

public class SBFunctionDetailActivity extends MvpActivity<SBFunctionDetailPresenter>  implements ISBFunctionDetailView {
    public ImageView ivBack;
    public TextView tvTitle;
    public LinearLayout llDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public SBFunctionDetailPresenter createPresenter() {
        return new SBFunctionDetailPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sbzy_detail;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        tvTitle = findViewById(R.id.tv_title);
        llDesc = findViewById(R.id.ll_desc);
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);
        tvTitle.setText(presenter.getTitle(type));
        View view = getDesc(type);
        llDesc.addView(view);
    }
    public View getDesc(int type){
        View view= null;
        switch (type){
            case 1:
                view = View.inflate(this, R.layout.view_ylbx_detail, null);
                break;
            case 2:
                view = View.inflate(this, R.layout.view_yiliaobx_detail, null);
                break;
            case 3:
                view = View.inflate(this, R.layout.view_shiyehx_detail, null);
                break;
            case 4:
                view = View.inflate(this, R.layout.view_gongshangbx_detail, null);
                break;
            case 5:
                view = View.inflate(this, R.layout.view_shengyubx_detail, null);
                break;
        }
        return view;
    }


}
