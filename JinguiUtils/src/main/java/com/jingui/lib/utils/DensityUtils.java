package com.jingui.lib.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/17 12:40
 * <br>desc: TODO
 */
public class DensityUtils {

	public static final int DP1 = dip2px(1);

	public static final int DP5 = dip2px(5);

	public static final int DP10 = dip2px(10);

	public static final int DP20 = dip2px(20);

	/**
	 * 获取状态栏高度
	 */
	public static int getStatusBarHeight() {

		try {
//			int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//			int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
//			return statusBarHeight;
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object o = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = (Integer) field.get(o);
			return BaseApplication.instance().getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dip2px(17);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(float dpValue) {
		float scale = BaseApplication.instance().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(float pxValue) {
		final float scale = BaseApplication.instance().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(float spValue) {
		final float fontScale = BaseApplication.instance().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int getScreenHeight(){
		return BaseApplication.instance().getResources().getDisplayMetrics().heightPixels;
	}
	public static int getScreenWidth(){
		return BaseApplication.instance().getResources().getDisplayMetrics().widthPixels;
	}
}
