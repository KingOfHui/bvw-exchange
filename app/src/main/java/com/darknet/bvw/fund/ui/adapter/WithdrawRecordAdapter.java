package com.darknet.bvw.fund.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.fund.bean.DefiWithdraw;
import com.darknet.bvw.view.RItem;

public class WithdrawRecordAdapter extends BaseQuickAdapter<DefiWithdraw, BaseViewHolder> {
    public WithdrawRecordAdapter() {
        super(R.layout.item_income_fund);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefiWithdraw item) {
        RItem rItem = helper.getView(R.id.rItem);
        rItem.setTopTitle("BTD");
        rItem.setBottomInfo(item.getCreate_time());
        rItem.setRightText(item.getBonus() + "BTD");
    }
}
