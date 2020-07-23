package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.darknet.bvw.R;

import androidx.annotation.DrawableRes;

/**
 * Created by guoshiwen on 2020/7/24.
 */
public class ImageActivity extends BaseActivity {

	private ImageView mIvImage;
	private ImageView mIvBack;

	public static void start(Context context, @DrawableRes int image) {
	    Intent starter = new Intent(context, ImageActivity.class);
	    starter.putExtra("image", image);
	    context.startActivity(starter);
	}

	@Override
	public void initView() {
		mIvImage = findViewById(R.id.iv_image);
		mIvBack = findViewById(R.id.iv_back);
		mIvBack.setOnClickListener( v -> finish());
		Intent intent = getIntent();
		int image = intent.getIntExtra("image", 0);
		if(image != 0){
			mIvImage.setImageResource(image);
		}
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_image;
	}

	@Override
	public void initToolBar() {

	}

	@Override
	public void initDatas() {

	}

	@Override
	public void configViews() {

	}
}
