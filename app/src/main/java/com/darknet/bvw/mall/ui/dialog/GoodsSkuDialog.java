package com.darknet.bvw.mall.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.darknet.bvw.R;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.view.CustomCarCounterView;
import com.darknet.bvw.view.dialog.BottomDialog;

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
        CustomCarCounterView numberView = findViewById(R.id.numberView);
        TextView tvSure = findViewById(R.id.tvSure);
        TextView tvStock = findViewById(R.id.tvStockNum);
        numberView.setMinCount(BigDecimal.ONE);

        if (mGoodsDetailBean != null) {
            int stock = mGoodsDetailBean.getStock();
            numberView.setMaxCount(BigDecimal.valueOf(stock));
            tvStock.setText(String.format(getContext().getString(R.string.goods_stock),String.valueOf(stock)));
            List<GoodsDetailBean.SkuListBean> sku_list = mGoodsDetailBean.getSku_list();
            tvGoodsName.setText(mGoodsDetailBean.getName());
            mSelectSkuBean = sku_list.get(0);
            tvPrice.setText(sku_list.get(0).getPrice());
            tvSelectSku.setText(String.format(getContext().getString(R.string.select_sku),mGoodsDetailBean.getName(),mGoodsDetailBean.getSku_list().get(0).getSp1()));
            Glide.with(ivGoods.getContext())
                    .load(mGoodsDetailBean.getImg_url())
                    .apply(RequestOptions.centerCropTransform())
                    .into(ivGoods);
        }
        if (mSelectSkuBean != null) {
            numberView.setNumber(BigDecimal.valueOf(mSelectSkuBean.getQuantity()));
            // TODO: 2020/12/23 反显
        }

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal number = numberView.getNumber();
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

    private OnSelectSkuListener mSkuListener;

    public void setSkuListener(OnSelectSkuListener skuListener) {
        mSkuListener = skuListener;
    }

    public interface OnSelectSkuListener{
        void select(GoodsDetailBean.SkuListBean skuListBean);
    }
}
