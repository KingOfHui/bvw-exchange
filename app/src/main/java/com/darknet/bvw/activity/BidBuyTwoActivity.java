package com.darknet.bvw.activity;

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
import com.darknet.bvw.model.response.CreateTradeResponse.JsonRootBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.model.response.PublicAddressResponse;
import com.darknet.bvw.model.response.SendTradeResponse;
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
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BidBuyTwoActivity extends BaseActivity {


    BridgeWebView webView;
    //官方地址
    private String publicAddressVal;

    private String leftVal;

    private ImageView backView;
    private TextView leftView;
    private TextView shouldPayView;
    private TextView shouldSubPayView;
    private LinearLayout buyView;

    private String shouldPayVal;
    private String tuijianVal;

    @Override
    public void initView() {

        leftVal = getIntent().getStringExtra("leftVal");
        shouldPayVal = getIntent().getStringExtra("shouldVal");
        tuijianVal = getIntent().getStringExtra("tuijianVal");

        buyView = (LinearLayout) this.findViewById(R.id.bid_buy_sub_view);

        backView = (ImageView) this.findViewById(R.id.back_sign_view);

        webView = (BridgeWebView) findViewById(R.id.webView);


        leftView = (TextView) findViewById(R.id.bid_leader_left_money_view);

        shouldPayView = (TextView) findViewById(R.id.bid_lead_pay_val_view);
        shouldSubPayView = (TextView) findViewById(R.id.bid_leader_pay_val_view);

        leftView.setText(":" + leftVal + " BVW");
        shouldPayView.setText(new BigDecimal(shouldPayVal).stripTrailingZeros().toPlainString()  + "BVW");
        shouldSubPayView.setText(new BigDecimal(shouldPayVal).stripTrailingZeros().toPlainString() + "BVW");

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String tuijianVal = tuijinaView.getText().toString();
//                if (TextUtils.isEmpty(tuijianVal)) {
//                    Toast.makeText(BidBuyTwoActivity.this, getString(R.string.bid_buy_notice), Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (leftVal == null) {
                    Toast.makeText(BidBuyTwoActivity.this, getString(R.string.bid_yue_zero), Toast.LENGTH_SHORT).show();
                    return;
                }


                BigDecimal a = new BigDecimal(leftVal);
                BigDecimal b = new BigDecimal(shouldPayVal);

                int compareResult = a.compareTo(b);

                if (compareResult == -1) {
                    Toast.makeText(BidBuyTwoActivity.this, getString(R.string.bid_yue_not_encough), Toast.LENGTH_SHORT).show();
                    return;
                }


                BottomDialogView.Builder builder = new BottomDialogView.Builder(BidBuyTwoActivity.this);

                View dialogView = (View) LayoutInflater.from(BidBuyTwoActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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
                            Toast.makeText(BidBuyTwoActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                            if (contentVal.equals(walletModel.getPassword())) {
                                hintKeyBoard();
                                bottomDialog.dismiss();

                                makeTrade(tuijianVal);

                            } else {
                                Toast.makeText(BidBuyTwoActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        getPublicAddress();
    }

    private void makeTrade(String tuijianVals) {

        if (publicAddressVal == null) {
            //如果没有获取到官网地址，继续获取
            getPublicAddress();
            return;
        }

        createTrade(shouldPayVal, publicAddressVal, "BVW");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_bid_buy_two;
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
                .tag(BidBuyTwoActivity.this)
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
                                            getSignValThree(response.getData(), walletModel.getPrivateKey());
                                        } else {

                                            try {
                                                new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
                                                dismissDialog();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
                                            dismissDialog();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    dismissDialog();
//                                    Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                    try {
                                        dismissDialog();
                                        new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
                                    } catch (Exception es) {
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
                            new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }
                });
    }

    private void getSignValThree(SendTx data, String priVal) {

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
//                    Log.e("wallet", "....afterSignVal..data...." + afterSignVal);
                    sendTrade(afterSignVal);
                } catch (Exception e) {
                    e.printStackTrace();
//                    dismissDialog();

                    try {
                        new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
                        dismissDialog();
                    } catch (Exception es) {
                        es.printStackTrace();
                    }
                }
            }
        });

//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl(ConfigNetWork.WEB_URL);
    }


    //发送交易
    private void sendTrade(String signVal) {

//        String tuijianVal = tuijinaView.getText().toString();

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String headerSign = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

//        String tempAddress = editSkrAddress.getText().toString();
//        String tempNumVal = editZzMoney.getText().toString();

//        beizhuContent = editRemark.getText().toString();

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(shouldPayVal);
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol("BVW");
        sendTradeRequest.setTo_address(publicAddressVal);
        sendTradeRequest.setType(1);
        sendTradeRequest.setReferer_code(tuijianVal);
//        sendTradeRequest.setDemo(beizhuContent);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
                .tag(BidBuyTwoActivity.this)
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
                                        new SuccessDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_sucdess));


                                    } else {
//                                        Toast.makeText(BidBuyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        new FailDialogView().showTips(BidBuyTwoActivity.this, response.getMsg());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
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
                            new FailDialogView().showTips(BidBuyTwoActivity.this, getString(R.string.bid_open_fail));
                            dismissDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        try {
                            dismissDialog();
                        } catch (Exception e) {
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
                .tag(BidBuyTwoActivity.this)
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


//    publicAddressVal


}
