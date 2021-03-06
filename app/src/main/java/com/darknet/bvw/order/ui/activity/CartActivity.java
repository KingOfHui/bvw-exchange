package com.darknet.bvw.order.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityCartBinding;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.event.CartEvent;
import com.darknet.bvw.order.vm.CartViewModel;
import com.darknet.bvw.util.StatusBarUtil;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.util.view.ViewUtil;
import com.darknet.bvw.view.CustomCarCounterView;
import com.darknet.bvw.view.CustomDividerItemDecoration;
import com.jingui.lib.utils.DensityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName CartActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/22 0022 17:43
 */
public class CartActivity extends BaseBindingActivity<ActivityCartBinding> {

    private CartAdapter mCartAdapter;
    private CartViewModel mViewModel;
    private CartData mCartData;

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
        EventBus.getDefault().register(this);
        mViewModel = getViewModel(CartViewModel.class);
        mBinding.setVm(mViewModel);
        mCartAdapter = new CartAdapter();
        mBinding.setAdapter(mCartAdapter);
        mBinding.rvCartGoods.addItemDecoration(new CustomDividerItemDecoration.Builder()
                .setColor(Color.parseColor("#33FFFFFF"))
                .setSpace(DensityUtils.dip2px(1), DensityUtils.dip2px(30), DensityUtils.dip2px(15))
                .build());
        mViewModel.refresh();
        mCartAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.clCartGoods) {
                CartData.CartItemListBean item = mCartAdapter.getItem(position);
                if (item != null) {
                    mViewModel.checkCartBySku(item.getCheck() == 1 ? 0 : 1, String.valueOf(item.getProduct_sku_id()));
                }
            }
        });
        mCartAdapter.setAddOrSubListener(new CartAdapter.AddOrSubListener() {
            @Override
            public void onAdd(int product_sku_id) {
                mViewModel.addCartGoods(product_sku_id, 1);
            }

            @Override
            public void onSub(int product_sku_id) {
                mViewModel.subCartGoods(product_sku_id, 1);
            }

            @Override
            public void onDelete(int product_sku_id) {
                mViewModel.deleteBySku(product_sku_id);
            }
        });
        mBinding.tvSettle.setOnClickListener(view -> {
            if (mCartData.getChecked_product_count() > 0) {
                ConfirmOrderActivity.start(this);
            } else {
                ToastUtils.showToast(getString(R.string.first_check_goods));
            }
        });
        mBinding.ivAllSelected.setOnClickListener(view -> {
            mViewModel.checkCartBySku(mCartAdapter.allSelectedOrNot() ? 0 : 1,mCartAdapter.getAllIds());
        });
        mBinding.tvAllSelect.setOnClickListener(view -> {
            mViewModel.checkCartBySku(mCartAdapter.allSelectedOrNot() ? 0 : 1,mCartAdapter.getAllIds());
        });

        mViewModel.checkCartSuccessLive.observe(this, aBoolean -> {
            if (aBoolean) {
                ConfirmOrderActivity.start(this, null, null);
            }
        });
        mViewModel.cartDataLive.observe(this, this::updateBottomView);
    }

    private void updateBottomView(CartData data) {
        mCartData = data;
        mBinding.tvSettle.setText(String.format(getString(R.string.settle_s), String.valueOf(data.getChecked_product_count())));
        mBinding.tvTotalPrice.setText(String.format(getString(R.string.total_money), ValueUtil.stripTrailingZeros(data.getChecked_product_amount())));
        mBinding.tvGoodsCount.setText(String.format(getString(R.string.goods_count),String.valueOf(data.getProduct_total_count())));
        mBinding.ivAllSelected.setSelected(data.isAllSelected());
    }

    @Override
    public void initDatas() {

    }

    @Subscribe
    public void refreshCarts(CartEvent event) {
        mViewModel.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            helper.getView(R.id.tvDelete).setOnClickListener(view -> {
                if (mAddOrSubListener != null) {
                    mAddOrSubListener.onDelete(item.getProduct_sku_id());
                }
            });
            helper.addOnClickListener(R.id.clCartGoods);
            ViewUtil.setTextViewDeleteLine(helper.getView(R.id.tvOriginPrice));
        }

        private String getAllIds() {
            StringBuilder idsSb = new StringBuilder();
            List<CartData.CartItemListBean> data = getData();
            for (int i = 0; i < data.size(); i++) {
                CartData.CartItemListBean cartItemListBean = data.get(i);
                idsSb.append(cartItemListBean.getProduct_sku_id()).append(",");
            }
            return idsSb.toString();
        }

        private boolean allSelectedOrNot() {
            boolean isAllSelect = true;
            List<CartData.CartItemListBean> data = getData();
            for (CartData.CartItemListBean datum : data) {
                if (datum.getCheck() == 0) {
                    isAllSelect = false;
                    break;
                }
            }
            return isAllSelect;
        }

        private AddOrSubListener mAddOrSubListener;

        public void setAddOrSubListener(AddOrSubListener addOrSubListener) {
            mAddOrSubListener = addOrSubListener;
        }

        public interface AddOrSubListener {
            void onAdd(int product_sku_id);

            void onSub(int product_sku_id);

            void onDelete(int product_sku_id);
        }

    }
}
