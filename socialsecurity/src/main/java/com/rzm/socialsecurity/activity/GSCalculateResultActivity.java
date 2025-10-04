package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.mvp.MvpActivity;
import com.common.wheel.util.ImmersiveModeHelper;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.GSCalculateResultPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.TablelayoutUtil;
import com.rzm.socialsecurity.view.IGSCalculateResultView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 个税计算结果页面
 */
public class GSCalculateResultActivity extends MvpActivity<GSCalculateResultPresenter> implements IGSCalculateResultView {

    public ImageView ivBack;
    public TableLayout tbLSl;
    public TextView tvMoney, tvMoney1, tvSk, tvKc,tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        ImmersiveModeHelper.setStatusBarMode(this, true);
    }

    @Override
    public GSCalculateResultPresenter createPresenter() {
        return new GSCalculateResultPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gs_calculate;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        ivBack.setOnClickListener(v -> finish());
        tvTitle.setText("税后工资计算");
        tvTitle.setVisibility(VISIBLE);

        tbLSl=findViewById(R.id.tbL_sl);
        tvMoney = findViewById(R.id.tv_money);
        tvMoney1 = findViewById(R.id.tv_money1);
        tvSk = findViewById(R.id.tv_sk);
        tvKc = findViewById(R.id.tv_kc);
        FrameLayout flInfoAd=findViewById(R.id.fl_info_ad);
        ADUtil.showInfoFlowAd(this, ConstantConfig.AD_INFO, flInfoAd, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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

    @Override
    public void initData(List<String> titles, List<List<String>> tableData) {
        float monthMoney=getIntent().getFloatExtra(ConstantConfig.monthMoney, 0);
        float sbgrMoney=getIntent().getFloatExtra(ConstantConfig.sbgrMoney, 0);
        float zxkcMoney=getIntent().getFloatExtra(ConstantConfig.zxkcMoney, 0);
        BigDecimal bg1=new BigDecimal(monthMoney).setScale(2, RoundingMode.HALF_UP);// 收入
        BigDecimal bg2=new BigDecimal(sbgrMoney).setScale(2, RoundingMode.HALF_UP);// 五险一金
        BigDecimal bg3=new BigDecimal(zxkcMoney).setScale(2, RoundingMode.HALF_UP);// 专项扣除
        BigDecimal bg4 =new BigDecimal(5000).setScale(2, RoundingMode.HALF_UP);
        // 应纳税所得额
        BigDecimal bg5 = monthMoney > 5000 ? bg1.subtract(bg2).subtract(bg3).subtract(bg4).setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0);
        // 应纳税额
        BigDecimal bg6 = monthMoney > 5000 ? presenter.calculateSk(bg5) : new BigDecimal(0);
        // 本月扣除总额
        BigDecimal bg7 = bg6.add(bg2).setScale(2, RoundingMode.HALF_UP);
        // 本月税后所得
        BigDecimal bg8 = bg1.subtract(bg7).setScale(2, RoundingMode.HALF_UP);
        tvMoney.setText(String.valueOf(bg8));
        tvMoney1.setText(String.valueOf(bg1));
        tvSk.setText(String.valueOf(bg6));
        tvKc.setText(String.valueOf(bg7));

        TablelayoutUtil.addTableRow(this, tbLSl,titles,
                tableData,getColor(R.color.color_fafafb), getColor(R.color.white),
                200, 150);
    }
}
