package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityInconeFundBinding;
import com.darknet.bvw.fund.ui.adapter.IncomeFundAdapter;
import com.darknet.bvw.fund.vm.IncomeFundViewModel;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class IncomeFundActivity extends BaseBindingActivity<ActivityInconeFundBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_incone_fund;
    }

    @Override
    public void initView() {
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText(R.string.income_detail);
        mBinding.setAdapter(new IncomeFundAdapter());
        IncomeFundViewModel viewModel = getViewModel(IncomeFundViewModel.class);
        mBinding.setVm(viewModel);

    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context, String symbol) {
        Intent intent = new Intent(context, IncomeFundActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }
}
