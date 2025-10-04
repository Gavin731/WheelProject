package com.rzm.socialsecurity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

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
    public boolean isShow=false;
    public FrameLayout fl_info_ad;

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
    public void lazyLoad() {
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
    public void initView() {
        view.findViewById(R.id.ll_zfzj).setOnClickListener(v->jumpDetail(1));
        view.findViewById(R.id.ll_sylr).setOnClickListener(v->jumpDetail(2));
        view.findViewById(R.id.ll_jxjy).setOnClickListener(v->jumpDetail(3));
        view.findViewById(R.id.ll_dbyl).setOnClickListener(v->jumpDetail(4));
        view.findViewById(R.id.ll_znjy).setOnClickListener(v->jumpDetail(5));
        view.findViewById(R.id.ll_zfdk).setOnClickListener(v->jumpDetail(6));
        view.findViewById(R.id.ll_yyrzg).setOnClickListener(v->jumpDetail(7));
        fl_info_ad= view.findViewById(R.id.fl_info_ad_tax);
        view.findViewById(R.id.tv_download).setOnClickListener(v->{
            ADUtil.showRewardAd(getActivity(), ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    download();
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

    public void jumpDetail(int type){
        Intent intent=new Intent(getActivity(), TaxGuideDetailActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }

    public void download(){
        String path = PathUtils.getExternalAppDownloadPath()+"/个人所得税年度自行纳税申报表.pdf";
        FileDownloader.getImpl().create("https://shanghai.chinatax.gov.cn/bsfw/xzzx/bgxz/sbzsl/202402/P020240227631678268732.pdf")
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        showToast("下载成功，路径为："+path);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        showToast("下载失败");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }
}
