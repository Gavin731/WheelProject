package com.film.television;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.common.wheel.admanager.AdvertisementManager;
import com.common.wheel.admanager.InterstitialAdManager;

public class MainActivity extends AppCompatActivity {

    InterstitialAdManager interstitialAdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdvertisementManager.getInstance().requestPermissionIfNecessary(this);

        findViewById(R.id.init_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisementManager.getInstance().init(MainActivity.this);
            }
        });
        findViewById(R.id.load_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAdManager = new InterstitialAdManager(MainActivity.this);
                interstitialAdManager.loadAd(MainActivity.this, "964568346");
            }
        });
        findViewById(R.id.show_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAdManager.showAd();
            }
        });
    }
}