package com.darknet.bvw.qvkuaibao.bean;

import java.math.BigDecimal;

public class PosWalletData {

    /**
     * dayRate :
     * posBonusAmount :
     * posInvestAmount :
     * symbol :
     * vipDegree : 0
     * yesterdayPosBonusAmount :
     */

    private String dayRate;  //日利率
    private String posBonusAmount;  //个人历史总收益=利息收益+返佣收益
    private BigDecimal posInvestAmount;  //个人质押总金额
    private String symbol;  //质押币种
    private int vipDegree;  //会员等级
    private String yesterdayPosBonusAmount;  //昨日收益

    public String getDayRate() {
        return dayRate;
    }

    public void setDayRate(String dayRate) {
        this.dayRate = dayRate;
    }

    public String getPosBonusAmount() {
        return posBonusAmount;
    }

    public void setPosBonusAmount(String posBonusAmount) {
        this.posBonusAmount = posBonusAmount;
    }

    public BigDecimal getPosInvestAmount() {
        return posInvestAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getVipDegree() {
        return vipDegree;
    }

    public void setVipDegree(int vipDegree) {
        this.vipDegree = vipDegree;
    }

    public String getYesterdayPosBonusAmount() {
        return yesterdayPosBonusAmount;
    }

    public void setYesterdayPosBonusAmount(String yesterdayPosBonusAmount) {
        this.yesterdayPosBonusAmount = yesterdayPosBonusAmount;
    }
}
