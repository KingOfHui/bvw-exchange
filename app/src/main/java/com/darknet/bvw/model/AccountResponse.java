package com.darknet.bvw.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 作者：created by albert on 2020-01-06 12:40
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class AccountResponse {


    /**
     * code : 0
     * data : [{"balance":0,"create_at":"","id":0,"is_lock":0,"lock_balance":0,"lock_value_usd":"","price":0,"symbol":"","update_at":"","user_id":0,"value_usd":""}]
     * msg :
     * success : true
     */

    private int code;
    private String msg;
    private boolean success;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * balance : 0
         * create_at :
         * id : 0
         * is_lock : 0
         * lock_balance : 0
         * lock_value_usd :
         * price : 0
         * symbol :
         * update_at :
         * user_id : 0
         * value_usd :
         */

        private BigDecimal balance;
        private String create_at;
        private String id;
        private String is_lock;
        private BigDecimal lock_balance;
        private BigDecimal lock_value_usd;
        private BigDecimal price;
        private String symbol;
        private String update_at;
        private String user_id;
        private BigDecimal value_usd;

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(String is_lock) {
            this.is_lock = is_lock;
        }

        public BigDecimal getLock_balance() {
            return lock_balance;
        }

        public void setLock_balance(BigDecimal lock_balance) {
            this.lock_balance = lock_balance;
        }

        public BigDecimal getLock_value_usd() {
            return lock_value_usd;
        }

        public void setLock_value_usd(BigDecimal lock_value_usd) {
            this.lock_value_usd = lock_value_usd;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public BigDecimal getValue_usd() {
            return value_usd;
        }

        public void setValue_usd(BigDecimal value_usd) {
            this.value_usd = value_usd;
        }
    }
}
