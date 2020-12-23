package com.darknet.bvw.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityGoodsDetailBinding;
import com.darknet.bvw.mall.bean.GoodsBannerBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.ui.dialog.GoodsSkuDialog;
import com.darknet.bvw.mall.vm.GoodsDetailViewModel;
import com.darknet.bvw.order.ui.activity.CartActivity;
import com.darknet.bvw.util.GlideImageLoader;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.util.ToastUtils;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

public class GoodsDetailActivity extends BaseBindingActivity<ActivityGoodsDetailBinding> {

    private GoodsDetailBean.SkuListBean mSelectSkuListBean;

    public static void start(Context context, int product_id) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("product_id", product_id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {
        int product_id = getIntent().getIntExtra("product_id", 0);
        GoodsDetailViewModel viewModel = getViewModel(GoodsDetailViewModel.class);
        viewModel.getGoodsDetail(product_id);
        mBinding.banner.setIndicatorGravity(Gravity.BOTTOM);
        mBinding.ivBack.setOnClickListener(v -> finish());
        mBinding.tvToCart.setOnClickListener(v -> CartActivity.start(this));
        mBinding.tvAddToCart.setOnClickListener(view -> {
            if (mSelectSkuListBean != null) {
                viewModel.addToCart(mSelectSkuListBean.getId(), mSelectSkuListBean.getQuantity());
            } else {
                ToastUtils.showToast(getString(R.string.please_select_sku));
            }
        });
        mBinding.tvSelectSkuTip.setOnClickListener(v -> {
            GoodsDetailBean value = viewModel.productDetailLive.getValue();
            if (value != null) {
                List<GoodsDetailBean.SkuListBean> sku_list = value.getSku_list();
                if (CollectionUtil.isNotEmpty(sku_list)) {
                    GoodsSkuDialog skuDialog = new GoodsSkuDialog(this, value, mSelectSkuListBean);
                    skuDialog.setSkuListener(new GoodsSkuDialog.OnSelectSkuListener() {
                        @Override
                        public void select(GoodsDetailBean.SkuListBean skuListBean) {
                            mSelectSkuListBean = skuListBean;
                            mBinding.tvSelectSku.setText(String.format(getString(R.string.select_goods_sku), skuListBean.getSp1()));
                        }
                    });
                    skuDialog.show();
                }
            }
        });
        viewModel.productDetailLive.observe(this, productDetailResp -> {
            if (productDetailResp != null) {
                String img_url_list = productDetailResp.getImg_url_list();
                String[] split = img_url_list.split(",");
                List<String> imageList = Arrays.asList(split);
                mBinding.banner.setImageLoader(new GlideImageLoader());
                mBinding.banner.setImages(imageList);
                mBinding.banner.start();
                mBinding.banner.setDelayTime(20000);
                mBinding.banner.stopAutoPlay();
                mBinding.tvGoodsName.setText(productDetailResp.getName());
                mBinding.tvSales.setText(String.format(getString(R.string.monthly_sales), "" + productDetailResp.getSale()));
                mBinding.tvPrice.setText(SpanHelper.start()
                        .next("USDT")
                        .setTextColor(Color.parseColor("#01FCDA"))
                        .setTextSize(14)
                        .next(" ")
                        .next(productDetailResp.getOriginal_price())
                        .setTextSize(16)
                        .get());
                mBinding.webview.loadData(productDetailResp.getDetail_html(), "", "");

                List<GoodsDetailBean.SkuListBean> sku_list = productDetailResp.getSku_list();
                mBinding.tvSelectSku.setVisibility(CollectionUtil.isEmpty(sku_list) ? View.GONE : View.VISIBLE);
                mBinding.tvSelectSkuTip.setVisibility(CollectionUtil.isEmpty(sku_list) ? View.GONE : View.VISIBLE);
                if (CollectionUtil.isNotEmpty(sku_list)) {
                    mBinding.tvSelectSku.setText(String.format(getString(R.string.species_optional_3), sku_list.size() + ""));
                }
                float coupon_discount = productDetailResp.getCoupon_discount();
                int sale = productDetailResp.getSale();
                mBinding.tvSales.setText(String.format(getString(R.string.monthly_sales), sale + ""));
                if (coupon_discount > 0) {
                    mBinding.tvCanUsedDiscounts.setText(String.format(getString(R.string.discounts), coupon_discount + ""));
                    mBinding.groupDiscounts.setVisibility(View.VISIBLE);
//                    mBinding.tvCanUsedDiscountsTip.setVisibility(View.VISIBLE);
//                    mBinding.ivDiscountsMore.setVisibility(View.VISIBLE);
                } else {
                    mBinding.groupDiscounts.setVisibility(View.GONE);
//                    mBinding.tvCanUsedDiscountsTip.setVisibility(View.GONE);
//                    mBinding.ivDiscountsMore.setVisibility(View.GONE);
                }
            }
        });
        viewModel.isAddSuccessLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ToastUtils.showToast(getString(R.string.add_to_cart_success));
                    finish();
                }
            }
        });
    }

    @Override
    public void initDatas() {

    }
}
