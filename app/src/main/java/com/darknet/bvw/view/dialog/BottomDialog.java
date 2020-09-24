package com.darknet.bvw.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/8 21:29
 * <br>desc: 位于屏幕底部的弹窗
 */
public class BottomDialog extends BaseDialog{

	public BottomDialog(@NonNull Context context) {
		super(context);
	}

	@Override
	public void configAttrs(WindowManager.LayoutParams params, Window window) {
		super.configAttrs(params, window);
		params.gravity = Gravity.BOTTOM;
	}
}
