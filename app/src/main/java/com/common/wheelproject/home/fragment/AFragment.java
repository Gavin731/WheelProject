package com.common.wheelproject.home.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InterstitialAdManager;
import com.common.wheel.mvp.MvpFragment;
import com.common.wheelproject.R;
import com.common.wheelproject.home.presenter.APresenter;
import com.common.wheelproject.home.view.IAView;
import com.common.wheelproject.test.activity.CardViewActivity;
import com.common.wheelproject.test.activity.CoordinatorLayoutActivity;
import com.common.wheelproject.test.activity.HttpLayoutActivity;
import com.common.wheelproject.test.activity.RefreshLayoutActivity;

import butterknife.OnClick;

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
    InterstitialAdManager interstitialAdManager;

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

    }

    @OnClick(R.id.btn_card_view)
    public void skipCardViewActivity() {
        ActivityUtils.startActivity(CardViewActivity.class);
    }

    @OnClick(R.id.btn_coordinator_layout)
    public void skipCoordinatorLayoutActivity() {
        ActivityUtils.startActivity(CoordinatorLayoutActivity.class);
    }

    @OnClick(R.id.btn_refresh_layout)
    public void skipRefreshLayoutActivity() {
        ActivityUtils.startActivity(RefreshLayoutActivity.class);
    }

    @OnClick(R.id.btn_http_layout)
    public void skipHttpActivity() {
        ActivityUtils.startActivity(HttpLayoutActivity.class);
    }
    @OnClick(R.id.init_ad)
    public void initSdk() {
        AdvertisementManager.getInstance().init(getActivity(), "5670955", "终端测试软件");
    }
    @OnClick(R.id.load_ad)
    public void Load() {
//        interstitialAdManager = new InterstitialAdManager();
//        interstitialAdManager.loadAd(getActivity(), "102935580");
    }

    @OnClick(R.id.show_ad)
    public void showAd(){
//        interstitialAdManager.showAd();
    }
}
