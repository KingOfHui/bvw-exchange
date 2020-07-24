package com.darknet.bvw.model;

public class PerformanceAtom {
    private String bonus;
    private String date;
    private String performance;
    private String bvwUsdPrice;
    private double bvwUsdRate;
    private String previous_bonus_date;
    private String snapshort_hold_amount;

    private String performance_bvw;

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getBvwUsdPrice() {
        return bvwUsdPrice;
    }

    public void setBvwUsdPrice(String bvwUsdPrice) {
        this.bvwUsdPrice = bvwUsdPrice;
    }

    public double getBvwUsdRate() {
        return bvwUsdRate;
    }

    public void setBvwUsdRate(double bvwUsdRate) {
        this.bvwUsdRate = bvwUsdRate;
    }

    public String getPrevious_bonus_date() {
        return previous_bonus_date;
    }

    public void setPrevious_bonus_date(String previous_bonus_date) {
        this.previous_bonus_date = previous_bonus_date;
    }

    public String getSnapshort_hold_amount() {
        return snapshort_hold_amount;
    }

    public void setSnapshort_hold_amount(String snapshort_hold_amount) {
        this.snapshort_hold_amount = snapshort_hold_amount;
    }

    public String getPerformance_bvw() {
        return performance_bvw;
    }

    public void setPerformance_bvw(String performance_bvw) {
        this.performance_bvw = performance_bvw;
    }
}
