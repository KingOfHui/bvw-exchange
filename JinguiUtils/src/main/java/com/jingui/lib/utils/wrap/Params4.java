package com.jingui.lib.utils.wrap;

/**
 * Created by guoshiwen on 2020/5/27.
 * @see Params
 */
public class Params4<T1, T2, T3, T4> extends Params3<T1, T2, T3>{

	public final T4 fourth;

	public Params4(T1 first, T2 second, T3 third, T4 fourth) {
		super(first, second, third);
		this.fourth = fourth;
	}
}
