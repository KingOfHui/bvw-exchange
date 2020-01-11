package com.darknet.bvw.model;

import java.io.Serializable;
import java.util.List;

public class BtcDo implements Serializable {

    private String privateKey;
    private String publicKey;
    private List<String> listZjc;
    private String address;
    private String keystorePath;
    private String keyStoreVal;
    private String zjcContent;


    public String getZjcContent() {
        return zjcContent;
    }

    public void setZjcContent(String zjcContent) {
        this.zjcContent = zjcContent;
    }

    public String getKeyStoreVal() {
        return keyStoreVal;
    }

    public void setKeyStoreVal(String keyStoreVal) {
        this.keyStoreVal = keyStoreVal;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public List<String> getListZjc() {
        return listZjc;
    }

    public void setListZjc(List<String> listZjc) {
        this.listZjc = listZjc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    @Override
    public String toString() {
        return "BtcDo{" +
                "privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", listZjc=" + listZjc +
                ", address='" + address + '\'' +
                ", keystorePath='" + keystorePath + '\'' +
                '}';
    }
}
