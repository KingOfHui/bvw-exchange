package com.darknet.bvw.order.ui.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.util.view.ViewUtil;

import java.math.BigDecimal;
import java.util.List;

public class ConfirmGoodsAdapter extends BaseQuickAdapter<CartData.CartItemListBean, BaseViewHolder> {
    public ConfirmGoodsAdapter() {
        super(R.layout.item_order_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartData.CartItemListBean item) {
        helper.setText(R.id.tvGoodsName, item.getProduct_name());
        helper.setText(R.id.tvSelectSku, item.getSp1());
        helper.setText(R.id.tvPrice, "USDT " + item.getPrice());
        helper.setText(R.id.tvGoodsNum, String.format("x%s", item.getQuantity()));
        ImageView ivGoods = helper.getView(R.id.ivGoodsImg);
        ivGoods.setImageURI(Uri.parse(item.getProduct_img_url()));
        MyCouponBean selectCouponBean = item.getSelectCouponBean();
        if (selectCouponBean == null) {
            helper.setGone(R.id.tvSelectDiscounts, item.getCoupon_discount().compareTo(BigDecimal.ZERO) > 0);
            helper.setText(R.id.tvSelectDiscounts, String.format(mContext.getString(R.string.select_coupon_top), ValueUtil.stripTrailingZeros(item.getCoupon_discount())));
        } else {
            helper.setVisible(R.id.tvSelectDiscounts, true);
            helper.setText(R.id.tvSelectDiscounts, String.format(mContext.getString(R.string.goods_coupon), ValueUtil.stripTrailingZeros(selectCouponBean.getDiscount())));
        }
        helper.addOnClickListener(R.id.tvSelectDiscounts);
        ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
//            binding.layoutOrder.ivGoodsImg.setImageURI(Uri.parse(""));
    }

    public BigDecimal getCouponAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        List<CartData.CartItemListBean> data = getData();
        for (CartData.CartItemListBean datum : data) {
            MyCouponBean selectCouponBean = datum.getSelectCouponBean();
            if (selectCouponBean != null) {
                BigDecimal coupon_discount = datum.getCoupon_discount();
                amount = amount.add(coupon_discount);
            }
        }
        return amount;
    }
}
