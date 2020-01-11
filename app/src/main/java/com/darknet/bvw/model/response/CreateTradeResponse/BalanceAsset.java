package com.darknet.bvw.model.response.CreateTradeResponse;

import java.math.BigDecimal;
import java.util.Map;

public class BalanceAsset {

    String name = null;
    String issuetxid = null;
    String assetref = null;
    Long multiple = null;
    Double units = null;
    Boolean open = null;
    Map<String, String> details = null;
    BigDecimal qty = null;
    Long raw = null;
    Double issueqty = null;
    Long issueraw = null;
    Boolean subscribed = null;
    String type = null;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuetxid() {
        return issuetxid;
    }

    public void setIssuetxid(String issuetxid) {
        this.issuetxid = issuetxid;
    }

    public String getAssetref() {
        return assetref;
    }

    public void setAssetref(String assetref) {
        this.assetref = assetref;
    }

    public Long getMultiple() {
        return multiple;
    }

    public void setMultiple(Long multiple) {
        this.multiple = multiple;
    }

    public Double getUnits() {
        return units;
    }

    public void setUnits(Double units) {
        this.units = units;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Long getRaw() {
        return raw;
    }

    public void setRaw(Long raw) {
        this.raw = raw;
    }

    public Double getIssueqty() {
        return issueqty;
    }

    public void setIssueqty(Double issueqty) {
        this.issueqty = issueqty;
    }

    public Long getIssueraw() {
        return issueraw;
    }

    public void setIssueraw(Long issueraw) {
        this.issueraw = issueraw;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
