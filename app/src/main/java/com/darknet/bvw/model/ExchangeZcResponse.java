package com.darknet.bvw.model;

import com.darknet.bvw.model.response.BaseResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ExchangeZcResponse extends BaseResponse {



    private List<EZcModel> data;


    public List<EZcModel> getData() {
        return data;
    }

    public void setData(List<EZcModel> data) {
        this.data = data;
    }

    public static class EZcModel implements Serializable {

        private String id;
        private String user_id;
        private String symbol;
        private BigDecimal balance;
        private BigDecimal lock_balance;
        private String create_at;
        private String update_at;
        private String is_lock;
        private BigDecimal price;
        private BigDecimal value_usd;
        private BigDecimal lock_value_usd;

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

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public BigDecimal getLock_balance() {
            return lock_balance;
        }

        public void setLock_balance(BigDecimal lock_balance) {
            this.lock_balance = lock_balance;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public String getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(String is_lock) {
            this.is_lock = is_lock;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getValue_usd() {
            return value_usd;
        }

        public void setValue_usd(BigDecimal value_usd) {
            this.value_usd = value_usd;
        }

        public BigDecimal getLock_value_usd() {
            return lock_value_usd;
        }

        public void setLock_value_usd(BigDecimal lock_value_usd) {
            this.lock_value_usd = lock_value_usd;
        }


        @Override
        public String toString() {
            return "EZcModel{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", balance=" + balance +
                    ", lock_balance=" + lock_balance +
                    ", create_at='" + create_at + '\'' +
                    ", update_at='" + update_at + '\'' +
                    ", is_lock='" + is_lock + '\'' +
                    ", price=" + price +
                    ", value_usd=" + value_usd +
                    ", lock_value_usd=" + lock_value_usd +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ExchangeZcResponse{" +
                "data=" + data +
                '}';
    }


    //    "id": 514,
//            "user_id": 1000001,
//            "symbol": "BTW",
//            "balance": 96.48900000,
//            "lock_balance": 3.50100000,
//            "create_at": "2020-01-03 06:09:14",
//            "update_at": null,
//            "is_lock": 0,
//            "price": 0.42000000,
//            "value_usd": "40.53",
//            "lock_value_usd": "1.47"




}
