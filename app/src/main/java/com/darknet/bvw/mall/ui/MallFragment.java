package com.darknet.bvw.mall.ui;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.databinding.FragmentMallBindingImpl;
import com.darknet.bvw.mall.vm.MallViewModel;
import com.darknet.bvw.order.ui.OrderListFragment;

/**
 * @ClassName MallFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:43
 */
public class MallFragment extends BaseBindingFragment<MallViewModel, FragmentMallBindingImpl> {
    @Override
    public int setLayoutResId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected MallViewModel initViewModel() {
        return getFragmentViewModel(MallViewModel.class);
    }

    @Override
    protected void initData() {
        String[] arr = new String[]{"首页", "家居", "美妆", "服饰", "母婴", "疫情防护"};
        BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getChildFragmentManager());
        for (String s : arr) {
            adapter.add(GoodsListFragment.newInstance(s), s);
        }
        mDataBinding.viewPager.setOffscreenPageLimit(3);
        mDataBinding.viewPager.setAdapter(adapter);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);
    }

    public static MallFragment newInstance() {
        return new MallFragment();
    }
}
