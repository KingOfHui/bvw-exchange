package com.darknet.bvw.util;

import android.text.TextUtils;


import com.darknet.bvw.MyApp;
import com.darknet.bvw.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class ValueUtil {
    private static DecimalFormat sPriceFormat = new DecimalFormat();
    static {
        sPriceFormat.setMaximumFractionDigits(2);
        sPriceFormat.setMinimumFractionDigits(0);
    }

    private ValueUtil() {}

    public static String stripTrailingZeros(BigDecimal bigDecimal) {
        return bigDecimal.compareTo(BigDecimal.ZERO) == 0 ?
                "0" : bigDecimal.stripTrailingZeros().toPlainString();
    }

    public static String formatCustomPrice(String currency, BigDecimal bigDecimal) {
        return String.format("%1$s%2$s",
                currency, sPriceFormat.format(bigDecimal));
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    public static boolean isIntegerValue(BigDecimal bigDecimal) {
        return bigDecimal.signum() == 0 || bigDecimal.scale() <= 0
                || bigDecimal.stripTrailingZeros().scale() <= 0;
    }

    public static int getFractionLength(BigDecimal bigDecimal) {
        String value = stripTrailingZeros(bigDecimal);
        int dotPosition = value.indexOf('.');
        return dotPosition == -1 ? 0 : value.length() - 1 - dotPosition;
    }

    public static boolean compare(BigDecimal one , BigDecimal tow){
        return one.compareTo(tow)==0;
    }

}
