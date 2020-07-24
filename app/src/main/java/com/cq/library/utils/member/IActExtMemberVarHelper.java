package com.cq.library.utils.member;

import android.app.Activity;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/29 19:40
 */
public interface IActExtMemberVarHelper<A extends Activity> {

	void clear(A activity, String key);
	void set(A activity, String key, Object value);
	<T> T get(A activity, String key, Class<T> type);
}
