package com.darknet.bvw.model.event;

public class SellAndBuyEvent {

    public static final int BUY = 0;

    public static final int SELL = 1;
    /**
     * 0买入，1卖出
     */
    private int type;

    private String marketId;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
