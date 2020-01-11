package com.darknet.bvw.model;

import java.util.List;

public class ChangeDepthResponse {
    private String idtLastPriceKey;
    private List<AsksBean> asks;
    private List<BidsBean> bids;

    public String getIdtLastPriceKey() {
        return idtLastPriceKey;
    }

    public void setIdtLastPriceKey(String idtLastPriceKey) {
        this.idtLastPriceKey = idtLastPriceKey;
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
         * amount : 4440.59
         * price : 0.00014265
         */

        private String amount;
        private String price;
        private String total;
        private String precent;

        public String getPrecent() {
            return precent;
        }

        public void setPrecent(String precent) {
            this.precent = precent;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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
         * amount : 15.33
         * price : 0.00014159
         */

        private String amount;
        private String price;
        private String total;
        private String precent;

        public String getPrecent() {
            return precent;
        }

        public void setPrecent(String precent) {
            this.precent = precent;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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
