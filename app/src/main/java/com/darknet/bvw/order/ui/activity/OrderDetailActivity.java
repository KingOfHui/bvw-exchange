package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderDetailBinding;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.order.bean.OrderStatusEnum;
import com.darknet.bvw.order.ui.adapter.OrderGoodsAdapter;
import com.darknet.bvw.order.vm.OrderListViewModel;
import com.darknet.bvw.util.StatusBarUtil;

import java.util.List;

/**
 * @ClassName OrderDetailActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/14 0014 10:55
 */
public class OrderDetailActivity extends BaseBindingActivity<ActivityOrderDetailBinding> {

    private OrderListViewModel mViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        mBinding.layoutTitle.layBack.setOnClickListener(v -> finish());
        mBinding.layoutTitle.title.setText(R.string.order_detail);
    }

    @Override
    public void initDatas() {
        OrderResp order = (OrderResp) getIntent().getSerializableExtra("order");
        mViewModel = getViewModel(OrderListViewModel.class);
        mViewModel.mOrderDetailLiveData.observe(this, this::refreshUI);
        refreshUI(order);
    }

    private void refreshUI(OrderResp order) {
        OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatus(order.getOrderState());
        mBinding.tvOrderStatus.setText(OrderStatusEnum.getOrderStatusText(this,order.getOrderState()));
        mBinding.ivOrderStatus.setImageResource(orderStatus.getDrawable());
        mBinding.tvOrderSubmitTime.setText(String.format(getString(R.string.order_submit_time), order.getCreate_time()));
        mBinding.tvContract.setText(String.format("%s   %s", order.getReceiver_name(), order.getReceiver_phone()));
        mBinding.tvOrderAddress.setText(String.format("%s%s%s%s\n%s", order.getReceiver_nation(), order.getReceiver_province(), order.getReceiver_city(), order.getReceiver_county(), order.getReceiver_detail_address()));
        mBinding.layoutOrderList.tvOrderStatus.setText(OrderStatusEnum.getOrderStatusText(this,order.getOrderState()));
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
        mBinding.tvToPay.setVisibility(order.getOrderState() == 0 ? View.VISIBLE : View.GONE);
        mBinding.tvToPay.setOnClickListener(view -> {
                PayOrderActivity.start(this,order.getId());
        });
        switch (order.getOrderState()) {
            case 0:
                mBinding.layoutOrderList.tvOperationLeft.setVisibility(View.VISIBLE);
                mBinding.layoutOrderList.tvOperationLeft.setText(getString(R.string.order_cancel));
                mBinding.layoutOrderList.tvOperationRight.setText(getString(R.string.to_pay));
                mBinding.layoutOrderList.tvOperationRight.setVisibility(View.GONE);
                mBinding.layoutOrderList.tvOperationLeft.setOnClickListener(v -> {
                    mViewModel.cancelOrder(order.getId());
                });
                mBinding.tvOrderTime.setText(String.format(getString(R.string.order_close_time), "23"));
                break;
            case 1:
                mBinding.layoutOrderList.tvOperationLeft.setVisibility(View.INVISIBLE);
                mBinding.layoutOrderList.tvOperationRight.setText(getString(R.string.remind_ship));
                mBinding.layoutOrderList.tvOperationRight.setOnClickListener(v -> mViewModel.tipDelivery(order.getId()));
                mBinding.tvOrderTime.setText("您的包裹已准备完毕");
                break;
            case 2:
                mBinding.layoutOrderList.tvOperationLeft.setVisibility(View.VISIBLE);
                mBinding.layoutOrderList.tvOperationLeft.setText(getString(R.string.view_logistics));
                mBinding.layoutOrderList.tvOperationRight.setText(getString(R.string.confirm_receipt));
                mBinding.layoutOrderList.tvOperationLeft.setOnClickListener(v -> LogisticsTrackingActivity.start(this));
                mBinding.layoutOrderList.tvOperationRight.setOnClickListener(v -> mViewModel.confirmReceive(order.getId()));
                mBinding.tvOrderTime.setText("您的包裹正在配送中");
                break;
            case 3:
                mBinding.tvOrderTime.setText("您的包裹已签收");
                mBinding.layoutOrderList.clOperation.setVisibility(View.GONE);
                break;
            case 4:
                mBinding.tvOrderTime.setText("您的订单被取消");
                mBinding.layoutOrderList.clOperation.setVisibility(View.GONE);
                break;
            case 5:
            default:
                mBinding.layoutOrderList.clOperation.setVisibility(View.GONE);
                break;
        }
    }

    public static void start(Context context, OrderResp order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }
}
