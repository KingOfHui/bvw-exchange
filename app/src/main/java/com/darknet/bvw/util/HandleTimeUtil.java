package com.darknet.bvw.util;

import android.content.Context;

import com.darknet.bvw.util.language.SPUtil;

import java.util.Calendar;

public class HandleTimeUtil {


    // lantype = 0中文，1英文
    public static String backTimeVal(Context context) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
//        int year = cal.get(Calendar.YEAR);
//        int dow = cal.get(Calendar.DAY_OF_WEEK);
//        int dom = cal.get(Calendar.DAY_OF_MONTH);
//        int doy = cal.get(Calendar.DAY_OF_YEAR);

        System.out.println("Current Date: " + cal.getTime());
        System.out.println("Day: " + day);
        System.out.println("Month: " + month);
//        System.out.println("Year: " + year);
//        System.out.println("Day of Week: " + dow);
//        System.out.println("Day of Month: " + dom);
//        System.out.println("Day of Year: " + doy);

        int lanType = SPUtil.getInstance(context).getSelectLanguage();
        if (lanType == 1) {
            return month + "月" + day + "号";
            //中文
        } else {
            //英文
            return moth(month) + " " + day + ".";
        }

    }


    public static String moth(int mothVal) {

        String backVal = "Jan";

        if (mothVal == 1) {
            backVal = "Jan";
        } else if (mothVal == 2) {
            backVal = "Feb";
        } else if (mothVal == 3) {
            backVal = "Mar";
        } else if (mothVal == 4) {
            backVal = "Apr";
        } else if (mothVal == 5) {
            backVal = "May";
        } else if (mothVal == 6) {
            backVal = "Jun";
        } else if (mothVal == 7) {
            backVal = "Jul";
        } else if (mothVal == 8) {
            backVal = "Aug";
        } else if (mothVal == 9) {
            backVal = "Sep";
        } else if (mothVal == 10) {
            backVal = "Oct";
        } else if (mothVal == 11) {
            backVal = "Nov";
        } else if (mothVal == 12) {
            backVal = "Dec";
        }
        return backVal;
    }


}
