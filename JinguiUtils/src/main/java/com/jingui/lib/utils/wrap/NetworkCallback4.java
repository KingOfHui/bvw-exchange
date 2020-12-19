package com.jingui.lib.utils.wrap;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/19 01:06
 */
public interface NetworkCallback4<P1, P2, P3, P4> {
	void onSuccess(P1 param1, P2 param2, P3 param3, P4 param4);
	void onFailed(int code, String message);
}