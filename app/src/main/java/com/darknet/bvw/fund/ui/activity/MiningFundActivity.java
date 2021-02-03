package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.databinding.ActivityMiningFundBinding;
import com.darknet.bvw.fund.ui.fragment.FundPledgeFragment;
import com.darknet.bvw.util.StatusBarUtil;

public class MiningFundActivity extends BaseBindingActivity<ActivityMiningFundBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_mining_fund;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.tvHistoryBonus.setOnClickListener(view -> IncomeFundActivity.start(this,""));
        mBinding.tvMyAsset.setOnClickListener(view -> PledgeDetailActivity.start(this));
        BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        adapter.add(new FundPledgeFragment(), "BIW");
        adapter.add(new FundPledgeFragment(), "BTC");
        adapter.add(new FundPledgeFragment(), "ETH");
        adapter.add(new FundPledgeFragment(), "USDT");
        mBinding.vp.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.vp);
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MiningFundActivity.class));
    }
}
