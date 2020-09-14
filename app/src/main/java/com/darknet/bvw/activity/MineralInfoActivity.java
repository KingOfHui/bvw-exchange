package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.databinding.ActivityMineralInfoBinding;
import com.darknet.bvw.model.response.MineralListResponse;

import java.io.Serializable;

/**
 * @ClassName MineralInfoActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 16:02
 */
public class MineralInfoActivity extends BaseBindingActivity<ActivityMineralInfoBinding> {
    public static void startSelf(Context context, MineralListResponse.ItemsBean itemsBean) {
        Intent intent = new Intent(context, MineralInfoActivity.class);
        intent.putExtra("imteInfo", itemsBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mineral_info;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.title.setText("矿机");
        mBinding.layoutTitle.titleRight.setText("矿池");
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        MineralListResponse.ItemsBean itemInfo = (MineralListResponse.ItemsBean) intent.getSerializableExtra("itemInfo");
        mBinding.setInfo(itemInfo);
    }
}
