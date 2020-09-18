package com.darknet.bvw.activity.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.model.MineralBonusListResponse;

import java.util.ArrayList;
import java.util.List;

public class IncomeRecordFragment extends BaseFragment {

    private MyAdapter mAdapter;

    public static IncomeRecordFragment newInstance(boolean b) {
        return new IncomeRecordFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_income_record;
    }

    @Override
    public void initView(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_income_record);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        mAdapter = new MyAdapter();
        rv.setAdapter(mAdapter);
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void initEvent() {

    }

    public void setList(List<MineralBonusListResponse.ItemsBean> itemsBeans) {
        mAdapter.setNewData(itemsBeans);
    }

    private static class MyAdapter extends BaseQuickAdapter<MineralBonusListResponse.ItemsBean, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_income_record_first);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineralBonusListResponse.ItemsBean item) {
            helper.setText(R.id.tv_time, item.getCreate_at());
            helper.setText(R.id.tv_bonus, String.valueOf(item.getBonus_big_node()));
        }
    }
}
