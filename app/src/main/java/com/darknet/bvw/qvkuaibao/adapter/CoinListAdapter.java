package com.darknet.bvw.qvkuaibao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.qvkuaibao.bean.PosWallet;

import java.math.BigDecimal;

public class CoinListAdapter extends BaseQuickAdapter<PosWallet, BaseViewHolder> {
    public CoinListAdapter() {
        super(R.layout.item_coin_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PosWallet item) {
        helper.setText(R.id.tvCoinName, item.getSymbol());
        helper.setText(R.id.tvBonusToday, String.format(helper.itemView.getContext().getString(R.string.yesterday_earnings) + "：%s %s", item.getYesterdayPosBonusAmount(), item.getSymbol()));
        helper.setText(R.id.tvBalance, item.getInvest_amount_str());
        helper.setText(R.id.tvCny, "≈$"+item.getInvest_amount().multiply(item.getUsd_rate()).setScale(2, BigDecimal.ROUND_DOWN).toEngineeringString());
    }
}
