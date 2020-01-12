package com.darknet.bvw.model;

import java.util.List;

public class KLineDataModel {
    private long time;
    private float open;
    private float high;
    private float low;
    private float close;
    private float volume;

    //[1578498300000,0,0,0,0,0]  时间 开 高 低 收 量
    public KLineDataModel(List<String> data) {
        time = Long.parseLong(data.get(0));
        open = Float.parseFloat(data.get(1));
        high = Float.parseFloat(data.get(2));
        low = Float.parseFloat(data.get(3));
        close = Float.parseFloat(data.get(4));
        volume = Float.parseFloat(data.get(5));

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
