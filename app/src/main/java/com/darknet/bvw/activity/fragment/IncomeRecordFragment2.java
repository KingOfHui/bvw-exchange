package com.darknet.bvw.activity.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.model.MineralBonusListResponse;
import com.darknet.bvw.viewmodel.IncomeRecordViewModel;

public class IncomeRecordFragment2 extends BaseFragment {

    private MyAdapter mAdapter;
    private LinearLayout llTitle;
    private LinearLayout layNoData;
    private RecyclerView mRv;

    public static IncomeRecordFragment2 newInstance(int type) {
        return new IncomeRecordFragment2();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_income_record_second;
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
        viewModel.getIncomeRecord(2);
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void initEvent() {

    }

    private static class MyAdapter extends BaseQuickAdapter<MineralBonusListResponse.ItemsBean, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_income_record_second);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineralBonusListResponse.ItemsBean item) {
            helper.setText(R.id.tv_person, String.valueOf(item.getUser_id()));
            helper.setText(R.id.tv_bonus, String.valueOf(item.getBonus_refer())+"BTW");
            helper.setText(R.id.tv_time, item.getCreate_at());
        }
    }
}
