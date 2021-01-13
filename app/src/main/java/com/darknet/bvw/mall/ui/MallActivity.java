package com.darknet.bvw.mall.ui;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentMallBindingImpl;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.ui.search.SearchActivity;
import com.darknet.bvw.mall.vm.MallViewModel;
import com.darknet.bvw.order.bean.event.CartEvent;
import com.darknet.bvw.order.ui.activity.CartActivity;
import com.darknet.bvw.order.vm.CartViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName MallFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:43
 */
public class MallActivity extends BaseBindingActivity<FragmentMallBindingImpl> {

    private MallViewModel mViewModel;
    private CartViewModel mCartViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    public void initView() {
        mBinding.llSearch.setOnClickListener(v -> {
            SearchActivity.start(v.getContext());
        });
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        initViewModel();
        initData();
    }

    protected void initViewModel() {
        mCartViewModel = getViewModel(CartViewModel.class);
        mViewModel = getViewModel(MallViewModel.class);
    }

    protected void initData() {
        Log.e("dhdhdh", "initData: --------------");
        mViewModel.getCategory().observe(this, this::initViewPager);
        mViewModel.loadCategory();
        mViewModel.initSearchHint(keyword -> {
            mBinding.tvSearch.setText(keyword);
            return null;
        });
        mCartViewModel.refresh();
        mCartViewModel.cartItemListLive.observe(this,it->
                mBinding.shoppingNum.setText(String.valueOf(CollectionUtil.isNotEmpty(it)?it.size():0)));
    }

    private void initViewPager(List<CategoryBean> category) {
        BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        for (CategoryBean c : category) {
            adapter.add(GoodsListFragment.newInstance(c), c.getName());
        }
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.ivClassification.setOnClickListener(v -> {
            int position = mBinding.viewPager.getCurrentItem();
            CategoryActivity.start(v.getContext(), category.get(position));
        });
        mBinding.ivCart.setOnClickListener(view -> CartActivity.start(this));
    }


    @Subscribe
    public void refreshCartData(CartEvent event) {
        mCartViewModel.refresh();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
