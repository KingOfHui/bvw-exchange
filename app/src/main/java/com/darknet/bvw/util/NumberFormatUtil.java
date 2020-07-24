package com.darknet.bvw.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author lijingya
 * @description 数字的转化工具
 * @email lijingya@91118.com
 * @createDate 2020/1/14
 * @company 杭州天音
 */
public class NumberFormatUtil {

    /**
     * 保留几位小数
     *
     * @param format
     * @param f
     */
    public static String getNumber(String format, double f) {
        DecimalFormat df = new DecimalFormat(format);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(f);
    }

}
