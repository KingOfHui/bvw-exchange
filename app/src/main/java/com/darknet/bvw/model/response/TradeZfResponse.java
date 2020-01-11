package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class TradeZfResponse extends BaseResponse {


    private ZfData data;

    public ZfData getData() {
        return data;
    }

    public void setData(ZfData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TradeZfResponse{" +
                "data=" + data +
                '}';
    }

    public static class ZfData {
        private List<ZfModel> changeRank;

        public List<ZfModel> getChangeRank() {
            return changeRank;
        }

        public void setChangeRank(List<ZfModel> changeRank) {
            this.changeRank = changeRank;
        }

        @Override
        public String toString() {
            return "ZfData{" +
                    "changeRank=" + changeRank +
                    '}';
        }
    }


    public static class ZfModel {
        private String marketId;
        private BigDecimal open;
        private BigDecimal high;
        private BigDecimal low;
        private BigDecimal close;
        private BigDecimal chg;
        private BigDecimal change;
        private BigDecimal volume;
        private BigDecimal turnover;
        private BigDecimal lastDayClose;
        private String closeStr;
        private Object trend;
        private BigDecimal usdRate;
        private BigDecimal quoteUsdRate;


        public String getMarketId() {
            return marketId;
        }

        public void setMarketId(String marketId) {
            this.marketId = marketId;
        }

        public BigDecimal getOpen() {
            return open;
        }

        public void setOpen(BigDecimal open) {
            this.open = open;
        }

        public BigDecimal getHigh() {
            return high;
        }

        public void setHigh(BigDecimal high) {
            this.high = high;
        }

        public BigDecimal getLow() {
            return low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

        public BigDecimal getClose() {
            return close;
        }

        public void setClose(BigDecimal close) {
            this.close = close;
        }

        public BigDecimal getChg() {
            return chg;
        }

        public void setChg(BigDecimal chg) {
            this.chg = chg;
        }

        public BigDecimal getChange() {
            return change;
        }

        public void setChange(BigDecimal change) {
            this.change = change;
        }

        public BigDecimal getVolume() {
            return volume;
        }

        public void setVolume(BigDecimal volume) {
            this.volume = volume;
        }

        public BigDecimal getTurnover() {
            return turnover;
        }

        public void setTurnover(BigDecimal turnover) {
            this.turnover = turnover;
        }

        public BigDecimal getLastDayClose() {
            return lastDayClose;
        }

        public void setLastDayClose(BigDecimal lastDayClose) {
            this.lastDayClose = lastDayClose;
        }

        public String getCloseStr() {
            return closeStr;
        }

        public void setCloseStr(String closeStr) {
            this.closeStr = closeStr;
        }

        public Object getTrend() {
            return trend;
        }

        public void setTrend(Object trend) {
            this.trend = trend;
        }

        public BigDecimal getUsdRate() {
            return usdRate;
        }

        public void setUsdRate(BigDecimal usdRate) {
            this.usdRate = usdRate;
        }

        public BigDecimal getQuoteUsdRate() {
            return quoteUsdRate;
        }

        public void setQuoteUsdRate(BigDecimal quoteUsdRate) {
            this.quoteUsdRate = quoteUsdRate;
        }


        @Override
        public String toString() {
            return "ZfModel{" +
                    "marketId='" + marketId + '\'' +
                    ", open=" + open +
                    ", high=" + high +
                    ", low=" + low +
                    ", close=" + close +
                    ", chg=" + chg +
                    ", change=" + change +
                    ", volume=" + volume +
                    ", turnover=" + turnover +
                    ", lastDayClose=" + lastDayClose +
                    ", closeStr='" + closeStr + '\'' +
                    ", trend=" + trend +
                    ", usdRate=" + usdRate +
                    ", quoteUsdRate=" + quoteUsdRate +
                    '}';
        }
    }

}
