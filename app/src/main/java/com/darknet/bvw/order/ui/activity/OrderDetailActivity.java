package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderDetailBinding;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.order.bean.OrderStatusEnum;
import com.darknet.bvw.order.ui.adapter.OrderGoodsAdapter;
import com.darknet.bvw.order.ui.adapter.OrderListAdapter;
import com.darknet.bvw.util.StatusBarUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.List;

/**
 * @ClassName OrderDetailActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/14 0014 10:55
 */
public class OrderDetailActivity extends BaseBindingActivity<ActivityOrderDetailBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        mBinding.layoutTitle.layBack.setOnClickListener(v -> finish());
        mBinding.layoutTitle.title.setText(R.string.order_detail);
        mBinding.tvToPay.setOnClickListener((view) -> {
            LogisticsTrackingActivity.start(this);
        });
    }

    @Override
    public void initDatas() {
        OrderResp order = (OrderResp) getIntent().getSerializableExtra("order");
        OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatus(order.getState());
        mBinding.tvOrderStatus.setText(orderStatus.getText());
        mBinding.tvOrderTime.setText(order.getFinish_time());
        mBinding.ivOrderStatus.setImageResource(orderStatus.getDrawable());
        mBinding.tvOrderSubmitTime.setText(String.format(getString(R.string.order_submit_time), order.getCreate_time()));
        mBinding.tvContract.setText(String.format("%s   %s", order.getReceiver_name(), order.getReceiver_phone()));
        mBinding.tvOrderAddress.setText(String.format("%s%s%s%s\n%s", order.getReceiver_nation(), order.getReceiver_province(), order.getReceiver_city(), order.getReceiver_county(), order.getReceiver_detail_address()));
        mBinding.layoutOrderList.tvOrderStatus.setText(orderStatus.getText());
        OrderGoodsAdapter adapter = new OrderGoodsAdapter();
        List<OrderResp.OrderItemListBean> order_item_list = order.getOrder_item_list();
        adapter.setNewData(order_item_list);
        mBinding.layoutOrderList.rvGoodsList.setAdapter(adapter);
        mBinding.layoutOrderList.group.setVisibility(View.GONE);
        mBinding.layoutOrderList.tvCount.setText(String.format(getString(R.string.order_count_number), order_item_list.size() + ""));
        mBinding.layoutOrderList.tvOrderNum.setText(String.format(getString(R.string.order_number), order.getOrder_no()));
        mBinding.layoutOrderList.tvTotalPrice.setText("USDT " + order.getPay_amount());

        mBinding.hlvFreight.setRightText("USDT " + order.getFreight_amount());
        mBinding.hlvDiscounts.setRightText("- USDT " + order.getCoupon_amount());
//        mBinding.hlvMoneyOff.setRightText("- USDT "+order.getof);
        mBinding.hlvTotalPrice.setRightText("USDT " + order.getTotal_amount());
        mBinding.tvToPay.setOnClickListener(view -> {
                PayOrderActivity.start(this,order.getId());
        });
    }

    public static void start(Context context, OrderResp order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }
}
