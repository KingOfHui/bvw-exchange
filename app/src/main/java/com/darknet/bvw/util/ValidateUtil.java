package com.darknet.bvw.util;

import java.util.Collection;
import java.util.stream.Stream;

public class ValidateUtil {

	/**
	 * 判断字符串的有效性 
	 */
	public static boolean isValid(String str){
		return str != null && !"".equals(str.trim());
	}

	/**
	 * 判断字符串的有效性   多个字符串
	 */
	public static boolean isValid(String... s){
		return !Stream.of(s).anyMatch(str->!isValid(str));
	}
	
	/**
	 * 判断集合有效性
	 */
	public static boolean isValid(Collection col){
		return col !=null && !col.isEmpty();
	}

	/**
	 * 判断是否数组有效
	 */
	public static boolean isValid(Object[] arr) {
		return arr != null && arr.length != 0;
	}
	
}
