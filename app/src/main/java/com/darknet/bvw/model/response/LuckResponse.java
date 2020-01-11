package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class LuckResponse extends BaseResponse{

    private List<LuckModel> data;

    public List<LuckModel> getData() {
        return data;
    }

    public void setData(List<LuckModel> data) {
        this.data = data;
    }

    public static class LuckModel{
        private String address;
        private String orders;
        private String bonus_rate;
        private String pay_amount;
        private BigDecimal bonus;

        public BigDecimal getBonus() {
            return bonus;
        }

        public void setBonus(BigDecimal bonus) {
            this.bonus = bonus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getBonus_rate() {
            return bonus_rate;
        }

        public void setBonus_rate(String bonus_rate) {
            this.bonus_rate = bonus_rate;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        @Override
        public String toString() {
            return "LuckModel{" +
                    "address='" + address + '\'' +
                    ", orders='" + orders + '\'' +
                    ", bonus_rate='" + bonus_rate + '\'' +
                    ", pay_amount='" + pay_amount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LuckResponse{" +
                "data=" + data +
                '}';
    }
}
