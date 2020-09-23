package com.darknet.bvw.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.databinding.ActivityMineralInfoBinding;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.event.BidSuccessEvent;
import com.darknet.bvw.model.event.TradeSuccessEvent;
import com.darknet.bvw.model.request.CreateTradeRequest;
import com.darknet.bvw.model.request.SendTradeRequest;
import com.darknet.bvw.model.response.CreateTradeResponse.JsonRootBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.model.response.MineralStatusResponse;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BottomDialogView;
import com.darknet.bvw.view.FailZZDialogView;
import com.darknet.bvw.view.SuccessDialogView;
import com.darknet.bvw.viewmodel.MineralViewModel;
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName MineralInfoActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 16:02
 */
public class MineralInfoActivity extends BaseBindingActivity<ActivityMineralInfoBinding> {

    MineralViewModel mViewModel;
    Button btnNext;
    BridgeWebView webView;
    private MineralListResponse.ItemsBean mItemInfo;

    public static void startSelfForResult(Activity context, MineralListResponse.ItemsBean itemsBean, MineralStatusResponse value, int requestCode) {
        Intent intent = new Intent(context, MineralInfoActivity.class);
        intent.putExtra("itemInfo", itemsBean);
        intent.putExtra("statusInfo", value);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mineral_info;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MineralViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.layoutTitle.layBack.setOnClickListener(v -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.mineral));
        mBinding.layoutTitle.titleRight.setText(getString(R.string.mineral_pool));
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
        mBinding.setLifecycleOwner(this);
        btnNext = mBinding.btnToPledge;
//        btnNext.setTextScaleX(a.getFloat(attr, 1.0f));
        webView = mBinding.webView;
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        btnNext.setOnClickListener(view -> {
            mViewModel.getPublicAddress();
        });
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        mItemInfo = (MineralListResponse.ItemsBean) intent.getSerializableExtra("itemInfo");
        MineralStatusResponse statusInfo = (MineralStatusResponse) intent.getSerializableExtra("statusInfo");
        mBinding.setInfo(mItemInfo);
        mBinding.layoutTitle.titleRight.setOnClickListener(v->AddMineralActivity.startSelf(this, mItemInfo.getMinerInfo()));
        mViewModel.getMineralStatusResponseLiveData().setValue(statusInfo);
        mViewModel.address.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                payDialog(s);
            }
        });
        mViewModel.isOpenBid.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value!=null && value) {
                    mViewModel.toPledge();
                } else {
                    Intent buyIntent = new Intent(MineralInfoActivity.this, BidBuyActivity.class);
                    startActivity(buyIntent);
                }
            }
        });
        mBinding.tvIncomeRecord.setOnClickListener(view -> IncomeRecordActivity.startSelf(this, statusInfo));
        mBinding.tvMineralStatus.setText(mItemInfo.getState()==2?getString(R.string.gu_zhang_zhong):mItemInfo.getState() ==1?getString(R.string.wa_kuang_zhong):getString(R.string.wei_kai_ji));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveClose(BidSuccessEvent nameEvent) {
        mViewModel.isOpenBid.setValue(true);
    }

    private void payDialog(String address) {

        BottomDialogView.Builder builder = new BottomDialogView.Builder(MineralInfoActivity.this);

        View dialogView = (View) LayoutInflater.from(MineralInfoActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

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
                    Toast.makeText(MineralInfoActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                    if (contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        bottomDialog.dismiss();
                        createTrade(String.valueOf(mItemInfo.getPay_amount()),address,"BTW");

                    } else {
                        Toast.makeText(MineralInfoActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

        showDialog("");


        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.CTEATE_RAW_TX)
                .tag(MineralInfoActivity.this)
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
                                        if (response.getData() != null && response.getCode() == 0) {
                                            getSignValThree(response.getData(), walletModel.getPrivateKey());
                                        } else {

                                            try {
                                                new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
                                                btnNext.setEnabled(true);
//                                                dismissDialog();

                                                if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                                                    dismissDialog();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        Toast.makeText(TransferAccountsActivity.this, getString(R.string.trade_wrong_data), Toast.LENGTH_SHORT).show();
                                        try {
                                            new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
                                            btnNext.setEnabled(true);
//                                            dismissDialog();
                                            if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                                                dismissDialog();
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
                                        if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                                            dismissDialog();
                                        }
                                        new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
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
                            if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                                dismissDialog();
                            }
                            new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
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
                        new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
                        btnNext.setEnabled(true);
//                        dismissDialog();
                        if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                            dismissDialog();
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

//        String msg = "" + System.currentTimeMillis();
//        String signValss = BitcoinjKit.signMessageBy58(msg, priVal);

        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(String.valueOf(mItemInfo.getPay_amount()));
        sendTradeRequest.setRaw(signVal);
        sendTradeRequest.setSymbol("BTW");
        sendTradeRequest.setTo_address(mViewModel.address.getValue());
        sendTradeRequest.setType(13);
        sendTradeRequest.setDemo("质押矿机");
        sendTradeRequest.setMiner_id(mItemInfo.getId());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(sendTradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.SEND_RAW_TX)
                .tag(MineralInfoActivity.this)
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
                                    Toast.makeText(MineralInfoActivity.this, getString(R.string.trade_success), Toast.LENGTH_SHORT).show();
                                        //刷新状态
                                        try {
                                            new SuccessDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_success_sign));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        setResult(RESULT_OK);
                                        finish();

                                    } else {

                                        new FailZZDialogView().showTips(MineralInfoActivity.this, response.getMsg());
                                    }
                                } catch (Exception e) {
                                    try {
                                        new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
                                    } catch (Exception es) {
                                        es.printStackTrace();
                                    }
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        try {
                            new FailZZDialogView().showTips(MineralInfoActivity.this, getString(R.string.dialog_fail_sign));
                            if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                                dismissDialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        try {
                            if (!((Activity) MineralInfoActivity.this).isFinishing() && !((Activity) MineralInfoActivity.this).isDestroyed()) {
                                dismissDialog();
                            }
                            btnNext.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

}
