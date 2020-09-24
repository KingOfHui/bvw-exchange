package com.darknet.bvw.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/17 19:24
 * <br>desc: TODO
 */
public abstract class BaseDialog extends Dialog {

	public BaseDialog(@NonNull Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(true);
		Window window = getWindow();
		if(window != null) {
			WindowManager.LayoutParams params = window.getAttributes();
			params.gravity = Gravity.CENTER;
			window.setAttributes(params);
			window.getDecorView().setPadding(0, 0, 0, 0);
			window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
			window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			configAttrs(params, window);
		}
	}

	public void configAttrs(WindowManager.LayoutParams params, Window window) {
	}
}
