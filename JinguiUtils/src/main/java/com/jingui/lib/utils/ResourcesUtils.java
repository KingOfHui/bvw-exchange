package com.jingui.lib.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/19 00:13
 * <br>desc: 用于获取xml资源
 */
public class ResourcesUtils {

	public static String getString(@StringRes int resId){
		return BaseApplication.instance().getResources().getString(resId);
	}

	public static float getDimension(@DimenRes int resId){
		return BaseApplication.instance().getResources().getDimension(resId);
	}

	public static Drawable getDrawable(@DrawableRes int resId){
		return BaseApplication.instance().getResources().getDrawable(resId);
	}

	public static int getColor(@ColorRes int resId){
		return BaseApplication.instance().getResources().getColor(resId);
	}
}
