package com.film.television.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.mvp.MvpActivity;
import com.film.television.R;
import com.film.television.adapter.TabViewPagerAdapter;
import com.film.television.presenter.MainPresenter;
import com.film.television.view.IMainView;
import com.film.television.widget.NoTouchViewPager;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class MainActivity extends MvpActivity<MainPresenter> implements IMainView {

    private final int[] COLORS = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

//    @BindView(R.id.pnv_Tab)
    PageNavigationView pnvTab;
//    @BindView(R.id.vp_Main)
    NoTouchViewPager vpMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdvertisementManager.getInstance().requestPermissionIfNecessary(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
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
                .addItem(R.drawable.ic_audiotrack_black_24dp, "活动", COLORS[1])
                .addItem(R.drawable.ic_book_black_24dp, "友圈", COLORS[2])
                .addItem(R.drawable.ic_news_black_24dp, "个人", COLORS[3])
                .enableAnimateLayoutChanges()
                .build();

        TabViewPagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), Math.max(5, mNavigationController.getItemCount()));
        vpMain.setAdapter(pagerAdapter);

        mNavigationController.setupWithViewPager(vpMain);

        mNavigationController.setMessageNumber(0, 100);
    }

    @Override
    public String getResourcesHint() {
        return "我是MainActivity提供的文案";
    }
}
