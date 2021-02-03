package com.darknet.bvw.fund.ui.fragment;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.fund.vm.FundViewModel;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FundPledgeFragment extends BaseBindingFragment<FundViewModel, ViewDataBinding> {

    @Override
    public int setLayoutResId() {
        return R.layout.fragment_fund_pledge;
    }

    @Override
    protected FundViewModel initViewModel() {
        return getFragmentViewModel(FundViewModel.class);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
