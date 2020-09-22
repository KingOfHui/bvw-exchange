package com.darknet.bvw.model;

import java.util.List;

public class MineralBonusListResponse {

    /**
     * items : [{"bonus_all":0,"bonus_big_node":0,"bonus_big_pool":0,"bonus_btc":0,"bonus_date":"","bonus_miner":0,"bonus_refer":0,"bonus_small_pool":0,"btc_pay_status":"","btc_tx_hash":"","btw_pay_status":"","btw_tx_hash":"","create_at":"","id":0,"payment_btc_id":0,"payment_id":0,"update_at":"","user_id":0}]
     * limit : 0
     * page : 0
     * totalCount : 0
     * totalPage : 0
     */

    private String limit;
    private String page;
    private String totalCount;
    private String totalPage;
    private List<ItemsBean> items;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
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
         * bonus_all : 0
         * bonus_big_node : 0
         * bonus_big_pool : 0
         * bonus_btc : 0
         * bonus_date :
         * bonus_miner : 0
         * bonus_refer : 0
         * bonus_small_pool : 0
         * btc_pay_status :
         * btc_tx_hash :
         * btw_pay_status :
         * btw_tx_hash :
         * create_at :
         * id : 0
         * payment_btc_id : 0
         * payment_id : 0
         * update_at :
         * user_id : 0
         */

        private String bonus_all;
        private String bonus_big_node;
        private String bonus_big_pool;
        private String bonus_btc;
        private String bonus_date;
        private String bonus_miner;
        private String bonus_refer;
        private String bonus_small_pool;
        private String btc_pay_status;
        private String btc_tx_hash;
        private String btw_pay_status;
        private String btw_tx_hash;
        private String create_at;
        private String id;
        private String payment_btc_id;
        private String payment_id;
        private String update_at;
        private String user_id;

        public String getBonus_all() {
            return bonus_all;
        }

        public void setBonus_all(String bonus_all) {
            this.bonus_all = bonus_all;
        }

        public String getBonus_big_node() {
            return bonus_big_node;
        }

        public void setBonus_big_node(String bonus_big_node) {
            this.bonus_big_node = bonus_big_node;
        }

        public String getBonus_big_pool() {
            return bonus_big_pool;
        }

        public void setBonus_big_pool(String bonus_big_pool) {
            this.bonus_big_pool = bonus_big_pool;
        }

        public String getBonus_btc() {
            return bonus_btc;
        }

        public void setBonus_btc(String bonus_btc) {
            this.bonus_btc = bonus_btc;
        }

        public String getBonus_date() {
            return bonus_date;
        }

        public void setBonus_date(String bonus_date) {
            this.bonus_date = bonus_date;
        }

        public String getBonus_miner() {
            return bonus_miner;
        }

        public void setBonus_miner(String bonus_miner) {
            this.bonus_miner = bonus_miner;
        }

        public String getBonus_refer() {
            return bonus_refer;
        }

        public void setBonus_refer(String bonus_refer) {
            this.bonus_refer = bonus_refer;
        }

        public String getBonus_small_pool() {
            return bonus_small_pool;
        }

        public void setBonus_small_pool(String bonus_small_pool) {
            this.bonus_small_pool = bonus_small_pool;
        }

        public String getBtc_pay_status() {
            return btc_pay_status;
        }

        public void setBtc_pay_status(String btc_pay_status) {
            this.btc_pay_status = btc_pay_status;
        }

        public String getBtc_tx_hash() {
            return btc_tx_hash;
        }

        public void setBtc_tx_hash(String btc_tx_hash) {
            this.btc_tx_hash = btc_tx_hash;
        }

        public String getBtw_pay_status() {
            return btw_pay_status;
        }

        public void setBtw_pay_status(String btw_pay_status) {
            this.btw_pay_status = btw_pay_status;
        }

        public String getBtw_tx_hash() {
            return btw_tx_hash;
        }

        public void setBtw_tx_hash(String btw_tx_hash) {
            this.btw_tx_hash = btw_tx_hash;
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

        public String getPayment_btc_id() {
            return payment_btc_id;
        }

        public void setPayment_btc_id(String payment_btc_id) {
            this.payment_btc_id = payment_btc_id;
        }

        public String getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(String payment_id) {
            this.payment_id = payment_id;
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
    }
}
