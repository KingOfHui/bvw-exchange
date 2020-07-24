package com.darknet.bvw.model;

import java.util.List;

public class kLineHisResponse {

    //{"code":0,"msg":"success","data":[[1578498300000,0,0,0,0,0]],"success":true}
    private int code;
    private String msg;
    //[1578498300000,0,0,0,0,0]  时间 开 高 低 收 量
    private List<List<String>> data;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
