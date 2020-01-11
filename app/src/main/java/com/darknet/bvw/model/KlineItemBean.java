package com.darknet.bvw.model;

/**
 * 作者：created by albert on 2020-01-07 14:52
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class KlineItemBean {

    /**
     * marketId : null
     * openPrice : 0
     * highestPrice : 0
     * lowestPrice : 0
     * closePrice : 0
     * time : 1578376800000
     * period : 30min
     * count : 0
     * volume : 0
     * turnover : 0
     */

    private String marketId;
    private int openPrice;
    private int highestPrice;
    private int lowestPrice;
    private int closePrice;
    private long time;
    private String period;
    private int count;
    private int volume;
    private int turnover;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public int getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(int openPrice) {
        this.openPrice = openPrice;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(int highestPrice) {
        this.highestPrice = highestPrice;
    }

    public int getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(int lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public int getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(int closePrice) {
        this.closePrice = closePrice;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    @Override
    public String toString() {
        return "KlineItemBean{" +
                "marketId='" + marketId + '\'' +
                ", openPrice=" + openPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", closePrice=" + closePrice +
                ", time=" + time +
                ", period='" + period + '\'' +
                ", count=" + count +
                ", volume=" + volume +
                ", turnover=" + turnover +
                '}';
    }
}
