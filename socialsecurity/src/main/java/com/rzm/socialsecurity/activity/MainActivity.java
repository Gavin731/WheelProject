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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
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
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.adapter.TabViewPagerAdapter;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.custom.HomeTabItemView;
import com.rzm.socialsecurity.entity.ShowInfoAdEvent;
import com.rzm.socialsecurity.presenter.MainPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.util.UMUtil;
import com.rzm.socialsecurity.view.IMainView;
import com.rzm.socialsecurity.widget.NoTouchViewPager;

import org.greenrobot.eventbus.EventBus;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class MainActivity extends MvpActivity<MainPresenter> implements IMainView {

    private final int[] COLORS = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

    PageNavigationView pnvTab;
    NoTouchViewPager vpMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInterstitialAd(1);
            }
        }, 100);

        BarUtils.setStatusBarLightMode(this, true);
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
        tax.setTitle("税务指南");
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
        String userEnv = Hawk.get(ConstantConfig.userEnv);
        if (isShowAppHint || "false".equals(userEnv)) {
            showUserPrivacy(false);
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

    /**
     * 用户隐私协议
     */
    public void showUserPrivacy(boolean isEit) {
        boolean isShowUserPrivacy = Hawk.get(ConstantConfig.isAgreeUserPrivacy, false);
        if (isShowUserPrivacy) {
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
                .setCustomView(new OnBindView<CustomDialog>(R.layout.view_user_privacy_dialog) {
                    @Override
                    public void onBind(CustomDialog dialog, View v) {
                        TextView textView = v.findViewById(R.id.tv_content);
                        String fullText = "尊敬的用户:\n" +
                                "      衷心感谢您选用社保个税计算!我们非常尊重并保护您的个人信息和隐私，为了更好的保障您的权利，在您使用我们的产品前，请您务必谨慎阅读《用户协议》和《隐私政策》内的所有条款。\n" +
                                "请注意:\n" +
                                "1.在您使用本产品时，我们可能会收集您的:安卓ID、网络状态、APP版本号、MAC地址、IME1、所在位置信息、手机存储权限、IP地址等，用于统计APP的使用情况、定位错误问题和不断提供APP稳定性和安全性;\n" +
                                "2.我们会尽力采取各种安全技术保护您的个人信息，未经您的同意，我们不会从第三方获取、共享或对外提供您的信息。\n" +
                                "如您同意以上协议内容，请您点击“同意并继续”，开始使用我的产品。";

                        SpannableString spannableString = new SpannableString(fullText);
                        // 设置"用户协议"可点击
                        ClickableSpan userAgreementSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // 这里可以跳转到用户协议页面
                                showWebView(2);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);        // 设置文字颜色
                                ds.setUnderlineText(false);     // 移除下划线
                            }
                        };

                        // 设置"隐私政策"可点击
                        ClickableSpan privacyPolicySpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // 这里可以跳转到隐私政策页面
                                showWebView(1);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);
                                ds.setUnderlineText(false);
                            }
                        };

                        // 设置Span的范围
                        int userAgreementStart = fullText.indexOf("用户协议");
                        int userAgreementEnd = userAgreementStart + "用户协议".length();
                        spannableString.setSpan(userAgreementSpan, userAgreementStart, userAgreementEnd,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        int privacyPolicyStart = fullText.indexOf("隐私政策");
                        int privacyPolicyEnd = privacyPolicyStart + "隐私政策".length();
                        spannableString.setSpan(privacyPolicySpan, privacyPolicyStart, privacyPolicyEnd,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        textView.setText(spannableString);
                        textView.setMovementMethod(LinkMovementMethod.getInstance()); // 必须设置这个才能点击
                        textView.setHighlightColor(Color.TRANSPARENT); // 移除点击时的背景色

                        TextView tvFindUserPrivacy = v.findViewById(R.id.tv_find_user_privacy);
                        tvFindUserPrivacy.setOnClickListener(v1 -> {
                            if (isEit) {
                                finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
//                                MobclickAgent.onKillProcess(MainActivity.this);
                            } else {
                                dialog.dismiss();
                                showUserPrivacy2();
                            }
                        });
                        TextView tvOkUserPrivacy = v.findViewById(R.id.tv_ok_user_privacy);
                        tvOkUserPrivacy.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            confirmUserPrivacy();
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

    public void showUserPrivacy2() {
        CustomDialog dialog = CustomDialog.build();
        dialog.setMaskColor(Color.parseColor("#4d000000"))
                .setOnBackgroundMaskClickListener(new OnBackgroundMaskClickListener<CustomDialog>() {
                    @Override
                    public boolean onClick(CustomDialog dialog, View v) {
                        return true;
                    }
                })
                .setCustomView(new OnBindView<CustomDialog>(R.layout.view_user_privacy2_dialog) {
                    @Override
                    public void onBind(CustomDialog dialog, View v) {
                        TextView textView = v.findViewById(R.id.tv_content);
                        String fullText = "您需要同意《用户协议》和《隐私政策》才能使用我们提供的服务";

                        SpannableString spannableString = new SpannableString(fullText);
                        // 设置"用户协议"可点击
                        ClickableSpan userAgreementSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // 这里可以跳转到用户协议页面
                                showWebView(2);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);        // 设置文字颜色
                                ds.setUnderlineText(false);     // 移除下划线
                            }
                        };

                        // 设置"隐私政策"可点击
                        ClickableSpan privacyPolicySpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // 这里可以跳转到隐私政策页面
                                showWebView(1);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);
                                ds.setUnderlineText(false);
                            }
                        };

                        // 设置Span的范围
                        int userAgreementStart = fullText.indexOf("用户协议");
                        int userAgreementEnd = userAgreementStart + "用户协议".length();
                        spannableString.setSpan(userAgreementSpan, userAgreementStart, userAgreementEnd,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        int privacyPolicyStart = fullText.indexOf("隐私政策");
                        int privacyPolicyEnd = privacyPolicyStart + "隐私政策".length();
                        spannableString.setSpan(privacyPolicySpan, privacyPolicyStart, privacyPolicyEnd,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        textView.setText(spannableString);
                        textView.setMovementMethod(LinkMovementMethod.getInstance()); // 必须设置这个才能点击
                        textView.setHighlightColor(Color.TRANSPARENT); // 移除点击时的背景色

                        TextView tvFindUserPrivacy = v.findViewById(R.id.tv_find_user_privacy);
                        tvFindUserPrivacy.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            showUserPrivacy(true);
                        });
                        TextView tvOkUserPrivacy = v.findViewById(R.id.tv_ok_user_privacy);
                        tvOkUserPrivacy.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            confirmUserPrivacy();
                        });
                    }
                }).show();
    }

    public void confirmUserPrivacy(){
        Hawk.put(ConstantConfig.isAgreeUserPrivacy, true);
        requestPermission();
        UMUtil.init(MainActivity.this);
        if (!AdvertisementManager.getInstance().issInit()) {
            ADUtil.initAdManager(getApplicationContext(), new InitCallback() {
                @Override
                public void success() {
                    // 展示广告
                    showInterstitialAd(3);
                    EventBus.getDefault().post(new ShowInfoAdEvent(true));
                }

                @Override
                public void error() {
                }
            });
        }else{
            // 展示广告
            showInterstitialAd(3);
        }
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (type){
                            case 1:
                                showAppHintDialog();
                                break;
                            case 2:
                                showUserPrivacy(false);
                                break;
                        }
                    }
                }, 100);
            }

            @Override
            public void onVideoComplete() {

            }

            @Override
            public void onSkippedVideo() {

            }
        });
    }

    public void showWebView(int type) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(ConstantConfig.webType, type);
        startActivity(intent);
    }

    public void requestPermission() {
        AdvertisementManager.getInstance().requestPermissionIfNecessary(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        }
    }
}
