package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.model.DealModel;

import java.util.List;

public class DealAdapter extends BAdapter<DealModel>{

    public DealAdapter(Context context, List<DealModel> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_deal;
    }

    @Override
    public void onInitView(View view, int position) {

    }
}
