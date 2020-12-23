package com.darknet.bvw.order.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityCartBinding;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.vm.CartViewModel;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.util.view.ViewUtil;
import com.darknet.bvw.view.CustomCarCounterView;

import java.math.BigDecimal;

/**
 * @ClassName CartActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/22 0022 17:43
 */
public class CartActivity extends BaseBindingActivity<ActivityCartBinding> {
    public static void start(Context context) {
        context.startActivity(new Intent(context, CartActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public void initView() {
        CartViewModel viewModel = getViewModel(CartViewModel.class);
        mBinding.setVm(viewModel);
        CartAdapter cartAdapter = new CartAdapter();
        mBinding.setAdapter(cartAdapter);
        viewModel.refresh();
        cartAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CartData.CartItemListBean itemListBean = cartAdapter.getData().get(position);
                itemListBean.setSelected(!itemListBean.isSelected());
                cartAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void initDatas() {

    }

    public static class CartAdapter extends BaseQuickAdapter<CartData.CartItemListBean, BaseViewHolder> {

        public CartAdapter() {
            super(R.layout.item_cart_goods);
        }

        @Override
        protected void convert(BaseViewHolder helper, CartData.CartItemListBean item) {
            Glide.with(helper.itemView.getContext())
                    .load(item.getProduct_img_url())
                    .apply(RequestOptions.centerCropTransform())
                    .into((ImageView) helper.getView(R.id.sdvGoods));
            helper.getView(R.id.ivSelected).setSelected(item.isSelected());
            helper.setText(R.id.tvGoodsName, item.getProduct_name());
            helper.setText(R.id.tvPrice,  ValueUtil.formatCustomPrice("USDT",item.getPrice()));
            helper.setText(R.id.tvSku, item.getSp1());
            helper.setText(R.id.tvOriginPrice, ValueUtil.formatCustomPrice("USDT",item.getOriginal_price()));
            CustomCarCounterView numView = helper.getView(R.id.numView);
            numView.setMinCount(BigDecimal.ONE);
            numView.setNumber(BigDecimal.valueOf(item.getQuantity()));
            numView.setUpdateNumberListener(new CustomCarCounterView.UpdateNumberListener() {
                @Override
                public void updateNumber(BigDecimal number) {
                    item.setQuantity(number.intValue());
                }
            });
            ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
        }
    }
}
