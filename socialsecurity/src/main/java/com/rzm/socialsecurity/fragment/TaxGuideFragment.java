package com.rzm.socialsecurity.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpFragment;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.MainActivity;
import com.rzm.socialsecurity.activity.TaxGuideDetailActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.TaxGuidePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

/**
 * 税务指南
 */
public class TaxGuideFragment extends MvpFragment<TaxGuidePresenter> implements IBView {

    private static final String ARG_C = "content";
    public boolean isShow = false;
    public FrameLayout flInfoAdTax;

    public static TaxGuideFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        TaxGuideFragment fragment = new TaxGuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public TaxGuidePresenter createPresenter() {
        return new TaxGuidePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tax_guide;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isShow) {
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
            //            flInfoAdTax.removeAllViews();
            ADUtil.showInfoFlowAd(getActivity(), ConstantConfig.AD_INFO, flInfoAdTax, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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
        view.findViewById(R.id.ll_zfzj).setOnClickListener(v -> jumpDetail(1));
        view.findViewById(R.id.ll_sylr).setOnClickListener(v -> jumpDetail(2));
        view.findViewById(R.id.ll_jxjy).setOnClickListener(v -> jumpDetail(3));
        view.findViewById(R.id.ll_dbyl).setOnClickListener(v -> jumpDetail(4));
        view.findViewById(R.id.ll_znjy).setOnClickListener(v -> jumpDetail(5));
        view.findViewById(R.id.ll_zfdk).setOnClickListener(v -> jumpDetail(6));
        view.findViewById(R.id.ll_yyrzg).setOnClickListener(v -> jumpDetail(7));
        flInfoAdTax = view.findViewById(R.id.fl_info_ad_tax);
        view.findViewById(R.id.tv_download).setOnClickListener(v -> {
            ADUtil.showRewardAd(getActivity(), ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    checkPermissions();
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

    }

    public void jumpDetail(int type) {
        Intent intent = new Intent(getActivity(), TaxGuideDetailActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            return;
        }
        ((MainActivity)getActivity()).download();
    }
}
