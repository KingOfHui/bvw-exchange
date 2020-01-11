package com.darknet.bvw.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.darknet.bvw.R;
import com.darknet.bvw.view.TypefaceTextView;

public class PrivacyPolicyActivity extends BaseActivity {

    private RelativeLayout layBack;
    private TypefaceTextView title;

    private WebView webView;

    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        title.setText(getString(R.string.wallet_xieyi_content_two));
        webView = findViewById(R.id.privacy_web);
//        initAssetTxt();
        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyPolicyActivity.this.finish();
            }
        });

        WebSettings mWebSettings = webView.getSettings();
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

        webView.setWebViewClient(new CustomWebViewClient());

        webView.loadUrl("https://h5.bvw.im/privacy");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy_policy;
    }

//    void initAssetTxt() {
//        try {
//            InputStream is = getAssets().open("private.txt");
//            int size = is.available();
//
//            // Read the entire asset into a local byte buffer.
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            // Convert the buffer into a string.
//            String text = new String(buffer, "UTF-8");
//
//            // Finally stick the string into the text view.
//            txtPrivacyPolicy.setText(text);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

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
}
