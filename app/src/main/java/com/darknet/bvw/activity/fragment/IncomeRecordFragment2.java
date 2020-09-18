package com.darknet.bvw.activity.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.model.MineralBonusListResponse;

import java.util.ArrayList;
import java.util.List;

public class IncomeRecordFragment2 extends BaseFragment {

    public static IncomeRecordFragment2 newInstance(boolean b) {
        return new IncomeRecordFragment2();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_income_record;
    }

    @Override
    public void initView(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_income_record);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        MyAdapter adapter = new MyAdapter();
        rv.setAdapter(adapter);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("position" + i);
        }
        adapter.setNewData(list);
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void initEvent() {

    }

    public void setList(List<MineralBonusListResponse.ItemsBean> itemsBeans) {

    }

    private static class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_income_record_first);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
