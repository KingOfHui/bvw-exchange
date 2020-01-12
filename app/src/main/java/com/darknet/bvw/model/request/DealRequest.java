package com.darknet.bvw.model.request;

public class DealRequest {

    private int limit;
    private int page;
    private String marketId;


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

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    @Override
    public String toString() {
        return "DealRequest{" +
                "limit=" + limit +
                ", page=" + page +
                ", marketId='" + marketId + '\'' +
                '}';
    }
}
