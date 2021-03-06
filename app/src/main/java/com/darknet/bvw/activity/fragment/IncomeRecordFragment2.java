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
import com.darknet.bvw.model.ReferBonusListResponse;
import com.darknet.bvw.viewmodel.IncomeRecordViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.darknet.bvw.util.TimeUtil.getStringToDate;
import static com.darknet.bvw.util.TimeUtil.parseTime;

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
        viewModel.referBonusItemsLive.observe(this, itemsBeans -> {
            if (itemsBeans != null && itemsBeans.size() > 0) {
                mAdapter.setNewData(itemsBeans);
            } else {
                llTitle.setVisibility(View.GONE);
                mRv.setVisibility(View.GONE);
                layNoData.setVisibility(View.VISIBLE);
            }
        });
        viewModel.referBonusList();
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void initEvent() {

    }

    private static class MyAdapter extends BaseQuickAdapter<ReferBonusListResponse.ItemsBean, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_income_record_second);
        }

        @Override
        protected void convert(BaseViewHolder helper, ReferBonusListResponse.ItemsBean item) {
            helper.setText(R.id.tv_person, String.valueOf(item.getRefer_user_address()));
            helper.setText(R.id.tv_bonus, String.valueOf(item.getRefer_bonus())+"BIW");
            helper.setText(R.id.tv_time,  getStringToDate(item.getBonus_date(), "yyyy-MM-dd HH:mm"));
        }
    }
}
