package com.darknet.bvw.model;

public class CreateHeaderModel {

    private String clientOsVersion;
    private String appVersion;
    private String clientOs;
    private String lang;
    private String clientId;



    public String getClientOsVersion() {
        return clientOsVersion;
    }

    public void setClientOsVersion(String clientOsVersion) {
        this.clientOsVersion = clientOsVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getClientOs() {
        return clientOs;
    }

    public void setClientOs(String clientOs) {
        this.clientOs = clientOs;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "CreateHeaderModel{" +
                "clientOsVersion='" + clientOsVersion + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", clientOs='" + clientOs + '\'' +
                ", lang='" + lang + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
