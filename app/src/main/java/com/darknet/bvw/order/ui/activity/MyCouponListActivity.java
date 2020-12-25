package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityMyCouponListBinding;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.ui.adapter.MyCouponAdapter;
import com.darknet.bvw.order.vm.MyCouponViewModel;
import com.darknet.bvw.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @ClassName CouponListActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 10:29
 */
public class MyCouponListActivity extends BaseBindingActivity<ActivityMyCouponListBinding> {
    public static void start(Context context) {
        context.startActivity(new Intent(context, MyCouponListActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon_list;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        mBinding.layoutTitle.title.setText(getString(R.string.my_cash_coupon));
        MyCouponAdapter myCouponAdapter = new MyCouponAdapter();
        myCouponAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyCouponBean item = myCouponAdapter.getItem(position);
                if (item != null) {
                    EventBus.getDefault().post(item);
                    finish();
                }
            }
        });
        mBinding.setAdapter(myCouponAdapter);
    }

    @Override
    public void initDatas() {
        MyCouponViewModel viewModel = getViewModel(MyCouponViewModel.class);
        mBinding.setVm(viewModel);
        viewModel.refresh();
    }
}
