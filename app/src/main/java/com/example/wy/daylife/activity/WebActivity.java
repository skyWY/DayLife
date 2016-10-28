package com.example.wy.daylife.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wy.daylife.Interface.Defs;
import com.example.wy.daylife.R;
import com.example.wy.daylife.base.BaseActivity;

import butterknife.Bind;

public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";
    private static final Uri PROFILE_URI = Uri.parse(Defs.WEB_SCHEMA);

    private String uid;

    @Bind(R.id.webView)
    public WebView webView;
    @Bind(R.id.common_toolbar)
    public Toolbar toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    private void extractUidFromUri() {
        Uri uri = getIntent().getData();
        if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
            uid = uri.getQueryParameter(Defs.PARAM_UID);
            webView.loadUrl(String.valueOf(uid));
      //      Log.i(TAG, "uid from url: " + uid+":"+uri);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {
        toolbar.setTitle("DayLife");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(true);
        setting.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        extractUidFromUri();
    }

    @Override
    public void initTheme() {

    }
}
