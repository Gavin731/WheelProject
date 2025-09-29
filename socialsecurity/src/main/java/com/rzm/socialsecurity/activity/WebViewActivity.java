package com.rzm.socialsecurity.activity;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.constant.ConstantConfig;

public class WebViewActivity extends AppCompatActivity {

    public ImageView ivBack;
    public TextView tvTitle;
    public WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        int type = getIntent().getIntExtra(ConstantConfig.webType, 1);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(type == 1 ? "隐私政策" : "用户协议");
        webView = findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();

        // 基本设置
        settings.setJavaScriptEnabled(true); // 启用 JS
        settings.setDomStorageEnabled(true); // 启用 DOM 存储
        settings.setDatabaseEnabled(true); // 启用数据库

        // 缩放设置
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 显示缩放控件
        settings.setDisplayZoomControls(false); // 隐藏缩放控件（使用自定义）

        // 视口设置
        settings.setUseWideViewPort(true); // 使用宽视口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕大小

        // 缓存设置
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 默认缓存策略

        // 其他设置
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setAllowContentAccess(true); // 允许内容访问
        settings.setJavaScriptCanOpenWindowsAutomatically(false); // 禁止 JS 自动开窗

        webView.setWebViewClient(new WebViewClient() {

            // 页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 显示加载进度
            }

            // 页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 隐藏加载进度
            }

            // 拦截 URL 加载
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                // 处理特定 URL  scheme
                if (url.startsWith("tel:")) {
                    // 处理电话链接
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else if (url.startsWith("mailto:")) {
                    // 处理邮件链接
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                // 其他 URL 在 WebView 中加载
                view.loadUrl(url);
                return true;
            }

            // 处理加载错误
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 显示错误页面
//                view.loadUrl("file:///android_asset/error.html");
            }
        });
        webView.loadUrl(type == 1 ? "https://ht.njrzm.com/static/yszc1" : "https://ht.njrzm.com/static/yhxy1");
    }

}
