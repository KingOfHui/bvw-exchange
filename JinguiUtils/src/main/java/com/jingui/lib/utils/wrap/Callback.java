package com.jingui.lib.utils.wrap;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/18 14:30
 */
public interface Callback<P, R> {
	R callback(P params);
}
