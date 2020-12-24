package com.darknet.bvw.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.jingui.lib.utils.DensityUtils;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/8 21:29
 * <br>desc: 位于屏幕中间的弹窗
 */
public class CenterBaseDialog extends BaseDialog{

	public CenterBaseDialog(@NonNull Context context) {
		super(context);
	}

	@Override
	public void configAttrs(WindowManager.LayoutParams params, Window window) {
		params.gravity = Gravity.CENTER;
		window.setLayout(DensityUtils.getScreenWidth() * 3 / 4, WindowManager.LayoutParams.WRAP_CONTENT);
	}
}
