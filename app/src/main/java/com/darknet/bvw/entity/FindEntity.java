package com.darknet.bvw.entity;

public class FindEntity {
    private String title;
    private String des;
    private Integer icon;

    public FindEntity(String title, String des, Integer icon) {
        this.title = title;
        this.des = des;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
