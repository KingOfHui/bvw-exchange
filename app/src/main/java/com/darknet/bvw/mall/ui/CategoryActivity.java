package com.darknet.bvw.mall.ui;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseActivity;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/18 12:50
 * <br>desc: TODO
 */
public class CategoryActivity extends BaseActivity {

	public static void start(Context context) {
	    Intent starter = new Intent(context, CategoryActivity.class);
	    context.startActivity(starter);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_category;
	}

	@Override
	public void initView() {

	}

	@Override
	public void initDatas() {

	}
}
