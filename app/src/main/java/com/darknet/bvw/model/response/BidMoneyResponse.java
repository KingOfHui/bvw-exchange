package com.darknet.bvw.model.response;

public class BidMoneyResponse extends BaseResponse {


    private BidMoneyModel data;

    public BidMoneyModel getData() {
        return data;
    }

    public void setData(BidMoneyModel data) {
        this.data = data;
    }

    public static class BidMoneyModel{
        private String bid_price;
        private String vip_level;


        public String getBid_price() {
            return bid_price;
        }

        public void setBid_price(String bid_price) {
            this.bid_price = bid_price;
        }

        public String getVip_level() {
            return vip_level;
        }

        public void setVip_level(String vip_level) {
            this.vip_level = vip_level;
        }

        @Override
        public String toString() {
            return "BidMoneyResponse{" +
                    "bid_price='" + bid_price + '\'' +
                    ", vip_level='" + vip_level + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BidMoneyResponse{" +
                "data=" + data +
                '}';
    }
}
