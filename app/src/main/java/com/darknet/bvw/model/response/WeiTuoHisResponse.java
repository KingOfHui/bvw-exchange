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
        private String marketId;
        private BigDecimal price;
        private BigDecimal amount;
        private BigDecimal traded_amount;
        private BigDecimal fee;
        private BigDecimal turnover;
        private String side;
        private String state;
        private String created_at;
        private int direction;
        private String time;

        public BigDecimal getTraded_amount() {
            return traded_amount;
        }

        public void setTraded_amount(BigDecimal traded_amount) {
            this.traded_amount = traded_amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMarketId() {
            return marketId;
        }

        public void setMarketId(String marketId) {
            this.marketId = marketId;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

}
