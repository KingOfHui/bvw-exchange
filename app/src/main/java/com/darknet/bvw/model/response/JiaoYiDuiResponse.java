package com.darknet.bvw.model.response;

import java.math.BigDecimal;

public class JiaoYiDuiResponse extends BaseResponse {


    private JiaoYiDuiData data;

    public JiaoYiDuiData getData() {
        return data;
    }

    public void setData(JiaoYiDuiData data) {
        this.data = data;
    }

    public static class JiaoYiDuiData {

        private String id;
        private BigDecimal base_token_decimals;
        private String base_token_symbol;
        private BigDecimal quote_token_decimals;
        private String quote_token_symbol;
        private BigDecimal min_order_size;
        private String max_order_size;
        private BigDecimal maker_fee_rate;
        private BigDecimal taker_fee_rate;
        private BigDecimal price_precision;
        private BigDecimal price_decimals;
        private BigDecimal amount_decimals;
        private String is_published;
        private String orders;
        private String created_at;
        private String updated_at;
        private KLineTicker coinThumb;

        public KLineTicker getCoinThumb() {
            return coinThumb;
        }

        public void setCoinThumb(KLineTicker coinThumb) {
            this.coinThumb = coinThumb;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public BigDecimal getBase_token_decimals() {
            return base_token_decimals;
        }

        public void setBase_token_decimals(BigDecimal base_token_decimals) {
            this.base_token_decimals = base_token_decimals;
        }

        public String getBase_token_symbol() {
            return base_token_symbol;
        }

        public void setBase_token_symbol(String base_token_symbol) {
            this.base_token_symbol = base_token_symbol;
        }

        public BigDecimal getQuote_token_decimals() {
            return quote_token_decimals;
        }

        public void setQuote_token_decimals(BigDecimal quote_token_decimals) {
            this.quote_token_decimals = quote_token_decimals;
        }

        public String getQuote_token_symbol() {
            return quote_token_symbol;
        }

        public void setQuote_token_symbol(String quote_token_symbol) {
            this.quote_token_symbol = quote_token_symbol;
        }

        public BigDecimal getMin_order_size() {
            return min_order_size;
        }

        public void setMin_order_size(BigDecimal min_order_size) {
            this.min_order_size = min_order_size;
        }

        public String getMax_order_size() {
            return max_order_size;
        }

        public void setMax_order_size(String max_order_size) {
            this.max_order_size = max_order_size;
        }

        public BigDecimal getMaker_fee_rate() {
            return maker_fee_rate;
        }

        public void setMaker_fee_rate(BigDecimal maker_fee_rate) {
            this.maker_fee_rate = maker_fee_rate;
        }

        public BigDecimal getTaker_fee_rate() {
            return taker_fee_rate;
        }

        public void setTaker_fee_rate(BigDecimal taker_fee_rate) {
            this.taker_fee_rate = taker_fee_rate;
        }

        public BigDecimal getPrice_precision() {
            return price_precision;
        }

        public void setPrice_precision(BigDecimal price_precision) {
            this.price_precision = price_precision;
        }

        public BigDecimal getPrice_decimals() {
            return price_decimals;
        }

        public void setPrice_decimals(BigDecimal price_decimals) {
            this.price_decimals = price_decimals;
        }

        public BigDecimal getAmount_decimals() {
            return amount_decimals;
        }

        public void setAmount_decimals(BigDecimal amount_decimals) {
            this.amount_decimals = amount_decimals;
        }

        public String getIs_published() {
            return is_published;
        }

        public void setIs_published(String is_published) {
            this.is_published = is_published;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class KLineTicker {

        private String change;
        private String chg;
        private String close;
        private String closeStr;
        private double high;
        private String lastDayClose;
        private double low;
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

        public void setHigh(double high) {
            this.high = high;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public double getHigh() {
            return high;
        }

        public double getLow() {
            return low;
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


        public String getLastDayClose() {
            return lastDayClose;
        }

        public void setLastDayClose(String lastDayClose) {
            this.lastDayClose = lastDayClose;
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

        public void setUsdRate(String usdRate) {
            this.usdRate = usdRate;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }
    }


}
