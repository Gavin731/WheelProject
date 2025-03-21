package com.film.television;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InformationFlowManager;
import com.common.wheel.admanager.InterstitialAdManager;
import com.common.wheel.admanager.OpenScreenAdCallBack;
import com.common.wheel.admanager.OpenScreenAdManager;
import com.common.wheel.util.DeviceUtil;

public class MainActivity extends AppCompatActivity {

    InterstitialAdManager interstitialAdManager;
    OpenScreenAdManager openScreenAdManager;
    InformationFlowManager informationFlowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdvertisementManager.getInstance().requestPermissionIfNecessary(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        findViewById(R.id.init_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().init(MainActivity.this, "5670955", "终端测试软件");
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
        findViewById(R.id.show_kp_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().showOpenScreenAd(MainActivity.this, "5670955", "103403260", splashContainer, 1000, 1920, new OpenScreenAdCallBack() {
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
    }
}