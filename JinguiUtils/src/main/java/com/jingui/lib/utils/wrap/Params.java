package com.jingui.lib.utils.wrap;

/**
 * Created by guoshiwen on 2020/5/27.
 * 用于解决 RxJava 只能传递一个参数的问题
 * Params 可传两个参数
 * Params3 可传三个参数
 * Params4 可传四个参数
 */
public class Params<T1, T2> {

	public final T1 first;
	public final T2 second;

	public Params(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
}
