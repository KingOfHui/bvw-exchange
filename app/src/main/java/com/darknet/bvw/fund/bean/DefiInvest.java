package com.darknet.bvw.fund.bean;

import java.math.BigDecimal;

public class DefiInvest {

    /**
     * amount : 0
     * create_time :
     * id : 0
     * product_amount : 0
     * product_id : 0
     * product_symbol :
     * quantity : 0
     * release_amount : 0
     * release_time :
     * release_trade_no :
     * release_x_hash :
     * release_x_status :
     * remark :
     * state : 0
     * update_time :
     * user_address :
     * user_id : 0
     */

    private String amount;
    private String create_time;
    private int id;
    private String product_amount;
    private int product_id;
    private String product_symbol;
    private String quantity;
    private String release_amount;
    private String release_time;
    private String release_trade_no;
    private String release_x_hash;
    private String release_x_status;
    private String remark;
    private int state;
    private String update_time;
    private String user_address;
    private int user_id;
    private int min_lock_days;
    private BigDecimal min_lock_release_rate;

    public int getMin_lock_days() {
        return min_lock_days;
    }

    public void setMin_lock_days(int min_lock_days) {
        this.min_lock_days = min_lock_days;
    }

    public BigDecimal getMin_lock_release_rate() {
        return min_lock_release_rate;
    }

    public void setMin_lock_release_rate(BigDecimal min_lock_release_rate) {
        this.min_lock_release_rate = min_lock_release_rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(String product_amount) {
        this.product_amount = product_amount;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_symbol() {
        return product_symbol;
    }

    public void setProduct_symbol(String product_symbol) {
        this.product_symbol = product_symbol;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRelease_amount() {
        return release_amount;
    }

    public void setRelease_amount(String release_amount) {
        this.release_amount = release_amount;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getRelease_trade_no() {
        return release_trade_no;
    }

    public void setRelease_trade_no(String release_trade_no) {
        this.release_trade_no = release_trade_no;
    }

    public String getRelease_x_hash() {
        return release_x_hash;
    }

    public void setRelease_x_hash(String release_x_hash) {
        this.release_x_hash = release_x_hash;
    }

    public String getRelease_x_status() {
        return release_x_status;
    }

    public void setRelease_x_status(String release_x_status) {
        this.release_x_status = release_x_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
