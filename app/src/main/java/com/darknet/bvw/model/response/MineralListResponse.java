package com.darknet.bvw.model.response;

import java.io.Serializable;
import java.util.List;

public class MineralListResponse implements Serializable {


    /**
     * items : [{"create_at":"","f2pool_account":"","id":0,"lol_state":0,"minerInfo":{"base_electricity":"","base_name":"","base_out_symbol":"","base_pay_usdt":"","base_power":"","base_weight":"","base_years":"","net_btc_price":"","net_btw_price":"","net_day_bonus":"","net_make_time":"","net_miner_count":"","net_next_hard":"","net_power":"","pool_eu":"","pool_pay_time":"","pool_power":"","pool_us":"","pool_zh":""},"miner_no":"","miner_pwd":"","pay_amount":0,"pay_state":0,"pay_symbol":"","power":0,"power_btc_1_hour":"","power_btc_24_hour_avg":"","state":0,"update_at":"","user_address":"","user_id":0}]
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

    public static class ItemsBean implements Serializable{
        /**
         * create_at :
         * f2pool_account :
         * id : 0
         * lol_state : 0
         * minerInfo : {"base_electricity":"","base_name":"","base_out_symbol":"","base_pay_usdt":"","base_power":"","base_weight":"","base_years":"","net_btc_price":"","net_btw_price":"","net_day_bonus":"","net_make_time":"","net_miner_count":"","net_next_hard":"","net_power":"","pool_eu":"","pool_pay_time":"","pool_power":"","pool_us":"","pool_zh":""}
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
        private MinerInfoBean minerInfo;
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
        private int miner_type;

        public int getMiner_type() {
            return miner_type;
        }

        public void setMiner_type(int miner_type) {
            this.miner_type = miner_type;
        }

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

        public MinerInfoBean getMinerInfo() {
            return minerInfo;
        }

        public void setMinerInfo(MinerInfoBean minerInfo) {
            this.minerInfo = minerInfo;
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

        public static class MinerInfoBean implements Serializable{
            /**
             * base_electricity :
             * base_name :
             * base_out_symbol :
             * base_pay_usdt :
             * base_power :
             * base_weight :
             * base_years :
             * net_btc_price :
             * net_btw_price :
             * net_day_bonus :
             * net_make_time :
             * net_miner_count :
             * net_next_hard :
             * net_power :
             * pool_eu :
             * pool_pay_time :
             * pool_power :
             * pool_us :
             * pool_zh :
             */

            private String base_electricity;
            private String base_name;
            private String base_out_symbol;
            private String base_pay_usdt;
            private String base_power;
            private String base_weight;
            private String base_years;
            private String net_btc_price;
            private String net_btw_price;
            private String net_day_bonus;
            private String net_make_time;
            private String net_miner_count;
            private String net_next_hard;
            private String net_power;
            private String pool_eu;
            private String pool_pay_time;
            private String pool_power;
            private String pool_us;
            private String pool_zh;
            private String url_1;
            private String url_2;
            private String url_3;

            public String getUrl_1() {
                return url_1;
            }

            public void setUrl_1(String url_1) {
                this.url_1 = url_1;
            }

            public String getUrl_2() {
                return url_2;
            }

            public void setUrl_2(String url_2) {
                this.url_2 = url_2;
            }

            public String getUrl_3() {
                return url_3;
            }

            public void setUrl_3(String url_3) {
                this.url_3 = url_3;
            }

            public String getBase_electricity() {
                return base_electricity;
            }

            public void setBase_electricity(String base_electricity) {
                this.base_electricity = base_electricity;
            }

            public String getBase_name() {
                return base_name;
            }

            public void setBase_name(String base_name) {
                this.base_name = base_name;
            }

            public String getBase_out_symbol() {
                return base_out_symbol;
            }

            public void setBase_out_symbol(String base_out_symbol) {
                this.base_out_symbol = base_out_symbol;
            }

            public String getBase_pay_usdt() {
                return base_pay_usdt;
            }

            public void setBase_pay_usdt(String base_pay_usdt) {
                this.base_pay_usdt = base_pay_usdt;
            }

            public String getBase_power() {
                return base_power;
            }

            public void setBase_power(String base_power) {
                this.base_power = base_power;
            }

            public String getBase_weight() {
                return base_weight;
            }

            public void setBase_weight(String base_weight) {
                this.base_weight = base_weight;
            }

            public String getBase_years() {
                return base_years;
            }

            public void setBase_years(String base_years) {
                this.base_years = base_years;
            }

            public String getNet_btc_price() {
                return net_btc_price;
            }

            public void setNet_btc_price(String net_btc_price) {
                this.net_btc_price = net_btc_price;
            }

            public String getNet_btw_price() {
                return net_btw_price;
            }

            public void setNet_btw_price(String net_btw_price) {
                this.net_btw_price = net_btw_price;
            }

            public String getNet_day_bonus() {
                return net_day_bonus;
            }

            public void setNet_day_bonus(String net_day_bonus) {
                this.net_day_bonus = net_day_bonus;
            }

            public String getNet_make_time() {
                return net_make_time;
            }

            public void setNet_make_time(String net_make_time) {
                this.net_make_time = net_make_time;
            }

            public String getNet_miner_count() {
                return net_miner_count;
            }

            public void setNet_miner_count(String net_miner_count) {
                this.net_miner_count = net_miner_count;
            }

            public String getNet_next_hard() {
                return net_next_hard;
            }

            public void setNet_next_hard(String net_next_hard) {
                this.net_next_hard = net_next_hard;
            }

            public String getNet_power() {
                return net_power;
            }

            public void setNet_power(String net_power) {
                this.net_power = net_power;
            }

            public String getPool_eu() {
                return pool_eu;
            }

            public void setPool_eu(String pool_eu) {
                this.pool_eu = pool_eu;
            }

            public String getPool_pay_time() {
                return pool_pay_time;
            }

            public void setPool_pay_time(String pool_pay_time) {
                this.pool_pay_time = pool_pay_time;
            }

            public String getPool_power() {
                return pool_power;
            }

            public void setPool_power(String pool_power) {
                this.pool_power = pool_power;
            }

            public String getPool_us() {
                return pool_us;
            }

            public void setPool_us(String pool_us) {
                this.pool_us = pool_us;
            }

            public String getPool_zh() {
                return pool_zh;
            }

            public void setPool_zh(String pool_zh) {
                this.pool_zh = pool_zh;
            }
        }
    }
}
