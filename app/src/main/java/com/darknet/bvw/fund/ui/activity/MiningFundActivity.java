package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.databinding.ActivityMiningFundBinding;
import com.darknet.bvw.fund.bean.DefiProduct;
import com.darknet.bvw.fund.ui.fragment.FundPledgeFragment;
import com.darknet.bvw.fund.vm.FundViewModel;
import com.darknet.bvw.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollectionUtil;

public class MiningFundActivity extends BaseBindingActivity<ActivityMiningFundBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_mining_fund;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.tvHistoryBonus.setOnClickListener(view -> IncomeFundActivity.start(this, ""));
        mBinding.tvMyAsset.setOnClickListener(view -> PledgeDetailActivity.start(this));

    }

    @Override
    public void initDatas() {
        HashMap<String, ArrayList<DefiProduct>> map = new HashMap<>();
        FundViewModel viewModel = getViewModel(FundViewModel.class);
        viewModel.getListLive().observe(this, defiProducts -> {
            if (CollectionUtil.isNotEmpty(defiProducts)) {
                for (DefiProduct defiProduct : defiProducts) {
                    String name = defiProduct.getName();
                    if (map.containsKey(name)) {
                        ArrayList<DefiProduct> list = map.get(name);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(defiProduct);
                    } else {
                        ArrayList<DefiProduct> list = new ArrayList<>();
                        list.add(defiProduct);
                        map.put(name, list);
                    }
                }
            }
            BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
            for (Map.Entry<String, ArrayList<DefiProduct>> entry : map.entrySet()) {
                adapter.add(FundPledgeFragment.newInstance(entry.getValue()), entry.getKey());
            }
            mBinding.vp.setAdapter(adapter);
            mBinding.tabLayout.setupWithViewPager(mBinding.vp);
        });
        viewModel.refresh();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MiningFundActivity.class));
    }
}
