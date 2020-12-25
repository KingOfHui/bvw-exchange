package com.darknet.bvw.order.ui.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentOrderListBinding;
import com.darknet.bvw.databinding.ItemOrderListBinding;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.order.bean.OrderStatusEnum;
import com.darknet.bvw.order.ui.activity.OrderDetailActivity;
import com.darknet.bvw.order.ui.adapter.OrderGoodsAdapter;
import com.darknet.bvw.order.ui.adapter.OrderListAdapter;
import com.darknet.bvw.order.vm.OrderListViewModel;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.util.view.ViewUtil;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName OrderListFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 17:07
 */
public class OrderListFragment extends BaseBindingFragment<OrderListViewModel, FragmentOrderListBinding> {

    public static OrderListFragment newInstance(int state) {
        Bundle bundle = new Bundle();
        bundle.putInt("trade_state", state);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected OrderListViewModel initViewModel() {
        return getFragmentViewModel(OrderListViewModel.class);
    }

    @Override
    protected void initView() {
        OrderListAdapter orderListAdapter = new OrderListAdapter();
        orderListAdapter.setOnItemClickListener((adapter, view, position) -> OrderDetailActivity.start(requireActivity(),orderListAdapter.getItem(position)));
        mDataBinding.setAdapter(orderListAdapter);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int trade_state = bundle.getInt("trade_state", 0);
            mViewModel.getTradeStateLive().setValue(trade_state);
        }
        mViewModel.refresh();

    }

}
