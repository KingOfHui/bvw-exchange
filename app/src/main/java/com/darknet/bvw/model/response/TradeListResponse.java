package com.darknet.bvw.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TradeListResponse extends BaseResponse {

   private TradeListDataModel data;

    public TradeListDataModel getData() {
        return data;
    }

    public void setData(TradeListDataModel data) {
        this.data = data;
    }

    public static class TradeListDataModel{
        private int totalCount;
        private int limit;
        private int page;
        private int totalPage;
        private List<TradeListModel> items;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

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

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<TradeListModel> getItems() {
            return items;
        }

        public void setItems(List<TradeListModel> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "TradeListDataModel{" +
                    "totalCount=" + totalCount +
                    ", limit=" + limit +
                    ", page=" + page +
                    ", totalPage=" + totalPage +
                    ", items=" + items +
                    '}';
        }
    }

    public static class TradeListModel implements Serializable {
       private BigDecimal amount;
       private String create_at;
       private BigDecimal fee;
       private String from_address;
       private String id;
       private String send_wrapper;
       private String status;
       private String symbol;
       private String to_address;
       private String tx_hash;
       private String update_at;
       private String remark;
       private String height;
       private String create_at_wrapper;
       private String demo;
       private String type;
       private String pay_tx_hash;
       private BigDecimal service_fee;
       private String pay_status;

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public BigDecimal getService_fee() {
            return service_fee;
        }

        public void setService_fee(BigDecimal service_fee) {
            this.service_fee = service_fee;
        }

        public String getPay_tx_hash() {
            return pay_tx_hash;
        }

        public void setPay_tx_hash(String pay_tx_hash) {
            this.pay_tx_hash = pay_tx_hash;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDemo() {
            return demo;
        }

        public void setDemo(String demo) {
            this.demo = demo;
        }

        public String getCreate_at_wrapper() {
            return create_at_wrapper;
        }

        public void setCreate_at_wrapper(String create_at_wrapper) {
            this.create_at_wrapper = create_at_wrapper;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }


        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
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

        public String getFrom_address() {
            return from_address;
        }

        public void setFrom_address(String from_address) {
            this.from_address = from_address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSend_wrapper() {
            return send_wrapper;
        }

        public void setSend_wrapper(String send_wrapper) {
            this.send_wrapper = send_wrapper;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getTo_address() {
            return to_address;
        }

        public void setTo_address(String to_address) {
            this.to_address = to_address;
        }

        public String getTx_hash() {
            return tx_hash;
        }

        public void setTx_hash(String tx_hash) {
            this.tx_hash = tx_hash;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        @Override
        public String toString() {
            return "TradeListModel{" +
                    "amount='" + amount + '\'' +
                    ", create_at='" + create_at + '\'' +
                    ", fee='" + fee + '\'' +
                    ", from_address='" + from_address + '\'' +
                    ", id='" + id + '\'' +
                    ", send_wrapper='" + send_wrapper + '\'' +
                    ", status='" + status + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", to_address='" + to_address + '\'' +
                    ", tx_hash='" + tx_hash + '\'' +
                    ", update_at='" + update_at + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeListResponse{" +
                "data=" + data +
                '}';
    }
}
