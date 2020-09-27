package com.darknet.bvw.model;

import java.util.List;

/**
 * 作者：created by albert on 2020-01-05 14:36
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class DepthResponse {

    /**
     * code : 0
     * data : {"asks":[{"amount":1,"price":0.0015},{"amount":1,"price":0.0013}],"bids":[{"amount":1,"price":0.0013},{"amount":1,"price":0.0013}],"marketId":"BTW-USDT"}
     * msg : success
     * success : true
     */

    private int code;
    private DataBean data;
    private String msg;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * asks : [{"amount":1,"price":0.0015},{"amount":1,"price":0.0013}]
         * bids : [{"amount":1,"price":0.0013},{"amount":1,"price":0.0013}]
         * marketId : BTW-USDT
         */

        private String marketId;
        private List<AsksBean> asks;
        private List<BidsBean> bids;

        public String getMarketId() {
            return marketId;
        }

        public void setMarketId(String marketId) {
            this.marketId = marketId;
        }

        public List<AsksBean> getAsks() {
            return asks;
        }

        public void setAsks(List<AsksBean> asks) {
            this.asks = asks;
        }

        public List<BidsBean> getBids() {
            return bids;
        }

        public void setBids(List<BidsBean> bids) {
            this.bids = bids;
        }

        public static class AsksBean {
            /**
             * amount : 1
             * price : 0.0015
             */

            private String amount;
            private String price;
            private String total;
            private String precent;
            private String currentCount;

            public String getCurrentCount() {
                return currentCount;
            }

            public void setCurrentCount(String currentCount) {
                this.currentCount = currentCount;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getPrecent() {
                return precent;
            }

            public void setPrecent(String precent) {
                this.precent = precent;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }

        public static class BidsBean {
            /**
             * amount : 1
             * price : 0.0013
             */

            private String amount;
            private String price;
            private String total;
            private String precent;
            private String currentCount;

            public String getCurrentCount() {
                return currentCount;
            }

            public void setCurrentCount(String currentCount) {
                this.currentCount = currentCount;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getPrecent() {
                return precent;
            }

            public void setPrecent(String precent) {
                this.precent = precent;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
