package com.rzm.socialsecurity.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.mvp.MvpActivity;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.adapter.TabViewPagerAdapter;
import com.rzm.socialsecurity.presenter.MainPresenter;
import com.rzm.socialsecurity.view.IMainView;
import com.rzm.socialsecurity.widget.NoTouchViewPager;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class MainActivity extends MvpActivity<MainPresenter> implements IMainView {

    private final int[] COLORS = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

    PageNavigationView pnvTab;
    NoTouchViewPager vpMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdvertisementManager.getInstance().requestPermissionIfNecessary(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        presenter.initView();
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
    public void initNavigation() {
        pnvTab = findViewById(R.id.pnv_Tab);
        vpMain = findViewById(R.id.vp_Main);

        NavigationController mNavigationController = pnvTab.material()
                .addItem(R.drawable.ic_ondemand_video_black_24dp, "首页", COLORS[0])
                .addItem(R.drawable.ic_audiotrack_black_24dp, "更多工具", COLORS[1])
                .addItem(R.drawable.ic_book_black_24dp, "税务指南", COLORS[2])
                .addItem(R.drawable.ic_news_black_24dp, "个人中心", COLORS[3])
                .enableAnimateLayoutChanges()
                .build();

        TabViewPagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), 4);
        vpMain.setAdapter(pagerAdapter);

        mNavigationController.setupWithViewPager(vpMain);

        mNavigationController.setMessageNumber(0, 100);
    }

    @Override
    public String getResourcesHint() {
        return "我是MainActivity提供的文案";
    }
}
