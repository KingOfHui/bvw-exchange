package com.darknet.bvw.util;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public abstract class TimeBigToSmallComparator<T> implements Comparator<T> {

    public abstract String provideCompareField(@NonNull T target);

    @Override
    public int compare(T t, T t1) {
        String s1 = "";
        String s2 = "";
        if (t != null) {
            s1 = provideCompareField(t);
        }
        if (t1 != null) {
            s2 = provideCompareField(t1);
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = sdf1.parse(s1);
            Date date2 = sdf1.parse(s2);
            if (date1.after(date2)) {
                return -1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
