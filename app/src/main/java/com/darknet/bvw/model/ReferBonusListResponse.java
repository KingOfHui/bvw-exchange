package com.darknet.bvw.model;

import java.util.List;

public class ReferBonusListResponse {

    /**
     * items : [{"bonus_date":"","create_at":"","id":0,"refer_bonus":0,"refer_user_address":"","refer_user_id":0,"user_id":0}]
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
         * bonus_date :
         * create_at :
         * id : 0
         * refer_bonus : 0
         * refer_user_address :
         * refer_user_id : 0
         * user_id : 0
         */

        private String bonus_date;
        private String create_at;
        private String id;
        private String refer_bonus;
        private String refer_user_address;
        private String refer_user_id;
        private String user_id;

        public String getBonus_date() {
            return bonus_date;
        }

        public void setBonus_date(String bonus_date) {
            this.bonus_date = bonus_date;
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

        public String getRefer_bonus() {
            return refer_bonus;
        }

        public void setRefer_bonus(String refer_bonus) {
            this.refer_bonus = refer_bonus;
        }

        public String getRefer_user_address() {
            return refer_user_address;
        }

        public void setRefer_user_address(String refer_user_address) {
            this.refer_user_address = refer_user_address;
        }

        public String getRefer_user_id() {
            return refer_user_id;
        }

        public void setRefer_user_id(String refer_user_id) {
            this.refer_user_id = refer_user_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
