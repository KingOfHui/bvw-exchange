package com.darknet.bvw.mall.ui;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentMallBindingImpl;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.vm.MallViewModel;

import java.util.List;

/**
 * @ClassName MallFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:43
 */
public class MallFragment extends BaseBindingFragment<MallViewModel, FragmentMallBindingImpl> {
    @Override
    protected void initView() {
    }

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
        mViewModel.getCategory().observe(this, new Observer<List<CategoryBean>>() {
            @Override
            public void onChanged(List<CategoryBean> category) {
                initViewPager(category);
            }
        });
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
    }

    public static MallFragment newInstance() {
        return new MallFragment();
    }
}
