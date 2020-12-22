package com.darknet.bvw.order.ui.activity;


import android.content.Context;
import android.content.Intent;
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
import com.darknet.bvw.util.view.ViewUtil;

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
        mBinding.setAdapter(new CartAdapter());
        viewModel.refresh();
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
            ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
        }
    }
}
