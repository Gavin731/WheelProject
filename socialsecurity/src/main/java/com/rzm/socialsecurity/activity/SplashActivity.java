package com.rzm.socialsecurity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.InitCallback;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.mvp.MvpActivity;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.orhanobut.hawk.Hawk;
import com.rzm.socialsecurity.BuildConfig;
import com.rzm.socialsecurity.MyApp;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.IPEvent;
import com.rzm.socialsecurity.entity.ShowInfoAdEvent;
import com.rzm.socialsecurity.presenter.SplashPresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.ISplashView;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.listener.OnGetOaidListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends MvpActivity<SplashPresenter> implements ISplashView {

    FrameLayout splashContainer;
    private boolean isLoadAdCallback =false;// 加载广告是否有回调
    InitCallback initCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        MyApp.getInstance().isSplash = true;
        EventBus.getDefault().register(this);
    }

    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        // 延迟20秒执行，没有回调直接跳首页
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(!isLoadAdCallback){
//                    openMain();
//                }
//            }
//        }, 20000);
        splashContainer = findViewById(R.id.splashContainer);

        initCallback = new InitCallback() {
            @Override
            public void success() {
                LogUtils.e("aaaaa_开始获取开屏广告");
                ADUtil.showOpenScreenAd(SplashActivity.this, ConstantConfig.AD_SPLASH, splashContainer, ScreenUtils.getAppScreenWidth(), ScreenUtils.getAppScreenHeight(), new OpenScreenAdCallBack() {
                    @Override
                    public void onAdClose() {
                        LogUtils.e("开屏广告被关闭，跳转首页");
                        isLoadAdCallback = true;
                        openMain();
                    }

                    @Override
                    public void onSplashAdClick() {

                    }

                    @Override
                    public void onSplashAdShow() {
                        isLoadAdCallback = true;
                    }

                    @Override
                    public void onSplashLoadFail() {
                        LogUtils.e("开屏广告加载异常，跳转首页");
                        isLoadAdCallback = true;
                        openMain();
                    }

                    @Override
                    public void onSplashRenderFail() {
                        LogUtils.e("开屏广告渲染异常，跳转首页");
                        isLoadAdCallback = true;
                        openMain();
                    }
                });
            }

            @Override
            public void error() {
                LogUtils.e("aaaaa_初始化失败");
                isLoadAdCallback = true;
                openMain();
            }
        };

        showUserPrivacy(false);
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
                                "1.在您使用本产品时，我们可能会收集您的设备唯一标识符(IMEI、AndroidID、OAID、IDFA、OpenUDID、GUID、IDFV、SIM卡、IMSI信息、ICCID，MEID、SSID、ME、IP地址、磁力、加速度、重力、陀螺仪传感器、设备MAC地址、SUPI、SUCI、序列号)对用户进行唯一标识。通过网络状态、APP版本号、所在位置信息、电话、手机存储权限等，用于统计APP的使用情况、定位错误问题和不断提供APP稳定性和安全性;\n" +
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
                        ADUtil.showInfoFlowAd(SplashActivity.this, ConstantConfig.AD_INFO, flInfoAd, ScreenUtils.getScreenWidth(), 0, true, new InformationFlowAdCallback() {
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
        MyApp.getMyApp().initUm();
        // 获取信息是否可以上报
        ADUtil.getKey(this, initCallback);
    }

    public void openMain() {
        MyApp.getInstance().isSplash = false;
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    public void showWebView(int type) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(ConstantConfig.webType, type);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initAd(IPEvent ipEvent){
        ADUtil.initAd(getApplicationContext(), ipEvent.getIpAddress(), initCallback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
