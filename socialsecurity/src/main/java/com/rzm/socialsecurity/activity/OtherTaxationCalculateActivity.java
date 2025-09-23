package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.OtherTaxationCalculatePresenter;
import com.rzm.socialsecurity.view.IOtherTaxationCalculateView;

/**
 * 劳务报错、年终奖、股息计算、个体经营税务计算
 */
public class OtherTaxationCalculateActivity extends MvpActivity<OtherTaxationCalculatePresenter> implements IOtherTaxationCalculateView {

    public ImageView ivBack;
    public TextView tvName, tvMoneyHint;
    public EditText tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public OtherTaxationCalculatePresenter createPresenter() {
        return new OtherTaxationCalculatePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_other_taxation_calculate;
    }

    @Override
    public void initView() {
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        tvName =findViewById(R.id.tv_name);
        tvName.setText(getHint(type));
        tvMoneyHint = findViewById(R.id.tv_money_hint);
        tvMoneyHint.setText(getHint2(type));
        tvMoney =findViewById(R.id.tv_money);
        tvMoney.setHint(getHint3(type));
        findViewById(R.id.tv_calculate).setOnClickListener(v -> {
            String money = tvMoney.getText().toString().trim();
            if(TextUtils.isEmpty(money)){
                showToast(getHint3(type));
                return;
            }
            Intent intent = new Intent(OtherTaxationCalculateActivity.this, OtherTaxationCalculateResultActivity.class);
            intent.putExtra(ConstantConfig.bxKey, type);
            intent.putExtra(ConstantConfig.amount, Float.parseFloat(money));
            startActivity(intent);
        });
    }

    public String getHint(int type) {
        String name = "";
        switch (type) {
            case 1:
                name = "税后劳务报酬计算";
                break;
            case 2:
                name = "年终奖报酬计算";
                break;
            case 3:
                name = "股息分红报酬计算";
                break;
        }
        return name;
    }
    public String getHint2(int type) {
        String name = "";
        switch (type) {
            case 1:
                name = "税后劳务金额";
                break;
            case 2:
                name = "年终奖金额";
                break;
            case 3:
                name = "股息分红金额";
                break;
        }
        return name;
    }
    public String getHint3(int type) {
        String name = "";
        switch (type) {
            case 1:
                name = "请输入劳务报酬金额";
                break;
            case 2:
                name = "请输入年终奖金额";
                break;
            case 3:
                name = "请输入股息分红金额";
                break;
        }
        return name;
    }
}
