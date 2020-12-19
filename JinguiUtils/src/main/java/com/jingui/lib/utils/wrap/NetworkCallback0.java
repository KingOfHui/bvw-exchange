package com.jingui.lib.utils.wrap;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/18 20:13
 * <br>desc: 网络请求回调类
 */
public interface NetworkCallback0 {
	void onSuccess();
	void onFailed(int code, String message);
}
