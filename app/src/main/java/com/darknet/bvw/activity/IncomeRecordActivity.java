package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.databinding.ActivityIncomeRecordBinding;
import com.darknet.bvw.util.EnvironmentUtil;
import com.darknet.bvw.util.view.TabLayoutHelper;

/**
 * @ClassName IncomeRecordActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 15:37
 */
public class IncomeRecordActivity extends BaseBindingActivity<ActivityIncomeRecordBinding> {

    public static void startSelf(Context context) {
        context.startActivity(new Intent(context,IncomeRecordActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_record;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.income_record));
        BaseFragmentPagerAdapter<IncomeRecordFragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        adapter.add(IncomeRecordFragment.newInstance(true), getString(R.string.mineral_income));
        adapter.add(IncomeRecordFragment.newInstance(false), getString(R.string.tuijian_income));
        adapter.add(IncomeRecordFragment.newInstance(false), getString(R.string.da_kuang_gong_jiang_li));
        mBinding.vpIncome.setOffscreenPageLimit(2);
        mBinding.vpIncome.setAdapter(adapter);
//        TabLayoutHelper.setupWithViewPager(mBinding.tabIncome, mBinding.vpIncome, adapter.getFragmentTitles(), 16, 14, EnvironmentUtil.getScreenWidth(this) / 3-20);
        mBinding.tabIncome.setupWithViewPager(mBinding.vpIncome);
    }

    @Override
    public void initDatas() {

    }
}
