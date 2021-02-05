package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.databinding.ActivityMiningFundBinding;
import com.darknet.bvw.fund.bean.DefiProduct;
import com.darknet.bvw.fund.ui.fragment.FundPledgeFragment;
import com.darknet.bvw.fund.vm.FundViewModel;
import com.darknet.bvw.util.StatusBarUtil;
import com.darknet.bvw.view.wrap.OnTabSelectedListenerWrap;
import com.google.android.material.tabs.TabLayout;
import com.jingui.lib.utils.wrap.SmallToBigComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 质押产品列表页面
 */
public class MiningFundActivity extends BaseBindingActivity<ActivityMiningFundBinding> {

    private static final String BIW = "BIW";
    private String symbol = BIW;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mining_fund;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.tvHistoryBonus.setOnClickListener(view -> IncomeFundActivity.start(this, symbol));
        mBinding.tvMyAsset.setOnClickListener(view -> PledgeDetailActivity.start(this, symbol));
        mBinding.tabLayout.addOnTabSelectedListener(new OnTabSelectedListenerWrap() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CharSequence text = tab.getText();
                if (text!=null) {
                    symbol = text.toString();
                }
            }
        });
    }

    @Override
    public void initDatas() {
        FundViewModel viewModel = getViewModel(FundViewModel.class);
        viewModel.getListLive().observe(this, this::createTabAndVP);
        viewModel.refresh();
    }

    private void createTabAndVP(List<DefiProduct> defiProducts) {
        HashMap<String, ArrayList<DefiProduct>> map = new HashMap<>();
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
        if (map.containsKey(BIW)) {
            ArrayList<DefiProduct> biw = map.get(BIW);
            adapter.add(FundPledgeFragment.newInstance(biw), BIW);
            map.remove(BIW);
        }
        for (Map.Entry<String, ArrayList<DefiProduct>> entry : map.entrySet()) {
            ArrayList<DefiProduct> value = entry.getValue();
            adapter.add(FundPledgeFragment.newInstance(value), entry.getKey());
        }
        mBinding.vp.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.vp);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MiningFundActivity.class));
    }
}
