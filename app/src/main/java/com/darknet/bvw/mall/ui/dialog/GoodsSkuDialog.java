package com.darknet.bvw.mall.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.darknet.bvw.R;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.view.CornerTextView;
import com.darknet.bvw.view.CustomCarCounterView;
import com.darknet.bvw.view.dialog.BottomDialog;
import com.jingui.lib.utils.DensityUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName GoodsSkuDialog
 * @Description
 * @Author dinghui
 * @Date 2020/12/23 0023 13:35
 */
public class GoodsSkuDialog extends BottomDialog {
    private GoodsDetailBean mGoodsDetailBean;
    private GoodsDetailBean.SkuListBean mSelectSkuBean;
    private QMUIFloatLayout mFloatLayout;
    private CustomCarCounterView mNumberView;

    public GoodsSkuDialog(@NonNull Context context, GoodsDetailBean goodsDetailBean, GoodsDetailBean.SkuListBean selectSkuListBean) {
        super(context);
        mGoodsDetailBean = goodsDetailBean;
        mSelectSkuBean = selectSkuListBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goods_sku);
        ImageView ivGoods = findViewById(R.id.ivGoods);
        TextView tvGoodsName = findViewById(R.id.tvGoodsName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvSelectSku = findViewById(R.id.tvSelectSku);
        mNumberView = findViewById(R.id.numberView);
        TextView tvSure = findViewById(R.id.tvSure);
        TextView tvStock = findViewById(R.id.tvStockNum);
        mFloatLayout = findViewById(R.id.flexLayout);
        mNumberView.setMinCount(BigDecimal.ONE);

        if (mGoodsDetailBean != null) {
            int stock = mGoodsDetailBean.getStock();
            mNumberView.setMaxCount(BigDecimal.valueOf(stock));
            tvStock.setText(String.format(getContext().getString(R.string.goods_stock), String.valueOf(stock)));
            List<GoodsDetailBean.SkuListBean> sku_list = mGoodsDetailBean.getSku_list();
            tvGoodsName.setText(mGoodsDetailBean.getName());
            mSelectSkuBean = sku_list.get(0);
            tvPrice.setText(sku_list.get(0).getPrice());
            tvSelectSku.setText(String.format(getContext().getString(R.string.select_sku), mGoodsDetailBean.getName(), mGoodsDetailBean.getSku_list().get(0).getSp1()));
            Glide.with(ivGoods.getContext())
                    .load(mGoodsDetailBean.getImg_url())
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.mipmap.default_item)
                    .into(ivGoods);
            for (GoodsDetailBean.SkuListBean skuListBean : sku_list) {
                addGuiGeToLayout(skuListBean.getSp1(), mSelectSkuBean != null && mSelectSkuBean.getSp1().equals(skuListBean.getSp1()));
                addGuiGeToLayout(skuListBean.getSp1(), false);
            }
        }
        if (mSelectSkuBean != null) {
            mNumberView.setNumber(BigDecimal.valueOf(mSelectSkuBean.getQuantity()));
        }

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal number = mNumberView.getNumber();
                if (number.compareTo(BigDecimal.ONE) >= 0) {
                    if (mSkuListener != null && mSelectSkuBean != null) {
                        mSelectSkuBean.setQuantity(number.intValue());
                        mSkuListener.select(mSelectSkuBean);
                        dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void dismiss() {
        BigDecimal number = mNumberView.getNumber();
        if (number.compareTo(BigDecimal.ONE) >= 0) {
            if (mSkuListener != null && mSelectSkuBean != null) {
                mSelectSkuBean.setQuantity(number.intValue());
                mSkuListener.select(mSelectSkuBean);
            }
        }
        super.dismiss();
    }

    private void addGuiGeToLayout(String sp, boolean isSelect) {
        CornerTextView tv = new CornerTextView(getContext());
        tv.setCorner(DensityUtils.dip2px(2));
        tv.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.selector_coupon_buy));
        tv.setBackground(getContext().getResources().getDrawable(R.drawable.selector_stroke_rect_sku));
        int padding = DensityUtils.dip2px(10);
        tv.setPadding(padding, padding, padding, padding);
        tv.setTextSize(12);
        tv.setSelected(isSelect);
        tv.setText(sp);
        tv.setOnClickListener(v -> {
            int childCount = mFloatLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = mFloatLayout.getChildAt(i);
                if (view == v) {
                    v.setSelected(true);
                } else {
                    v.setSelected(false);
                }
            }
        });
        mFloatLayout.addView(tv);
    }

    private OnSelectSkuListener mSkuListener;

    public void setSkuListener(OnSelectSkuListener skuListener) {
        mSkuListener = skuListener;
    }

    public interface OnSelectSkuListener {
        void select(GoodsDetailBean.SkuListBean skuListBean);
    }
}
