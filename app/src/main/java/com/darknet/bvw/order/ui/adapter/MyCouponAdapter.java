package com.darknet.bvw.order.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.util.ValueUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName CouponAdapter
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 17:20
 */
public class MyCouponAdapter extends BaseQuickAdapter<MyCouponBean, BaseViewHolder> {
    public MyCouponAdapter() {
        super(R.layout.item_my_coupon);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCouponBean item) {
        helper.setText(R.id.tvCouponName, item.getName());
        helper.setText(R.id.tvValue, ValueUtil.stripTrailingZeros(item.getDiscount()));
        Context context = helper.itemView.getContext();
        helper.setText(R.id.tvDate, String.format(context.getString(R.string.validity_date),
                item.getEnd_time()));

        //使用状态 -1未支付 0未使用 1已使用 2已过期
        switch (item.getState()) {
            case -1:
                helper.getView(R.id.tvBuy).setEnabled(false);
                helper.setText(R.id.tvBuy, "未支付");
                break;
            case 0:
                helper.addOnClickListener(R.id.tvBuy);
                helper.setText(R.id.tvBuy, "去使用");
                helper.getView(R.id.tvBuy).setEnabled(true);
                helper.addOnClickListener(R.id.tvBuy);
                break;
            case 1:
                helper.getView(R.id.tvBuy).setEnabled(false);
                helper.setText(R.id.tvBuy, "已使用");
                break;
            case 2:
                helper.getView(R.id.tvBuy).setEnabled(false);
                helper.setText(R.id.tvBuy, "已过期");
                break;
            default:
                helper.getView(R.id.tvBuy).setEnabled(false);
                helper.setText(R.id.tvBuy, "未知");
                break;
        }
    }
}
