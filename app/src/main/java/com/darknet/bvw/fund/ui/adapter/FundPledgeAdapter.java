package com.darknet.bvw.fund.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.fund.bean.DefiProduct;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

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
        helper.getView(R.id.cbSelect).setSelected(item.isSelected());
        helper.itemView.setOnClickListener(v -> {
            List<DefiProduct> data = getData();
            if (CollectionUtil.isNotEmpty(data)) {
                for (DefiProduct datum : data) {
                    datum.setSelected(false);
                }
                item.setSelected(true);
                notifyDataSetChanged();
            }
        });
    }

    public DefiProduct getSelected() {
        List<DefiProduct> data = getData();
        if (CollectionUtil.isNotEmpty(data)) {
            for (DefiProduct datum : data) {
                if (datum.isSelected()) {
                    return datum;
                }
            }
        }
        return null;
    }

    public void setOriginStatusList() {
        getSelected().setSelected(false);
        notifyDataSetChanged();
    }
}
