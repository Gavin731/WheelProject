package com.rzm.socialsecurity.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.CalculateZXFJKCActivity;
import com.rzm.socialsecurity.activity.GSCalculateResultActivity;
import com.rzm.socialsecurity.activity.SBCalculateActivity;
import com.rzm.socialsecurity.activity.SBFunctionActivity;
import com.rzm.socialsecurity.activity.SBManageOrSuperviseActivity;
import com.rzm.socialsecurity.activity.YLBXCalculateActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.APresenter;
import com.rzm.socialsecurity.view.IAView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public class AFragment extends MvpFragment<APresenter> implements IAView {

    private static final String ARG_C = "content";

    public TextView tvTabGszxjs, tvTabSbjnjs, tvGoPage1, tvGoPage2;
    public LinearLayout llSb, llGs, tvStartCalculate, tvGsCalculate;
    public ImageView ivYanglao, ivYiliao, ivShiye;
    public EditText etMonthMoney, etSbMoney, etGsMonthMoney, etGsSbMoney, etGsZxkcMoney;

    public static AFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public APresenter createPresenter() {
        return new APresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_home;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        ivYanglao = view.findViewById(R.id.iv_yanglao);
        ivYiliao = view.findViewById(R.id.iv_yiliao);
        ivShiye = view.findViewById(R.id.iv_top_shiye);
        tvTabSbjnjs = view.findViewById(R.id.tv_sbjnjs);
        tvTabGszxjs = view.findViewById(R.id.tv_gszxjs);
        llSb = view.findViewById(R.id.ll_sb);
        llGs = view.findViewById(R.id.ll_gs);
        etMonthMoney = view.findViewById(R.id.et_month_money);
        etSbMoney = view.findViewById(R.id.et_sb_money);
        etGsMonthMoney = view.findViewById(R.id.et_gs_month_money);
        etGsSbMoney = view.findViewById(R.id.et_gs_sb_money);
        etGsZxkcMoney = view.findViewById(R.id.et_gs_zxkc_money);


        ivYanglao.setOnClickListener(v -> jumpCalculatePage(1));
        ivYiliao.setOnClickListener(v -> jumpCalculatePage(2));
        ivShiye.setOnClickListener(v -> jumpCalculatePage(3));
        tvTabSbjnjs.setOnClickListener(v -> {
            llSb.setVisibility(VISIBLE);
            llGs.setVisibility(GONE);
        });
        tvTabGszxjs.setOnClickListener(v -> {
            llSb.setVisibility(GONE);
            llGs.setVisibility(VISIBLE);
        });
        tvStartCalculate = view.findViewById(R.id.tv_start_calculate);
        tvStartCalculate.setOnClickListener(v -> {
            String monthMoney=etMonthMoney.getText().toString().trim();
            String sbMoney=etSbMoney.getText().toString().trim();
            if(TextUtils.isEmpty(monthMoney)){
                showToast("请先填写本月工资收入");
                return;
            }
            if(TextUtils.isEmpty(sbMoney)){
                showToast("请先填写社保个人部分");
                return;
            }
            Intent intent = new Intent(getActivity(), SBCalculateActivity.class);
            intent.putExtra(ConstantConfig.monthMoney, Float.parseFloat(monthMoney));
            intent.putExtra(ConstantConfig.sbgrMoney, Float.parseFloat(sbMoney));
            startActivity(intent);
        });
        tvGoPage1 = view.findViewById(R.id.tv_goPage1);
        tvGoPage1.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBFunctionActivity.class)));
        tvGoPage2 = view.findViewById(R.id.tv_goPage2);
        tvGoPage2.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBManageOrSuperviseActivity.class)));


        tvGsCalculate = view.findViewById(R.id.tv_gs_calculate);
        tvGsCalculate.setOnClickListener(v -> {
            String monthMoney=etGsMonthMoney.getText().toString().trim();
            String sbMoney=etGsSbMoney.getText().toString().trim();
            String zxkcMoney=etGsZxkcMoney.getText().toString().trim();
            if(TextUtils.isEmpty(monthMoney)){
                showToast("请先填写本月工资收入");
                return;
            }
            if(TextUtils.isEmpty(sbMoney)){
                showToast("请先填写五险一金");
                return;
            }
            if(TextUtils.isEmpty(zxkcMoney)){
                showToast("请先填写专项附加扣除");
                return;
            }
            Intent intent = new Intent(getActivity(), GSCalculateResultActivity.class);
            intent.putExtra(ConstantConfig.monthMoney, Float.parseFloat(monthMoney));
            intent.putExtra(ConstantConfig.sbgrMoney, Float.parseFloat(sbMoney));
            intent.putExtra(ConstantConfig.zxkcMoney, Float.parseFloat(zxkcMoney));
            startActivity(intent);
        });

        view.findViewById(R.id.tv_zxfjkc).setOnClickListener(v -> startActivity(new Intent(getActivity(), CalculateZXFJKCActivity.class)));

    }

    public void jumpCalculatePage(int type) {
        Intent intent = new Intent(getActivity(), YLBXCalculateActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }


}
