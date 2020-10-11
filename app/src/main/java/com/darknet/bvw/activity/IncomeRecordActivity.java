package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment2;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.databinding.ActivityIncomeRecordBinding;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.MineralBonusListResponse;
import com.darknet.bvw.model.response.MineralStatusResponse;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.viewmodel.MineralViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName IncomeRecordActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 15:37
 */
public class IncomeRecordActivity extends BaseBindingActivity<ActivityIncomeRecordBinding> {

    private IncomeRecordFragment mFragment;
    private IncomeRecordFragment2 mFragment1;
    private IncomeRecordFragment mFragment2;
    private IncomeRecordFragment mFragment3;

    public static void startSelf(Context context, MineralStatusResponse statusInfo) {
        Intent intent = new Intent(context, IncomeRecordActivity.class);
        intent.putExtra("statusInfo", statusInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_record;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        MineralStatusResponse statusInfo = (MineralStatusResponse) intent.getSerializableExtra("statusInfo");
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.income_record));
        BaseFragmentPagerAdapter<BaseFragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        mFragment = IncomeRecordFragment.newInstance(1);
        adapter.add(mFragment, getString(R.string.mineral_income));
        mFragment1 = IncomeRecordFragment2.newInstance(2);
        adapter.add(mFragment1, getString(R.string.tuijian_income));
        mFragment2 = IncomeRecordFragment.newInstance(3);
        adapter.add(mFragment2, getString(R.string.da_kuang_gong_jiang_li));
        mFragment3 = IncomeRecordFragment.newInstance(4);
        adapter.add(mFragment3, getString(R.string.btc_bonus));
        mBinding.vpIncome.setOffscreenPageLimit(2);
        mBinding.vpIncome.setAdapter(adapter);
//        TabLayoutHelper.setupWithViewPager(mBinding.tabIncome, mBinding.vpIncome, adapter.getFragmentTitles(), 16, 14, EnvironmentUtil.getScreenWidth(this) / 3-20);
        mBinding.tabIncome.setupWithViewPager(mBinding.vpIncome);
       /* mBinding.tabIncome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int btwTotal = 0;
                int btcTotal = 0;
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans) {
                            btcTotal += itemsBean.getBonus_btc();
                            btwTotal += itemsBean.getBonus_miner();
                        }
                        break;
                    case 1:
                        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans1) {
                            btcTotal += itemsBean.getBonus_btc();
                            btwTotal += itemsBean.getBonus_miner();
                        }
                        break;
                    case 2:
                        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans2) {
                            btcTotal += itemsBean.getBonus_btc();
                            btwTotal += itemsBean.getBonus_miner();
                        }
                        break;
                    default:

                        break;
                }
                updateViewNum(btwTotal, btcTotal);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        int btwTotal = 0;
        int btcTotal = 0;
        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans) {
            btcTotal += itemsBean.getBonus_btc();
            btwTotal += itemsBean.getBonus_miner();
        }*/
        updateViewNum(statusInfo.getSun_btw_bonus(), statusInfo.getSum_btc_bonus());
    }

    private void updateViewNum(String btwTotal, String btcTotal) {
        mBinding.tvBtwTotal.setText(SpanHelper.start()
                .next(String.valueOf(btwTotal))
                .setTextColor(Color.parseColor("#01FCDA"))
                .setTextSize(30)
                .next(" ")
                .next("BIW")
                .setTextSize(15)
                .setTextColor(Color.WHITE)
                .get());
        mBinding.tvBtcTotal.setText(SpanHelper.start()
                .next(String.valueOf(btcTotal))
                .setTextColor(Color.parseColor("#01FCDA"))
                .setTextSize(30)
                .next(" ")
                .next("BTC")
                .setTextSize(15)
                .setTextColor(Color.WHITE)
                .get());
    }

    @Override
    public void initDatas() {

    }


}
