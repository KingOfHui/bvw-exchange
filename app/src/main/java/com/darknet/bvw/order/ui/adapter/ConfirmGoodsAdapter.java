package com.darknet.bvw.order.ui.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.util.view.ViewUtil;

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
        ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
//            binding.layoutOrder.ivGoodsImg.setImageURI(Uri.parse(""));
    }
}
