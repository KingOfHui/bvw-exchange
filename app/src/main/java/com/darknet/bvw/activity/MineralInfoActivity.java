package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.darknet.bvw.R;
import com.darknet.bvw.databinding.ActivityMineralInfoBinding;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.model.response.MineralStatusResponse;
import com.darknet.bvw.viewmodel.MineralViewModel;

import java.io.Serializable;

/**
 * @ClassName MineralInfoActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 16:02
 */
public class MineralInfoActivity extends BaseBindingActivity<ActivityMineralInfoBinding> {

    MineralViewModel mViewModel;

    public static void startSelf(Context context, MineralListResponse.ItemsBean itemsBean, MineralStatusResponse value) {
        Intent intent = new Intent(context, MineralInfoActivity.class);
        intent.putExtra("itemInfo", itemsBean);
        intent.putExtra("statusInfo", value);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mineral_info;
    }

    @Override
    public void initView() {
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MineralViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.layoutTitle.layBack.setOnClickListener(v -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.mineral));
        mBinding.layoutTitle.titleRight.setText(getString(R.string.mineral_pool));
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
        mBinding.layoutTitle.titleRight.setOnClickListener(v->AddMineralActivity.startSelf(this));
        mBinding.setLifecycleOwner(this);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        MineralListResponse.ItemsBean itemInfo = (MineralListResponse.ItemsBean) intent.getSerializableExtra("itemInfo");
        MineralStatusResponse statusInfo = (MineralStatusResponse) intent.getSerializableExtra("statusInfo");
        mBinding.setInfo(itemInfo);
        mViewModel.getMineralStatusResponseLiveData().setValue(statusInfo);
    }
}
