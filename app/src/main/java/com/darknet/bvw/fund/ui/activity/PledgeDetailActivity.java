package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityPledgeDetailBinding;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PledgeDetailActivity extends BaseBindingActivity<ActivityPledgeDetailBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_pledge_detail;
    }

    @Override
    public void initView() {
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText("BTD");

    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PledgeDetailActivity.class));
    }
}
