package com.darknet.bvw.model.response.CreateTradeResponse;

import java.util.ArrayList;
import java.util.List;

public class ScriptPubKey {

    String asm = null;
    String hex = null;
    Long reqSigs = null;
    String type = null;
    List<String> addresses = new ArrayList<>();

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Long getReqSigs() {
        return reqSigs;
    }

    public void setReqSigs(Long reqSigs) {
        this.reqSigs = reqSigs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
}
