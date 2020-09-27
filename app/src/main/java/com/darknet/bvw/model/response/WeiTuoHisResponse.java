package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class WeiTuoHisResponse extends BaseResponse {

//    "id": 7,
//            "market_id": "BTW-USDT",
//            "price": 0.50000000,
//            "confirmed_amount": 0,
//            "fee": 0,
//            "turnover": 0,
//            "side": "sell",
//            "status": "canceled",
//            "created_at": "2020-01-08 09:31:49"

    private WeiHisData data;

    public WeiHisData getData() {
        return data;
    }

    public void setData(WeiHisData data) {
        this.data = data;
    }

    public static class WeiHisData{
        private List<WeiHisModel> items;

        public List<WeiHisModel> getItems() {
            return items;
        }

        public void setItems(List<WeiHisModel> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "WeiHisData{" +
                    "items=" + items +
                    '}';
        }
    }

    public static class WeiHisModel{
        private int id;
        private String market_id;
        private BigDecimal price;
        private BigDecimal confirmed_amount;
        private BigDecimal fee;
        private BigDecimal turnover;
        private String side;
        private String status;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMarket_id() {
            return market_id;
        }

        public void setMarket_id(String market_id) {
            this.market_id = market_id;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getConfirmed_amount() {
            return confirmed_amount;
        }

        public void setConfirmed_amount(BigDecimal confirmed_amount) {
            this.confirmed_amount = confirmed_amount;
        }

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }

        public BigDecimal getTurnover() {
            return turnover;
        }

        public void setTurnover(BigDecimal turnover) {
            this.turnover = turnover;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

}
