package com.jingui.lib.utils.wrap;


import java.util.List;

import androidx.annotation.NonNull;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/28 23:52
 * 辅助 {@link com.jingui.lib.utils.DataUtil#getMax(List, FindMaxComparator)}  比较, 获得两个数据中的最大值
 * 值为 a - b 的值
 */
public abstract class FindMaxComparator<T> {

	@NonNull
	public abstract Number provideCompareField(@NonNull T target);

	public final double leftSubtractRight(T left, T right) {
		Number l = 0;
		Number r = 0;
		if(left != null) provideCompareField(left);
		if(right != null) provideCompareField(right);
		return l.doubleValue() - r.doubleValue();
	}
}
