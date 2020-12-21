package com.darknet.bvw.order.bean;

import java.io.Serializable;

/**
 * @ClassName ShippingAddress
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 13:54
 */
public class ShippingAddress implements Serializable {

    /**
     * city :
     * county :
     * default_state : 0
     * detail_info :
     * nation :
     * postal :
     * province :
     * tel_number :
     * user_name :
     */

    private int id;
    private String user_id;
    private String city;
    private String county;
    private int default_state;
    private String detail_info;
    private String nation;
    private String postal;
    private String province;
    private String tel_number;
    private String user_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getDefault_state() {
        return default_state;
    }

    public void setDefault_state(int default_state) {
        this.default_state = default_state;
    }

    public String getDetail_info() {
        return detail_info;
    }

    public void setDetail_info(String detail_info) {
        this.detail_info = detail_info;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
