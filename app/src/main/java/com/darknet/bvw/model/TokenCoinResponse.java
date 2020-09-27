package com.darknet.bvw.model;

import java.util.List;

/**
 * 作者：created by albert on 2020-01-08 11:38
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class TokenCoinResponse {

    /**
     * code : 0
     * data : ["USDT","BTW"]
     * msg : success
     * success : true
     */

    private int code;
    private String msg;
    private boolean success;
    private List<String> data;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
