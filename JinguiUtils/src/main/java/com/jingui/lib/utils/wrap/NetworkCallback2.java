package com.jingui.lib.utils.wrap;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/19 01:06
 */
public interface NetworkCallback2<P1, P2> {
	void onSuccess(P1 param1, P2 param2);
	void onFailed(int code, String message);
}