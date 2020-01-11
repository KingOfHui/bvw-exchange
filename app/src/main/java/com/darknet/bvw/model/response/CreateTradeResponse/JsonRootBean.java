/**
  * Copyright 2019 bejson.com 
  */
package com.darknet.bvw.model.response.CreateTradeResponse;

/**
 * Auto-generated: 2019-11-08 15:31:43
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int code;
    private String msg;
    private SendTx data;
    private boolean success;

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

    public SendTx getData() {
        return data;
    }

    public void setData(SendTx data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}