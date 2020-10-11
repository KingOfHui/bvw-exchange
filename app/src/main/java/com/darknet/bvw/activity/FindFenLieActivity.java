package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.Entity.ZcMoneyModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.db.ZcDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.event.FenLieEvent;
import com.darknet.bvw.model.request.CreateTradeRequest;
import com.darknet.bvw.model.request.SendTradeRequest;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.CreateTradeResponse.JsonRootBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.model.response.FenLieOrderResponse;
import com.darknet.bvw.model.response.FenLieSecResponse;
import com.darknet.bvw.model.response.PublicAddressResponse;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.view.BottomDialogView;
import com.darknet.bvw.view.FailDialogView;
import com.darknet.bvw.view.FenLieDialogFail;
import com.darknet.bvw.view.FenLieDialogSuccess;
import com.darknet.bvw.view.FenLieFailTwoDialog;
import com.darknet.bvw.view.RunningDialog;
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

import java.math.BigDecimal;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FindFenLieActivity extends BaseActivity implements View.OnClickListener {


    private TextView titleRight;
    private RelativeLayout backLayout;

    private TextView btcView;
    private TextView ethView;

    private View btcLayout;
    private View ethLayout;

    //0 btc,1 eth
    private int type = 0;

    RelativeLayout btcBiaoView;
    RelativeLayout ethBiaoView;

    private ImageView btcguizeImg;
    private ImageView ethguizeImg;

    private TextView btcSubmitView;
    private TextView ethSubmitView;


    private TextView btcFenlieBiView;
    private TextView ethFenlieBiView;

    private TextView btcYueView;
    private TextView ethYueView;

//    private TextView btcFenlieNumView;
//    private TextView ethFenlieNumView;

    private String btcBvw;
    private String ethBvw;
    private String btcVal;
    private String btcLfVal;
    private String ethVal;
    private String ethLfVal;

    private String publicAddressVal;

    private EditText btcInputNumView;
    private EditText ethInputNumView;

    BridgeWebView webView;


    private ScrollView scrollView;

    private FenLieSecResponse.FenLieSendModel fenLieSendModel;


    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        btcBvw = getIntent().getStringExtra("btcBvw");
        ethBvw = getIntent().getStringExtra("ethBvw");

        btcVal = getIntent().getStringExtra("btcVal");
        btcLfVal = getIntent().getStringExtra("btcLfVal");
        ethVal = getIntent().getStringExtra("ethVal");
        ethLfVal = getIntent().getStringExtra("ethLfVal");
        fenLieSendModel = (FenLieSecResponse.FenLieSendModel) getIntent().getSerializableExtra("model");


        btcView = (TextView) this.findViewById(R.id.find_fenlie_btc_view);
        ethView = (TextView) this.findViewById(R.id.find_fenlie_eth_view);

        btcLayout = (View) this.findViewById(R.id.fenlie_btc_layout);
        ethLayout = (View) this.findViewById(R.id.fenlie_eth_layout);

        btcBiaoView = (RelativeLayout) this.findViewById(R.id.fenlie_btc_jishu_layout);
        ethBiaoView = (RelativeLayout) this.findViewById(R.id.fenlie_eth_jishu_layout);

        titleRight = (TextView) this.findViewById(R.id.title_right);
        btcguizeImg = (ImageView) this.findViewById(R.id.fenlie_guize_btc_img);
        ethguizeImg = (ImageView) this.findViewById(R.id.fenlie_guize_eth_img);

        btcSubmitView = (TextView) this.findViewById(R.id.fenlie_btc_submit);
        ethSubmitView = (TextView) this.findViewById(R.id.fenlie_eth_submit);

        btcFenlieBiView = (TextView) this.findViewById(R.id.btc_fenlie_bili);
        ethFenlieBiView = (TextView) this.findViewById(R.id.eth_fenlie_bili);

        webView = (BridgeWebView) this.findViewById(R.id.webView);

        btcYueView = (TextView) this.findViewById(R.id.btc_yue_view);
        ethYueView = (TextView) this.findViewById(R.id.eth_yue_view);

        btcInputNumView = (EditText) this.findViewById(R.id.fenlie_input_btc_num_view);
        ethInputNumView = (EditText) this.findViewById(R.id.fenlie_input_eth_num_view);

        btcInputNumView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        ethInputNumView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


//        btcFenlieNumView = (TextView) this.findViewById(R.id.btc_fenlie_num_view);
//        ethFenlieNumView = (TextView) this.findViewById(R.id.eth_fenlie_num_view);

        scrollView = (ScrollView) this.findViewById(R.id.fenlie_find_scrollview);


        backLayout = (RelativeLayout) this.findViewById(R.id.layBack);

        btcView.setOnClickListener(this);
        ethView.setOnClickListener(this);
        backLayout.setOnClickListener(this);
        btcBiaoView.setOnClickListener(this);
        ethBiaoView.setOnClickListener(this);
        titleRight.setOnClickListener(this);

        btcSubmitView.setOnClickListener(this);
        ethSubmitView.setOnClickListener(this);

        changeSelectSign(0);
        scrollView.scrollTo(0, 0);

        btcFenlieBiView.setText("1BTC:" + btcBvw + "BIW");
        ethFenlieBiView.setText("1ETH:" + ethBvw + "BIW");


//        btcFenlieNumView.setText("" + btcLfVal + "BTW");
//        ethFenlieNumView.setText("" + ethLfVal + "BTW");


        int lanType = SPUtil.getInstance(FindFenLieActivity.this).getSelectLanguage();
        if (lanType == 1) {
            btcguizeImg.setImageResource(R.mipmap.fenlie_notice_img);
            ethguizeImg.setImageResource(R.mipmap.fenlie_notice_img);
        } else if (lanType == 0) {
            btcguizeImg.setImageResource(R.mipmap.fenlie_notice_img_en);
            ethguizeImg.setImageResource(R.mipmap.fenlie_notice_img_en);
        } else {
            btcguizeImg.setImageResource(R.mipmap.fenlie_notice_img_en);
            ethguizeImg.setImageResource(R.mipmap.fenlie_notice_img_en);
        }

        hideSoftKeyBoard();
    }


    private void hideSoftKeyBoard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_find_fenlie;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getPublicAddress();
        showLeftMoney();
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_fenlie_btc_view:
                type = 0;
                changeSelectSign(0);
                scrollView.scrollTo(0, 0);
                break;
            case R.id.find_fenlie_eth_view:
                type = 1;
                changeSelectSign(1);
                scrollView.scrollTo(0, 0);
                break;
            case R.id.fenlie_btc_jishu_layout:
                Intent btcBiaoIntent = new Intent(FindFenLieActivity.this, FindFenLieBiaoActivity.class);
                startActivity(btcBiaoIntent);
                break;
            case R.id.fenlie_eth_jishu_layout:
                Intent ethBiaoIntent = new Intent(FindFenLieActivity.this, FindFenLieBiaoActivity.class);
                startActivity(ethBiaoIntent);
                break;
            case R.id.title_right:
                Intent orderListIntent = new Intent(FindFenLieActivity.this, FenLieOrderActivity.class);
//                Intent orderListIntent = new Intent(FindFenLieActivity.this, BidJiangLiActivity.class);
//                Intent orderListIntent = new Intent(FindFenLieActivity.this, ZhenLieActivity.class);
                startActivity(orderListIntent);
                break;
            case R.id.layBack:
                finish();
                break;
            case R.id.fenlie_btc_submit:

                if (fenLieSendModel == null) {
                    Toast.makeText(FindFenLieActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
                } else {
                    if (fenLieSendModel.getStatus() == 1) {
                        payBtcDialog();
                    } else {
                        Toast.makeText(FindFenLieActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.fenlie_eth_submit:

                if (fenLieSendModel == null) {
                    Toast.makeText(FindFenLieActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
                } else {
                    if (fenLieSendModel.getStatus() == 1) {
                        payEthDialog();
                    } else {
                        Toast.makeText(FindFenLieActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    private void changeSelectSign(int currentType) {

        if (currentType == 0) {
            btcView.setBackgroundResource(0);
            ethView.setBackgroundResource(R.drawable.fen_lie_first_bg);
            btcLayout.setVisibility(View.VISIBLE);
            ethLayout.setVisibility(View.GONE);
        } else {
            btcView.setBackgroundResource(R.drawable.fen_lie_first_bg);
            ethView.setBackgroundResource(0);
            btcLayout.setVisibility(View.GONE);
            ethLayout.setVisibility(View.VISIBLE);
        }

        scrollView.scrollTo(0, 0);

    }


    private void payBtcDialog() {


        String btcShouldPayVal = btcInputNumView.getText().toString().trim();
        if (btcShouldPayVal == null || btcShouldPayVal.length() == 0) {
            Toast.makeText(FindFenLieActivity.this, getString(R.string.fenlie_num_sign), Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal a = new BigDecimal(btcLeftVal);
        BigDecimal b = new BigDecimal(btcShouldPayVal);
        int compareResult = a.compareTo(b);

        if (compareResult == -1) {
            Toast.makeText(FindFenLieActivity.this, getString(R.string.bid_yue_not_encough), Toast.LENGTH_SHORT).show();
            return;
        }

        BottomDialogView.Builder builder = new BottomDialogView.Builder(FindFenLieActivity.this);

        View dialogView = (View) LayoutInflater.from(FindFenLieActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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

//                new FenLieDialogSuccess().showTips(FindFenLieActivity.this, "");

//                bottomDialog.dismiss();
//                hintKeyBoard();
//                payBtcDialog();
                String contentVal = contentView.getText().toString();
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(FindFenLieActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();
                        makeBTCTrade(btcShouldPayVal);
                    } else {
                        Toast.makeText(FindFenLieActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void payEthDialog() {

        String btcShouldPayVal = ethInputNumView.getText().toString().trim();
        if (btcShouldPayVal == null || btcShouldPayVal.length() == 0) {
            Toast.makeText(FindFenLieActivity.this, getString(R.string.fenlie_num_sign), Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal a = new BigDecimal(ethLeftVal);
        BigDecimal b = new BigDecimal(btcShouldPayVal);
        int compareResult = a.compareTo(b);

        if (compareResult == -1) {
            Toast.makeText(FindFenLieActivity.this, getString(R.string.bid_yue_not_encough), Toast.LENGTH_SHORT).show();
            return;
        }

        BottomDialogView.Builder builder = new BottomDialogView.Builder(FindFenLieActivity.this);

        View dialogView = (View) LayoutInflater.from(FindFenLieActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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

//                new FenLieDialogFail().showTips(FindFenLieActivity.this, "");

            }
        });


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                new FenLieDialogSuccess().showTips(FindFenLieActivity.this, "");

//                bottomDialog.dismiss();
//                hintKeyBoard();

                String contentVal = contentView.getText().toString();
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(FindFenLieActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();
                        makeETHTrade(btcShouldPayVal);
                    } else {
                        Toast.makeText(FindFenLieActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void makeBTCTrade(String shouldval) {
        if (publicAddressVal == null) {
            //如果没有获取到官网地址，继续获取
            getPublicAddress();
            return;
        }
        createBTCTrade(shouldval, "BTC");


    }


    private void makeETHTrade(String shouldval) {
        if (publicAddressVal == null) {
            //如果没有获取到官网地址，继续获取
            getPublicAddress();
            return;
        }
        createETHTrade(shouldval, "ETH");

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
                .tag(FindFenLieActivity.this)
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
                if (addressModel.getK().equalsIgnoreCase("org_address_fl")) {
                    publicAddressVal = addressModel.getV();
                    break;
                }
            }
        }
    }


    private void createBTCTrade(String num, String walletType) {

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
                .tag(FindFenLieActivity.this)
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
                                            getSignValBTCThree(response.getData(), walletModel.getPrivateKey());
                                        } else {

                                            try {
                                                new FailDialogView().showTips(FindFenLieActivity.this, response.getMsg());
                                                dismissDialog();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
                                        new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
                            new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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

    private void getSignValBTCThree(SendTx data, String priVal) {

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
                    sendBTCTrade(afterSignVal);
                } catch (Exception e) {
                    e.printStackTrace();
//                    dismissDialog();

                    try {
                        new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
    private void sendBTCTrade(String signVal) {

//        String tuijianVal = tuijinaView.getText().toString();


        String btcShouldPayVal = btcInputNumView.getText().toString().trim();

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String headerSign = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

//        String tempAddress = editSkrAddress.getText().toString();
//        String tempNumVal = editZzMoney.getText().toString();

//        beizhuContent = editRemark.getText().toString();

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(btcShouldPayVal);
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol("BTC");
        sendTradeRequest.setTo_address(publicAddressVal);
        sendTradeRequest.setType(2);
//        sendTradeRequest.setReferer_code(tuijianVal);
//        sendTradeRequest.setDemo(beizhuContent);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
                .tag(FindFenLieActivity.this)
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
//                                        EventBus.getDefault().post(new BidSuccessEvent());
//                                        new SuccessDialogView().showTips(FindFenLieActivity.this, getString(R.string.fenlie_open_sucdess));
                                        tempHashVal = response.getData();

                                        new RunningDialog().showTips(FindFenLieActivity.this, "");

                                    } else {
//                                        Toast.makeText(BidBuyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        new FailDialogView().showTips(FindFenLieActivity.this, response.getMsg());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
                            new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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


    private String btcLeftVal = "0";
    private String ethLeftVal = "0";
    private String tempHashVal;

    private void showLeftMoney() {

        List<ZcMoneyModel> allZcMoneys = ZcDaoUtils.getAllZcData();

        for (int k = 0; k < allZcMoneys.size(); k++) {
            ZcMoneyModel tempZcDel = allZcMoneys.get(k);
            if (tempZcDel.getSymbol().equalsIgnoreCase("BTC")) {
                btcLeftVal = tempZcDel.getValue_qty();
            }

            if (tempZcDel.getSymbol().equalsIgnoreCase("ETH")) {
                ethLeftVal = tempZcDel.getValue_qty();
            }


//            MoneyModel moneyModel = new MoneyModel();
//            moneyModel.setAddress(tempZcDel.getAddress());
//            moneyModel.setAssetref(tempZcDel.getAssetref());
//            moneyModel.setIcon(tempZcDel.getIcon());
//            moneyModel.setPrice(tempZcDel.getPrice());
//            moneyModel.setValue_qty(tempZcDel.getValue_qty());
//            moneyModel.setSymbol(tempZcDel.getSymbol());
//            moneyModel.setValue_cny(new BigDecimal(tempZcDel.getValue_cny()));
//            moneyModel.setValue_usd(new BigDecimal(tempZcDel.getValue_usd()));
//            moneyModel.setChain_deposit_address(tempZcDel.getChain_deposit_address());
        }

        btcYueView.setText("" + btcLeftVal + "BTC");
        ethYueView.setText("" + ethLeftVal + "ETH");
    }


    private void createETHTrade(String num, String walletType) {

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
                .tag(FindFenLieActivity.this)
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
                                    if (response != null) {

                                        if (response.getCode() == 0) {
                                            if (response.getData() != null) {
                                                getSignValETHThree(response.getData(), walletModel.getPrivateKey());
                                            } else {

                                                try {
                                                    new FailDialogView().showTips(FindFenLieActivity.this, response.getMsg());
                                                    dismissDialog();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else {
                                            try {
                                                new FailDialogView().showTips(FindFenLieActivity.this, response.getMsg());
                                                dismissDialog();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new FailDialogView().showTips(FindFenLieActivity.this, response.getMsg());
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
                                        new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
                            new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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

    private void getSignValETHThree(SendTx data, String priVal) {

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
                    sendETHTrade(afterSignVal);
                } catch (Exception e) {
                    e.printStackTrace();
//                    dismissDialog();

                    try {
                        new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
    private void sendETHTrade(String signVal) {

//        String tuijianVal = tuijinaView.getText().toString();


        String btcShouldPayVal = ethInputNumView.getText().toString().trim();

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String headerSign = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

//        String tempAddress = editSkrAddress.getText().toString();
//        String tempNumVal = editZzMoney.getText().toString();

//        beizhuContent = editRemark.getText().toString();

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(btcShouldPayVal);
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol("ETH");
        sendTradeRequest.setTo_address(publicAddressVal);
        sendTradeRequest.setType(2);
//        sendTradeRequest.setReferer_code(tuijianVal);
//        sendTradeRequest.setDemo(beizhuContent);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
                .tag(FindFenLieActivity.this)
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
//                                        EventBus.getDefault().post(new BidSuccessEvent());
//                                        new SuccessDialogView().showTips(FindFenLieActivity.this, getString(R.string.fenlie_open_sucdess));

                                        tempHashVal = response.getData();

                                        //发送交易走完，继续走30秒弹框
                                        new RunningDialog().showTips(FindFenLieActivity.this, "");

                                    } else {
//                                        Toast.makeText(BidBuyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        new FailDialogView().showTips(FindFenLieActivity.this, response.getMsg());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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
                            new FailDialogView().showTips(FindFenLieActivity.this, getString(R.string.bid_open_fail));
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(FenLieEvent addressEvent) {
        //拉订单接口，对比判断
        getOrderList();
    }


    private void getOrderList() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        TradeListRequest tradeRequest = new TradeListRequest();
//        tradeRequest.setType(0);
        tradeRequest.setPage(1);
        tradeRequest.setLimit(10);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_FIRST_ORDER_URL)
                .tag(FindFenLieActivity.this)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal","backVal="+backVal);
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    FenLieOrderResponse response = gson.fromJson(backVal, FenLieOrderResponse.class);
//                                    Log.e("backVal","backVal="+response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getCode() == 0 && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                                        checkVal(response.getData().getItems());
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
                    }
                });
    }


    private void checkVal(List<FenLieOrderResponse.FLOrderItemModel> orderItemModels) {

//        new FenLieFailTwoDialog().showTips(FindFenLieActivity.this, "");

        FenLieOrderResponse.FLOrderItemModel orderItemModel = orderItemModels.get(0);
        if (orderItemModel.getTx_hash().equals(tempHashVal)) {

            BigDecimal a = new BigDecimal(orderItemModel.getBack_rate());
            BigDecimal b = new BigDecimal("1");
            int compareResult = a.compareTo(b);

            if (compareResult == -1) {
                FenLieDialogFail fenLieDialogFail = new FenLieDialogFail();
                fenLieDialogFail.showTips(FindFenLieActivity.this, "", orderItemModel);
            } else {
                FenLieDialogSuccess fenLieDialogSuccess = new FenLieDialogSuccess();
                fenLieDialogSuccess.showTips(FindFenLieActivity.this, "", orderItemModel);
            }
        } else {
            new FenLieFailTwoDialog().showTips(FindFenLieActivity.this, "");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，
     * 来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
