package com.darknet.bvw.qvkuaibao.bean;

import java.math.BigDecimal;

public class PosWallet {

    /**
     * cny_rate : 0  人民币汇率
     * invest_amount : 0  锁仓中金额
     * invest_amount_str : 锁仓中金额格式化
     * symbol : 币符号
     * usd_rate : 0 美元汇率
     */

    private BigDecimal cny_rate;
    private BigDecimal invest_amount;
    private String invest_amount_str;
    private String symbol;
    private BigDecimal usd_rate;
    private String yesterdayPosBonusAmount;

    public String getYesterdayPosBonusAmount() {
        return yesterdayPosBonusAmount;
    }

    public void setYesterdayPosBonusAmount(String yesterdayPosBonusAmount) {
        this.yesterdayPosBonusAmount = yesterdayPosBonusAmount;
    }

    public BigDecimal getCny_rate() {
        return cny_rate;
    }

    public void setCny_rate(BigDecimal cny_rate) {
        this.cny_rate = cny_rate;
    }

    public BigDecimal getInvest_amount() {
        return invest_amount;
    }

    public void setInvest_amount(BigDecimal invest_amount) {
        this.invest_amount = invest_amount;
    }

    public String getInvest_amount_str() {
        return invest_amount_str;
    }

    public void setInvest_amount_str(String invest_amount_str) {
        this.invest_amount_str = invest_amount_str;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getUsd_rate() {
        return usd_rate;
    }

    public void setUsd_rate(BigDecimal usd_rate) {
        this.usd_rate = usd_rate;
    }
}
