package com.darknet.bvw.adapter;

import com.darknet.bvw.R;
import com.darknet.bvw.model.AmountModel;

public class AmountAdapter extends BaseSimpleRecycleViewAdapter<AmountModel> {


    @Override
    protected int bindLayout(int viewType) {
        return R.layout.item_amount;
    }

    @Override
    protected void bind(BaseViewHolder holder, AmountModel data, int position) {

    }
}
