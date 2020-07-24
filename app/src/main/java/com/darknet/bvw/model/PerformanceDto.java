package com.darknet.bvw.model;

import com.darknet.bvw.commonlib.util.StringUtil;

import java.util.List;

public class PerformanceDto {
    //    	"bonus": "",
//                "bonus_confirm": "",
//                "bonus_rate": "",
//                "bonus_release": "",
//                "bonus_sum": "",
//                "performanceList": [
//    {
//        "bonus": "",
//            "date": "",
//            "performance": ""
//    }
//		]

    private String bonus;
    private String bonus_confirm;
    private double bonus_rate;
    private String bonus_release;
    private String bonus_sum;
    private List<PerformanceAtom> performanceList;
    private String snapshort_bvw_price;

    private String snapshort_hold_amount_bvw;

    private String snapshort_hold_amount;

    public String getBonus() {
        return StringUtil.isEmpty(bonus) ? "0" : bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getBonus_confirm() {
        return StringUtil.isEmpty(bonus_confirm) ? "0" : bonus_confirm;
    }

    public void setBonus_confirm(String bonus_confirm) {
        this.bonus_confirm = bonus_confirm;
    }

    public double getBonus_rate() {
        return bonus_rate;
    }

    public void setBonus_rate(double bonus_rate) {
        this.bonus_rate = bonus_rate;
    }

    public String getBonus_release() {
        return StringUtil.isEmpty(bonus_release) ? "0" : bonus_release;
    }

    public void setBonus_release(String bonus_release) {
        this.bonus_release = bonus_release;
    }

    public String getBonus_sum() {
        return StringUtil.isEmpty(bonus_sum) ? "0" : bonus_sum;
    }

    public void setBonus_sum(String bonus_sum) {
        this.bonus_sum = bonus_sum;
    }

    public List<PerformanceAtom> getPerformanceList() {
        return performanceList;
    }

    public void setPerformanceList(List<PerformanceAtom> performanceList) {
        this.performanceList = performanceList;
    }

    public String getSnapshort_bvw_price() {
        return snapshort_bvw_price;
    }

    public void setSnapshort_bvw_price(String snapshort_bvw_price) {
        this.snapshort_bvw_price = snapshort_bvw_price;
    }

    public String getSnapshort_hold_amount_bvw() {
        return snapshort_hold_amount_bvw;
    }

    public void setSnapshort_hold_amount_bvw(String snapshort_hold_amount_bvw) {
        this.snapshort_hold_amount_bvw = snapshort_hold_amount_bvw;
    }

    public String getSnapshort_hold_amount() {
        return snapshort_hold_amount;
    }

    public void setSnapshort_hold_amount(String snapshort_hold_amount) {
        this.snapshort_hold_amount = snapshort_hold_amount;
    }
}
