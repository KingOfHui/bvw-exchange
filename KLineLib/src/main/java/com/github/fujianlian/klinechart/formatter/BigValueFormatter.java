package com.github.fujianlian.klinechart.formatter;

import com.github.fujianlian.klinechart.base.IValueFormatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * 对较大数据进行格式化 Created by tifezh on 2017/12/13.
 */

public class BigValueFormatter implements IValueFormatter {

    //必须是排好序的
    private int[] values = {10000, 1000000, 100000000};
    private String[] units = {"万", "百万", "亿"};

    @Override
    public String format(float value) {
//        String unit="";
//        int i=values.length-1;
//        while (i>=0)
//        {
//            if(value>values[i]) {
//                value /= values[i];
//                unit = units[i];
//                break;
//            }
//            i--;
//        }
        if (String.valueOf(value).contains("-")) {
            return new BigDecimal(value).setScale(5, BigDecimal.ROUND_DOWN).toPlainString();
        }
        if (value > 1000) {
            DecimalFormat df = new DecimalFormat("#.00");
            double v = value / 1000;
            return df.format(v) + "k";
        }
        return value + "";
//        return String.format(Locale.getDefault(),"%.2f", value)+unit;
    }
}
