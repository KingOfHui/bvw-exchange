package com.darknet.bvw.util;

import java.text.SimpleDateFormat;
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
    public static String timeConvert(long timeData){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new Date(timeData));
        return date;
    }

    //返回yyyy-MM-dd
    public static String timeConverDate(long timeData){
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date2 = sdf2.format(new Date(timeData));
        return date2;
    }


    //返回yyyy-MM-dd
    public static String timeConverTimes(long timeData){
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = sdf2.format(new Date(timeData));
        return date2;
    }


    //返回yyyy-MM-dd hh:mm:ss
    public static String hourTime(long timeData){

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    //返回yyyy-MM-dd
    public static String getCurrentDay(){
        Date currentTime = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date2 = sdf2.format(currentTime);
        return date2;
    }


    public static String getYesDay(){
        Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = simpleDateFormat.format(today);//获取昨天日期
        return yesterday;
    }





}
