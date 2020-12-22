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
import com.darknet.bvw.mall.vm.GoodsDetailViewModel;
import com.darknet.bvw.util.GlideImageLoader;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.util.ToastUtils;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.Arrays;
import java.util.List;

public class GoodsDetailActivity extends BaseBindingActivity<ActivityGoodsDetailBinding> {
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

        mBinding.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDetailBean detailResp = viewModel.productDetailLive.getValue();
                if (detailResp != null) {
                    viewModel.addToCart(237, 3);
                }
            }
        });
        viewModel.productDetailLive.observe(this, new Observer<GoodsDetailBean>() {
            @Override
            public void onChanged(GoodsDetailBean productDetailResp) {
                if (productDetailResp != null) {
                    String img_url_list = productDetailResp.getImg_url_list();
                    String[] split = img_url_list.split(",");
                    List<String> imageList = Arrays.asList(split);
                    mBinding.banner.setImageLoader(new GlideImageLoader());
                    mBinding.banner.setImages(imageList);
                    mBinding.banner.start();
                    mBinding.banner.setDelayTime(200000);
                    mBinding.banner.stopAutoPlay();
                    mBinding.tvGoodsName.setText(productDetailResp.getName());
                    mBinding.tvSales.setText(String.format("月销 %s", productDetailResp.getSale()));
                    mBinding.tvPrice.setText(SpanHelper.start()
                            .next("USDT")
                            .setTextColor(Color.parseColor("#01FCDA"))
                            .setTextSize(14)
                            .next(" ")
                            .next(productDetailResp.getOriginal_price())
                            .setTextSize(16)
                            .get());
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
