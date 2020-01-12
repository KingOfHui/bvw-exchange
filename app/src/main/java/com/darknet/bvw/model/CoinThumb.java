package com.darknet.bvw.model;

import java.text.DecimalFormat;

public class CoinThumb {
    private String change;
    private String chg;
    private String close;
    private String closeStr;
    private String high;
    private String lastDayClose;
    private String low;
    private String marketId;
    private String open;
    private String quoteUsdRate;
    private String turnover;
    private String usdRate;
    private String volume;

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChg() {
        return chg;
    }

    public void setChg(String chg) {
        this.chg = chg;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getCloseStr() {
        return closeStr;
    }

    public void setCloseStr(String closeStr) {
        this.closeStr = closeStr;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLastDayClose() {
        return lastDayClose;
    }

    public void setLastDayClose(String lastDayClose) {
        this.lastDayClose = lastDayClose;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getQuoteUsdRate() {
        return quoteUsdRate;
    }

    public void setQuoteUsdRate(String quoteUsdRate) {
        this.quoteUsdRate = quoteUsdRate;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getUsdRate() {
        return usdRate;
    }

    public void setUsdRate(double usdRate) {
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        this.usdRate = decimalFormat.format(usdRate);
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
