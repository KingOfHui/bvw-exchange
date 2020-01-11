package com.darknet.bvw.activity;

import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

public class TestWebView extends BaseActivity {

    BridgeWebView webView;

    @Override
    public void initView() {
        webView = (BridgeWebView) findViewById(R.id.webView);


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


//        webView.callHandler("functionInJs", "nihao", new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//
//                Log.e("wallet", "....receive...web..data......" + data);
//            }
//        });

//        webView.addJavascriptInterface(new MainJavascrotInterface(webView.getCallbacks(), webView), "android");
//        webView.setGson(new Gson());


//        User user = new User();
//        Location location = new Location();
//        location.address = "SDU";
//        user.location = location;
//        user.name = "大头鬼";


//        webView.send("hello");
//        webView.sendToWeb("hello");

//        createTrade();

    }

    String priVal = "L4YrK1xKLxtG9PRqV4bKeErzbFArU7XdZXoWfpeuxYJQRsFPKviY";
    String addresVal = "13ogNWNrmFhhBsTaq4do45dXo1UbtF3Sm1";
    String walletType = "BTC";
    int coinNum = 1;

//    private void createTrade() {
//
//        CreateTradeRequest tradeRequest = new CreateTradeRequest();
//        tradeRequest.setAmount(coinNum);
//        tradeRequest.setSymbol(walletType);
//        tradeRequest.setTo_address(addresVal);
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String jsonVal = gson.toJson(tradeRequest);
//
//        String msg = "" + System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg, priVal);
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        RequestBody requestBody = RequestBody.create(JSON, jsonVal);
//
//        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.CREATE_TRADE_URL)
//                .tag(TestWebView.this)
//                .upRequestBody(requestBody)
//                .headers("Chain-Authentication", addresVal + "#" + msg + "#" + signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
//                            if (backVal != null) {
//                                try {
//                                    Gson gson = new Gson();
//                                    JsonRootBean response = gson.fromJson(backVal, JsonRootBean.class);
//                                    if (response != null) {
//                                        if (response.getData() != null) {
//                                            getSignValThree(response.getData());
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                });
//    }


//                            try {
//                                JSONObject jsonObject = new JSONObject(backVal.toString())
//                                        .getJSONObject("data");
//                                String unspent = jsonObject.getString("unspent");
//                                String decodeRawTx = jsonObject.getString("decodeRawTx");
//                                Log.e("wallet2", "....unspent...=" + unspent);
//                                Log.e("wallet2", "....decodeRawTx...=" + decodeRawTx);
////                                getSignValTwo(unspent, decodeRawTx);
//                                collectStringVal(unspent, decodeRawTx, priVal);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                Log.e("wallet2", "....decodeRawTx...=" + e.toString());
//                            }
//                            Log.e("backVal", "backVal=" + backVal);

//    private void collectStringVal(String input, String decodedTransaction, String key) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("{");
//        sb.append("\"privKey\":").append("\""+key+"\":");
//        sb.append(",\"inputs\":").append(input);
//        sb.append(",\"decodedTransaction\":").append(decodedTransaction);
//        sb.append("}");
//
//        String inputVal = sb.toString();
//
//        Log.e("wallet2", "....inputVal22...=" + inputVal);
//
//        webView.callHandler("signTransaction",inputVal, new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//
//                Log.e("wallet", "....receive...web..data......" + data);
//            }
//        });
//
//        webView.loadUrl("file:///android_asset/index.html");
//    }


    private void getSignValThree(SendTx data) {

        String priVal = "L4YrK1xKLxtG9PRqV4bKeErzbFArU7XdZXoWfpeuxYJQRsFPKviY";
        String addresVal = "13ogNWNrmFhhBsTaq4do45dXo1UbtF3Sm1";

        List<Unspent> unspent = data.getUnspent();
        TransactionRAW decodeRawTx = data.getDecodeRawTx();


        SignModelTwo signModel = new SignModelTwo();
        signModel.setPrivKey(priVal);
        signModel.setInputs(unspent);
        signModel.setDecodedTransaction(decodeRawTx);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
//        System.out.println();

        String jsonVal = gson.toJson(signModel);

//        Log.e("wallet", "....gson.string......" + jsonVal);

        webView.callHandler("signTransaction", jsonVal, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                try {
                    Log.e("wallet", "....receive...web..data......" + data);
                    JSONObject jsonObject = new JSONObject(data);
                    String afterSignVal = jsonObject.getString("data");
                    Log.e("wallet", "....afterSignVal..data...." + afterSignVal);
//                    sendTrade(afterSignVal);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }


    //发送交易
//    private void sendTrade(String signVal) {
//
//        SendTradeRequest sendTradeRequest = new SendTradeRequest();
//        sendTradeRequest.setAmount(coinNum);
//        sendTradeRequest.setRaw(signVal);
//        sendTradeRequest.setSymbol(walletType);
//        sendTradeRequest.setTo_address(addresVal);
//        sendTradeRequest.setType(4);
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String jsonVal = gson.toJson(sendTradeRequest);
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        RequestBody requestBody = RequestBody.create(JSON, jsonVal);
//
//        String msg = "" + System.currentTimeMillis();
//        String signValss = BitcoinjKit.signMessageBy58(msg, priVal);
//
//        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
//                .tag(TestWebView.this)
//                .upRequestBody(requestBody)
//                .headers("Chain-Authentication", addresVal + "#" + msg + "#" + signValss)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
//                            if(backVal != null){
//                                Gson gson = new Gson();
//                                SendTradeResponse response = gson.fromJson(backVal, SendTradeResponse.class);
//                            }
//                        }
//                    }
//                });
//    }


//    private void getSignValTwo(String input, String dxcontent) {
//
//        String priVal = "L4YrK1xKLxtG9PRqV4bKeErzbFArU7XdZXoWfpeuxYJQRsFPKviY";
//        String addresVal = "13ogNWNrmFhhBsTaq4do45dXo1UbtF3Sm1";
//
//
//        SignModel signModel = new SignModel();
//        signModel.setPrivKey(priVal);
//        signModel.setInputs(input);
//        signModel.setDecodedTransaction(dxcontent);
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String jsonVal = gson.toJson(signModel);
//
//        Log.e("wallet", "....gson.string......" + jsonVal);
//
//        webView.callHandler("signTransaction", jsonVal, new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//
//                Log.e("wallet", "....receive...web..data......" + data);
//            }
//        });
//
//        webView.loadUrl("file:///android_asset/index.html");
//    }


//    private void getSignVal(SendTx data) {
//
//        String priVal = "L4YrK1xKLxtG9PRqV4bKeErzbFArU7XdZXoWfpeuxYJQRsFPKviY";
//        String addresVal = "13ogNWNrmFhhBsTaq4do45dXo1UbtF3Sm1";
//
//        List<Unspent> unspent = data.getUnspent();
//        TransactionRAW decodeRawTx = data.getDecodeRawTx();
//
//
//        SignModel signModel = new SignModel();
//        signModel.setPrivKey(priVal);
//        signModel.setInputs(unspent);
//        signModel.setDecodedTransaction(decodeRawTx);
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
////        System.out.println();
//
//        Log.e("wallet", "....gson.string......" + gson.toJson(signModel));
//
//        String jsonVal = gson.toJson(signModel);
//
//        webView.callHandler("signTransaction", jsonVal, new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//
//                Log.e("wallet", "....receive...web..data......" + data);
//            }
//        });
//
//        webView.loadUrl("file:///android_asset/index.html");
//    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test_webview;
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
}
