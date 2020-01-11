package com.darknet.bvw.model;

public class MarketModel {
    /**
     * coinId : 1
     * symbol : BTC
     * volume : 24793818779.81
     * price : 6683.35
     * changeRate : -3.56
     * isFocus : false
     */

    private int coinId;
    private String symbol;
    private String volume;
    private String price;
    private String changeRate;
    private boolean isFocus;

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public boolean isIsFocus() {
        return isFocus;
    }

    public void setIsFocus(boolean isFocus) {
        this.isFocus = isFocus;
    }

}

