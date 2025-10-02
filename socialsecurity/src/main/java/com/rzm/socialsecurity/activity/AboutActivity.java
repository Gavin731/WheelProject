package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.common.wheel.util.ImmersiveModeHelper;
import com.rzm.socialsecurity.BuildConfig;
import com.rzm.socialsecurity.R;

public class AboutActivity extends AppCompatActivity {

    public ImageView ivBack;
    public TextView tvTitle, tvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImmersiveModeHelper.setTransparentStatusBar(this);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText("关于我们");
        tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(BuildConfig.VERSION_NAME);
    }
}
