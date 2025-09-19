package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.YLBXCalculatePresenter;
import com.rzm.socialsecurity.view.IYLBXCalculateView;

/**
 * 养老、医疗、失业保险计算页面
 */
public class YLBXCalculateActivity extends MvpActivity<YLBXCalculatePresenter> implements IYLBXCalculateView {

    public ImageView ivBack;
    public TextView tvJnjs, tvCalculate, tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public YLBXCalculatePresenter createPresenter() {
        return new YLBXCalculatePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ylbxjs;
    }

    @Override
    public void initView() {

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvName = findViewById(R.id.tv_name);
        tvName.setText(getHint());
        tvJnjs = findViewById(R.id.tv_jnjs);
        tvJnjs.setOnClickListener(v -> showHintDialog());
        tvCalculate = findViewById(R.id.tv_calculate);
        tvCalculate.setOnClickListener(v -> startActivity(new Intent(this, YLBXCalculateResultActivity.class)));

    }

    public void showHintDialog() {
        CustomDialog dialog = CustomDialog.build();
        dialog.setMaskColor(Color.parseColor("#4d000000"))
                .setOnBackgroundMaskClickListener(new OnBackgroundMaskClickListener<CustomDialog>() {
                    @Override
                    public boolean onClick(CustomDialog dialog, View v) {
                        return true;
                    }
                })
                .setCustomView(new OnBindView<CustomDialog>(R.layout.view_jfjs_dialog) {
                    @Override
                    public void onBind(CustomDialog dialog, View v) {
                        TextView tv = v.findViewById(R.id.tv_close);
                        tv.setOnClickListener(v1 -> dialog.dismiss());
                    }
                }).show();

    }

    public String getHint() {
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);
        String name = "";
        switch (type) {
            case 1:
                name = "养老保险";
                break;
            case 2:
                name = "医疗保险";
                break;
            case 3:
                name = "失业保险";
                break;
        }
        return name;
    }
}
