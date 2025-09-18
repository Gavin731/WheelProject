package com.rzm.socialsecurity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InfoAdCallBack;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpFragment;
import com.common.wheel.util.DeviceUtil;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.presenter.APresenter;
import com.rzm.socialsecurity.view.IAView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public class AFragment extends MvpFragment<APresenter> implements IAView {

    private static final String ARG_C = "content";

    public static AFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public APresenter createPresenter() {
        return new APresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_home;
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

        String text = "是否开启root:" + isRoot + "，是否开始adb:" + isAdb + "，是否开始代理:" + isDl + "，是否开始Vpn:" + isVpn;

        Button init_ad = (Button) getActivity().findViewById(R.id.init_ad);
        init_ad.setText(text);
        getActivity().findViewById(R.id.init_ad).setOnClickListener(new View.OnClickListener() {
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
        getActivity().findViewById(R.id.show_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInterstitialAd(getActivity(), "103526723", new InfoAdCallBack() {
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
        getActivity().findViewById(R.id.show_ad2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInterstitialAd(getActivity(), "103526723", new InfoAdCallBack() {
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
        FrameLayout splashContainer = getActivity().findViewById(R.id.splashContainer);
        FrameLayout infoContainer = getActivity().findViewById(R.id.infoContainer);
        FrameLayout infoContainer2 = getActivity().findViewById(R.id.infoContainer2);
        getActivity().findViewById(R.id.show_kp_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showOpenScreenAd(getActivity(), "103403260", splashContainer, 1000, 1920, new OpenScreenAdCallBack() {
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
        getActivity().findViewById(R.id.show_info_image_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInfoFlowAd(getActivity(), "103401966", infoContainer, 800, 400, null);
            }
        });
        getActivity().findViewById(R.id.show_info_image_ad2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInfoFlowAd(getActivity(), "103401966", infoContainer2, 800, 400, new InformationFlowAdCallback() {
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
        getActivity().findViewById(R.id.http_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showRewardAd(getActivity(), "103428930", new RewardAdCallBack() {
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

//    @OnClick(R.id.btn_card_view)
//    public void skipCardViewActivity() {
//        ActivityUtils.startActivity(CardViewActivity.class);
//    }
//
//    @OnClick(R.id.btn_coordinator_layout)
//    public void skipCoordinatorLayoutActivity() {
//        ActivityUtils.startActivity(CoordinatorLayoutActivity.class);
//    }
//
//    @OnClick(R.id.btn_refresh_layout)
//    public void skipRefreshLayoutActivity() {
//        ActivityUtils.startActivity(RefreshLayoutActivity.class);
//    }
//
//    @OnClick(R.id.btn_http_layout)
//    public void skipHttpActivity() {
//        ActivityUtils.startActivity(HttpLayoutActivity.class);
//    }
}
