package com.darknet.bvw.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.event.BidSuccessEvent;
import com.darknet.bvw.model.request.CreateTradeRequest;
import com.darknet.bvw.model.request.SendTradeRequest;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.model.response.BidMoneyResponse;
import com.darknet.bvw.model.response.CreateTradeResponse.JsonRootBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.model.response.PublicAddressResponse;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BottomDialogView;
import com.darknet.bvw.view.FailDialogView;
import com.darknet.bvw.view.SuccessDialogView;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BidBuyActivity extends BaseActivity {


    private LinearLayout buyView;
    BridgeWebView webView;
    //官方地址
    private String publicAddressVal;

    private EditText tuijinaView;

    private String leftVal;

    private ImageView backView;

    private TextView leftView;

    private TextView priceNumView;

    private String leftMoneyVal = "0";

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        leftVal = getIntent().getStringExtra("leftVal");

        buyView = (LinearLayout) this.findViewById(R.id.bid_buy_sub_view);

        backView = (ImageView) this.findViewById(R.id.back_sign_view);

        webView = (BridgeWebView) findViewById(R.id.webView);

        tuijinaView = (EditText) findViewById(R.id.bid_buy_ma_view);

        leftView = (TextView) findViewById(R.id.bid_left_money_view);

        priceNumView = (TextView) findViewById(R.id.bid_buy_price_view);

        leftView.setText(":" + leftVal + " BIW");

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tuijianVal = tuijinaView.getText().toString();
                if (TextUtils.isEmpty(tuijianVal)) {
                    Toast.makeText(BidBuyActivity.this, getString(R.string.bid_buy_notice), Toast.LENGTH_SHORT).show();
                    return;
                }

                ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

                String privateKey = walletModel.getPrivateKey();
                String addressVals = walletModel.getAddress();

                String msg = "" + System.currentTimeMillis();
                String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

                showDialog(getString(R.string.load_data));
                buyView.setEnabled(false);

                HashMap<String, String> map = new HashMap<>();
                map.put("invest_code", tuijianVal);
                String json = new Gson().toJson(map);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.OPEN_BID)
                        .tag(BidBuyActivity.this)
                        .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                        .upRequestBody(requestBody)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> backresponse) {
                                if (backresponse != null) {
                                    String backVal = backresponse.body();
                                    if (backVal != null) {
                                        Gson gson = new Gson();
                                        try {
                                            BaseResponse response = gson.fromJson(backVal, BaseResponse.class);
                                            if (response != null && response.getCode() == 0) {
                                                //刷新状态
                                                ToastUtils.showSingleToast(getString(R.string.bid_open_sucdess));
                                                EventBus.getDefault().post(new BidSuccessEvent());
                                                finish();
//                                                new SuccessDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_sucdess));
                                            }else {
                                                Toast.makeText(BidBuyActivity.this,response.getMsg(),Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                dismissDialog();
                                buyView.setEnabled(true);
                            }
                        });
                //获取实际需要支付的接口
//                getRealPayData();
            }
        });

        getPublicAddress();
    }


    //获取实际要支付的数据
    private void getRealPayData() {

        String tuijianVal = tuijinaView.getText().toString();

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));
        buyView.setEnabled(false);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.REAL_PAY_BID_PRICE_URL + "/" + tuijianVal)
                .tag(BidBuyActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BidMoneyResponse response = gson.fromJson(backVal, BidMoneyResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            handleType(response.getData());
                                        }else {
                                            Toast.makeText(BidBuyActivity.this,response.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(BidBuyActivity.this,response.getMsg(),Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissDialog();
                        buyView.setEnabled(true);
                    }
                });

    }


    private void handleType(BidMoneyResponse.BidMoneyModel bidMoneyModel) {

        String tuijianVal = tuijinaView.getText().toString();

        if (bidMoneyModel.getVip_level().equals("1")) {
            //跳转到大领导
            Intent bidIntent = new Intent(BidBuyActivity.this, BidBuyTwoActivity.class);
            bidIntent.putExtra("leftVal", leftVal);
            bidIntent.putExtra("tuijianVal", tuijianVal);
            bidIntent.putExtra("shouldVal", bidMoneyModel.getBid_price());
            startActivity(bidIntent);
        } else {
            //直接支付
            priceNumView.setText(bidMoneyModel.getBid_price() + " BIW");
            directPay(bidMoneyModel.getBid_price());
        }

    }

    //直接支付
    private void directPay(String shouPayVals) {

        String tuijianVal = tuijinaView.getText().toString();

        BottomDialogView.Builder builder = new BottomDialogView.Builder(BidBuyActivity.this);

        View dialogView = (View) LayoutInflater.from(BidBuyActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

        builder.setContentView(dialogView);
        BottomDialogView bottomDialog = builder.create();
        bottomDialog.show();

        ImageView closeDialog = (ImageView) dialogView.findViewById(R.id.close_dialog_two_view);
        EditText contentView = (EditText) dialogView.findViewById(R.id.common_content);
        Button sureBtn = (Button) dialogView.findViewById(R.id.wallet_select_create_view);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
                hintKeyBoard();
            }
        });


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contentVal = contentView.getText().toString();
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(BidBuyActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();

                        makeTrade(tuijianVal,shouPayVals);

                    } else {
                        Toast.makeText(BidBuyActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void makeTrade(String tuijianVals,String shouldPayVals) {

        if (publicAddressVal == null) {
            //如果没有获取到官网地址，继续获取
            getPublicAddress();
            return;
        }
        buyView.setEnabled(false);
        createTrade(shouldPayVals, publicAddressVal, "BIW");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_bid_buy;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getLeftMoneyRequest();
    }

    @Override
    public void configViews() {

    }


    private void createTrade(String num, String address, String walletType) {

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

        CreateTradeRequest tradeRequest = new CreateTradeRequest();
        tradeRequest.setAmount(num);
        tradeRequest.setSymbol(walletType);
        tradeRequest.setTo_address(publicAddressVal);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.CREATE_TRADE_URL)
                .tag(BidBuyActivity.this)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", walletModel.getAddress() + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    JsonRootBean response = gson.fromJson(backVal, JsonRootBean.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            getSignValThree(num,response.getData(), walletModel.getPrivateKey());
                                        } else {

                                            try {
                                                new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                                                buyView.setEnabled(true);
                                                dismissDialog();
                                            } catch (Exception e) {
                                                buyView.setEnabled(true);
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                                            buyView.setEnabled(true);
                                            dismissDialog();
                                        } catch (Exception e) {
                                            buyView.setEnabled(true);
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    dismissDialog();
//                                    Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                    try {
                                        dismissDialog();
                                        new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                                        buyView.setEnabled(true);
                                    } catch (Exception es) {
                                        buyView.setEnabled(true);
                                        es.printStackTrace();
                                    }

                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        dismissDialog();
                        try {
                            dismissDialog();
                            new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                            buyView.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            buyView.setEnabled(true);
                        }

//                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }
                });
    }

    private void getSignValThree(String shouldPayVals,SendTx data, String priVal) {

//        String priVal = "L4YrK1xKLxtG9PRqV4bKeErzbFArU7XdZXoWfpeuxYJQRsFPKviY";
//        String addresVal = "13ogNWNrmFhhBsTaq4do45dXo1UbtF3Sm1";

        List<Unspent> unspent = data.getUnspent();
        TransactionRAW decodeRawTx = data.getDecodeRawTx();

        SignModelTwo signModel = new SignModelTwo();
        signModel.setPrivKey(priVal);
        signModel.setInputs(unspent);
        signModel.setDecodedTransaction(decodeRawTx);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(signModel);

        webView.callHandler("signTransaction", jsonVal, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                try {
//                    Log.e("wallet", "....receive...web..data......" + data);
                    JSONObject jsonObject = new JSONObject(data);
                    String afterSignVal = jsonObject.getString("data");

                    //签名结束，发送交易
                    sendTrade(shouldPayVals,afterSignVal);

                } catch (Exception e) {
                    e.printStackTrace();
//                    dismissDialog();

                    try {
                        new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                        buyView.setEnabled(true);
                        dismissDialog();
                    } catch (Exception es) {
                        buyView.setEnabled(true);
                        es.printStackTrace();
                    }
                }
            }
        });

//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl(ConfigNetWork.WEB_URL);
    }


    //发送交易
    private void sendTrade(String shouldPayVals,String signVal) {

        String tuijianVal = tuijinaView.getText().toString();

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String headerSign = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(shouldPayVals);
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol("BIW");
        sendTradeRequest.setTo_address(publicAddressVal);
        sendTradeRequest.setType(1);
        sendTradeRequest.setReferer_code(tuijianVal);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
                .tag(BidBuyActivity.this)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", walletModel.getAddress() + "#" + msg + "#" + headerSign)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                try {

                                    Gson gson = new Gson();
                                    SendTradeResponse response = gson.fromJson(backVal, SendTradeResponse.class);
                                    if (response != null && response.getCode() == 0) {
//                                    Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_success), Toast.LENGTH_SHORT).show();

                                        //刷新状态
                                        EventBus.getDefault().post(new BidSuccessEvent());
                                        new SuccessDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_sucdess));


                                    } else {
//                                        Toast.makeText(BidBuyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        new FailDialogView().showTips(BidBuyActivity.this, response.getMsg());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

//                                Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        try {
                            new FailDialogView().showTips(BidBuyActivity.this, getString(R.string.bid_open_fail));
                            dismissDialog();
                            buyView.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            buyView.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        try {
                            buyView.setEnabled(true);
                            dismissDialog();
                        } catch (Exception e) {
                            buyView.setEnabled(true);
                            e.printStackTrace();
                        }
                    }

                });
    }


    //获取官方地址
    private void getPublicAddress() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.PUBLIC_ADDRESS_URL + "/org_address")
                .tag(BidBuyActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    PublicAddressResponse response = gson.fromJson(backVal, PublicAddressResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().size() > 0) {
                                            setAddress(response.getData());
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissDialog();
                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setAddress(List<PublicAddressResponse.PublicAddressModel> data) {
        for (int i = 0; i < data.size(); i++) {
            PublicAddressResponse.PublicAddressModel addressModel = data.get(i);
            if (addressModel != null) {
                if (addressModel.getK().equalsIgnoreCase("org_address_bid")) {
                    publicAddressVal = addressModel.getV();
                    break;
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveClose(BidSuccessEvent nameEvent) {
        finish();
    }



    private void getLeftMoneyRequest() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(BidBuyActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    LeftMoneyResponse response = gson.fromJson(backVal, LeftMoneyResponse.class);
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().size() > 0) {
                                        getLeftMoney(response.getData());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissDialog();
                    }
                });
    }


    private void getLeftMoney(List<LeftMoneyResponse.LeftMoneyModel> allMoney) {
//        List<ZcMoneyModel> allMoney = ZcDaoUtils.getAllZcData();
        if (allMoney != null && allMoney.size() > 0) {
            for (int i = 0; i < allMoney.size(); i++) {
                LeftMoneyResponse.LeftMoneyModel zcMoneyModel = allMoney.get(i);
                if (!TextUtils.isEmpty(zcMoneyModel.getName())) {
                    if (zcMoneyModel.getName().equalsIgnoreCase("BIW")) {
                        if (TextUtils.isEmpty(zcMoneyModel.getValue_qty()) || zcMoneyModel.getValue_qty().equals("0") || zcMoneyModel.getValue_qty().equals("0.000000")) {
                            leftMoneyVal = "0";
                        } else {
                            leftMoneyVal = zcMoneyModel.getValue_qty();
                        }
                    }
                }
            }
        }

        leftView.setText(":" + leftMoneyVal + " BIW");

    }


//    publicAddressVal


}
