package com.darknet.bvw.mall.ui;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import cn.hutool.core.collection.CollectionUtil;
import java8.util.function.Function;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentMallBindingImpl;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.ui.search.SearchActivity;
import com.darknet.bvw.mall.vm.MallViewModel;
import com.darknet.bvw.order.bean.event.CartEvent;
import com.darknet.bvw.order.ui.activity.CartActivity;
import com.darknet.bvw.order.vm.CartViewModel;
import com.darknet.bvw.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @ClassName MallFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:43
 */
public class MallFragment extends BaseBindingFragment<MallViewModel, FragmentMallBindingImpl> {

    private CartViewModel mCartViewModel;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mDataBinding.llSearch.setOnClickListener(v -> {
            SearchActivity.start(v.getContext());
        });
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected MallViewModel initViewModel() {
        mCartViewModel = getFragmentViewModel(CartViewModel.class);
        return getFragmentViewModel(MallViewModel.class);
    }

    @Override
    protected void initData() {
        Log.e("dhdhdh", "initData: --------------");
        mViewModel.getCategory().observe(this, this::initViewPager);
        mViewModel.loadCategory();
        mViewModel.initSearchHint(keyword -> {
            mDataBinding.tvSearch.setText(keyword);
            return null;
        });
        mCartViewModel.refresh();
        mCartViewModel.cartItemListLive.observe(getViewLifecycleOwner(),it->
                mDataBinding.shoppingNum.setText(String.valueOf(CollectionUtil.isNotEmpty(it)?it.size():0)));
    }

    private void initViewPager(List<CategoryBean> category) {
        BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getChildFragmentManager());
        for (CategoryBean c : category) {
            adapter.add(GoodsListFragment.newInstance(c), c.getName());
        }
        mDataBinding.viewPager.setOffscreenPageLimit(3);
        mDataBinding.viewPager.setAdapter(adapter);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);
        mDataBinding.ivClassification.setOnClickListener(v -> {
            int position = mDataBinding.viewPager.getCurrentItem();
            CategoryActivity.start(v.getContext(), category.get(position));
        });
        mDataBinding.ivCart.setOnClickListener(view -> CartActivity.start(requireActivity()));
    }


    @Subscribe
    public void refreshCartData(CartEvent event) {
        mCartViewModel.refresh();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public static MallFragment newInstance() {
        return new MallFragment();
    }

    @Override
    public boolean ChangeStatus() {
        return true;
    }
}
