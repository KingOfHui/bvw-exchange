package com.darknet.bvw.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.Entity.ZcMoneyModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.db.ZcDaoUtils;
import com.darknet.bvw.model.ExchangeZcResponse;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.event.TradeSuccessEvent;
import com.darknet.bvw.model.request.CreateTradeRequest;
import com.darknet.bvw.model.request.HuaZhangRequest;
import com.darknet.bvw.model.request.SendTradeRequest;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.model.response.CreateTradeResponse.JsonRootBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.model.response.DayLeftMoneyResponse;
import com.darknet.bvw.model.response.HZLeftMoneyResponse;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.model.response.PublicAddressResponse;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.util.DecimalInputTextWatcher;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BottomDialogView;
import com.darknet.bvw.view.CustomProgressDialog;
import com.darknet.bvw.view.HuaZhangFailDialogView;
import com.darknet.bvw.view.HuaZhangSuccessDialogView;
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

public class HuaZhangActivity extends BaseActivity implements View.OnClickListener {


    private TextView titleContent;

    private RelativeLayout backLayout;


    private ExchangeZcResponse.EZcModel zcModel;


    private TextView hzFromSign;
    private TextView hzFromAddress;

    private TextView hzToSign;
    private TextView hzToAddress;

    private LinearLayout hzChangeLayout;
    private EditText hzNumView;

    private TextView leftMoneyView;

    private TextView hzSubmitView;

    private TextView hzTypeView;


    private String publicAddressVal;
    private String qianbaoAddressVal;

    BridgeWebView webView;

    private Dialog mDialog;

    //0 从jy 到 qb,1 从qb 到 jy
    private int type = 0;

    private String qianbaoLeftMoney;

    private TextView huazhangTypeView;

    private TextView allinView;

    private TextView leftMoneyNoticeView;

    private TextView leftMoneySignNotice;

    @Override
    public void initView() {

        zcModel = (ExchangeZcResponse.EZcModel) getIntent().getSerializableExtra("zcModel");

//        Log.e("zcModel", zcModel.toString());

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);

        hzFromSign = findViewById(R.id.huazhang_top_view_item_two);
        hzFromAddress = findViewById(R.id.huazhang_top_view_item_three);

        hzToSign = findViewById(R.id.huazhang_bom_view_item_two);
        hzToAddress = findViewById(R.id.huazhang_bom_view_item_three);

        hzChangeLayout = findViewById(R.id.huangzhang_change_view);
        hzNumView = findViewById(R.id.huangzhang_num_view);
        leftMoneyView = findViewById(R.id.hz_left_money_view);

        hzSubmitView = findViewById(R.id.hz_submit_view);

        hzTypeView = findViewById(R.id.hz_money_type);

        huazhangTypeView = findViewById(R.id.huazhang_input_usdt_txt);

        leftMoneyNoticeView = findViewById(R.id.left_money_notice_view);

        allinView = findViewById(R.id.huazhang_input_all_txt);

        leftMoneySignNotice = findViewById(R.id.jiaoyisuo_leftmoney_notice_view);

        webView = findViewById(R.id.webView);
//        showLeftMoney();
        getLeftMoneyRequest();
        initDialog();

        hzSubmitView.setOnClickListener(this);
        hzChangeLayout.setOnClickListener(this);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hideSoftKeyBoard();


        hzTypeView.setText("(" + zcModel.getSymbol() + ")");

        huazhangTypeView.setText(zcModel.getSymbol());

//        leftMoneyView.setText(zcModel.getBalance().stripTrailingZeros().toPlainString() + zcModel.getSymbol());


        if (zcModel.getSymbol() != null) {
            if (zcModel.getSymbol().equalsIgnoreCase("BVW")) {
                hzNumView.addTextChangedListener(new DecimalInputTextWatcher(hzNumView, 20, 2));
            } else if (zcModel.getSymbol().equalsIgnoreCase("BTC")) {
                hzNumView.addTextChangedListener(new DecimalInputTextWatcher(hzNumView, 20, 6));
            } else if (zcModel.getSymbol().equalsIgnoreCase("ETH")) {
                hzNumView.addTextChangedListener(new DecimalInputTextWatcher(hzNumView, 20, 4));
            } else if (zcModel.getSymbol().equalsIgnoreCase("USDT")) {
                hzNumView.addTextChangedListener(new DecimalInputTextWatcher(hzNumView, 20, 3));
            }
        }


        allinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (type == 1) {

//                    if(ArithmeticUtils.compare(zcModel.getBalance().toPlainString(),"0.001")){
                    if (ArithmeticUtils.compare(leftBvwMoney, "0.001")) {
                        if (zcModel.getSymbol().equalsIgnoreCase("BVW")) {
//                            qianbaoLeftMoney = ArithmeticUtils.minusDown(qianbaoLeftMoney, "0.001", 2);
                            String afterHandleVal = ArithmeticUtils.minusDown(qianbaoLeftMoney, "0.001", 2);
                            hzNumView.setText(afterHandleVal);
                        } else {
                            hzNumView.setText(qianbaoLeftMoney);
                        }
//                        hzNumView.setText(qianbaoLeftMoney);
                    } else {
                        Toast.makeText(HuaZhangActivity.this, getResources().getString(R.string.notice_add_money_type), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hzNumView.setText(zcModel.getBalance().stripTrailingZeros().toPlainString());
                }

            }
        });

    }

    private void initDialog() {
        mDialog = CustomProgressDialog.createLoadingDialog(this, getString(R.string.load_data));
    }

    private void hideSoftKeyBoard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_huazhang;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getPublicAddress();
        getMoneyLeftData();

    }

    @Override
    public void configViews() {

    }



    //用户每日划转剩余额度
    private void getMoneyLeftData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.MONEY_HUAZHANG_LEFT_URL)
                .tag(HuaZhangActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    HZLeftMoneyResponse response = gson.fromJson(backVal, HZLeftMoneyResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setLeftMoneyVal(response.getData());
                                        }
                                    }else {
                                        leftMoneySignNotice.setVisibility(View.GONE);
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
//                        dismissDialog();
                    }
                });

    }


    private void setLeftMoneyVal(HZLeftMoneyResponse.HzLeftMoneyModel hzLeftMoneyModel){
        if(!TextUtils.isEmpty(hzLeftMoneyModel.getMsg())){
            leftMoneySignNotice.setText(hzLeftMoneyModel.getMsg());
        }else {
            leftMoneySignNotice.setVisibility(View.GONE);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hz_submit_view:
                if (type == 1) {
                    submitToJiaosuoClick();
                } else {
                    submitToQianBaoOne();
                }
//                submitClick();
                break;
            case R.id.huangzhang_change_view:

                if (type == 0) {
                    type = 1;

                    if(zcModel.getSymbol().equalsIgnoreCase("BVW")){
                        leftMoneySignNotice.setVisibility(View.VISIBLE);
                    }else {
                        leftMoneySignNotice.setVisibility(View.GONE);
                    }

                } else {
                    type = 0;
                    leftMoneySignNotice.setVisibility(View.GONE);
                }
                changeHzType();
                break;
        }
    }


    private void submitToQianBaoOne() {
        String numVal = hzNumView.getText().toString();
        if (numVal == null || numVal.trim().length() == 0) {
            Toast.makeText(HuaZhangActivity.this, getResources().getString(R.string.hz_input_num_sign), Toast.LENGTH_SHORT).show();
            return;
        }

        String leftVal = zcModel.getBalance().toString();

        //如果转账金额，大于余额，提示
        if (new BigDecimal(numVal).compareTo(new BigDecimal(leftVal)) > 0) {
            Toast.makeText(HuaZhangActivity.this, getString(R.string.trade_money_to_much), Toast.LENGTH_SHORT).show();
            return;
        }

        if (new BigDecimal(numVal).compareTo(BigDecimal.ZERO) == 0) {
            Toast.makeText(HuaZhangActivity.this, getString(R.string.trade_money_to_low), Toast.LENGTH_SHORT).show();
            return;
        }


        BottomDialogView.Builder builder = new BottomDialogView.Builder(HuaZhangActivity.this);
        View dialogView = (View) LayoutInflater.from(HuaZhangActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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
                    Toast.makeText(HuaZhangActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();
                        submitToQianBaoClick();
                    } else {
                        Toast.makeText(HuaZhangActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void submitToQianBaoClick() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        String numVal = hzNumView.getText().toString();


        HuaZhangRequest huaZhangRequest = new HuaZhangRequest();
        huaZhangRequest.setAmount(numVal);
        huaZhangRequest.setSymbol(zcModel.getSymbol());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(huaZhangRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        mDialog.show();

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.HUAZHANG_FROM_SUO_TO_BAO)
                .tag(HuaZhangActivity.this)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal", "backVal=" + backVal);
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    BaseResponse response = gson.fromJson(backVal, BaseResponse.class);
                                    if (response != null && response.getCode() == 0) {
//                                        setMoreVal(response.getData().getItems());
//                                        Intent tradeIntent = new Intent(HuaZhangActivity.this, MessageCenterActivity.class);
//                                        startActivity(tradeIntent);
//                                        finish();

                                        try {
                                            new HuaZhangSuccessDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_success_sign));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        Toast.makeText(HuaZhangActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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
//                        setNoData();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mDialog.dismiss();
                    }
                });

    }


    private void submitToJiaosuoClick() {

        String numVal = hzNumView.getText().toString();
        if (numVal == null || numVal.trim().length() == 0) {
            Toast.makeText(HuaZhangActivity.this, getResources().getString(R.string.hz_input_num_sign), Toast.LENGTH_SHORT).show();
            return;
        }

        String leftVal = qianbaoLeftMoney;

        //如果转账金额，大于余额，提示
        if (new BigDecimal(numVal).compareTo(new BigDecimal(leftVal)) > 0) {
            Toast.makeText(HuaZhangActivity.this, getString(R.string.trade_money_to_much), Toast.LENGTH_SHORT).show();
            return;
        }

        if (new BigDecimal(numVal).compareTo(BigDecimal.ZERO) == 0) {
            Toast.makeText(HuaZhangActivity.this, getString(R.string.trade_money_to_low), Toast.LENGTH_SHORT).show();
            return;
        }

//        try {
//            if (numVal.contains(".")) {
//                //说明有小数
//                String afterPoint = numVal.substring(numVal.indexOf(".") + 1, numVal.length());
//                if (afterPoint.length() > 6) {
//                    Toast.makeText(HuaZhangActivity.this, getString(R.string.wallet_trade_after_notice), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        BottomDialogView.Builder builder = new BottomDialogView.Builder(HuaZhangActivity.this);

        View dialogView = (View) LayoutInflater.from(HuaZhangActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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
                    Toast.makeText(HuaZhangActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();
                        createTrade(numVal, zcModel.getSymbol());
                    } else {
                        Toast.makeText(HuaZhangActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    private void createTrade(String num, String walletType) {

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


        try {
//            showDialog(getString(R.string.load_data));
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.CREATE_TRADE_URL)
                .tag(HuaZhangActivity.this)
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
                                        if (response.getData() != null) {
                                            getSignValThree(response.getData(), walletModel.getPrivateKey());
                                        } else {

                                            try {
                                                new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, response.getMsg());
                                                hzSubmitView.setEnabled(true);
//                                                dismissDialog();

                                                if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                                                    mDialog.dismiss();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
                                            hzSubmitView.setEnabled(true);
//                                            dismissDialog();
                                            if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                                                mDialog.dismiss();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    dismissDialog();
//                                    Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                    try {
//                                        dismissDialog();
                                        if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                                            mDialog.dismiss();
                                        }
                                        new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
                                        hzSubmitView.setEnabled(true);
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
//                            dismissDialog();
                            if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                                mDialog.dismiss();
                            }
                            new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
                            hzSubmitView.setEnabled(true);
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
                        new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
                        hzSubmitView.setEnabled(true);
//                        dismissDialog();
                        if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                            mDialog.dismiss();
                        }
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

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String headerSign = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

        if (publicAddressVal == null) {
            Toast.makeText(HuaZhangActivity.this, "获取地址失败，请退出重试", Toast.LENGTH_SHORT).show();
            return;
        }

        String numVal = hzNumView.getText().toString();

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(numVal);
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol(zcModel.getSymbol());
        sendTradeRequest.setTo_address(publicAddressVal);
        sendTradeRequest.setType(5);
        //备战最大30字符
//        sendTradeRequest.setDemo(beizhuContent);
        //提现地址,仅提现类型时使用
//        sendTradeRequest.setWithdraw_address(tempAddress);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
                .tag(HuaZhangActivity.this)
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
                                        EventBus.getDefault().post(new TradeSuccessEvent());

                                        try {
                                            new HuaZhangSuccessDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_success_sign));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                        EventBus.getDefault().post(new TradeSuccessEvent());

//                                        EventBus.getDefault().post(new PushEvent());

                                        Toast.makeText(HuaZhangActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                } catch (Exception e) {
                                    try {
                                        new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
//                                        EventBus.getDefault().post(new PushEvent());
//                                        finish();
                                    } catch (Exception es) {
                                        es.printStackTrace();
                                    }
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
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
                            new HuaZhangFailDialogView().showTips(HuaZhangActivity.this, getString(R.string.dialog_fail_sign));
//                            dismissDialog();
                            if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                                mDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        try {
//                            dismissDialog();
                            if (!((Activity) HuaZhangActivity.this).isFinishing() && !((Activity) HuaZhangActivity.this).isDestroyed()) {
                                mDialog.dismiss();
                            }
                            hzSubmitView.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });


        //发送完请求，结束当前页面
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                syncSleepData();
//                //关闭当前页面
//                EventBus.getDefault().post(new TradeSuccessEvent());
//                //通知主页面，跳转消息记录
////                EventBus.getDefault().post(new PushEvent());
//
//                Intent tradeIntent = new Intent(HuaZhangActivity.this, MessageCenterActivity.class);
//                startActivity(tradeIntent);
//
//                finish();
//            }
//        }, 1100);


    }


    private void changeHzType() {

        hzNumView.setText("");

        //            String addressVals = tradeListModel.getTo_address();
        StringBuilder sbPub = new StringBuilder();
        if (publicAddressVal != null) {
            sbPub.append(publicAddressVal.substring(0, 5));
            sbPub.append("...");
            sbPub.append(publicAddressVal.substring((publicAddressVal.length() - 5), publicAddressVal.length()));
        }

//            String addressVals = tradeListModel.getTo_address();
        StringBuilder sbQB = new StringBuilder();
        if (qianbaoAddressVal != null) {
            sbQB.append(qianbaoAddressVal.substring(0, 5));
            sbQB.append("...");
            sbQB.append(qianbaoAddressVal.substring((qianbaoAddressVal.length() - 5), qianbaoAddressVal.length()));
        }

        if (type == 0) {
            hzFromSign.setText(getResources().getString(R.string.hz_jiaoyi_suo_sign));
            hzToSign.setText(getResources().getString(R.string.hz_myqianbao_sign));

            hzFromAddress.setText(sbPub.toString());
            hzToAddress.setText(sbQB.toString());
            leftMoneyView.setText(zcModel.getBalance().stripTrailingZeros().toPlainString() + zcModel.getSymbol());

            leftMoneyNoticeView.setText(getResources().getString(R.string.hz_jiaoyisuo_left_money));

            leftMoneySignNotice.setVisibility(View.GONE);
        } else {
            hzFromSign.setText(getResources().getString(R.string.hz_myqianbao_sign));
            hzToSign.setText(getResources().getString(R.string.hz_jiaoyi_suo_sign));
            hzFromAddress.setText(sbQB.toString());
            hzToAddress.setText(sbPub.toString());
            leftMoneyView.setText(qianbaoLeftMoney + zcModel.getSymbol());
            leftMoneyNoticeView.setText(getResources().getString(R.string.hz_jiaoyisuo_left_money_two));

            if(zcModel.getSymbol().equalsIgnoreCase("BVW")){
                leftMoneySignNotice.setVisibility(View.VISIBLE);
            }else {
                leftMoneySignNotice.setVisibility(View.GONE);
            }

        }


    }


    //获取官方地址
    private void getPublicAddress() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.PUBLIC_ADDRESS_URL + "/org_address")
                .tag(HuaZhangActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            Log.d(TAG, "response:" + backVal);
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
//                        dismissDialog();
                    }
                });
    }


    private void setAddress(List<PublicAddressResponse.PublicAddressModel> data) {
        for (int i = 0; i < data.size(); i++) {
            PublicAddressResponse.PublicAddressModel addressModel = data.get(i);
            if (addressModel != null) {
                if (addressModel.getK().equalsIgnoreCase("org_address_hz2ex")) {
                    publicAddressVal = addressModel.getV();
                    break;
                }
            }
        }

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        qianbaoAddressVal = walletModel.getAddress();


        //            String addressVals = tradeListModel.getTo_address();
        StringBuilder sbPub = new StringBuilder();
        if (publicAddressVal != null) {
            sbPub.append(publicAddressVal.substring(0, 5));
            sbPub.append("...");
            sbPub.append(publicAddressVal.substring((publicAddressVal.length() - 5), publicAddressVal.length()));
        }

//            String addressVals = tradeListModel.getTo_address();
        StringBuilder sbQB = new StringBuilder();
        if (qianbaoAddressVal != null) {
            sbQB.append(qianbaoAddressVal.substring(0, 5));
            sbQB.append("...");
            sbQB.append(qianbaoAddressVal.substring((qianbaoAddressVal.length() - 5), qianbaoAddressVal.length()));
        }

        if (type == 0) {
            hzFromSign.setText(getResources().getString(R.string.hz_jiaoyi_suo_sign));
            hzToSign.setText(getResources().getString(R.string.hz_myqianbao_sign));

            hzFromAddress.setText(sbPub.toString());
            hzToAddress.setText(sbQB.toString());
        } else {
            hzFromSign.setText(getResources().getString(R.string.hz_myqianbao_sign));
            hzToSign.setText(getResources().getString(R.string.hz_jiaoyi_suo_sign));
            hzFromAddress.setText(sbQB.toString());
            hzToAddress.setText(sbPub.toString());
        }
    }


    private String leftBvwMoney;


    private void showLeftMoney() {

        List<ZcMoneyModel> allZcMoneys = ZcDaoUtils.getAllZcData();

        for (int k = 0; k < allZcMoneys.size(); k++) {
            ZcMoneyModel tempZcDel = allZcMoneys.get(k);
            if (tempZcDel.getSymbol().equalsIgnoreCase(zcModel.getSymbol())) {
                qianbaoLeftMoney = tempZcDel.getValue_qty();
            }

            if (tempZcDel.getSymbol().equalsIgnoreCase("BVW")) {
                leftBvwMoney = tempZcDel.getValue_qty();
            }
        }
    }


    private void getLeftMoneyRequest() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(HuaZhangActivity.this)
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

                        try {
                            dismissDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    private void getLeftMoney(List<LeftMoneyResponse.LeftMoneyModel> allMoney) {

        if (allMoney != null && allMoney.size() > 0) {
            for (int i = 0; i < allMoney.size(); i++) {
                LeftMoneyResponse.LeftMoneyModel zcMoneyModel = allMoney.get(i);
                if (!TextUtils.isEmpty(zcMoneyModel.getName())) {

                    if (zcMoneyModel.getName().equalsIgnoreCase(zcModel.getSymbol())) {
                        qianbaoLeftMoney = zcMoneyModel.getValue_qty();
                    }

                    if (zcMoneyModel.getName().equalsIgnoreCase("BVW")) {
                        leftBvwMoney = zcMoneyModel.getValue_qty();
                    }
                }
            }

            if (type == 0) {
                type = 1;
            } else {
                type = 0;
            }
            changeHzType();

            getPublicAddress();
        }
    }


}
