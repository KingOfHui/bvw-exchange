package com.darknet.bvw.commonlib.util;

import java.text.NumberFormat;

public class StringUtil {
    /**
     * 判断是否为空字符串最优代码
     *
     * @param str
     * @return 如果为空，则返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || "".equals(str);
    }

    /**
     * 获取保留几位小数
     *
     * @param value
     * @param index
     * @return
     */
    public static String getNumberDecimal(double value, int index) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(index);
        return percentInstance.format(value);
    }

    /**
     * 字符串数字转化成333,333类型
     *
     * @param data
     * @return
     */
    public static String formatString(String data) {
        StringBuffer str = new StringBuffer(data);
        //从后往前每隔三位添加逗号
        for (int i = str.length() - 3; i > 0; i -= 3) {
            str.insert(i, ",");
        }
        return str.toString();
    }

    /**
     * 数字转化成k，M（1，000，000），B（1，000，000，000）
     *
     * @param value
     * @return
     */
//    public static String numberToString(String value) {
////        String[] str = value.split(".");
////        String str1 = str[0];
////        if (str1.length() > 3) {
////
////        }
//    }
}
