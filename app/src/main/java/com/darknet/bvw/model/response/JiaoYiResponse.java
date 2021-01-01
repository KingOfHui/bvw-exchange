package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class JiaoYiResponse extends BaseResponse {

    private List<JiaoYiData> data;

    public List<JiaoYiData> getData() {
        return data;
    }

    public void setData(List<JiaoYiData> data) {
        this.data = data;
    }

    public static class JiaoYiData{
        private BigDecimal amount;//成交数量
        private String amountStr;
        private String time;
        private int direction; //buy 0 sell 1
        private String price;//成交价格
        private String priceStr;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getAmountStr() {
            return amountStr;
        }

        public void setAmountStr(String amountStr) {
            this.amountStr = amountStr;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }
    }

}
