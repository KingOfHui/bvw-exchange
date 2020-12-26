package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BasePayActivity;
import com.darknet.bvw.databinding.ActivityOrderConfirmBinding;
import com.darknet.bvw.databinding.ActivityOrderPayBinding;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.ui.adapter.ConfirmGoodsAdapter;
import com.darknet.bvw.order.ui.adapter.OrderGoodsAdapter;
import com.darknet.bvw.order.ui.dialog.PayOrderDialog;
import com.darknet.bvw.order.vm.ConfirmOrderViewModel;
import com.darknet.bvw.order.vm.MyAddressViewModel;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.view.InputPasswordDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName PayOrderActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/15 0015 14:47
 */
public class PayOrderActivity extends BasePayActivity<ActivityOrderPayBinding> {

    private PayViewModel mViewModel;
    private int mOrderId;
    private OrderGoodsAdapter mAdapter;
    private OrderResp mOrderDetail;
    private PayOrderDialog mPayOrderDialog;

    public static void start(Context context, int orderId) {
        Intent intent = new Intent(context, PayOrderActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_pay;
    }

    @Override
    public void initView() {
        Intent data = getIntent();
        mOrderId = data.getIntExtra("orderId", 0);
        mViewModel = getViewModel(PayViewModel.class);
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.order_settle));
        mBinding.tvSubmitOrder.setText(getString(R.string.pay_now));
        mBinding.tvSubmitOrder.setOnClickListener(view -> {
            if (mOrderDetail!=null) {
                mPayOrderDialog = new PayOrderDialog(this, mOrderDetail.getPay_amount())
                        .setOnPayClickListener(() -> {
                            showInputPwdDialog(mOrderDetail.getMall_pay_address());
                        });
                mPayOrderDialog.show();
            }
        });

        mAdapter = new OrderGoodsAdapter();
        mBinding.setAdapter(mAdapter);
    }

    @Override
    public void initDatas() {
        mViewModel.getOrderDetail(mOrderId);
        mViewModel.mOrderDetailLiveData.observe(this, new Observer<OrderResp>() {
            @Override
            public void onChanged(OrderResp orderResp) {
                mOrderDetail = orderResp;
                List<OrderResp.OrderItemListBean> order_item_list = orderResp.getOrder_item_list();
                mAdapter.setNewData(order_item_list);
                mBinding.tvContact.setText(String.format("%s   %s", orderResp.getReceiver_name(), orderResp.getReceiver_phone()));
                mBinding.tvAddress.setText(String.format("%s%s%s%s\n%s",
                        orderResp.getReceiver_nation(), orderResp.getReceiver_province(),
                        orderResp.getReceiver_city(), orderResp.getReceiver_county(), orderResp.getReceiver_detail_address()));
                mBinding.hlvDiscounts.setRightText(orderResp.getCoupon_amount());
                mBinding.hlvTotalPrice.setRightText(orderResp.getTotal_amount());
                mBinding.hlvFreight.setRightText(orderResp.getFreight_amount());
                mBinding.tvDiscounts.setText(orderResp.getCoupon_amount());
                mBinding.etRemark.setText(orderResp.getNote());
            }
        });
        mViewModel.tradeSuccessLive.observe(this, aBoolean -> {
            if (aBoolean) {
                if (mPayOrderDialog != null) {
                    mPayOrderDialog.dismiss();
                }

                OrderListActivity.start(this, 0);
                finish();
            }
        });
        mViewModel.mSendTxMutableLiveData.observe(this, new Observer<SendTx>() {
            @Override
            public void onChanged(SendTx sendTx) {
                callH5(sendTx);
            }
        });
    }

    @Override
    protected void createTrade(String address) {
        mViewModel.createTrade(String.valueOf(mOrderDetail.getPay_amount()), address, "USDT");

    }

    @Override
    protected void sendTrade(String afterSignVal) {
        mViewModel.sendOrderTrade(afterSignVal,mOrderDetail.getPay_amount(),mOrderDetail.getMall_pay_address(),mOrderDetail.getId());
    }

    private ETHWalletModel getWalletModel() {
        return WalletDaoUtils.getCurrent();
    }

}
