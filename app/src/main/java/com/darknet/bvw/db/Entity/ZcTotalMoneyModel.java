package com.darknet.bvw.db.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ZcTotalMoneyModel {

    @Id(autoincrement = true)
    private Long id;
    private String totalCny;
    private String totalUsd;
    @Generated(hash = 1497267168)
    public ZcTotalMoneyModel(Long id, String totalCny, String totalUsd) {
        this.id = id;
        this.totalCny = totalCny;
        this.totalUsd = totalUsd;
    }
    @Generated(hash = 474094632)
    public ZcTotalMoneyModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTotalCny() {
        return this.totalCny;
    }
    public void setTotalCny(String totalCny) {
        this.totalCny = totalCny;
    }
    public String getTotalUsd() {
        return this.totalUsd;
    }
    public void setTotalUsd(String totalUsd) {
        this.totalUsd = totalUsd;
    }




}
