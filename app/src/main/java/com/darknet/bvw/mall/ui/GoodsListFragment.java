package com.darknet.bvw.mall.ui;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentGoodsListBinding;
import com.darknet.bvw.mall.vm.GoodsListViewModel;

public class GoodsListFragment extends BaseBindingFragment<GoodsListViewModel, FragmentGoodsListBinding> {

    public static GoodsListFragment newInstance(String titleName) {
        GoodsListFragment fragment = new GoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", titleName);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int setLayoutResId() {
        return R.layout.fragment_goods_list;
    }

    @Override
    protected GoodsListViewModel initViewModel() {
        return getFragmentViewModel(GoodsListViewModel.class);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        mDataBinding.tv.setText(title);
    }
}
