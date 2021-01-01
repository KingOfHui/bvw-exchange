package com.darknet.bvw.model;

import java.io.Serializable;
import java.util.List;

public class CoinsModel implements Serializable {


    /**
     * code : 0
     * data : [{"amount_decimals":5,"base_token_decimals":8,"base_token_symbol":"BTC","created_at":"Aug 23, 2019 4:51:28 PM","id":"BTC-USDT","is_favor_market":true,"is_published":1,"maker_fee_rate":0,"min_order_size":0.001,"orders":1,"price_decimals":5,"price_precision":5,"quote_token_decimals":8,"quote_token_symbol":"USDT","taker_fee_rate":0,"thumb":{"change":0,"chg":0,"close":0,"closeStr":"0.00000","high":0,"lastDayClose":0,"low":0,"marketId":"BTC-USDT","open":0,"quoteUsdRate":null,"trend":null,"turnover":0,"usdRate":1,"volume":0}},{"amount_decimals":5,"base_token_decimals":8,"base_token_symbol":"BTW","created_at":"Aug 23, 2019 4:51:28 PM","id":"BTW-USDT","is_favor_market":true,"is_published":1,"maker_fee_rate":0.003,"min_order_size":0.001,"orders":2,"price_decimals":5,"price_precision":5,"quote_token_decimals":8,"quote_token_symbol":"USDT","taker_fee_rate":0.001,"thumb":{"change":0.32,"chg":3.2,"close":0.42,"closeStr":"0.42000","high":0.42,"lastDayClose":0.1,"low":0.1,"marketId":"BTW-USDT","open":0.1,"quoteUsdRate":null,"trend":null,"turnover":5.5316026,"usdRate":1,"volume":25.935}},{"amount_decimals":5,"base_token_decimals":8,"base_token_symbol":"BTC","created_at":"Aug 23, 2019 4:51:28 PM","id":"BTC-BTW","is_favor_market":false,"is_published":1,"maker_fee_rate":0.003,"min_order_size":0.001,"orders":3,"price_decimals":5,"price_precision":5,"quote_token_decimals":8,"quote_token_symbol":"BTW","taker_fee_rate":0.001,"thumb":{"change":0,"chg":0,"close":0,"closeStr":"0.00000","high":0,"lastDayClose":0,"low":0,"marketId":"BTC-BTW","open":0,"quoteUsdRate":null,"trend":null,"turnover":0,"usdRate":0.42,"volume":0}},{"amount_decimals":5,"base_token_decimals":8,"base_token_symbol":"ETH","created_at":"Aug 23, 2019 4:51:28 PM","id":"ETH-BTW","is_favor_market":false,"is_published":1,"maker_fee_rate":0.003,"min_order_size":0.001,"orders":3,"price_decimals":5,"price_precision":5,"quote_token_decimals":8,"quote_token_symbol":"BTW","taker_fee_rate":0.001,"thumb":{"change":0,"chg":0,"close":0,"closeStr":"0.00000","high":0,"lastDayClose":0,"low":0,"marketId":"ETH-BTW","open":0,"quoteUsdRate":null,"trend":null,"turnover":0,"usdRate":0.42,"volume":0}},{"amount_decimals":5,"base_token_decimals":8,"base_token_symbol":"ETH","created_at":"Aug 23, 2019 4:51:28 PM","id":"ETH-USDT","is_favor_market":true,"is_published":1,"maker_fee_rate":0.003,"min_order_size":0.001,"orders":3,"price_decimals":5,"price_precision":5,"quote_token_decimals":8,"quote_token_symbol":"USDT","taker_fee_rate":0.001,"thumb":{"change":0,"chg":0,"close":0,"closeStr":"0.00000","high":0,"lastDayClose":0,"low":0,"marketId":"ETH-USDT","open":0,"quoteUsdRate":null,"trend":null,"turnover":0,"usdRate":1,"volume":0}}]
     * msg : success
     * success : true
     */

    private int code;
    private String msg;
    private boolean success;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount_decimals : 5
         * base_token_decimals : 8
         * base_token_symbol : BTC
         * created_at : Aug 23, 2019 4:51:28 PM
         * id : BTC-USDT
         * is_favor_market : true
         * is_published : 1
         * maker_fee_rate : 0.0
         * min_order_size : 0.001
         * orders : 1
         * price_decimals : 5
         * price_precision : 5
         * quote_token_decimals : 8
         * quote_token_symbol : USDT
         * taker_fee_rate : 0.0
         * thumb : {"change":0,"chg":0,"close":0,"closeStr":"0.00000","high":0,"lastDayClose":0,"low":0,"marketId":"BTC-USDT","open":0,"quoteUsdRate":null,"trend":null,"turnover":0,"usdRate":1,"volume":0}
         */

//        private String amount_decimals;
        private String base_token_decimals;
        private String trade_symbol;
        private String created_at;
        private String id;
        private boolean is_favor_market;
        private int fav;
        private String is_published;
        private String maker_fee_rate;
        private String min_order_size;
        private String orders;
//        private String price_decimals;
        private String price_precision;
        private String quote_symbol_scale;
        private String quote_symbol;
        private String taker_fee_rate;
        private ThumbBean coinThumb;

//        public String getAmount_decimals() {
//            return amount_decimals;
//        }
//
//        public void setAmount_decimals(String amount_decimals) {
//            this.amount_decimals = amount_decimals;
//        }

        public int getFav() {
            return fav;
        }

        public void setFav(int fav) {
            this.fav = fav;
        }

        public String getBase_token_decimals() {
            return base_token_decimals;
        }

        public void setBase_token_decimals(String base_token_decimals) {
            this.base_token_decimals = base_token_decimals;
        }

        public String getTrade_symbol() {
            return trade_symbol;
        }

        public void setTrade_symbol(String trade_symbol) {
            this.trade_symbol = trade_symbol;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIs_favor_market() {
            return is_favor_market;
        }

        public void setIs_favor_market(boolean is_favor_market) {
            this.is_favor_market = is_favor_market;
        }

        public String getIs_published() {
            return is_published;
        }

        public void setIs_published(String is_published) {
            this.is_published = is_published;
        }

        public String getMaker_fee_rate() {
            return maker_fee_rate;
        }

        public void setMaker_fee_rate(String maker_fee_rate) {
            this.maker_fee_rate = maker_fee_rate;
        }

        public String getMin_order_size() {
            return min_order_size;
        }

        public void setMin_order_size(String min_order_size) {
            this.min_order_size = min_order_size;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

//        public String getPrice_decimals() {
//            return price_decimals;
//        }
//
//        public void setPrice_decimals(String price_decimals) {
//            this.price_decimals = price_decimals;
//        }

        public String getPrice_precision() {
            return price_precision;
        }

        public void setPrice_precision(String price_precision) {
            this.price_precision = price_precision;
        }

        public String getQuote_symbol_scale() {
            return quote_symbol_scale;
        }

        public void setQuote_symbol_scale(String quote_symbol_scale) {
            this.quote_symbol_scale = quote_symbol_scale;
        }

        public String getQuote_symbol() {
            return quote_symbol;
        }

        public void setQuote_symbol(String quote_symbol) {
            this.quote_symbol = quote_symbol;
        }

        public String getTaker_fee_rate() {
            return taker_fee_rate;
        }

        public void setTaker_fee_rate(String taker_fee_rate) {
            this.taker_fee_rate = taker_fee_rate;
        }

        public ThumbBean getCoinThumb() {
            return coinThumb;
        }

        public void setCoinThumb(ThumbBean coinThumb) {
            this.coinThumb = coinThumb;
        }

        public static class ThumbBean {
            /**
             * change : 0
             * chg : 0
             * close : 0
             * closeStr : 0.00000
             * high : 0
             * lastDayClose : 0
             * low : 0
             * marketId : BTC-USDT
             * open : 0
             * quoteUsdRate : null
             * trend : null
             * turnover : 0
             * usdRate : 1
             * volume : 0.0
             */

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
}
