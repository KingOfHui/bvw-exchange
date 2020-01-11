package com.darknet.bvw.activity;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darknet.bvw.R;

public class HelpActivity extends BaseActivity {

    private WebView contentView;

    private TextView titleContent;

    private RelativeLayout backLayout;


    @Override
    public void initView() {

        contentView = findViewById(R.id.help_web);

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
        titleContent.setText(getString(R.string.help_title));

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentView.canGoBack()) {
                    contentView.goBack();
                } else {
                    finish();
                }
            }
        });


        WebSettings mWebSettings = contentView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setTextZoom(100);

        contentView.setWebViewClient(new CustomWebViewClient());

        contentView.loadUrl("https://h5.bvw.im/help");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }


    private class CustomWebViewClient extends WebViewClient {
        public CustomWebViewClient() {
            super();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && contentView.canGoBack()) {
            contentView.goBack();
            return true;
        } else {
            finish();
        }
        return false;
    }
}
