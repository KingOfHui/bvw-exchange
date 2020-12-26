package com.darknet.bvw.order.ui.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.mall.ui.GoodsDetailActivity;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.order.ui.activity.OrderDetailActivity;
import com.darknet.bvw.util.view.ViewUtil;

public class OrderGoodsAdapter extends BaseQuickAdapter<OrderResp.OrderItemListBean, BaseViewHolder> {
    public OrderGoodsAdapter() {
        super(R.layout.item_order_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderResp.OrderItemListBean item) {
        helper.setText(R.id.tvGoodsName, item.getProduct_name());
        helper.setText(R.id.tvSelectSku, item.getSp1());
        helper.setText(R.id.tvPrice, "USDT " + item.getProduct_price());
        helper.setText(R.id.tvGoodsNum, String.format("x%s", item.getProduct_quantity()));
        ImageView ivGoods = helper.getView(R.id.ivGoodsImg);
        ivGoods.setImageURI(Uri.parse(item.getProduct_img_url()));
        ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
//            binding.layoutOrder.ivGoodsImg.setImageURI(Uri.parse(""));
        helper.itemView.setOnClickListener(view -> {
            GoodsDetailActivity.start(mContext, item.getProduct_id());
        });
    }
}
