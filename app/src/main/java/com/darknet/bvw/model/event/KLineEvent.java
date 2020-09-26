package com.darknet.bvw.model.event;

public class KLineEvent {

    //{"marketId":"BTW-USDT","openPrice":0.44,"highestPrice":0.44,"lowestPrice":0.44,"closePrice":0.44
    // ,"time":1578502620000,"period":"1min","count":0,"volume":0,"turnover":0}
    private String marketId;
    private float openPrice;
    private float highestPrice;
    private float lowestPrice;
    private float closePrice;
    private long time;
    private String period;
    private float count;
    private float volume;
    private float turnover;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    public float getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(float highestPrice) {
        this.highestPrice = highestPrice;
    }

    public float getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(float lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
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

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    @Override
    public String toString() {
        return "KLineEvent{" +
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
