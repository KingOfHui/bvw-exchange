package com.darknet.bvw.order.bean;

import java.math.BigDecimal;

public class MyCouponBean {

    /**
     * coupon_template_id : 0
     * create_time :
     * discount : 0
     * end_time :
     * id : 0
     * name :
     * price : 0
     * remark :
     * state : 0
     * tx_hash :
     * type : 0
     * user_id : 0
     */

    private int coupon_template_id;
    private String create_time;
    private int discount;
    private String end_time;
    private int id;
    private String name;
    private BigDecimal price;
    private String remark;
    private int state;
    private String tx_hash;
    private int type;
    private int user_id;

    public int getCoupon_template_id() {
        return coupon_template_id;
    }

    public void setCoupon_template_id(int coupon_template_id) {
        this.coupon_template_id = coupon_template_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
