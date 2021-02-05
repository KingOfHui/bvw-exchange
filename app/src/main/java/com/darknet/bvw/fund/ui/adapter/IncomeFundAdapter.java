package com.darknet.bvw.fund.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.fund.bean.DefiBonus;
import com.darknet.bvw.view.RItem;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class IncomeFundAdapter extends BaseQuickAdapter<DefiBonus, BaseViewHolder> {
    public IncomeFundAdapter() {
        super(R.layout.item_income_fund);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefiBonus item) {
        RItem rItem = helper.getView(R.id.rItem);
        rItem.setTopTitle(mContext.getString(R.string.income_in));
        rItem.setBottomInfo(item.getCreate_time());
        rItem.setRightText(item.getBonus() + "BTD");
    }
}
