package com.darknet.bvw.model;

import java.math.BigDecimal;

public class MoneyModel {

    private int id;
    private String symbol;
    private String icon;
    private String assetref;
//    private String qty;
    private String address;
    private String price;
    private BigDecimal value_usd;
    private BigDecimal value_cny;
    private String value_qty;//数量
    private String chain_deposit_address;


    public String getChain_deposit_address() {
        return chain_deposit_address;
    }

    public void setChain_deposit_address(String chain_deposit_address) {
        this.chain_deposit_address = chain_deposit_address;
    }

    public String getValue_qty() {
        return value_qty;
    }

    public void setValue_qty(String value_qty) {
        this.value_qty = value_qty;
    }

    public BigDecimal getValue_usd() {
        return value_usd;
    }

    public void setValue_usd(BigDecimal value_usd) {
        this.value_usd = value_usd;
    }

    public BigDecimal getValue_cny() {
        return value_cny;
    }

    public void setValue_cny(BigDecimal value_cny) {
        this.value_cny = value_cny;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAssetref() {
        return assetref;
    }

    public void setAssetref(String assetref) {
        this.assetref = assetref;
    }

//    public String getQty() {
//        return qty;
//    }
//
//    public void setQty(String qty) {
//        this.qty = qty;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MoneyModel{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", icon='" + icon + '\'' +
                ", assetref='" + assetref + '\'' +
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", value_usd=" + value_usd +
                ", value_cny=" + value_cny +
                ", value_qty='" + value_qty + '\'' +
                '}';
    }
}
