package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class FenLieOrderResponse extends BaseResponse {


    private FLOrderData data;

    public FLOrderData getData() {
        return data;
    }

    public void setData(FLOrderData data) {
        this.data = data;
    }

    public static class FLOrderData{
        private List<FLOrderItemModel> items;

        public List<FLOrderItemModel> getItems() {
            return items;
        }

        public void setItems(List<FLOrderItemModel> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "FLOrderData{" +
                    "items=" + items +
                    '}';
        }
    }

    public static class FLOrderItemModel{

        private String id;
        private String user_id;
        private String symbol;
        private String order_id;
        private BigDecimal order_amount;
        private String result;
        private String height;
        private BigDecimal base_bvw;
        private BigDecimal  back_bvw;
        private BigDecimal back_pool_bvw;
        private  BigDecimal burn_bvw;
        private String create_at;
        private String stage;
        private String tx_hash;
        private String start_bvw;
        private String end_bvw;
        private String back_rate;
        private String back_pool_rate;
        private String burn_rate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public BigDecimal getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(BigDecimal order_amount) {
            this.order_amount = order_amount;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public BigDecimal getBase_bvw() {
            return base_bvw;
        }

        public void setBase_bvw(BigDecimal base_bvw) {
            this.base_bvw = base_bvw;
        }

        public BigDecimal getBack_bvw() {
            return back_bvw;
        }

        public void setBack_bvw(BigDecimal back_bvw) {
            this.back_bvw = back_bvw;
        }

        public BigDecimal getBack_pool_bvw() {
            return back_pool_bvw;
        }

        public void setBack_pool_bvw(BigDecimal back_pool_bvw) {
            this.back_pool_bvw = back_pool_bvw;
        }

        public BigDecimal getBurn_bvw() {
            return burn_bvw;
        }

        public void setBurn_bvw(BigDecimal burn_bvw) {
            this.burn_bvw = burn_bvw;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getTx_hash() {
            return tx_hash;
        }

        public void setTx_hash(String tx_hash) {
            this.tx_hash = tx_hash;
        }

        public String getStart_bvw() {
            return start_bvw;
        }

        public void setStart_bvw(String start_bvw) {
            this.start_bvw = start_bvw;
        }

        public String getEnd_bvw() {
            return end_bvw;
        }

        public void setEnd_bvw(String end_bvw) {
            this.end_bvw = end_bvw;
        }

        public String getBack_rate() {
            return back_rate;
        }

        public void setBack_rate(String back_rate) {
            this.back_rate = back_rate;
        }

        public String getBack_pool_rate() {
            return back_pool_rate;
        }

        public void setBack_pool_rate(String back_pool_rate) {
            this.back_pool_rate = back_pool_rate;
        }

        public String getBurn_rate() {
            return burn_rate;
        }

        public void setBurn_rate(String burn_rate) {
            this.burn_rate = burn_rate;
        }

        @Override
        public String toString() {
            return "FLOrderItemModel{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", order_amount=" + order_amount +
                    ", result='" + result + '\'' +
                    ", height='" + height + '\'' +
                    ", base_bvw=" + base_bvw +
                    ", back_bvw=" + back_bvw +
                    ", back_pool_bvw=" + back_pool_bvw +
                    ", burn_bvw=" + burn_bvw +
                    ", create_at='" + create_at + '\'' +
                    ", stage='" + stage + '\'' +
                    ", tx_hash='" + tx_hash + '\'' +
                    ", start_bvw='" + start_bvw + '\'' +
                    ", end_bvw='" + end_bvw + '\'' +
                    ", back_rate='" + back_rate + '\'' +
                    ", back_pool_rate='" + back_pool_rate + '\'' +
                    ", burn_rate='" + burn_rate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FenLieOrderResponse{" +
                "data=" + data +
                '}';
    }
}
