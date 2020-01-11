package com.darknet.bvw.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.DataRequest20;
import com.darknet.bvw.R;
import com.darknet.bvw.model.ChangeDepthResponse;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.view.MyListView;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：created by albert on 2019-12-23 21:18
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class DepthFragment extends Fragment {


    private MyListView depth_out_lv;
    private MyListView depth_in_lv;

    private InDepthAdapter mInAdapter;
    private OutDepthAdapter mOutAdapter;

    private List<ChangeDepthResponse.AsksBean> mInList = new ArrayList<>();
    private List<ChangeDepthResponse.BidsBean> mOutList = new ArrayList<>();
    private int first = 0;

    public static DepthFragment newInstance() {
        DepthFragment fragment = new DepthFragment();
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depth,container,false);
        initView(view);
        initData();
        return view;

    }

    private void initView(View view) {
        depth_out_lv = view.findViewById(R.id.depth_out_lv);
        depth_in_lv = view.findViewById(R.id.depth_in_lv);

        mInAdapter = new InDepthAdapter(getActivity(), mInList);
        depth_in_lv.setAdapter(mInAdapter);
        mOutAdapter = new OutDepthAdapter(getActivity(), mOutList);
        depth_out_lv.setAdapter(mOutAdapter);
    }


    private void initData() {
        ChangeDepthResponse dataBean = DataRequest20.getALL(getActivity());
        String base = "0";
        mInList.clear();
        mOutList.clear();

        for (int i = 0; i < 20; i++) {
            if (i == 0) {
                dataBean.getAsks().get(i).setTotal(dataBean.getAsks().get(i).getAmount());
                dataBean.getBids().get(i).setTotal(dataBean.getBids().get(i).getAmount());
            } else {
                String askBuy = ArithmeticUtils.plus(dataBean.getAsks().get(i).getAmount(),
                        dataBean.getAsks().get(i - 1).getTotal())
                        .setScale(0, RoundingMode.UP)
                        .stripTrailingZeros().toPlainString();
                dataBean.getAsks().get(i).setTotal(askBuy);
                String bidSell = ArithmeticUtils.plus(dataBean.getBids().get(i).getAmount(),
                        dataBean.getBids().get(i - 1).getTotal()).setScale(0, RoundingMode.UP)
                        .stripTrailingZeros().toPlainString();
                dataBean.getBids().get(i).setTotal(bidSell);
            }
        }
        if (!ArithmeticUtils.compare(base, dataBean.getAsks().get(4).getTotal())) {
            base = dataBean.getAsks().get(19).getTotal();
        }
        if (!ArithmeticUtils.compare(base, dataBean.getBids().get(4).getTotal())) {
            base = dataBean.getBids().get(19).getTotal();
        }
        for (int i = 0; i < 20; i++) {
            String askP = ArithmeticUtils.multiply(ArithmeticUtils.divide(dataBean.getAsks().get(i).getTotal(), base, 5), "100").setScale(0, RoundingMode.UP).toPlainString();
            dataBean.getAsks().get(i).setPrecent(askP);
            String bidP = ArithmeticUtils.multiply(ArithmeticUtils.divide(dataBean.getBids().get(i).getTotal(), base, 5), "100").setScale(0, RoundingMode.UP).toPlainString();
            dataBean.getBids().get(i).setPrecent(bidP);
        }

        mInList.addAll(dataBean.getAsks());
        Collections.reverse(mInList);
        mOutList.addAll(dataBean.getBids());

        mOutAdapter.notifyDataSetChanged();
        mInAdapter.notifyDataSetChanged();


    }
}
