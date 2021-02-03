package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityAboutBtdBinding;
import com.darknet.bvw.util.StatusBarUtil;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AboutBTDActivity extends BaseBindingActivity<ActivityAboutBtdBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_about_btd;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText(R.string.online_plan_btd);
        mBinding.titleLayout.title.setOnClickListener(v -> IncomeFundActivity.start(this,""));
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AboutBTDActivity.class));
    }
}
