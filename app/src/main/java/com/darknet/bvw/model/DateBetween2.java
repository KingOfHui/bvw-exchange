package com.darknet.bvw.model;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;

import java.util.Calendar;
import java.util.Date;

public class DateBetween2 {

    private static final long serialVersionUID = 1L;
    private Date begin;
    private Date end;

    public static DateBetween2 create(Date begin, Date end) {
        return new DateBetween2(begin, end);
    }

    public static DateBetween2 create(Date begin, Date end, boolean isAbs) {
        return new DateBetween2(begin, end, isAbs);
    }

    public DateBetween2(Date begin, Date end) {
        this(begin, end, true);
    }

    public DateBetween2(Date begin, Date end, boolean isAbs) {
        Assert.notNull(begin, "Begin date is null !", new Object[0]);
        Assert.notNull(end, "End date is null !", new Object[0]);
        if (isAbs && begin.after(end)) {
            this.begin = end;
            this.end = begin;
        } else {
            this.begin = begin;
            this.end = end;
        }

    }

    public long between(DateUnit unit) {
        long diff = this.end.getTime() - this.begin.getTime();
        return diff / unit.getMillis();
    }

    public long between(long min) {
        long diff = this.end.getTime() - this.begin.getTime();
        return diff / (DateUnit.MINUTE.getMillis() * min);
    }

    public long betweenMonth(boolean isReset) {
        Calendar beginCal = DateUtil.calendar(this.begin);
        Calendar endCal = DateUtil.calendar(this.end);
        int betweenYear = endCal.get(1) - beginCal.get(1);
        int betweenMonthOfYear = endCal.get(2) - beginCal.get(2);
        int result = betweenYear * 12 + betweenMonthOfYear;
        if (!isReset) {
            endCal.set(1, beginCal.get(1));
            endCal.set(2, beginCal.get(2));
            long between = endCal.getTimeInMillis() - beginCal.getTimeInMillis();
            if (between < 0L) {
                return (long) (result - 1);
            }
        }

        return (long) result;
    }

    public long betweenYear(boolean isReset) {
        Calendar beginCal = DateUtil.calendar(this.begin);
        Calendar endCal = DateUtil.calendar(this.end);
        int result = endCal.get(1) - beginCal.get(1);
        if (!isReset) {
            endCal.set(1, beginCal.get(1));
            long between = endCal.getTimeInMillis() - beginCal.getTimeInMillis();
            if (between < 0L) {
                return (long) (result - 1);
            }
        }

        return (long) result;
    }

    public long betweenMin(long min) {
        return (new DateBetween2(begin, end, true)).between(min);
    }

    public String toString(BetweenFormater.Level level) {
        return DateUtil.formatBetween(this.between(DateUnit.MS), level);
    }

    public String toString() {
        return this.toString(BetweenFormater.Level.MILLSECOND);
    }
}
