package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.databinding.ActivityOrderListBinding;
import com.darknet.bvw.order.ui.fragment.OrderListFragment;
import com.darknet.bvw.order.vm.OrderListViewModel;

/**
 * @ClassName OrderListActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 16:48
 */
public class OrderListActivity extends BaseBindingActivity<ActivityOrderListBinding> {

    //类型 -1全部 0->待付款；1->待发货 2->已发货|待收货 3->已完成 4->已取消 8待评价,可用值:-1,0,1,2,3,8
    private final static int STATE_ALL = -1;
    private final static int STATE_TO_BE_PAID = 0;
    private final static int STATE_TO_BE_DELIVERY = 1;
    private final static int STATE_TO_BE_TAKEN = 2;
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {
        OrderListViewModel viewModel = getViewModel(OrderListViewModel.class);
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.order_all));
        BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        adapter.add(OrderListFragment.newInstance(STATE_ALL), getString(R.string.hz_input_all_sign));
        adapter.add(OrderListFragment.newInstance(STATE_TO_BE_PAID), getString(R.string.order_to_be_paid));
        adapter.add(OrderListFragment.newInstance(STATE_TO_BE_DELIVERY), getString(R.string.order_to_be_delivered));
        adapter.add(OrderListFragment.newInstance(STATE_TO_BE_TAKEN), getString(R.string.order_to_be_taken));
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        int page = getIntent().getIntExtra("page", 0);
        mBinding.viewPager.setCurrentItem(page, false);
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context, int page) {
        Intent intent = new Intent(context, OrderListActivity.class);
        intent.putExtra("page", page);
        context.startActivity(intent);

    }
}
