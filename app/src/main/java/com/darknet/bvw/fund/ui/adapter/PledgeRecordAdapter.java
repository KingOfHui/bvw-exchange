package com.darknet.bvw.fund.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.fund.bean.DefiInvest;

public class PledgeRecordAdapter extends BaseQuickAdapter<DefiInvest, BaseViewHolder> {
    public PledgeRecordAdapter() {
        super(R.layout.item_pledge_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefiInvest item) {
        helper.setText(R.id.tvTime, item.getCreate_time());
        helper.setText(R.id.tvAmount, item.getAmount()+item.getProduct_symbol());
        helper.setVisible(R.id.tvCancel, item.getState() == 1);
        helper.setVisible(R.id.tvStatus, item.getState() != 1);
        helper.setText(R.id.tvStatus, item.getState() == 0 ? mContext.getString(R.string.paying) : mContext.getString(R.string.end));
        helper.addOnClickListener(R.id.tvCancel);
    }
}
