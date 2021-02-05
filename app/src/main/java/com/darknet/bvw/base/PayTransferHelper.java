package com.darknet.bvw.base;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.util.ToastUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PayTransferHelper implements LifecycleObserver {
    private BridgeWebView mWebview;
    private Context mContext;
    public PayTransferHelper(Context context) {
        mContext = context;
        mWebview = new BridgeWebView(context);
        mWebview.loadUrl(ConfigNetWork.WEB_URL);
    }

    public static PayTransferHelper attach(Context context) {
        return new PayTransferHelper(context);
    }

    public void callH5CanNull(SendTx sendTx, Function<String, Void> callback) {
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
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String afterSignVal = jsonObject.getString("data");
                    callback.apply(afterSignVal);
                } catch (Exception e) {
                    ToastUtils.showToast(mContext.getString(R.string.send_trade_state_fail));
                    callback.apply(null);
                }
            }
        });
    }

    private ETHWalletModel getWalletModel() {
        return WalletDaoUtils.getCurrent();
    }

}
