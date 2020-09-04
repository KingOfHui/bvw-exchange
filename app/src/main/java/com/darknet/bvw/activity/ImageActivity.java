package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.darknet.bvw.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

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
	protected void onCreate(Bundle savedInstanceState) {
		QMUIStatusBarHelper.isFullScreen(this);
		if (Build.VERSION.SDK_INT >= 21) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(Color.parseColor("#000000"));
		}
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
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
