package com.darknet.bvw.mall.ui;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseActivity;

/**
 * Created by guoshiwen on 2020/12/22.
 */
public class DetailActivity extends BaseActivity {

	public static void start(Context context) {
	    Intent starter = new Intent(context, DetailActivity.class);
	    context.startActivity(starter);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_goods_detail_2;
	}

	@Override
	public void initView() {

	}

	@Override
	public boolean immerse() {
		return true;
	}

	@Override
	public void initDatas() {

	}
}