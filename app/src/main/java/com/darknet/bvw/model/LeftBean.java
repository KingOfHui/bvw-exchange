package com.darknet.bvw.model;

/**
 * 作者：created by albert on 2020-01-10 11:18
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class LeftBean {

    private String coins;
    private int color;

    public LeftBean(String coins, int color) {
        this.coins = coins;
        this.color = color;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
