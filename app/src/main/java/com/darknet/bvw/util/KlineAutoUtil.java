package com.darknet.bvw.util;

import android.util.Log;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.darknet.bvw.model.DateBetween2;
import com.darknet.bvw.model.KLineDataModel;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSONArray;

public class KlineAutoUtil {

    //接口返回的数组是六个值，判 断中间四个是不是 全是0，如果是替换成上一个不为0的收盘价
    public static JSONArray auto(List<KLineDataModel> list, String period) {
        //日期补全
        JSONArray array = new JSONArray();
        KLineDataModel preKline = null;
        KLineDataModel preDataKline = null;
        for (KLineDataModel item : list) {
            if (preKline == null) {
                preKline = item;
            } else {
                //计算上个日期与当前日期相差的天数
                DateTime start = DateUtil.date(preKline.getTime());
                DateTime end = DateUtil.date(item.getTime());
                long between = getDateBetween(start.toJdkDate(), end.toJdkDate(), period);
                Log.d("空缺数量 {}", between + "");
                if (between == 0) {
                    //同一天不需要重复加入
                    preKline = item;
                    continue;
                } else if (between > 1) {
                    //有空缺天数
                    for (int i = 1; i < between; i++) {
                        Date date = getAutoDate(start, period, i);
                        JSONArray group = fillGroup(preKline, date.getTime());
                        if (preKline.getHigh() == 0
                                && preKline.getLow() == 0
                                && preKline.getOpen() == 0
                                && preKline.getClose() == 0) {
                            //如果全是0 取上一个不为0的数据
                            group = fillGroup(preDataKline, date.getTime());
                        }
                        array.add(group);
                        Log.d("补全空缺 {}", DateUtil.formatDateTime(date));
                    }
                }
            }

            JSONArray group = fillGroup(item);
            if (item.getHigh() != 0
                    || item.getLow() != 0
                    || item.getOpen() != 0
                    || item.getClose() != 0) {
                preDataKline = item;
            } else {
                //如果全是0 取上一个不为0的数据
                group = fillGroup(preDataKline, item.getTime());
            }
            //正常加入
            array.add(group);
            preKline = item;

            Log.d("正常加入 {}", DateUtil.formatDateTime(DateUtil.date(item.getTime())));
        }
        return array;
    }

    public static Date getAutoDate(Date start, String period, int offset) {
        if (period.contains("1min")) {
            return DateUtil.offsetMinute(start, offset);
        } else if (period.contains("5min")) {
            return DateUtil.offsetMinute(start, offset * 5);
        } else if (period.contains("10min")) {
            return DateUtil.offsetMinute(start, offset * 10);
        } else if (period.contains("15min")) {
            return DateUtil.offsetMinute(start, offset * 15);
        } else if (period.contains("30min")) {
            return DateUtil.offsetMinute(start, offset * 30);
        } else if (period.contains("1hour")) {
            return DateUtil.offsetHour(start, offset);
        } else if (period.contains("1day")) {
            return DateUtil.offsetDay(start, offset);
        } else if (period.contains("1week")) {
            return DateUtil.offsetWeek(start, offset);
        } else if (period.contains("1month")) {
            return DateUtil.offsetMonth(start, offset);
        }
        return new Date();
    }

    /**
     * 生成1分钟k线 生成5分钟k线 生成10分钟k线 生成15分钟k线 生成30分钟k线 生成1小时K线 生成日K线 生成星期K线 生成月K线
     */
    public static long getDateBetween(Date start, Date end, String period) {
        if (period.contains("1min")) {
            return new DateBetween2(start, end).between(1);
        } else if (period.contains("15min")) {
            return new DateBetween2(start, end).between(15);
        } else if (period.contains("10min")) {
            return new DateBetween2(start, end).between(10);
        } else if (period.contains("5min")) {
            return new DateBetween2(start, end).between(5);
        } else if (period.contains("30min")) {
            return new DateBetween2(start, end).between(30);
        } else if (period.contains("1hour")) {
            return new DateBetween2(start, end).between(60);
        } else if (period.contains("1day")) {
            return DateUtil.betweenDay(start, end, true);
        } else if (period.contains("1week")) {
            return DateUtil.between(start, end, DateUnit.WEEK, true);
        } else if (period.contains("1month")) {
            return DateUtil.betweenMonth(start, end, true);
        }
        return 0;
    }

    public static JSONArray fillGroup(KLineDataModel item) {
        JSONArray group = new JSONArray();
        group.add(0, item.getTime()); // 时间
        group.add(1, item.getOpen()); // 开盘价
        group.add(2, item.getHigh()); // 最高价
        group.add(3, item.getLow()); // 最低价
        group.add(4, item.getClose()); // 最新价
        group.add(5, item.getVolume()); // 成交量
        return group;
    }

    public static JSONArray fillGroup(KLineDataModel item, long time) {
        JSONArray group = new JSONArray();
        if (item == null) {
            group.add(0, time); // 时间
            group.add(1, BigDecimal.ZERO); // 开盘价
            group.add(2, BigDecimal.ZERO); // 最高价
            group.add(3, BigDecimal.ZERO); // 最低价
            group.add(4, BigDecimal.ZERO); // 最新价
            group.add(5, BigDecimal.ZERO); // 成交量
        } else {
//            group.add(0, time); // 时间
//            group.add(1, item.getOpenPrice()); // 开盘价
//            group.add(2, item.getHighestPrice()); // 最高价
//            group.add(3, item.getLowestPrice()); // 最低价
//            group.add(4, item.getClosePrice()); // 最新价
//            group.add(5, BigDecimal.ZERO); // 成交量

            group.add(0, time); // 时间
            group.add(1, item.getOpen()); // 开盘价
            group.add(2, item.getHigh()); // 最高价
            group.add(3, item.getLow()); // 最低价
            group.add(4, item.getClose()); // 最新价
            group.add(5, BigDecimal.ZERO); // 成交量
        }

        return group;
    }
}
