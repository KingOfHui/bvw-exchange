package com.darknet.bvw.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.DuiLieToastModel;
import com.darknet.bvw.model.LanguageModel;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class WebLieDuiActivity extends BaseActivity {

    BridgeWebView webView;

    private TextView titleContent;

    private RelativeLayout backLayout;

    private int type = 0;

    @Override
    public void initView() {

        type = getIntent().getIntExtra("type",0);

        webView = (BridgeWebView) findViewById(R.id.duilie_webView);


        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mUploadMessage = uploadMsg;
//                pickFile();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                mUploadMessageArray = filePathCallback;
//                pickFile();
                return true;
            }
        });


        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
//        titleContent.setText(getString(R.string.help_title));

        titleContent.setText(getString(R.string.find_bid_pop_three));

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        if(type == 0){
            webView.loadUrl("file:///android_asset/index.html#/tree?side=left");
        }else {
            webView.loadUrl("file:///android_asset/index.html#/tree?side=right");
        }

        webView.registerHandler("showLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showDialog(getString(R.string.load_data));
//                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });

        webView.registerHandler("hideLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                dismissDialog();
//                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });


        webView.registerHandler("showToast", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    if (data != null) {
                        Gson gson = new Gson();
                        DuiLieToastModel response = gson.fromJson(data, DuiLieToastModel.class);
                        if (response != null) {
                            Toast.makeText(WebLieDuiActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        webView.registerHandler("getRequestHeaders", new

                BridgeHandler() {
                    @Override
                    public void handler(String data, CallBackFunction function) {

                        String sysversion = getSystemVersion();

//                CreateHeaderModel createHeaderModel = new CreateHeaderModel();

                        Map sendData = new HashMap();


                        if (sysversion == null) {
                            sendData.put("clientOsVersion", "");
                        } else {
                            sendData.put("clientOsVersion", sysversion);
                        }

                        String appversion = getCurVersionName(WebLieDuiActivity.this);

                        if (appversion == null) {
                            sendData.put("appVersion", "");
                        } else {
                            sendData.put("appVersion", appversion);
                        }

                        sendData.put("clientOs", "android");

                        int lanType = SPUtil.getInstance(WebLieDuiActivity.this).getSelectLanguage();

                        if (lanType == 1) {
                            //英文
                            sendData.put("lang", "en");
                        } else if (lanType == 0) {
                            //中文
                            sendData.put("lang", "cn");
                        } else {
                            sendData.put("lang", "en");
                        }

                        sendData.put("clientId", getClientId());

                        //本地当前选中的钱包
                        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                        String msg = "" + System.currentTimeMillis();
                        String signVal = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

                        sendData.put("Chain-Authentication", walletModel.getAddress() + "#" + msg + "#" + signVal);


                        Gson gson = new Gson();
                        String headerVal = gson.toJson(sendData);

//                JSONObject allJson = new JSONObject();

//                try {
//                    allJson.put("SYS_HEAD", sendData);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                JSONObject jsonObject = new JSONObject(createHeaderModel);
//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//                String headerVal = gson.toJson(createHeaderModel);
                        Log.e("headerrequest", "headerVal=" + headerVal);

                        function.onCallBack(headerVal);
                    }
                });

        webView.registerHandler("getLang", new

                BridgeHandler() {
                    @Override
                    public void handler(String data, CallBackFunction function) {

                        int lanType = SPUtil.getInstance(WebLieDuiActivity.this).getSelectLanguage();

                        LanguageModel model = new LanguageModel();


                        if (lanType == 1) {
                            //英文
                            model.setLang("en");
                        } else if (lanType == 0) {
                            //中文
                            model.setLang("cn");
                        } else {
                            model.setLang("en");
                        }

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        String headerVal = gson.toJson(model);
                        Log.e("headerrequest", "headerVal=" + headerVal);

                        function.onCallBack(headerVal);
                    }
                });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_duilie_webview;
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
        }
        return false;
    }

    public static String getSystemVersion() {
        try {
            return android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    public static String getCurVersionName(Context ctx) {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


}
