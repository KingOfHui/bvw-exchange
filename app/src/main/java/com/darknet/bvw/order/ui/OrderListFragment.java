package com.darknet.bvw.order.ui;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentOrderListBindingImpl;
import com.darknet.bvw.order.vm.OrderListViewModel;

/**
 * @ClassName OrderListFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 17:07
 */
public class OrderListFragment extends BaseBindingFragment<OrderListViewModel, FragmentOrderListBindingImpl> {

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
    protected void setDataToBinding() {
        super.setDataToBinding();
        OrderListAdapter orderListAdapter = new OrderListAdapter();
        mDataBinding.setAdapter(orderListAdapter);
    }

    @Override
    protected void initData() {
        mViewModel.refresh();

    }

    public static class OrderListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public OrderListAdapter() {
            super(R.layout.item_order_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv, item);
        }
    }

}
