package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.common.wheel.util.ImmersiveModeHelper;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.TaxGuidePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IBView;

/**
 * 税务指南页面
 */
public class TaxGuideActivity extends MvpActivity<TaxGuidePresenter> implements IBView {

    public FrameLayout flInfoAdTax;
    public ImageView ivBack;
    public TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeHelper.setStatusBarMode(this, true);
        initView();
    }

    @Override
    public TaxGuidePresenter createPresenter() {
        return new TaxGuidePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tax_guide;
    }

    public void initView(){
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());

        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("税务指南");

        findViewById(R.id.ll_zfzj).setOnClickListener(v -> jumpDetail(1));
        findViewById(R.id.ll_sylr).setOnClickListener(v -> jumpDetail(2));
        findViewById(R.id.ll_jxjy).setOnClickListener(v -> jumpDetail(3));
        findViewById(R.id.ll_dbyl).setOnClickListener(v -> jumpDetail(4));
        findViewById(R.id.ll_znjy).setOnClickListener(v -> jumpDetail(5));
        findViewById(R.id.ll_zfdk).setOnClickListener(v -> jumpDetail(6));
        findViewById(R.id.ll_yyrzg).setOnClickListener(v -> jumpDetail(7));
        flInfoAdTax = findViewById(R.id.fl_info_ad_tax);
        findViewById(R.id.tv_download).setOnClickListener(v -> {
            ADUtil.showRewardAd(this, ConstantConfig.AD_Reward, new RewardAdCallBack() {
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
        //            flInfoAdTax.removeAllViews();
        ADUtil.showInfoFlowAd(this, ConstantConfig.AD_INFO, flInfoAdTax, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
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

    public void jumpDetail(int type) {
        Intent intent = new Intent(this, TaxGuideDetailActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            return;
        }
        download();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 下载时，申请读写文件权限
        if (requestCode == 2) {
            boolean isPass = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    isPass = false;
                }
            }
            if (isPass) {
                download();
            }
        }

    }

    public void download() {
        String path = PathUtils.getExternalAppDownloadPath() + "/个人所得税年度自行纳税申报表.pdf";
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
                        showToast("下载成功，路径为：" + path);
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
