package com.darknet.bvw.model.response.CreateTradeResponse;

public class Permission {

    String address = null;
    String type = null;
    Long startblock = null;
    Long endblock = null;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStartblock() {
        return startblock;
    }

    public void setStartblock(Long startblock) {
        this.startblock = startblock;
    }

    public Long getEndblock() {
        return endblock;
    }

    public void setEndblock(Long endblock) {
        this.endblock = endblock;
    }
}
