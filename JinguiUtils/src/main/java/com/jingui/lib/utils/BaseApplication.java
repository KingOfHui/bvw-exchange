package com.jingui.lib.utils;

import android.app.Application;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/18 13:13
 * <br>desc: TODO
 */
public class BaseApplication {

	private static Application INSTANCE;
	private BaseApplication(){}

	public static void setInstance(Application application){
		INSTANCE = application;
	}
	public static Application instance() {
		return null;
	}
}
