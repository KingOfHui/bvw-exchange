package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.databinding.ActivityAddMineralBinding;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.util.ClipBoardHelper;

public class AddMineralActivity extends BaseBindingActivity<ActivityAddMineralBinding> {

    public static void startSelf(Context context, MineralListResponse.ItemsBean.MinerInfoBean itemInfo) {
        Intent intent = new Intent(context, AddMineralActivity.class);
        intent.putExtra("model", itemInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_mineral;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener(v -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.mineral));
    }

    private void clickCopy(String text) {
        ClipBoardHelper.clickToCopy(this, text);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        MineralListResponse.ItemsBean.MinerInfoBean infoBean = (MineralListResponse.ItemsBean.MinerInfoBean) intent.getSerializableExtra("model");
        mBinding.setModel(infoBean);
        mBinding.etUrl1.setOnClickListener(view -> clickCopy(infoBean.getUrl_1()));
        mBinding.etUrl2.setOnClickListener(view -> clickCopy(infoBean.getUrl_2()));
        mBinding.etUrl3.setOnClickListener(view -> clickCopy(infoBean.getUrl_3()));
    }
}
