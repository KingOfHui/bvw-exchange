package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class InviteDataResponse {

    /**
     * lowerLevel1List : {"items":[{"address":"","bonus":0,"create_time":""}],"limit":0,"page":0,"totalCount":0,"totalPage":0}
     * lowerLevel1Num : 0
     * lowerLevelGt1Num : 0
     * lowerNum : 0
     */

    private LowerLevel1ListBean lowerLevel1List;
    private int lowerLevel1Num; //直推人数
    private int lowerLevelGt1Num; //间接推人数
    private int lowerNum; // 子属总人数
    private int lowerLevel1MinerNum; // 直推矿机台数
    private BigDecimal lowerMinerPower; //子属矿机总算力T

    public int getLowerLevel1MinerNum() {
        return lowerLevel1MinerNum;
    }

    public void setLowerLevel1MinerNum(int lowerLevel1MinerNum) {
        this.lowerLevel1MinerNum = lowerLevel1MinerNum;
    }

    public BigDecimal getLowerMinerPower() {
        return lowerMinerPower;
    }

    public void setLowerMinerPower(BigDecimal lowerMinerPower) {
        this.lowerMinerPower = lowerMinerPower;
    }

    public LowerLevel1ListBean getLowerLevel1List() {
        return lowerLevel1List;
    }

    public void setLowerLevel1List(LowerLevel1ListBean lowerLevel1List) {
        this.lowerLevel1List = lowerLevel1List;
    }

    public int getLowerLevel1Num() {
        return lowerLevel1Num;
    }

    public void setLowerLevel1Num(int lowerLevel1Num) {
        this.lowerLevel1Num = lowerLevel1Num;
    }

    public int getLowerLevelGt1Num() {
        return lowerLevelGt1Num;
    }

    public void setLowerLevelGt1Num(int lowerLevelGt1Num) {
        this.lowerLevelGt1Num = lowerLevelGt1Num;
    }

    public int getLowerNum() {
        return lowerNum;
    }

    public void setLowerNum(int lowerNum) {
        this.lowerNum = lowerNum;
    }

    public static class LowerLevel1ListBean {
        /**
         * items : [{"address":"","bonus":0,"create_time":""}]
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
             * address :
             * bonus : 0
             * create_time :
             */

            private String address;
            private String bonus;
            private String create_time;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBonus() {
                return bonus;
            }

            public void setBonus(String bonus) {
                this.bonus = bonus;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
