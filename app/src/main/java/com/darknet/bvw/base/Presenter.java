package com.darknet.bvw.base;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/21 16:19
 * <br>desc: TODO
 */
public abstract class Presenter<V> {

	protected V mView;
	public Presenter(V v) {
		mView = v;
	}
}
