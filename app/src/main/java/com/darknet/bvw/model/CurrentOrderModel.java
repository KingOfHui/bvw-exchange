package com.darknet.bvw.model;

import java.math.BigDecimal;
import java.util.List;

public class CurrentOrderModel {


    /**
     * code : 0
     * data : {"items":[{"amount":0,"created_at":"","id":0,"maker":0,"maker_fee":0,"maker_order_id":0,"market_id":"","price":0,"status":"","taker":0,"taker_fee":0,"taker_order_id":0,"taker_side":"","total":0,"updated_at":""}],"limit":0,"page":0,"totalCount":0,"totalPage":0}
     * msg :
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
         * items : [{"amount":0,"created_at":"","id":0,"maker":0,"maker_fee":0,"maker_order_id":0,"market_id":"","price":0,"status":"","taker":0,"taker_fee":0,"taker_order_id":0,"taker_side":"","total":0,"updated_at":""}]
         * limit : 0
         * page : 0
         * totalCount : 0
         * totalPage : 0
         */

        private int limit;
        private int page;
        private int totalCount;
        private int totalPage;
        private List<ItemsBean> items;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * amount : 0
             * created_at :
             * id : 0
             * maker : 0
             * maker_fee : 0
             * maker_order_id : 0
             * market_id :
             * price : 0
             * status :
             * taker : 0
             * taker_fee : 0
             * taker_order_id : 0
             * taker_side :
             * total : 0
             * updated_at :
             */

            private BigDecimal amount;
            private String created_at;
            private String id;
            private String maker;
            private String maker_fee;
            private String maker_order_id;
            private String market_id;
            private BigDecimal price;
            private String status;
            private String taker;
            private String taker_fee;
            private String taker_order_id;
            private String taker_side;
            private BigDecimal total;
            private String updated_at;
            private String side;


            public BigDecimal getAmount() {
                return amount;
            }

            public void setAmount(BigDecimal amount) {
                this.amount = amount;
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

            public String getMaker() {
                return maker;
            }

            public void setMaker(String maker) {
                this.maker = maker;
            }

            public String getMaker_fee() {
                return maker_fee;
            }

            public void setMaker_fee(String maker_fee) {
                this.maker_fee = maker_fee;
            }

            public String getMaker_order_id() {
                return maker_order_id;
            }

            public void setMaker_order_id(String maker_order_id) {
                this.maker_order_id = maker_order_id;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTaker() {
                return taker;
            }

            public void setTaker(String taker) {
                this.taker = taker;
            }

            public String getTaker_fee() {
                return taker_fee;
            }

            public void setTaker_fee(String taker_fee) {
                this.taker_fee = taker_fee;
            }

            public String getTaker_order_id() {
                return taker_order_id;
            }

            public void setTaker_order_id(String taker_order_id) {
                this.taker_order_id = taker_order_id;
            }

            public String getTaker_side() {
                return taker_side;
            }

            public void setTaker_side(String taker_side) {
                this.taker_side = taker_side;
            }

            public BigDecimal getTotal() {
                return total;
            }

            public void setTotal(BigDecimal total) {
                this.total = total;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getSide() {
                return side;
            }

            public void setSide(String side) {
                this.side = side;
            }
        }
    }
}
