package com.rzm.socialsecurity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpFragment;
import com.common.wheel.util.DeviceUtil;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.presenter.BPresenter;
import com.rzm.socialsecurity.view.IBView;

public class BFragment extends MvpFragment<BPresenter> implements IBView {

    private static final String ARG_C = "content";

    public static BFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        BFragment fragment = new BFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BPresenter createPresenter() {
        return new BPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_home_b;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        boolean isRoot = DeviceUtil.isRoot();
        boolean isAdb = DeviceUtil.isAdb(getActivity());
        boolean isDl = DeviceUtil.isDl(getActivity());
        boolean isVpn = DeviceUtil.isVpnActive(getActivity());

        String text = "B页面是否开启root:" + isRoot + "，是否开始adb:" + isAdb + "，是否开始代理:" + isDl + "，是否开始Vpn:" + isVpn;

        Button init_ad = (Button) getActivity().findViewById(R.id.init_ad_b);
        init_ad.setText(text);
        getActivity().findViewById(R.id.init_ad_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
//        findViewById(R.id.load_ad).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                interstitialAdManager = new InterstitialAdManager();
////                interstitialAdManager.loadAd(MainActivity.this, "964568346");
//            }
//        });
        getActivity().findViewById(R.id.show_ad_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInterstitialAd(getActivity(), "103656207", new InfoAdCallBack() {
                    @Override
                    public void onError() {
                        LogUtils.i("页面提示：插屏广告获取失败");
                    }

                    @Override
                    public void onLoadSuccess() {
                        LogUtils.i("页面提示：插屏广告获取成功");
                    }

                    @Override
                    public void onStartShow() {
                        LogUtils.i("页面提示：插屏广告开始显示");
                    }

                    @Override
                    public void onAdShow() {
                        LogUtils.i("页面提示：插屏广告已展示");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        LogUtils.i("页面提示：插屏广告被点击");
                    }

                    @Override
                    public void onAdClose() {
                        LogUtils.i("页面提示：插屏广告被关闭");
                    }

                    @Override
                    public void onVideoComplete() {
                        LogUtils.i("页面提示：插屏广告视频完成");
                    }

                    @Override
                    public void onSkippedVideo() {
                        LogUtils.i("页面提示：插屏广告跳过视频");
                    }
                });
            }
        });
        getActivity().findViewById(R.id.show_ad2_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInterstitialAd(getActivity(), "103656207", new InfoAdCallBack() {
                    @Override
                    public void onError() {
                        LogUtils.i("页面提示：插屏广告2获取失败");
                    }

                    @Override
                    public void onLoadSuccess() {
                        LogUtils.i("页面提示：插屏广告2获取成功");
                    }

                    @Override
                    public void onStartShow() {
                        LogUtils.i("页面提示：插屏广告2开始显示");
                    }

                    @Override
                    public void onAdShow() {
                        LogUtils.i("页面提示：插屏广告2已展示");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        LogUtils.i("页面提示：插屏广告2被点击");
                    }

                    @Override
                    public void onAdClose() {
                        LogUtils.i("页面提示：插屏广告2被关闭");
                    }

                    @Override
                    public void onVideoComplete() {
                        LogUtils.i("页面提示：插屏广告2视频完成");
                    }

                    @Override
                    public void onSkippedVideo() {
                        LogUtils.i("页面提示：插屏广告2跳过视频");
                    }
                });
            }
        });
        FrameLayout splashContainer = getActivity().findViewById(R.id.splashContainer_b);
        FrameLayout infoContainer = getActivity().findViewById(R.id.infoContainer_b);
        FrameLayout infoContainer2 = getActivity().findViewById(R.id.infoContainer2_b);
        getActivity().findViewById(R.id.show_kp_ad_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showOpenScreenAd(getActivity(), "103656025", splashContainer, 1000, 1920, new OpenScreenAdCallBack() {
                    @Override
                    public void onAdClose() {
                        LogUtils.i("开屏广告关闭");
                        splashContainer.removeAllViews();
                    }

                    @Override
                    public void onSplashAdClick() {
                        LogUtils.i("开屏广告被点击");
                    }

                    @Override
                    public void onSplashAdShow() {
                        LogUtils.i("开屏广告显示");
                    }

                    @Override
                    public void onSplashLoadFail() {
                        LogUtils.i("开屏广告加载失败");
                    }

                    @Override
                    public void onSplashRenderFail() {
                        LogUtils.i("开屏广告渲染失败");
                    }
                });
            }
        });
        getActivity().findViewById(R.id.show_info_image_ad_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInfoFlowAd(getActivity(), "103655294", infoContainer, 800, 400, null);
            }
        });
        getActivity().findViewById(R.id.show_info_image_ad2_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInfoFlowAd(getActivity(), "103655294", infoContainer2, 800, 400, new InformationFlowAdCallback() {
                    @Override
                    public void onError() {
                        LogUtils.i("信息流2广告获取失败");
                    }

                    @Override
                    public void onFeedAdLoad() {
                        LogUtils.i("信息流2已获取广告");
                    }

                    @Override
                    public void onRenderSuccess() {
                        LogUtils.i("信息流2广告被加载");
                    }

                    @Override
                    public void onAdClick() {
                        LogUtils.i("信息流2广告被点击");
                    }

                    @Override
                    public void onRenderFail() {
                        LogUtils.i("信息流2广告渲染失败");
                    }
                });
            }
        });
        getActivity().findViewById(R.id.http_request_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showRewardAd(getActivity(), "103653498", new RewardAdCallBack() {
                    @Override
                    public void onAdClose() {
                        LogUtils.i("激励广告关闭");
                    }

                    @Override
                    public void onVideoComplete() {
                        LogUtils.i("激励广告视频完成");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        LogUtils.i("激励广告视频被点击");
                    }

                    @Override
                    public void onVideoError() {
                        LogUtils.i("激励广告视频获取失败");
                    }

                    @Override
                    public void onRewardArrived() {
                        LogUtils.i("激励广告奖励发放");
                    }

                    @Override
                    public void onSkippedVideo() {
                        LogUtils.i("激励广告跳过");
                    }

                    @Override
                    public void onAdShow() {
                        LogUtils.i("激励广告显示");
                    }

                    @Override
                    public void onError() {
                        LogUtils.i("激励广告加载失败");
                    }
                });
            }
        });
    }
}
