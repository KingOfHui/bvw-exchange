package com.darknet.bvw.order.bean;

import java.math.BigDecimal;

/**
 * @ClassName CouponBean
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 17:14
 */
public class CouponBean {

    /**
     * id : 1
     * lang : zh-CN
     * name : 现金立减券全场通用
     * remark :
     * type : 1
     * state : 1
     * stock : 1000
     * discount : 50
     * price : 1000
     * create_time : 2020-06-09 19:13:11
     * start_time : 2020-06-09 19:13:11
     * end_time : 2021-06-09 19:13:11
     */

    private int id;
    private String lang;
    private String name;
    private String remark;
    private int type;
    private int state;
    private int stock;
    private BigDecimal discount;
    private BigDecimal price;
    private String create_time;
    private String start_time;
    private String end_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
