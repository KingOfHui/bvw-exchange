package com.darknet.bvw.db.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShippingAddressModel {
    @Id(autoincrement = true)
    private Long id;
    private int isDefault;
    private String name;
    private String mobile;
    private String address;
    @Generated(hash = 2065407335)
    public ShippingAddressModel(Long id, int isDefault, String name, String mobile,
            String address) {
        this.id = id;
        this.isDefault = isDefault;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
    }
    @Generated(hash = 1840487315)
    public ShippingAddressModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getIsDefault() {
        return this.isDefault;
    }
    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}
