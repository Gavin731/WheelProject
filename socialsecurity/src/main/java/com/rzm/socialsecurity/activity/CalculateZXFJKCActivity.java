package com.rzm.socialsecurity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.CalculateZXFJKCPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.DialogUtil;
import com.rzm.socialsecurity.view.ICalculateZXFJKCView;

import java.math.BigDecimal;
import java.util.List;

/**
 * 计算专项附加扣除
 */
public class CalculateZXFJKCActivity extends MvpActivity<CalculateZXFJKCPresenter> implements ICalculateZXFJKCView {

    public ImageView ivBack;
    public LinearLayout llSylr, llZfzj, llZfdklx, llJxjy, llZnjy, llYyrzh;
    public TextView tvSylr, tvSylrAmount,tvZfzj,tvZfzjAmount,
            tvZfdklx, tvZfdklxAmount, tvJxjy, tvJxjyAmount,
            tvZnjy, tvZnjyAmount, tvYyrzh, tvYyrzhAmount,tvTotalAmount;

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
    public CalculateZXFJKCPresenter createPresenter() {
        return new CalculateZXFJKCPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_calculate_zxfjkc;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        llSylr = findViewById(R.id.ll_sylr);
        tvSylr = findViewById(R.id.tv_sylr);
        tvSylrAmount = findViewById(R.id.tv_sylr_amount);
        llSylr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showSylrDialog();
            }
        });

        tvZfzj = findViewById(R.id.tv_zfzj);
        tvZfzjAmount = findViewById(R.id.tv_zfzj_amount);
        llZfzj =findViewById(R.id.ll_zfzj);
        llZfzj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showZfzjDialog();
            }
        });

        tvZfdklx = findViewById(R.id.tv_zfdklx);
        tvZfdklxAmount = findViewById(R.id.tv_zfdklx_amount);
        llZfdklx =findViewById(R.id.ll_zfdklx);
        llZfdklx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showZfdklxDialog();
            }
        });

        tvJxjy = findViewById(R.id.tv_jxjy);
        tvJxjyAmount = findViewById(R.id.tv_jxjy_amount);
        llJxjy =findViewById(R.id.ll_jxjy);
        llJxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showJxjyDialog();
            }
        });

        tvZnjy = findViewById(R.id.tv_znjy);
        tvZnjyAmount = findViewById(R.id.tv_znjy_amount);
        llZnjy =findViewById(R.id.ll_znjy);
        llZnjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showZnjyDialog();
            }
        });

        tvYyrzh = findViewById(R.id.tv_yyrzh);
        tvYyrzhAmount = findViewById(R.id.tv_yyrzh_amount);
        llYyrzh =findViewById(R.id.ll_yyrzh);
        llYyrzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showYyrzhDialog();
            }
        });

        tvTotalAmount = findViewById(R.id.tv_total_amount);
    }

    @Override
    public void showSylrDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                presenter.calculateSylrAmount(position);
                // 清空
                if (position == 5) {
                    tvSylr.setText("");
                } else {
                    tvSylr.setText(value);
                }
            }
        }, "赡养老人");
    }

    @Override
    public void showSylrAmount(float amount) {
        tvSylrAmount.setText(String.valueOf(amount));
    }

    @Override
    public void showZfzjDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                presenter.calculateZfzjAmount(position);
                // 清空
                if (position == 3) {
                    tvZfzj.setText("");
                } else {
                    tvZfzj.setText(value);
                }
            }
        }, "租房租金");
    }

    @Override
    public void showZfzjAmount(float amount) {
        tvZfzjAmount.setText(String.valueOf(amount));
    }

    @Override
    public void showZfdklxDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                presenter.calculateZfdklxAmount(position);
                // 清空
                if (position == 2) {
                    tvZfdklx.setText("");
                } else {
                    tvZfdklx.setText(value);
                }
            }
        }, "住房贷利息");
    }

    @Override
    public void showZfdklxAmount(float amount) {
        tvZfdklxAmount.setText(String.valueOf(amount));
    }

    @Override
    public void showJxjyDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                presenter.calculateJxjyAmount(position);
                // 清空
                if (position == 2) {
                    tvJxjy.setText("");
                } else {
                    tvJxjy.setText(value);
                }
            }
        }, "继续教育");
    }

    @Override
    public void showJxjyAmount(float amount) {
        tvJxjyAmount.setText(String.valueOf(amount));
    }

    @Override
    public void showZnjyDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                presenter.calculateZnjyAmount(position);
                // 清空
                if (position == 5) {
                    tvZnjy.setText("");
                } else {
                    tvZnjy.setText(value);
                }
            }
        }, "子女教育");
    }

    @Override
    public void showZnjyAmount(float amount) {
        tvZnjyAmount.setText(String.valueOf(amount));
    }

    @Override
    public void showYyrzhDialog(List<String> datas) {
        DialogUtil.showBottomWheelDialog(this, datas, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                presenter.calculateYyrzhAmount(position);
                // 清空
                if (position == 5) {
                    tvYyrzh.setText("");
                } else {
                    tvYyrzh.setText(value);
                }
            }
        }, "婴幼儿教育");
    }

    @Override
    public void showYyrzhAmount(float amount) {
        tvYyrzhAmount.setText(String.valueOf(amount));
    }

    @Override
    public void showTotalAmount(BigDecimal amount) {
        tvTotalAmount.setText(String.valueOf(amount));
    }

}
