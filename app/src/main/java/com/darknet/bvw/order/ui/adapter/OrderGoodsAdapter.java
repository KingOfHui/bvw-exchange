package com.darknet.bvw.order.ui.adapter;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;

public class OrderGoodsAdapter extends BaseDataBindingAdapter<String, ViewDataBinding> {
    public OrderGoodsAdapter() {
        super(R.layout.item_order_goods);
    }

    @Override
    protected void convert(ViewDataBinding viewDataBinding, String item) {

    }
}
