package com.darknet.bvw.model.request;

public class SendTradeRequest {

    private String amount;
    private String symbol;
    private String to_address;
    private int type;
    private String raw;
    private String withdraw_address;
    private String demo;
    private int miner_id;
    private int coupon_template_id;

    public int getCoupon_template_id() {
        return coupon_template_id;
    }

    public void setCoupon_template_id(int coupon_template_id) {
        this.coupon_template_id = coupon_template_id;
    }

    public int getMiner_id() {
        return miner_id;
    }

    public void setMiner_id(int miner_id) {
        this.miner_id = miner_id;
    }

    private String referer_code;

    public String getReferer_code() {
        return referer_code;
    }

    public void setReferer_code(String referer_code) {
        this.referer_code = referer_code;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    //    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getWithdraw_address() {
        return withdraw_address;
    }

    public void setWithdraw_address(String withdraw_address) {
        this.withdraw_address = withdraw_address;
    }
}
