package com.darknet.bvw.model;

/**
 * 作者：created by albert on 2020-01-05 13:17
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class TradeRequest {


    /**
     * limit : 0
     * marketId : HOT-ETH
     * page : 0
     */

    private int limit;
    private String marketId;
    private int page;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
