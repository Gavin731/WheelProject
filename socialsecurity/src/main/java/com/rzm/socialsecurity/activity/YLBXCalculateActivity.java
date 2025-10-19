package com.rzm.socialsecurity.activity;

import static android.view.View.GONE;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.YLBXCalculatePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IYLBXCalculateView;

/**
 * 养老、医疗、失业、工伤、生育保险计算页面
 */
public class YLBXCalculateActivity extends MvpActivity<YLBXCalculatePresenter> implements IYLBXCalculateView {

    public ImageView ivBack;
    public TextView tvHint1, tvHint2;
    public LinearLayout tvCalculate, tvJnjs, llTop;
    public EditText etCardinalNumber, etCompany, etPersonal;

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
    public YLBXCalculatePresenter createPresenter() {
        return new YLBXCalculatePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ylbxjs;
    }

    @Override
    public void initView() {
        int type = getIntent().getIntExtra(ConstantConfig.bxKey, 1);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        llTop = findViewById(R.id.ll_top);
        Glide.with(this).load(getTopDrawable(type)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                llTop.setBackground(resource);
            }
        });

        tvJnjs = findViewById(R.id.tv_jnjs);
        tvJnjs.setOnClickListener(v -> showHintDialog());

        etCardinalNumber = findViewById(R.id.et_cardinal_number);
        etCompany = findViewById(R.id.et_company);
        etPersonal = findViewById(R.id.et_personal);

        tvCalculate = findViewById(R.id.tv_calculate);
        tvCalculate.setOnClickListener(v -> {
            String etCardinalNumberText = etCardinalNumber.getText().toString().trim();
            String etCompanyText = etCompany.getText().toString().trim();
            String etPersonalText = etPersonal.getText().toString().trim();
            if (TextUtils.isEmpty(etCardinalNumberText)) {
                showToast("请输入缴费基数");
                return;
            }
            if (TextUtils.isEmpty(etCompanyText)) {
                showToast("请输入单位缴纳比例");
                return;
            }
            try {
                Float.parseFloat(etCardinalNumberText);
            }catch (Exception e){
                etCardinalNumberText = "0";
            }
            float etCompanyTextBl = 0;
            try {
                etCompanyTextBl = Float.parseFloat(etCompanyText);
            }catch (Exception e){
                etCompanyText = "0";
            }
            if (etCompanyTextBl > 20) {
                showToast("比例不能超过20");
                return;
            }
            if (type == 4 || type == 5) {
                etPersonalText = "0";
            } else {
                if (TextUtils.isEmpty(etPersonalText)) {
                    showToast("请输入个人缴纳比例");
                    return;
                }
                float etPersonalTextBl = 0;
                try {
                    etPersonalTextBl = Float.parseFloat(etPersonalText);
                }catch (Exception e){
                    etPersonalText = "0";
                }
                if (etPersonalTextBl > 20) {
                    showToast("比例不能超过20");
                    return;
                }
            }
            Intent intent = new Intent(this, YLBXCalculateResultActivity.class);
            intent.putExtra(ConstantConfig.cardinalNumberText, etCardinalNumberText);
            intent.putExtra(ConstantConfig.companyText, etCompanyText);
            intent.putExtra(ConstantConfig.personalText, etPersonalText);
            intent.putExtra(ConstantConfig.bxKey, type);
            intent.putExtra(ConstantConfig.isHidePersonalText, type == 4 || type == 5);

            ADUtil.showRewardAd(this, ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    startActivity(intent);
                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void onAdVideoBarClick() {

                }

                @Override
                public void onVideoError() {

                }

                @Override
                public void onRewardArrived() {

                }

                @Override
                public void onSkippedVideo() {

                }

                @Override
                public void onAdShow() {

                }

                @Override
                public void onError() {

                }
            });
        });

        tvHint1 = findViewById(R.id.tv_hint1);
        tvHint2 = findViewById(R.id.tv_hint2);
        tvHint1.setText(getHint1(type));

        if (type == 4 || type == 5) {
            findViewById(R.id.tv_presonal).setVisibility(GONE);
            findViewById(R.id.ll_presonal).setVisibility(GONE);
        }
        FrameLayout fl_info_ad= findViewById(R.id.fl_info_ad);
        ADUtil.showInfoFlowAd(this, ConstantConfig.AD_INFO, fl_info_ad, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
            @Override
            public void onError() {

            }

            @Override
            public void onFeedAdLoad() {

            }

            @Override
            public void onRenderSuccess() {

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onRenderFail() {

            }
        });
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

    public Drawable getTopDrawable(int type) {
        Drawable result;
        switch (type) {
            case 1:
                result = getDrawable(R.mipmap.bxjs_yanglao);
                break;
            case 2:
                result = getDrawable(R.mipmap.bxjs_yiliao);
                break;
            case 3:
                result = getDrawable(R.mipmap.bxjs_shiye);
                break;
            case 4:
                result = getDrawable(R.mipmap.bxjs_gongshang);
                break;
            case 5:
                result = getDrawable(R.mipmap.bxjs_shengyu);
                break;
            default:
                result = getDrawable(R.mipmap.bxjs_yanglao);
                break;
        }
        return result;
    }

    public String getHint1(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "养老保险:企业缴纳比例一般为16%，职工个人缴费比例为 8%。";
                break;
            case 2:
                result = "医疗保险:企业缴费比例约为 8%，个人缴费比例为 2%。";
                break;
            case 3:
                result = "失业保险:企业缴费比例通常为 0.5%，个人缴费比例为 0.5%。";
                break;
            case 4:
                result = "工伤保险:缴费比例根据单位被划分的行业范围来确定，通常在 0.2%-1.9% 之间，个人不缴费。";
                break;
            case 5:
                result = "生育保险:企业缴纳比例一般为0.8%";
                break;
        }
        return result;
    }
}
