package com.darknet.bvw.mall.ui;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.mall.vm.MallViewModel;

/**
 * @ClassName MallFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:43
 */
public class MallFragment extends BaseBindingFragment<MallViewModel, ViewDataBinding> {
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

    }
}
