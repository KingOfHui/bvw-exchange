package com.darknet.bvw.order.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.order.bean.CouponBean;

/**
 * @ClassName CouponAdapter
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 17:20
 */
public class CouponAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {
    public CouponAdapter() {
        super(R.layout.item_coupon);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {

    }
}
