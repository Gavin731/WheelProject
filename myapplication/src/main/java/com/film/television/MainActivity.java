package com.film.television;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.util.DeviceUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdvertisementManager.getInstance().requestPermissionIfNecessary(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        boolean isRoot = DeviceUtil.isRoot();
        boolean isAdb = DeviceUtil.isAdb(this);
        boolean isDl = DeviceUtil.isDl(this);
        boolean isVpn = DeviceUtil.isVpnActive(this);

        String text = "是否开启root:"+isRoot+"，是否开始adb:"+isAdb+"，是否开始代理:"+isDl+"，是否开始Vpn:"+isVpn;

        Button init_ad = (Button)findViewById(R.id.init_ad);
        init_ad.setText(text);
        findViewById(R.id.init_ad).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.show_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInterstitialAd(MainActivity.this, "964568346");
            }
        });
        FrameLayout splashContainer = findViewById(R.id.splashContainer);
        FrameLayout infoContainer = findViewById(R.id.infoContainer);
        FrameLayout infoContainer2 = findViewById(R.id.infoContainer2);
        findViewById(R.id.show_kp_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showOpenScreenAd(MainActivity.this, "103403260", splashContainer, 1000, 1920, new OpenScreenAdCallBack() {
                    @Override
                    public void onAdClose() {
                        LogUtils.i("广告关闭");
                        splashContainer.removeAllViews();
                    }
                });
            }
        });
        findViewById(R.id.show_info_image_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInfoFlowAd(MainActivity.this, "103401966", infoContainer, 800, 400);
            }
        });
        findViewById(R.id.show_info_image_ad2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showInfoFlowAd(MainActivity.this, "103401966", infoContainer2, 800, 400);
            }
        });
        findViewById(R.id.http_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showRewardAd(MainActivity.this, "103428930", new RewardAdCallBack() {
                    @Override
                    public void onAdClose() {

                    }

                    @Override
                    public void onVideoComplete() {

                    }

                    @Override
                    public void onAdVideoBarClick() {

                    }
                });
            }
        });
    }
}