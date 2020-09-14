package com.darknet.bvw.model.response;

import java.io.Serializable;
import java.util.List;

public class MineralListResponse {

    /**
     * items : [{"create_at":"","f2pool_account":"","id":0,"lol_state":0,"miner_no":"","miner_pwd":"","pay_amount":0,"pay_state":0,"pay_symbol":"","power":0,"power_btc_1_hour":"","power_btc_24_hour_avg":"","state":0,"update_at":"","user_address":"","user_id":0}]
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

    public static class ItemsBean implements Serializable {
        /**
         * create_at :
         * f2pool_account :
         * id : 0
         * lol_state : 0
         * miner_no :
         * miner_pwd :
         * pay_amount : 0
         * pay_state : 0
         * pay_symbol :
         * power : 0
         * power_btc_1_hour :
         * power_btc_24_hour_avg :
         * state : 0
         * update_at :
         * user_address :
         * user_id : 0
         */

        private String create_at;
        private String f2pool_account;
        private int id;
        private int lol_state;
        private String miner_no;
        private String miner_pwd;
        private int pay_amount;
        private int pay_state;
        private String pay_symbol;
        private int power;
        private String power_btc_1_hour;
        private String power_btc_24_hour_avg;
        private int state;
        private String update_at;
        private String user_address;
        private int user_id;

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getF2pool_account() {
            return f2pool_account;
        }

        public void setF2pool_account(String f2pool_account) {
            this.f2pool_account = f2pool_account;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLol_state() {
            return lol_state;
        }

        public void setLol_state(int lol_state) {
            this.lol_state = lol_state;
        }

        public String getMiner_no() {
            return miner_no;
        }

        public void setMiner_no(String miner_no) {
            this.miner_no = miner_no;
        }

        public String getMiner_pwd() {
            return miner_pwd;
        }

        public void setMiner_pwd(String miner_pwd) {
            this.miner_pwd = miner_pwd;
        }

        public int getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(int pay_amount) {
            this.pay_amount = pay_amount;
        }

        public int getPay_state() {
            return pay_state;
        }

        public void setPay_state(int pay_state) {
            this.pay_state = pay_state;
        }

        public String getPay_symbol() {
            return pay_symbol;
        }

        public void setPay_symbol(String pay_symbol) {
            this.pay_symbol = pay_symbol;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public String getPower_btc_1_hour() {
            return power_btc_1_hour;
        }

        public void setPower_btc_1_hour(String power_btc_1_hour) {
            this.power_btc_1_hour = power_btc_1_hour;
        }

        public String getPower_btc_24_hour_avg() {
            return power_btc_24_hour_avg;
        }

        public void setPower_btc_24_hour_avg(String power_btc_24_hour_avg) {
            this.power_btc_24_hour_avg = power_btc_24_hour_avg;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
