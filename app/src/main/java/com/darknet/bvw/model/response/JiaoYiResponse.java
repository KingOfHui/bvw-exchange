package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class JiaoYiResponse extends BaseResponse {

//    "id": 1225,
//            "status": "successful",
//            "market_id": "BVW-USDT",
//            "maker": 1042081,
//            "taker": 1042081,
//            "price": 0.60000000,
//            "amount": 32888.54000000,
//            "total": 19733.12400000,
//            "taker_side": "sell",
//            "maker_order_id": 2016,
//            "taker_order_id": 2019,
//            "maker_fee": 75.64364200,
//            "taker_fee": 45.38618520,
//            "updated_at": null,
//            "created_at": "2020-01-12 03:23:13"


    private JiaoYiData data;

    public JiaoYiData getData() {
        return data;
    }

    public void setData(JiaoYiData data) {
        this.data = data;
    }

    public static class JiaoYiData{
        private List<JiaoYiModel> items;

        public List<JiaoYiModel> getItems() {
            return items;
        }

        public void setItems(List<JiaoYiModel> items) {
            this.items = items;
        }
    }

    public static class JiaoYiModel{
        private int id;
        private String status;
        private String market_id;
        private String maker;
        private String taker;
        private BigDecimal price;
        private BigDecimal amount;
        private BigDecimal total;
        private String taker_side;
        private String maker_order_id;
        private String taker_order_id;
        private BigDecimal maker_fee;
        private BigDecimal taker_fee;
        private String updated_at;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMarket_id() {
            return market_id;
        }

        public void setMarket_id(String market_id) {
            this.market_id = market_id;
        }

        public String getMaker() {
            return maker;
        }

        public void setMaker(String maker) {
            this.maker = maker;
        }

        public String getTaker() {
            return taker;
        }

        public void setTaker(String taker) {
            this.taker = taker;
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

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public String getTaker_side() {
            return taker_side;
        }

        public void setTaker_side(String taker_side) {
            this.taker_side = taker_side;
        }

        public String getMaker_order_id() {
            return maker_order_id;
        }

        public void setMaker_order_id(String maker_order_id) {
            this.maker_order_id = maker_order_id;
        }

        public String getTaker_order_id() {
            return taker_order_id;
        }

        public void setTaker_order_id(String taker_order_id) {
            this.taker_order_id = taker_order_id;
        }

        public BigDecimal getMaker_fee() {
            return maker_fee;
        }

        public void setMaker_fee(BigDecimal maker_fee) {
            this.maker_fee = maker_fee;
        }

        public BigDecimal getTaker_fee() {
            return taker_fee;
        }

        public void setTaker_fee(BigDecimal taker_fee) {
            this.taker_fee = taker_fee;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }



}
