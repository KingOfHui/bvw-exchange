package com.darknet.bvw.qvkuaibao.bean;

import java.math.BigDecimal;

public class PosProduct {

    /**
     * amount : 0  产品额度
     * amount_scala : 0 投资金额小数精度
     * create_time :  创建时间
     * days : 0   产品天数
     * id : 0
     * invest_max_amount : 0  最大投资金额
     * invest_min_amount : 0  最小投资金额
     * keyword  关键字
     * off_date :  活动截止日期
     * pro_name :  产品名称
     * pro_no :    产品编号
     * release_days : 0  平均返本天数
     * sort : 0  排序
     * state : 0  产品状态 0未开标 1开标中 2已结束
     * symbol :  币种
     * type : 0   pos类型 1 随进随出 2固定期限
     * year_rate : 0   年华利率
     */

    private BigDecimal amount;
    private BigDecimal amount_scala;
    private String create_time;
    private int days;
    private int id;
    private BigDecimal invest_max_amount;
    private BigDecimal invest_min_amount;
    private String keyword;
    private String off_date;
    private String pro_name;
    private String pro_no;
    private int release_days;
    private int sort;
    private int state;
    private String symbol;
    private int type;
    private BigDecimal year_rate;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount_scala() {
        return amount_scala;
    }

    public void setAmount_scala(BigDecimal amount_scala) {
        this.amount_scala = amount_scala;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getInvest_max_amount() {
        return invest_max_amount;
    }

    public void setInvest_max_amount(BigDecimal invest_max_amount) {
        this.invest_max_amount = invest_max_amount;
    }

    public BigDecimal getInvest_min_amount() {
        return invest_min_amount;
    }

    public void setInvest_min_amount(BigDecimal invest_min_amount) {
        this.invest_min_amount = invest_min_amount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOff_date() {
        return off_date;
    }

    public void setOff_date(String off_date) {
        this.off_date = off_date;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_no() {
        return pro_no;
    }

    public void setPro_no(String pro_no) {
        this.pro_no = pro_no;
    }

    public int getRelease_days() {
        return release_days;
    }

    public void setRelease_days(int release_days) {
        this.release_days = release_days;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getYear_rate() {
        return year_rate;
    }

    public void setYear_rate(BigDecimal year_rate) {
        this.year_rate = year_rate;
    }
}
