package com.rzm.socialsecurity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.InitCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.adapter.TabViewPagerAdapter;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.custom.HomeTabItemView;
import com.rzm.socialsecurity.entity.IPEvent;
import com.rzm.socialsecurity.entity.MainInterstitialAdEvent;
import com.rzm.socialsecurity.entity.ShowInfoAdEvent;
import com.rzm.socialsecurity.presenter.MainPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.UMUtil;
import com.rzm.socialsecurity.view.IMainView;
import com.rzm.socialsecurity.widget.NoTouchViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class MainActivity extends MvpActivity<MainPresenter> implements IMainView {

    private final int[] COLORS = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

    PageNavigationView pnvTab;
    NoTouchViewPager vpMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Hawk.isBuilt()) {
            Hawk.init(this).build();
        }
        presenter.initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInterstitialAd(1);
            }
        }, 100);

        BarUtils.setStatusBarLightMode(this, true);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_2;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initNavigation() {
        pnvTab = findViewById(R.id.pnv_Tab);
        vpMain = findViewById(R.id.vp_Main);

//        NavigationController mNavigationController = pnvTab.material()
//                .addItem(R.drawable.ic_ondemand_video_black_24dp, "首页", COLORS[0])
//                .addItem(R.drawable.ic_audiotrack_black_24dp, "更多工具", COLORS[1])
//                .addItem(R.drawable.ic_book_black_24dp, "税务指南", COLORS[2])
//                .addItem(R.drawable.ic_news_black_24dp, "个人中心", COLORS[3])
//                .enableAnimateLayoutChanges()
//                .build();
        HomeTabItemView home = new HomeTabItemView(this);
        home.setDefaultDrawable(getDrawable(R.mipmap.icon_home_tab_home_def));
        home.setSelectedDrawable(getDrawable(R.mipmap.icon_home_tab_home));
        home.setTitle("首页");
        home.setChecked(true);

        HomeTabItemView tool = new HomeTabItemView(this);
        tool.setDefaultDrawable(getDrawable(R.mipmap.icon_home_tab_tool));
        tool.setSelectedDrawable(getDrawable(R.mipmap.icon_home_tab_tool_select));
        tool.setTitle("更多工具");
        tool.setChecked(false);

        HomeTabItemView tax = new HomeTabItemView(this);
        tax.setDefaultDrawable(getDrawable(R.mipmap.icon_home_tab_tax_guide));
        tax.setSelectedDrawable(getDrawable(R.mipmap.icon_home_tab_tax_guide_select));
        tax.setTitle("社保账单");
        tax.setChecked(false);

        HomeTabItemView personal = new HomeTabItemView(this);
        personal.setDefaultDrawable(getDrawable(R.mipmap.icon_home_tab_personal_center));
        personal.setSelectedDrawable(getDrawable(R.mipmap.icon_home_tab_personal_center_select));
        personal.setTitle("个人中心");
        personal.setChecked(false);

        NavigationController mNavigationController = pnvTab.custom().addItem(home).addItem(tool).addItem(tax).addItem(personal).build();

        TabViewPagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), 4);
        vpMain.setAdapter(pagerAdapter);
        vpMain.setOffscreenPageLimit(4);

        mNavigationController.setupWithViewPager(vpMain);

        // 设置红点
//        mNavigationController.setMessageNumber(0, 100);
    }

    public void jumpCurrentPage(int index) {
        vpMain.setCurrentItem(index, true);
    }

    @Override
    public String getResourcesHint() {
        return "";
    }

    public void showAppHintDialog() {
        boolean isShowAppHint = Hawk.get(ConstantConfig.isShowAppDialog, false);
        if (isShowAppHint) {
            confirmUserPrivacy();
            return;
        }

        CustomDialog dialog = CustomDialog.build();
        dialog.setMaskColor(Color.parseColor("#4d000000"))
                .setOnBackgroundMaskClickListener(new OnBackgroundMaskClickListener<CustomDialog>() {
                    @Override
                    public boolean onClick(CustomDialog dialog, View v) {
                        return true;
                    }
                })
                .setCustomView(new OnBindView<CustomDialog>(R.layout.view_app_dialog) {
                    @Override
                    public void onBind(CustomDialog dialog, View v) {
                        ImageView tv = v.findViewById(R.id.iv_close);
                        tv.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            showInterstitialAd(2);
                        });

                        LinearLayout tvShowUserPrivacy = v.findViewById(R.id.tv_showUserPrivacy);
                        tvShowUserPrivacy.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            Hawk.put(ConstantConfig.isShowAppDialog, true);
                            ADUtil.showRewardAd(MainActivity.this, ConstantConfig.AD_Reward, new RewardAdCallBack() {
                                @Override
                                public void onAdClose() {
                                    showInterstitialAd(2);
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
                        FrameLayout flInfoAd=v.findViewById(R.id.fl_info_ad);
                        ADUtil.showInfoFlowAd(MainActivity.this, ConstantConfig.AD_INFO, flInfoAd, ScreenUtils.getScreenWidth(), 0, true, new InformationFlowAdCallback() {
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
                }).show();
    }


    public void confirmUserPrivacy(){
        requestPermission();
        // 展示广告
        showInterstitialAd(3);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void initAd(IPEvent ipEvent){
//        ADUtil.initAd(getApplicationContext(), ipEvent.getIpAddress(), initCallback);
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void interstitialAdEvent(MainInterstitialAdEvent mainInterstitialAdEvent){
        LogUtils.i("ceshi:"+mainInterstitialAdEvent.getType());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (mainInterstitialAdEvent.getType()){
                    case 1:
                        showAppHintDialog();
                        break;
                    case 2:
                        confirmUserPrivacy();
                        break;
                }
            }
        }, 100);
    }

    public void showInterstitialAd(int type){
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
                EventBus.getDefault().post(new MainInterstitialAdEvent(type));
            }

            @Override
            public void onVideoComplete() {

            }

            @Override
            public void onSkippedVideo() {

            }
        }, type !=3);
    }

    public void showWebView(int type) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(ConstantConfig.webType, type);
        startActivity(intent);
    }

    public void requestPermission() {
        boolean isPass = Hawk.get("isCheckPermission", false);
//        String userEnv = Hawk.get(ConstantConfig.userEnv);
//        if("true".equals(userEnv)){
//            AdvertisementManager.getInstance().requestPermissionIfNecessary(this);
//        }
        // 没有申请权限，且上报接口开了才申请电话权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED && !isPass) {
            Hawk.put("isCheckPermission", true);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }
}
