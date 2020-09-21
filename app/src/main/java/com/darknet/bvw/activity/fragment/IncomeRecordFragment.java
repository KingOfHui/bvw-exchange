package com.darknet.bvw.activity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.commonlib.widget.ProgressLayout;
import com.darknet.bvw.model.MineralBonusListResponse;
import com.darknet.bvw.viewmodel.IncomeRecordViewModel;
import com.fasterxml.jackson.databind.ser.std.IterableSerializer;

import java.util.ArrayList;
import java.util.List;

public class IncomeRecordFragment extends BaseFragment {

    private MyAdapter mAdapter;
    private LinearLayout llTitle;
    private LinearLayout layNoData;
    private RecyclerView mRv;
    private int mType;

    public static IncomeRecordFragment newInstance(int type) {
        IncomeRecordFragment incomeRecordFragment = new IncomeRecordFragment();
        Bundle bundle = new Bundle();bundle.putInt("type", type);
        incomeRecordFragment.setArguments(bundle);
        return incomeRecordFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_income_record;
    }

    @Override
    public void initView(View view) {
        mRv = view.findViewById(R.id.rv_income_record);
        llTitle = view.findViewById(R.id.ll_title);
        layNoData = view.findViewById(R.id.layNoData);
        mRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        mAdapter = new MyAdapter();
        mRv.setAdapter(mAdapter);

        IncomeRecordViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(IncomeRecordViewModel.class);
        viewModel.itemsLive.observe(this, itemsBeans -> {
            if (itemsBeans != null && itemsBeans.size() > 0) {
                mAdapter.setNewData(itemsBeans);
            } else {
                llTitle.setVisibility(View.GONE);
                mRv.setVisibility(View.GONE);
                layNoData.setVisibility(View.VISIBLE);
            }
        });
        Bundle budle = getArguments();
        if (budle!=null) {
            mType = budle.getInt("type", 1);
            viewModel.getIncomeRecord(mType);
        }
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void initEvent() {

    }

    private class MyAdapter extends BaseQuickAdapter<MineralBonusListResponse.ItemsBean, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_income_record_first);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineralBonusListResponse.ItemsBean item) {
            helper.setText(R.id.tv_time, item.getCreate_at());
            int bonus = 0;
            if (mType == 1) {
                bonus = item.getBonus_miner();
            } else if (mType == 3) {
                bonus = item.getBonus_big_node();
            } else if (mType == 4) {
                bonus = item.getBonus_btc();
            }
            helper.setText(R.id.tv_bonus, String.valueOf(bonus));
        }
    }
}
