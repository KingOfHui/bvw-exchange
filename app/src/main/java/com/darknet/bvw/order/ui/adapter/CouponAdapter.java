package com.darknet.bvw.order.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.util.ValueUtil;

/**
 * @ClassName CouponAdapter
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 17:20
 */
public class CouponAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {
    public CouponAdapter() {
        super(R.layout.item_coupon);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        helper.setText(R.id.tvCouponName, item.getName());
        helper.setText(R.id.tvValue, ValueUtil.stripTrailingZeros(item.getDiscount()));
        Context context = helper.itemView.getContext();
        helper.setText(R.id.tvDate, String.format(context.getString(R.string.s_biw_can_buy),
                ValueUtil.stripTrailingZeros(item.getPrice())));
        if (item.getStock() > 0) {
            helper.addOnClickListener(R.id.tvBuy);
            helper.setText(R.id.tvBuy, R.string.buy);
            helper.getView(R.id.tvBuy).setEnabled(true);
        } else {
            helper.getView(R.id.tvBuy).setEnabled(false);
            helper.setText(R.id.tvBuy, R.string.sold_out);
        }
    }
}
