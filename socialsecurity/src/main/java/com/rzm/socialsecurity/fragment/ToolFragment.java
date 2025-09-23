package com.rzm.socialsecurity.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.OtherTaxationCalculateActivity;
import com.rzm.socialsecurity.activity.YLBXCalculateActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.ToolPresenter;
import com.rzm.socialsecurity.view.IBView;

public class ToolFragment extends MvpFragment<ToolPresenter> implements IBView {
    private static final String ARG_C = "content";

    public static ToolFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        ToolFragment fragment = new ToolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ToolPresenter createPresenter() {
        return new ToolPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tool;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        view.findViewById(R.id.ll_yanglao).setOnClickListener(v -> jumpCalculatePage(1));
        view.findViewById(R.id.ll_yiliao).setOnClickListener(v -> jumpCalculatePage(2));
        view.findViewById(R.id.ll_shiye).setOnClickListener(v -> jumpCalculatePage(3));
        view.findViewById(R.id.ll_gongshang).setOnClickListener(v -> jumpCalculatePage(4));
        view.findViewById(R.id.ll_shengyu).setOnClickListener(v -> jumpCalculatePage(5));
        // 其他税务计算
        view.findViewById(R.id.ll_laowu).setOnClickListener(v -> jumpOtherCalculatePage(1));
        view.findViewById(R.id.ll_nzj).setOnClickListener(v -> jumpOtherCalculatePage(2));
        view.findViewById(R.id.ll_gxfh).setOnClickListener(v -> jumpOtherCalculatePage(3));
    }

    public void jumpCalculatePage(int type) {
        Intent intent = new Intent(getActivity(), YLBXCalculateActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }

    public void jumpOtherCalculatePage(int type) {
        Intent intent = new Intent(getActivity(), OtherTaxationCalculateActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }
}
