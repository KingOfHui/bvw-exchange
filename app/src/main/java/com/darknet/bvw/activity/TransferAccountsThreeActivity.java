package com.darknet.bvw.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.event.AddressEvent;
import com.darknet.bvw.model.event.TradeSuccessEvent;
import com.darknet.bvw.model.request.CreateTradeRequest;
import com.darknet.bvw.model.request.SendTradeRequest;
import com.darknet.bvw.model.response.CreateTradeResponse.JsonRootBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BottomDialogView;
import com.darknet.bvw.view.CustomProgressDialog;
import com.darknet.bvw.view.FailDialogView;
import com.darknet.bvw.view.SuccessDialogView;
import com.darknet.bvw.view.TypefaceTextView;
import com.darknet.bvw.view.ZhuanZhangNoticeDialog;
import com.darknet.bvw.zxing.android.CaptureActivity;
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


/**
 * @author zhangchen
 * <p>
 * 转账页面
 */
public class TransferAccountsThreeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title, txtKgFee;
    private EditText editSkrAddress, editZzMoney, editRemark;
    private SeekBar seekBar;
    private Switch switch1;
    private Button btnNext;
//    private TypefaceTextView monyType;

    BridgeWebView webView;
    private String moneyTypeVal;
    private String moneyType;
    private ImageView backSignImg;

    private String beizhuContent;
    private String leftVal;
    private TypefaceTextView userMoneyView;

    private ImageView contackPeopleView;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;


    private Dialog mDialog;

    private ImageView erweimaView;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        moneyType = getIntent().getStringExtra("type");
        leftVal = getIntent().getStringExtra("leftval");
        moneyTypeVal = moneyType;
//        EventBus.getDefault().register(this);

        backSignImg = findViewById(R.id.back_sign_view);
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        txtKgFee = findViewById(R.id.txtKgFee);
        editSkrAddress = findViewById(R.id.editSkrAddress);
        editZzMoney = findViewById(R.id.editZzMoney);
        editRemark = findViewById(R.id.editRemark);
        backSignImg.setImageResource(R.mipmap.close_trade_img);

//        monyType = findViewById(R.id.select_money_type);
        webView = (BridgeWebView) findViewById(R.id.webView);

        erweimaView = findViewById(R.id.trade_erweima_view);

        seekBar = findViewById(R.id.seekBar);
        switch1 = findViewById(R.id.switch1);
        btnNext = findViewById(R.id.btnNext);

        contackPeopleView = findViewById(R.id.trade_contack_people_view);

        userMoneyView = findViewById(R.id.trade_use_money_view);


        userMoneyView.setText(moneyTypeVal + " " + getString(R.string.trade_left_money_val) + ":" + leftVal);

        title.setText(moneyTypeVal +" "+ getString(R.string.trade_sign_title));
//        title.setText(getString(R.string.trade_account_one_title));

        layBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        contackPeopleView.setOnClickListener(this);
        erweimaView.setOnClickListener(this);
//        monyType.setOnClickListener(this);
        initDialog();
        hideSoftKeyBoard();
    }

    private void hideSoftKeyBoard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private void initDialog() {

        mDialog = CustomProgressDialog.createLoadingDialog(this, getString(R.string.load_data));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tranfer_accounts_three;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trade_erweima_view:
                //动态权限申请
                if (ContextCompat.checkSelfPermission(TransferAccountsThreeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TransferAccountsThreeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                break;
            case R.id.trade_contack_people_view:

                Intent intent = new Intent(TransferAccountsThreeActivity.this, ContactPeopleActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.layBack:
                TransferAccountsThreeActivity.this.finish();
                break;
            case R.id.btnNext:

                if (moneyType.equalsIgnoreCase("BVW")) {

                    String addressVal = editSkrAddress.getText().toString();
                    String numVal = editZzMoney.getText().toString();

                    boolean cheackVal = verifyInfo(addressVal, numVal);
                    if (cheackVal) {
                        //继续往下走
                    } else {
                        return;
                    }

                    BigDecimal compareVal = new BigDecimal(leftVal).subtract(new BigDecimal(numVal));
                    if (compareVal.compareTo(new BigDecimal(200)) < 0) {
//                    if ( < 200) {
                        new ZhuanZhangNoticeDialog().showTips(TransferAccountsThreeActivity.this, "", new ZhuanZhangNoticeDialog.DialogClickL() {
                            @Override
                            public void clickView() {
                                payDialog();
                            }
                        });
                    } else {
                        payDialog();
                    }
                } else {
                    payDialog();
                }


                break;
//            case R.id.select_money_type:
//                Intent intent = new Intent(TransferAccountsActivity.this, MoneyTypeActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan(){
        Intent intent = new Intent(TransferAccountsThreeActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.scan_quanxian_notice), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
//                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                editSkrAddress.setText(content);
//                tv_scanResult.setText("你扫描到的内容是：" + content);
            }
        }
    }



    private void sendTo() {

        String addressVal = editSkrAddress.getText().toString();
        String numVal = editZzMoney.getText().toString();

        boolean cheackVal = verifyInfo(addressVal, numVal);
        if (cheackVal) {
            //先查询余额
            btnNext.setEnabled(false);

            checkMoney(numVal, addressVal);
            //创建交易
//           createTrade();
        }
    }

    //1 默认方式(推荐)
    private void payDialog() {

        String addressVal = editSkrAddress.getText().toString();
        String numVal = editZzMoney.getText().toString();

        boolean cheackVal = verifyInfo(addressVal, numVal);
        if (cheackVal) {
            //继续往下走
        } else {
            return;
        }


        //如果转账金额，大于余额，提示
        if (new BigDecimal(numVal).compareTo(new BigDecimal(leftVal)) > 0) {
            Toast.makeText(TransferAccountsThreeActivity.this, getString(R.string.trade_money_to_much), Toast.LENGTH_SHORT).show();
            return;
        }

        if (new BigDecimal(numVal).compareTo(BigDecimal.ZERO) == 0) {
            Toast.makeText(TransferAccountsThreeActivity.this, getString(R.string.trade_money_to_low), Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            if (numVal.contains(".")) {
                //说明有小数
                String afterPoint = numVal.substring(numVal.indexOf(".") + 1, numVal.length());
                if (afterPoint.length() > 6) {
                    Toast.makeText(TransferAccountsThreeActivity.this, getString(R.string.wallet_trade_after_notice), Toast.LENGTH_SHORT).show();
                    return;
                }
//                System.out.println(afterPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//            if(){
//
//        }

        String beizhuVal = editRemark.getText().toString();
        if (!TextUtils.isEmpty(beizhuVal) && beizhuVal.length() > 30) {
            Toast.makeText(TransferAccountsThreeActivity.this, getString(R.string.trade_beizhu_notice), Toast.LENGTH_SHORT).show();
            return;
        }

        BottomDialogView.Builder builder = new BottomDialogView.Builder(TransferAccountsThreeActivity.this);

        View dialogView = (View) LayoutInflater.from(TransferAccountsThreeActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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
                    Toast.makeText(TransferAccountsThreeActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();


                        sendTo();

                    } else {
                        Toast.makeText(TransferAccountsThreeActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        new CommonInputPwdDialogTwoView().showTips(TransferAccountsActivity.this, new CommonInputPwdDialogTwoView.OnCloseListener() {
//            @Override
//            public void closeClick() {
//
//            }
//
//            @Override
//            public void sureClick(String contentVal) {
//                if (null == contentVal || contentVal.length() == 0) {
//                    Toast.makeText(TransferAccountsActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//                    if (contentVal.equals(walletModel.getPassword())) {
//                        sendTo();
//                    } else {
//                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });


//        final PayPassDialog dialog = new PayPassDialog(this);
//        dialog.getPayViewPass()
//                .setPayClickListener(new PayPassView.OnPayClickListener() {
//                    @Override
//                    public void onPassFinish(String passContent) {
//                        //6位输入完成回调
//                        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//                        if (passContent.equals(walletModel.getPassword())) {
//                            sendTo();
//                        } else {
//                            Toast.makeText(TransferAccountsActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPayClose() {
//                        try {
//                            dialog.dismiss();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                        //关闭弹框
//                    }
//                });
    }

    //    String priVal = "L4YrK1xKLxtG9PRqV4bKeErzbFArU7XdZXoWfpeuxYJQRsFPKviY";
//    String addresVal = "13ogNWNrmFhhBsTaq4do45dXo1UbtF3Sm1";
//    String walletType = "BTC";
//    int coinNum = 1;


    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 查询余额
     */
    private void checkMoney(String num, String address) {

        createTrade(num, address, moneyTypeVal);


//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//        String privateKey = walletModel.getPrivateKey();
//        String addressVals = walletModel.getAddress();
//        String msg = "" + System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
//        try {
//            showDialog(getString(R.string.load_data));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Log.e("paydialog", ".....find...left...money....");
//
//        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
//                .tag(TransferAccountsActivity.this)
//                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
////                            Log.e("backVal", "backVal=" + backVal);
//                            if (backVal != null) {
//
//                                try {
//
//                                    Gson gson = new Gson();
//                                    LeftMoneyResponse response = gson.fromJson(backVal, LeftMoneyResponse.class);
////                                Log.e("backVal", "backVal=" + response.toString());
//
//                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().size() > 0) {
//
//                                        int find = 0;
//
//                                        for (int i = 0; i < response.getData().size(); i++) {
//                                            LeftMoneyResponse.LeftMoneyModel leftMoneyModel = response.getData().get(i);
//                                            if (moneyTypeVal.equals(leftMoneyModel.getName())) {
//                                                find = 1;
//                                                if (leftMoneyModel.getValue_qty().equals("0")) {
//                                                    try {
//                                                        dismissDialog();
//                                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_no_money), Toast.LENGTH_SHORT).show();
//                                                    } catch (Exception e) {
//                                                        e.printStackTrace();
//                                                        btnNext.setEnabled(true);
//                                                    }
//
//                                                    return;
//                                                } else {
//                                                    //创建交易
//                                                    createTrade(num, address, moneyTypeVal);
//                                                }
//                                                break;
//                                            }
//                                        }
//
//                                        if (find == 0) {
//                                            try {
//                                                dismissDialog();
//                                                Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_no_money), Toast.LENGTH_SHORT).show();
//                                                btnNext.setEnabled(true);
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//
//                                        }
//
//                                    } else {
//                                        try {
//                                            dismissDialog();
//                                            new FailDialogView().showTips(TransferAccountsActivity.this, getString(R.string.dialog_fail_sign));
//                                            btnNext.setEnabled(true);
//                                        } catch (Exception e) {
//
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    btnNext.setEnabled(true);
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        try {
//                            dismissDialog();
//                            btnNext.setEnabled(true);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                    }
//                });
    }


    private void createTrade(String num, String address, String walletType) {

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());


        CreateTradeRequest tradeRequest = new CreateTradeRequest();
        tradeRequest.setAmount(num);
        tradeRequest.setSymbol(walletType);
        tradeRequest.setTo_address(address);
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

//        try {
//            showDialog(getString(R.string.load_data));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Log.e("paydialog", ".....创建交易....");

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.CREATE_TRADE_URL)
                .tag(TransferAccountsThreeActivity.this)
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
                                                new FailDialogView().showTips(TransferAccountsThreeActivity.this, response.getMsg());
                                                btnNext.setEnabled(true);
//                                                dismissDialog();

                                                if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
                                                    mDialog.dismiss();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
                                            btnNext.setEnabled(true);
//                                            dismissDialog();
                                            if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
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
                                        if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
                                            mDialog.dismiss();
                                        }
                                        new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
                                        btnNext.setEnabled(true);
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
                            if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
                                mDialog.dismiss();
                            }
                            new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
                            btnNext.setEnabled(true);
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
                        new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
                        btnNext.setEnabled(true);
//                        dismissDialog();
                        if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
                            mDialog.dismiss();
                        }
                    } catch (Exception es) {
                        es.printStackTrace();
                    }
                }


            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }


    //发送交易
    private void sendTrade(String signVal) {

        //本地当前选中的钱包
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String msg = "" + System.currentTimeMillis();
        String headerSign = BitcoinjKit.signMessageBy58(msg, walletModel.getPrivateKey());

//        String msg = "" + System.currentTimeMillis();
//        String signValss = BitcoinjKit.signMessageBy58(msg, priVal);


        String tempAddress = editSkrAddress.getText().toString();
        String tempNumVal = editZzMoney.getText().toString();

        beizhuContent = editRemark.getText().toString();

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(tempNumVal);
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol(moneyTypeVal);
        sendTradeRequest.setTo_address(tempAddress);
        sendTradeRequest.setType(0);
        sendTradeRequest.setDemo(beizhuContent);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_TRADE_URL)
                .tag(TransferAccountsThreeActivity.this)
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
                                            new SuccessDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_success_sign));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                        EventBus.getDefault().post(new TradeSuccessEvent());

//                                        EventBus.getDefault().post(new PushEvent());

                                        Intent tradeIntent = new Intent(TransferAccountsThreeActivity.this, MessageCenterActivity.class);
                                        startActivity(tradeIntent);

                                        Toast.makeText(TransferAccountsThreeActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                } catch (Exception e) {
                                    try {
                                        new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
//                                        EventBus.getDefault().post(new PushEvent());
//                                        finish();
                                    } catch (Exception es) {
                                        es.printStackTrace();
                                    }
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
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
                            new FailDialogView().showTips(TransferAccountsThreeActivity.this, getString(R.string.dialog_fail_sign));
//                            dismissDialog();
                            if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
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
                            if (!((Activity) TransferAccountsThreeActivity.this).isFinishing() && !((Activity) TransferAccountsThreeActivity.this).isDestroyed()) {
                                mDialog.dismiss();
                            }
                            btnNext.setEnabled(true);
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
//                Intent tradeIntent = new Intent(TransferAccountsThreeActivity.this, MessageCenterActivity.class);
//                startActivity(tradeIntent);
//                finish();
//            }
//        }, 1100);


    }


    private boolean verifyInfo(String address, String num) {
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showToast(getString(R.string.trade_input_wallet_address));
            return false;
        } else if (TextUtils.isEmpty(num)) {
            ToastUtils.showToast(getString(R.string.trade_input_wallet_num));
            return false;
        }
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(AddressEvent addressEvent) {
        editSkrAddress.setText(addressEvent.getAddress());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
