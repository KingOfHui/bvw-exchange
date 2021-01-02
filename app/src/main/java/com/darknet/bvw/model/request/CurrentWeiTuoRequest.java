package com.darknet.bvw.model.request;

public class CurrentWeiTuoRequest {
    private String marketId;
    private String state;
    private int limit;
    private int page;
//    private int direction;

//    public int getDirection() {
//        return direction;
//    }
//
//    public void setDirection(int direction) {
//        this.direction = direction;
//    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
