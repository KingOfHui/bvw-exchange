package com.darknet.bvw.order.ui.adapter;

import android.content.Context;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ItemOrderListBinding;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.order.bean.OrderStatusEnum;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName OrderListAdapter
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 14:46
 */
public class OrderListAdapter extends BaseDataBindingAdapter<OrderResp, ItemOrderListBinding> {
    public OrderListAdapter() {
        super(R.layout.item_order_list);
    }

    @Override
    protected void convert(ItemOrderListBinding binding, OrderResp item) {
        Context context = binding.layoutOrder.tvOrderNum.getContext();
        List<OrderResp.OrderItemListBean> order_item_list = item.getOrder_item_list();
        if (CollectionUtil.isNotEmpty(order_item_list)) {
            OrderGoodsAdapter adapter = new OrderGoodsAdapter();
            adapter.setNewData(order_item_list);
            binding.layoutOrder.rvGoodsList.setAdapter(adapter);
            binding.layoutOrder.tvCount.setText(String.format(context.getString(R.string.order_count_number), order_item_list.size() + ""));
        }
        binding.layoutOrder.tvOrderNum.setText(String.format(context.getString(R.string.order_number), item.getOrder_no()));
        binding.layoutOrder.tvTotalPrice.setText("USDT " + item.getPay_amount());
        binding.layoutOrder.tvOrderStatus.setText(OrderStatusEnum.getOrderStatus(item.getState()).getText());
        //订单状态：0->待付款；1->待发货 2->已发货 3->已完成 4->已关闭 5->无效订单
        int state = item.getState();
        switch (state) {
            case 0:
                binding.layoutOrder.clOperation.setVisibility(View.VISIBLE);
                binding.layoutOrder.tvOperationLeft.setVisibility(View.VISIBLE);
                binding.layoutOrder.tvOperationLeft.setText(context.getString(R.string.order_cancel));
                binding.layoutOrder.tvOperationRight.setText(context.getString(R.string.to_pay));
                break;
            case 1:
                binding.layoutOrder.clOperation.setVisibility(View.VISIBLE);
                binding.layoutOrder.tvOperationLeft.setVisibility(View.INVISIBLE);
                binding.layoutOrder.tvOperationRight.setText(context.getString(R.string.remind_ship));
                binding.layoutOrder.tvOperationLeft.setOnClickListener(v -> {

                });
                binding.layoutOrder.tvOperationRight.setOnClickListener(v -> {

                });
                break;
            case 2:
                binding.layoutOrder.clOperation.setVisibility(View.VISIBLE);
                binding.layoutOrder.tvOperationLeft.setVisibility(View.VISIBLE);
                binding.layoutOrder.tvOperationLeft.setText(context.getString(R.string.view_logistics));
                binding.layoutOrder.tvOperationRight.setText(context.getString(R.string.confirm_receipt));
                binding.layoutOrder.tvOperationLeft.setOnClickListener(v -> {

                });
                binding.layoutOrder.tvOperationRight.setOnClickListener(v -> {

                });
                break;
            case 3:
                binding.layoutOrder.clOperation.setVisibility(View.GONE);
                binding.layoutOrder.tvOperationLeft.setOnClickListener(v -> {

                });
                binding.layoutOrder.tvOperationRight.setOnClickListener(v -> {

                });
                break;
            case 4:
                binding.layoutOrder.tvOperationLeft.setText("");
                binding.layoutOrder.tvOperationRight.setText("");
                binding.layoutOrder.tvOperationLeft.setOnClickListener(v -> {

                });
                binding.layoutOrder.tvOperationRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case 5:
                binding.layoutOrder.tvOperationLeft.setText("");
                binding.layoutOrder.tvOperationRight.setText("");
                binding.layoutOrder.tvOperationLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                binding.layoutOrder.tvOperationRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
        }
    }
}
