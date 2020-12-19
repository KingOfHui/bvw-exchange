package com.jingui.lib.utils.wrap;

/**
 * Created by guoshiwen on 2020/5/27.
 * @see Params
 */
public class Params3<T1, T2, T3> extends Params<T1, T2>{

	public final T3 third;

	public Params3(T1 first, T2 second, T3 third) {
		super(first, second);
		this.third = third;
	}
}
