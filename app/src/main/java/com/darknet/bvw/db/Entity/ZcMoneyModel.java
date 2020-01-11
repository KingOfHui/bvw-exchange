package com.darknet.bvw.db.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
@Entity
public class ZcMoneyModel {

    @Id(autoincrement = true)
    private Long id;
    private String symbol;
    private String icon;
    private String assetref;
//    private String qty;
    private String address;
    private String price;
    private String value_usd;
    private String value_cny;
    private String value_qty;//数量
    private String chain_deposit_address;

    @Generated(hash = 1776315727)
    public ZcMoneyModel(Long id, String symbol, String icon, String assetref,
            String address, String price, String value_usd, String value_cny,
            String value_qty, String chain_deposit_address) {
        this.id = id;
        this.symbol = symbol;
        this.icon = icon;
        this.assetref = assetref;
        this.address = address;
        this.price = price;
        this.value_usd = value_usd;
        this.value_cny = value_cny;
        this.value_qty = value_qty;
        this.chain_deposit_address = chain_deposit_address;
    }
    @Generated(hash = 1287942655)
    public ZcMoneyModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSymbol() {
        return this.symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getAssetref() {
        return this.assetref;
    }
    public void setAssetref(String assetref) {
        this.assetref = assetref;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getValue_usd() {
        return this.value_usd;
    }
    public void setValue_usd(String value_usd) {
        this.value_usd = value_usd;
    }
    public String getValue_cny() {
        return this.value_cny;
    }
    public void setValue_cny(String value_cny) {
        this.value_cny = value_cny;
    }
    public String getValue_qty() {
        return this.value_qty;
    }
    public void setValue_qty(String value_qty) {
        this.value_qty = value_qty;
    }


    @Override
    public String toString() {
        return "ZcMoneyModel{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", icon='" + icon + '\'' +
                ", assetref='" + assetref + '\'' +
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", value_usd='" + value_usd + '\'' +
                ", value_cny='" + value_cny + '\'' +
                ", value_qty='" + value_qty + '\'' +
                '}';
    }
    public String getChain_deposit_address() {
        return this.chain_deposit_address;
    }
    public void setChain_deposit_address(String chain_deposit_address) {
        this.chain_deposit_address = chain_deposit_address;
    }

}
