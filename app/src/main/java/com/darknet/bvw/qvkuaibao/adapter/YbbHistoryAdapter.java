package com.darknet.bvw.qvkuaibao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.qvkuaibao.bean.PosBonus;

/**
 * Created by guoshiwen on 2021/1/28.
 */
public class YbbHistoryAdapter extends BaseQuickAdapter<PosBonus, BaseViewHolder> {
	public YbbHistoryAdapter() {
		super(R.layout.item_ybb_history);
	}

	@Override
	protected void convert(BaseViewHolder helper, PosBonus item) {
		String amount = item.getAmount();
		boolean isOut = amount.startsWith("-");
		helper.setText(R.id.tvInOutType, isOut ? R.string.transfer_out : R.string.transfer_in);
		helper.setText(R.id.tvNumber, (isOut ? amount.substring(1) : amount) + " " + item.getSymbol());
		helper.setText(R.id.tvTime, item.getCreate_time());
	}
}