package com.darknet.bvw.config.constant;

/**
 * @author lijingya
 * @description 添加描述
 * @email lijingya@91118.com
 * @createDate 2020/1/16
 * @company 杭州天音
 */
public enum KLineTypeEnum {
    //1min,5min,10min,15min,30min,1hour,1day,1week,1month
    /**
     * 时分
     */
    LINE("1min", "1", 0),
    /**
     * 1分
     */
    min1("1min", "1", 1),
    /**
     * 5分
     */
    min5("5min", "5", 2),
    /**
     * 10分
     */
    min10("10min", "10", 3),
    /**
     * 15分
     */
    min15("15min", "15", 4),
    /**
     * 15分
     */
    min30("30min", "30", 5),
    /**
     * 1小时
     */
    hour1("1hour", "1h", 6),
    /**
     * 1d
     */
    daily("1day", "1d", 7),
    /**
     * 1w
     */
    week("1week", "1w", 8),
    /**
     * 月
     */
    month("1month", "1m", 9),

    /**
     * 更多
     */
    more("more", "more", 10);

    private String period;
    private String value;

    private int type;

    KLineTypeEnum(String period, String value, int type) {
        this.period = period;
        this.value = value;
        this.type = type;
    }

    public String getPeriod() {
        return period;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
}
