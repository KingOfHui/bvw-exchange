package com.jingui.lib.utils;


import com.jingui.lib.utils.wrap.FindMaxComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;

public class DataUtil {

	// 如果num=96.0 toString则为 "96" 而非 "96.0"
	public static String floatToString(float num) {
		if (num % 1 == 0) {
			return String.valueOf((int) num);
		}
		return String.valueOf(num);
	}

	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}

	//是否全部为空
	public static boolean isAllEmpty(Object[] objects) {
		if (isEmpty(objects))
			return true;
		for (Object object : objects) {
			if (object != null)
				return false;
		}
		return true;
	}

	//是否全部非空
	public static boolean isAllNotEmpty(Object[] objects) {
		if (isEmpty(objects))
			return false;
		for (Object object : objects) {
			if (object == null)
				return false;
		}
		return true;
	}

	//移除数组中所有的空元素
	public static <T> T[] removeAllEmptyElement(T[] objects, @NonNull ArrayFactory<T> arrayCreator) {
		if (objects == null)
			return arrayCreator.createArray(0);
		if (objects.length == 0)
			return objects;
		List<T> list = new ArrayList<>();
		for (T object : objects) {
			if (isEmpty(objects))
				continue;
			try {
				list.add(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list.toArray(arrayCreator.createArray(list.size()));
	}

	public static <T> List<T> createList(int size, @NonNull ObjectFactory<T> factory) {
		List<T> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(factory.create());
		}
		return list;
	}

	public static <T> T getMax(List<T> list, FindMaxComparator<T> comparator){
		if(isEmpty(list)) return null;
		T max = null;
		for (T t : list) {
			if(max == null){
				max = t;
			}else {
				double result = comparator.leftSubtractRight(max, t);
				if(result < 0) max = t;
			}
		}
		return max;
	}

	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}

	public static <T> List<T> getEmptyList() {
		return new ArrayList<>();
	}

	public static int getSize(Collection collection) {
		if (isEmpty(collection))
			return 0;
		return collection.size();
	}

	public static int getSize(Object[] filters) {
		if (filters == null) return 0;
		return filters.length;
	}

	public interface ArrayFactory<T> {
		T[] createArray(int length);
	}

	public interface ObjectFactory<T> {
		T create();
	}
}