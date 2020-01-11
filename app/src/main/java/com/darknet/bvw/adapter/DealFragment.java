package com.darknet.bvw.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.model.DealModel;
import com.darknet.bvw.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：created by albert on 2019-12-23 21:22
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class DealFragment extends Fragment {

    private MyListView mListView;
    private DealAdapter mDealAdapter;
    private List<DealModel> mList = new ArrayList<>();

    public static DealFragment newInstance() {
        DealFragment fragment = new DealFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deal, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.deal_lv);
        mDealAdapter = new DealAdapter(getActivity(), mList);
        mListView.setAdapter(mDealAdapter);

        for (int i = 0; i < 20; i++) {
            mList.add(new DealModel());
        }
        mDealAdapter.notifyDataSetChanged();
    }
}
