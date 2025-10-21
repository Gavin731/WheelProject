package com.rzm.socialsecurity.fragment;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.OtherTaxationCalculateActivity;
import com.rzm.socialsecurity.activity.YLBXCalculateActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.ToolPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

public class ToolFragment extends MvpFragment<ToolPresenter> implements IBView {
    private static final String ARG_C = "content";
    public boolean isShow=false;
    public FrameLayout flInfoAdTool;


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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(!isShow){
                isShow = true;
                ADUtil.showInterstitialAd(getActivity(), ConstantConfig.AD_Interstitial, new InfoAdCallBack() {
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
                //            flInfoAdTool.removeAllViews();
                showInfoAd();
            }
            if(flInfoAdTool!=null &&  flInfoAdTool.getVisibility() == VISIBLE){
                showInfoAd();
            }
        }
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
        view.findViewById(R.id.ll_gtjy).setOnClickListener(v -> jumpOtherCalculatePage(4));
        flInfoAdTool = view.findViewById(R.id.fl_info_ad_tool);

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
    public void showInfoAd(){
        LogUtils.i("------aa2222");
        ADUtil.showInfoFlowAd(getActivity(), ConstantConfig.AD_INFO, flInfoAdTool, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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
}
