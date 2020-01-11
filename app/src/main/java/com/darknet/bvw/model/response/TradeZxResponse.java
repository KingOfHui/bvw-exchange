package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class TradeZxResponse extends BaseResponse {

    private List<ZxDataModel> data;

    public List<ZxDataModel> getData() {
        return data;
    }

    public void setData(List<ZxDataModel> data) {
        this.data = data;
    }

    public static class ZxDataModel {
        private String user_id;
        private String market_id;
        private ZxModel recommend;
        private String id;
        private String create_at;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMarket_id() {
            return market_id;
        }

        public void setMarket_id(String market_id) {
            this.market_id = market_id;
        }

        public ZxModel getRecommend() {
            return recommend;
        }

        public void setRecommend(ZxModel recommend) {
            this.recommend = recommend;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        @Override
        public String toString() {
            return "ZxDataModel{" +
                    "user_id='" + user_id + '\'' +
                    ", market_id='" + market_id + '\'' +
                    ", recommend=" + recommend +
                    ", id='" + id + '\'' +
                    ", create_at='" + create_at + '\'' +
                    '}';
        }
    }


    public static class ZxModel {
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
            return "ZxModel{" +
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


    @Override
    public String toString() {
        return "TradeZxResponse{" +
                "data=" + data +
                '}';
    }
}
