package com.darknet.bvw.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.darknet.bvw.R;
import com.darknet.bvw.databinding.ActivityAddMineralBinding;

public class AddMineralActivity extends BaseBindingActivity<ActivityAddMineralBinding> {

    public static void startSelf(Context context) {
        context.startActivity(new Intent(context, AddMineralActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_mineral;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.title.setText(getString(R.string.mineral));
    }

    @Override
    public void initDatas() {

    }
}
