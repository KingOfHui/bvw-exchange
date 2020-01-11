package com.darknet.bvw.model;

/**
 * 作者：created by albert on 2020-01-06 11:42
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class CreateOrderRequest {

    /**
     * amount : 1.501
     * marketId : HOT-ETH
     * price : 0.0013
     * side : sell or buy
     * type : market or limit
     * userId : 0
     */

    private String amount;
    private String marketId;
    private String price;
    private String side;
    private String type;
    private String userId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
