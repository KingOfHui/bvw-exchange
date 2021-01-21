package com.darknet.bvw.activity.qvkuaibao;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityCoinDetailBinding;
import com.darknet.bvw.databinding.ActivityCoinDetailBindingImpl;
import com.darknet.bvw.order.ui.adapter.MyCouponAdapter;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/01/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CoinDetailActivity extends BaseBindingActivity<ActivityCoinDetailBinding> {
    public static void startSelf(Context context) {
        context.startActivity(new Intent(context, CoinDetailActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coin_detail;
    }

    @Override
    public void initView() {
        mBinding.titleLayout.layBack.setOnClickListener(view -> finish());
        mBinding.titleLayout.title.setText("区块宝");
        mBinding.titleLayout.ivRight.setImageResource(R.mipmap.ic_qvkuaibao_record);
        mBinding.setAdapter(new MyCouponAdapter());

    }

    @Override
    public void initDatas() {

    }
}
