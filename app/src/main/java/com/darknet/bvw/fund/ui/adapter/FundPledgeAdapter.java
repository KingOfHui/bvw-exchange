package com.darknet.bvw.fund.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.fund.bean.DefiProduct;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FundPledgeAdapter extends BaseQuickAdapter<DefiProduct, BaseViewHolder> {
    public FundPledgeAdapter() {
        super(R.layout.item_fund_pledge);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefiProduct item) {
        helper.setText(R.id.tvAmount, item.getAmount() + item.getSymbol());
    }
}
