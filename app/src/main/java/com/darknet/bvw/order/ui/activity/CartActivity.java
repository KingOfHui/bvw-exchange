package com.darknet.bvw.order.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.darknet.bvw.util.StatusBarUtil;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.util.view.ViewUtil;
import com.darknet.bvw.view.CustomCarCounterView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CartActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/22 0022 17:43
 */
public class CartActivity extends BaseBindingActivity<ActivityCartBinding> {

    private CartAdapter mCartAdapter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CartActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        CartViewModel viewModel = getViewModel(CartViewModel.class);
        mBinding.setVm(viewModel);
        mCartAdapter = new CartAdapter();
        mBinding.setAdapter(mCartAdapter);
        viewModel.refresh();
        mCartAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CartData.CartItemListBean> data = mCartAdapter.getData();
                CartData.CartItemListBean itemListBean = data.get(position);
                itemListBean.setCheck(itemListBean.getCheck() == 1 ? 0 : 1);
                mCartAdapter.notifyItemChanged(position);
                updateBottomView(mCartAdapter.getData());
            }
        });
        mCartAdapter.setAddOrSubListener(new CartAdapter.AddOrSubListener() {
            @Override
            public void onAdd(int product_sku_id) {
                viewModel.addToCart(product_sku_id,1);
            }

            @Override
            public void onSub(int product_sku_id) {
                viewModel.subCartGoods(product_sku_id,1);
            }
        });
        mBinding.tvSettle.setOnClickListener(view -> {
            StringBuilder sbSelect = new StringBuilder();
            StringBuilder sbUnSelect = new StringBuilder();
            List<CartData.CartItemListBean> data = mCartAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                CartData.CartItemListBean cartItemListBean = data.get(i);
                if (cartItemListBean.getCheck() == 1) {
                    sbSelect.append(cartItemListBean.getProduct_id()).append(",");
                } else {
                    sbUnSelect.append(cartItemListBean.getProduct_id()).append(",");
                }
            }
            if (TextUtils.isEmpty(sbSelect.toString())) {
                ToastUtils.showToast(getString(R.string.first_check_goods));
                return;
            }
            viewModel.checkCartByProduct(sbUnSelect.toString(), sbSelect.toString());
        });
        mBinding.ivAllSelected.setOnClickListener(view -> {
            mCartAdapter.allSelectedOrNot();
            mBinding.ivAllSelected.setSelected(mCartAdapter.isAllSelect());
        });
        mBinding.tvAllSelect.setOnClickListener(view -> {
            mCartAdapter.allSelectedOrNot();
            mBinding.ivAllSelected.setSelected(mCartAdapter.isAllSelect());
        });

        viewModel.checkCartSuccessLive.observe(this, aBoolean -> {
            if (aBoolean) {
                ConfirmOrderActivity.start(this, mCartAdapter.getAllSelected());
            }
        });
        viewModel.cartItemListLive.observe(this, this::updateBottomView);
    }

    private void updateBottomView(List<CartData.CartItemListBean> data) {
        int selectCount = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < data.size(); i++) {
            boolean selected = data.get(i).getCheck() == 1;
            if (selected) {
                selectCount += 1;
                total = total.add(data.get(i).getPrice());
            }
        }
        mBinding.tvSettle.setText(String.format(getString(R.string.settle_s), String.valueOf(selectCount)));
        mBinding.tvTotalPrice.setText(String.format(getString(R.string.total_money), ValueUtil.stripTrailingZeros(total)));
    }

    @Override
    public void initDatas() {

    }

    public static class CartAdapter extends BaseQuickAdapter<CartData.CartItemListBean, BaseViewHolder> {

        private boolean isAllSelect = false;

        public CartAdapter() {
            super(R.layout.item_cart_goods);
        }

        @Override
        protected void convert(BaseViewHolder helper, CartData.CartItemListBean item) {
            Glide.with(helper.itemView.getContext())
                    .load(item.getProduct_img_url())
                    .apply(RequestOptions.centerCropTransform())
                    .into((ImageView) helper.getView(R.id.sdvGoods));
            helper.getView(R.id.ivSelected).setSelected(item.getCheck() == 1);
            helper.setText(R.id.tvGoodsName, item.getProduct_name());
            helper.setText(R.id.tvPrice, ValueUtil.formatCustomPrice("USDT", item.getPrice()));
            helper.setText(R.id.tvSku, item.getSp1());
            helper.setText(R.id.tvOriginPrice, ValueUtil.formatCustomPrice("USDT", item.getOriginal_price()));
            CustomCarCounterView numView = helper.getView(R.id.numView);
            numView.setMinCount(BigDecimal.ONE);
            numView.setNumber(BigDecimal.valueOf(item.getQuantity()));
//            numView.setUpdateNumberListener(number -> item.setQuantity(number.intValue()));
            numView.setAddOrSubListener(new CustomCarCounterView.AddOrSubListener() {
                @Override
                public void onAdd() {
                    if (mAddOrSubListener != null) {
                        mAddOrSubListener.onAdd(item.getProduct_sku_id());
                    }
                }

                @Override
                public void onSub() {
                    if (mAddOrSubListener != null) {
                        mAddOrSubListener.onSub(item.getProduct_sku_id());
                    }
                }
            });
            ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
        }

        private ArrayList<CartData.CartItemListBean> getAllSelected() {
            ArrayList<CartData.CartItemListBean> cartItemListBeans = new ArrayList<>();
            List<CartData.CartItemListBean> data = getData();
            for (int i = 0; i < data.size(); i++) {
                CartData.CartItemListBean cartItemListBean = data.get(i);
                boolean selected = cartItemListBean.getCheck() == 1;
                if (selected) {
                    cartItemListBeans.add(cartItemListBean);
                }
            }
            return cartItemListBeans;
        }

        private void allSelectedOrNot() {
            isAllSelect = !isAllSelect;
            List<CartData.CartItemListBean> data = getData();
            for (CartData.CartItemListBean datum : data) {
                datum.setCheck(isAllSelect ? 1 : 0);
            }
            notifyDataSetChanged();
        }

        public boolean isAllSelect() {
            return isAllSelect;
        }

        private AddOrSubListener mAddOrSubListener;

        public void setAddOrSubListener(AddOrSubListener addOrSubListener) {
            mAddOrSubListener = addOrSubListener;
        }
        public interface AddOrSubListener{
            void onAdd(int product_sku_id);

            void onSub(int product_sku_id);
        }

    }
}
