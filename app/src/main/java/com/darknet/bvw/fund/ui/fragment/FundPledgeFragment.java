package com.darknet.bvw.fund.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentFundPledgeBinding;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.fund.bean.DefiProduct;
import com.darknet.bvw.fund.ui.activity.AboutBTDActivity;
import com.darknet.bvw.fund.ui.adapter.FundPledgeAdapter;
import com.darknet.bvw.fund.ui.dialog.PledgeDialog;
import com.darknet.bvw.fund.vm.FundViewModel;
import com.darknet.bvw.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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

    public static Fragment newInstance(ArrayList<DefiProduct> value) {
        FundPledgeFragment fragment = new FundPledgeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list",value);
        fragment.setArguments(bundle);
        return fragment;
    }

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
        mDataBinding.setAdapter(new FundPledgeAdapter());
    }

    @Override
    protected void initData() {

        Bundle bundle = getArguments();
        if (bundle!=null) {
            ArrayList<DefiProduct> list = bundle.getParcelableArrayList("list");
            mDataBinding.setList(list);
        }

    }
}
