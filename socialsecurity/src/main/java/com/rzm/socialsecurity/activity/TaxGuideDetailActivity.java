package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.TaxGuideDetailPresenter;
import com.rzm.socialsecurity.view.ITaxGuideDetailView;

/**
 * 专项指南页面
 */
public class TaxGuideDetailActivity extends MvpActivity<TaxGuideDetailPresenter> implements ITaxGuideDetailView {

    public ImageView ivBack, ivName;
    public TextView tvTitle, tvName, tv_kcfw, tv_kcbz, tv_kcfs, tv_cjwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public TaxGuideDetailPresenter createPresenter() {
        return new TaxGuideDetailPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tax_guide_detail;
    }

    @Override
    public void initView() {
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(presenter.getTitleName(type));

        tvName = findViewById(R.id.tv_name);
        tvName.setText(presenter.getBXName(type));

        ivName = findViewById(R.id.iv_name);
        Glide.with(this).load(getBXNameImage(type)).into(ivName);

        tv_kcfw = findViewById(R.id.tv_kcfw);
        tv_kcfw.setText(Html.fromHtml(presenter.getKcfw(type), Html.FROM_HTML_MODE_LEGACY));
        tv_kcbz = findViewById(R.id.tv_kcbz);
        tv_kcbz.setText(Html.fromHtml(presenter.getKcbz(type), Html.FROM_HTML_MODE_LEGACY));
        tv_kcfs = findViewById(R.id.tv_kcfs);
        tv_kcfs.setText(Html.fromHtml(presenter.getKcfs(type), Html.FROM_HTML_MODE_LEGACY));
        tv_cjwt = findViewById(R.id.tv_cjwt);
        tv_cjwt.setText(Html.fromHtml(presenter.getcjwt(type), Html.FROM_HTML_MODE_LEGACY));
    }

    public Drawable getBXNameImage(int type) {
        Drawable result = getDrawable(R.mipmap.ic_launcher);
        switch (type) {
            case 1:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
            case 2:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
            case 3:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
            case 4:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
            case 5:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
            case 6:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
            case 7:
                result = getDrawable(R.mipmap.ic_launcher);
                break;
        }
        return result;
    }

}
