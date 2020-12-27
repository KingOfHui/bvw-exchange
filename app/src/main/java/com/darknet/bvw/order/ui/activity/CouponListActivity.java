package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.databinding.ActivityCouponListBinding;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.SignModelTwo;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.ui.adapter.CouponAdapter;
import com.darknet.bvw.order.ui.dialog.BuyCouponDialog;
import com.darknet.bvw.order.vm.CouponViewModel;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.util.StatusBarUtil;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.view.InputPasswordDialog;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CouponListActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 10:29
 */
public class CouponListActivity extends BaseBindingActivity<ActivityCouponListBinding> {

    private CouponViewModel mViewModel;
    private PayViewModel mPayViewModel;
    private CouponBean mCouponBean;
    private BridgeWebView mWebview;
    private BuyCouponDialog mCouponDialog;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CouponListActivity.class));
    }
    public static void start(Context context, ArrayList<String> selectList) {
        Intent intent = new Intent(context, CouponListActivity.class);
        intent.putStringArrayListExtra("selectList", selectList);
        context.startActivity(intent);    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_list;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mWebview = new BridgeWebView(this);
        mWebview.loadUrl(ConfigNetWork.WEB_URL);

        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        mViewModel = getViewModel(CouponViewModel.class);
        mPayViewModel = getViewModel(PayViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.cash_coupon));
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setText(getString(R.string.my_cash_coupon));
        mBinding.layoutTitle.titleRight.setTextColor(ContextCompat.getColor(this, R.color._01FCDA));
        CouponAdapter adapter = new CouponAdapter();
        mBinding.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            mCouponBean = adapter.getItem(position);
            mCouponDialog = new BuyCouponDialog(this, mCouponBean).setListener(() -> {
                mPayViewModel.getPayAddress("SHOP_PAY_COUPON");
            });
            mCouponDialog.show();
        });
        mBinding.layoutTitle.titleRight.setOnClickListener(view ->
                MyCouponListActivity.start(this, getIntent().getStringArrayListExtra("selectList")));
    }

    @Override
    public void initDatas() {
        mViewModel.refresh();
        mPayViewModel.tradeSuccessLive.observe(this, a -> {
            if (mCouponDialog != null && mCouponDialog.isShowing()) {
                mCouponDialog.dismiss();
            }
            ToastUtils.showToast(getString(R.string.buy_success));
        });
        mPayViewModel.couponAddress.observe(this, this::showInputPwdDialog);
        mPayViewModel.mSendTxMutableLiveData.observe(this, this::callH5);
    }

    private void callH5(SendTx sendTx) {
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
                    mPayViewModel.sendDiscountsTrade(afterSignVal, String.valueOf(mCouponBean.getPrice()), mCouponBean.getId());
                } catch (Exception e) {
                    ToastUtils.showToast(getString(R.string.send_trade_state_fail));
                }
            }
        });
    }

    private void showInputPwdDialog(String address) {
        InputPasswordDialog dialog = new InputPasswordDialog(this);
        dialog.setListener(new InputPasswordDialog.OnClickListener() {
            @Override
            public void clickSure(String contentVal) {
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(CouponListActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                } else {
                    ETHWalletModel walletModel = getWalletModel();
                    if (walletModel != null && contentVal.equals(walletModel.getPassword())) {
                        hintKeyBoard();
                        dialog.dismiss();
                        mPayViewModel.createTrade(String.valueOf(mCouponBean.getPrice()), address, "BIW");

                    } else {
                        Toast.makeText(CouponListActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
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

    private ETHWalletModel getWalletModel() {
        return WalletDaoUtils.getCurrent();
    }



    @Subscribe()
    public void onEvent(MyCouponBean couponBean) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
