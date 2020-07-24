package com.darknet.bvw.util;

import android.content.Context;

import com.darknet.bvw.util.language.SPUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    //获取当前日期
    public static String currentDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    //获取当前日期
    public static String currentDayDetail() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    //获取当前日期
    public static String currentChatDayDetail() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //返回yyyy-MM-dd hh:mm:ss
    public static String timeConvert(long timeData) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new Date(timeData));
        return date;
    }

    //返回yyyy-MM-dd
    public static String timeConverDate(long timeData) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date2 = sdf2.format(new Date(timeData));
        return date2;
    }


    //返回yyyy-MM-dd
    public static String timeConverTimes(long timeData) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = sdf2.format(new Date(timeData));
        return date2;
    }


    //返回yyyy-MM-dd hh:mm:ss
    public static String hourTime(long timeData) {

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    //返回yyyy-MM-dd
    public static String getCurrentDay() {
        Date currentTime = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date2 = sdf2.format(currentTime);
        return date2;
    }


    public static String getYesDay() {
        Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = simpleDateFormat.format(today);//获取昨天日期
        return yesterday;
    }


    public static String getStringToDate(String s, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        try {
            Date date = fmt.parse(s);
            return fmt.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取昨天的时间
     *
     * @param formate
     * @return
     */
    public static String getZuoTian(String formate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(formate).format(cal.getTime());
    }


    public static String getYHDVal(String timeVal) {

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = df.parse(timeVal);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) + 1;
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.get(Calendar.HOUR_OF_DAY);
            calendar.get(Calendar.MINUTE);
            calendar.get(Calendar.SECOND);
            return y + "-" + m + "-" + d;
        } catch (Exception e) {
            return "";
        }
    }


    public static String getDayDayVal(Context context, String timeVal) {

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = df.parse(timeVal);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) + 1;
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.get(Calendar.HOUR_OF_DAY);
            calendar.get(Calendar.MINUTE);
            calendar.get(Calendar.SECOND);

            int lanType = SPUtil.getInstance(context).getSelectLanguage();
            if (lanType == 1) {
                return m + "月" + d + "日";
            }else {
                return m + " month " + d + " day ";
            }
        } catch (Exception e) {
            return "";
        }
    }

}
