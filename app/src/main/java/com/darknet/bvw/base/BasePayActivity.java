package com.darknet.bvw.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.view.InputPasswordDialog;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.databinding.ViewDataBinding;

public abstract class BasePayActivity<BINDING extends ViewDataBinding>  extends BaseBindingActivity<BINDING> {
    private BridgeWebView mWebview;
    protected PayViewModel mPayViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebview = new BridgeWebView(this);
        mWebview.loadUrl(ConfigNetWork.WEB_URL);
        mPayViewModel = getViewModel(PayViewModel.class);

    }


    protected void showInputPwdDialog(String address) {
        InputPasswordDialog dialog = new InputPasswordDialog(this);
        dialog.setListener(new InputPasswordDialog.OnClickListener() {
            @Override
            public void clickSure(String contentVal) {
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(BasePayActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                } else {
                    ETHWalletModel walletModel = getWalletModel();
                    if (walletModel != null && contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        dialog.dismiss();
                        createTrade(address);

                    } else {
                        Toast.makeText(BasePayActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void clickCancel() {
                hintKeyBoard();
            }
        });
        dialog.show();
    }

    protected void callH5(SendTx sendTx) {
        callH5(sendTx, after -> {
            sendTrade(after);
            return null;
        });
    }

    protected void callH5(SendTx sendTx, Function<String, Void> callback) {
        List<Unspent> unspent = sendTx.getUnspent();
        TransactionRAW decodeRawTx = sendTx.getDecodeRawTx();
        String privateKey = getWalletModel().getPrivateKey();
        SignModelTwo signModel = new SignModelTwo();
        signModel.setPrivKey(privateKey);
        signModel.setInputs(unspent);
        signModel.setDecodedTransaction(decodeRawTx);
        mWebview.callHandler("signTransaction", new Gson().toJson(signModel), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("okhttp dhdhdh", "onCallBack: ---------------------");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String afterSignVal = jsonObject.getString("data");
                    callback.apply(afterSignVal);
                } catch (Exception e) {
                    ToastUtils.showToast(getString(R.string.send_trade_state_fail));
                }
            }
        });
    }

    protected abstract void createTrade(String address);
    protected abstract void sendTrade(String afterSignVal);

    private ETHWalletModel getWalletModel() {
        return WalletDaoUtils.getCurrent();
    }
}
