package com.rzm.socialsecurity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.AboutActivity;
import com.rzm.socialsecurity.activity.WebViewActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.PersonalCenterPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

import java.io.File;

public class PersonalCenterFragment extends MvpFragment<PersonalCenterPresenter> implements IBView {

    private static final String ARG_C = "content";
    public boolean isShow=false;
    public FrameLayout fl_info_ad;

    public TextView tvCache;
    public String path = PathUtils.getExternalAppDownloadPath() + "/个人所得税年度自行纳税申报表.pdf";

    public static PersonalCenterFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public PersonalCenterPresenter createPresenter() {
        return new PersonalCenterPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && !isShow){
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
        }
        if(isVisibleToUser){
            ADUtil.showInfoFlowAd(getActivity(), ConstantConfig.AD_INFO, fl_info_ad, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        tvCache = view.findViewById(R.id.tv_cache);
        initFiLe();
        fl_info_ad= view.findViewById(R.id.fl_info_ad_personal);
        view.findViewById(R.id.ll_yszc).setOnClickListener(v -> showWebView(1));
        view.findViewById(R.id.ll_yhxy).setOnClickListener(v -> showWebView(2));
        view.findViewById(R.id.ll_gywm).setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutActivity.class)));
        view.findViewById(R.id.ll_qlhc).setOnClickListener(v -> {
            FileUtils.delete(path);
            initFiLe();
        });

    }

    public void initFiLe() {
        boolean isExist = FileUtils.isFileExists(path);
        String size = FileUtils.getSize(new File(path));
        if (isExist) {
            tvCache.setText(size);
        } else {
            tvCache.setText("0KB");
        }
    }

    public void showWebView(int type) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(ConstantConfig.webType, type);
        startActivity(intent);
    }
}
