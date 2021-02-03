package com.darknet.bvw.fund.ui.fragment;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentFundPledgeBinding;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.fund.ui.activity.AboutBTDActivity;
import com.darknet.bvw.fund.ui.dialog.PledgeDialog;
import com.darknet.bvw.fund.vm.FundViewModel;
import com.darknet.bvw.util.ToastUtils;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FundPledgeFragment extends BaseBindingFragment<FundViewModel, FragmentFundPledgeBinding> {

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
        mDataBinding.tvBTD.setOnClickListener(view -> AboutBTDActivity.start(requireContext()));
        mDataBinding.tvPledge.setOnClickListener(view -> {
            PledgeDialog pledgeDialog = new PledgeDialog(requireContext(), "2000BIW");
            pledgeDialog.setListener(pwd -> {
                if (!WalletDaoUtils.checkPassword(pwd)) {
                    ToastUtils.showToast(R.string.wrong_pwd);
                    return;
                }
                mViewModel.isLoadingLive.setValue(true);
            });
            pledgeDialog.show();
        });
    }

    @Override
    protected void initData() {

    }
}
