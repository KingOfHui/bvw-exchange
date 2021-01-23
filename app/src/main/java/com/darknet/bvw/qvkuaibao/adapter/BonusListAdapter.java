package com.darknet.bvw.qvkuaibao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.qvkuaibao.bean.PosBonus;

public class BonusListAdapter extends BaseQuickAdapter<PosBonus, BaseViewHolder> {
    public BonusListAdapter() {
        super(R.layout.item_bonus_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PosBonus item) {
        helper.setText(R.id.tvBonus, item.getBonus_invest());
        helper.setText(R.id.tvTime, item.getBonus_date());
    }
}
