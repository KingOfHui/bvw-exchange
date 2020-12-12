package com.darknet.bvw.order.ui;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.order.vm.OrderListViewModel;

/**
 * @ClassName OrderListFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 17:07
 */
public class OrderListFragment extends BaseBindingFragment<OrderListViewModel, ViewDataBinding> {

    public static OrderListFragment newInstance() {
        return new OrderListFragment();
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
    protected void initData() {

    }
}
