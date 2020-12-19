package com.jingui.lib.utils.wrap;


import java.util.Comparator;

import androidx.annotation.NonNull;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/3 00:43
 * <br>desc: 用于排序, 从大到小排列
 */
public abstract class BigToSmallComparator<T> implements Comparator<T> {

	/**
	 * 提供进行比较的字段
	 * @param target 进行比较的对象
	 * @return Int/Long/Float/Double类型, 用于进行比较
	 */
	@NonNull
	public abstract Number provideCompareField(@NonNull T target);

	@Override
	public final int compare(T o1, T o2) {
		Number number1 = 0;
		Number number2 = 0;
		if(o1 != null){
			number1 = provideCompareField(o1);
		}
		if(o2 != null){
			number2 = provideCompareField(o2);
		}
		double temp = number1.doubleValue() - number2.doubleValue();
		if (temp > 0) return -1;
		if (temp < 0) return 1;
		return 0;
	}
}
