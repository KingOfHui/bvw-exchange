package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class TopResponse extends BaseResponse {


    private List<TopModel> data;

    public List<TopModel> getData() {
        return data;
    }

    public void setData(List<TopModel> data) {
        this.data = data;
    }

    public static class TopModel{
        private String address;
        private String refer_count;
        private String bonus_rate;
        private String sum_bvw;
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

        public String getRefer_count() {
            return refer_count;
        }

        public void setRefer_count(String refer_count) {
            this.refer_count = refer_count;
        }

        public String getBonus_rate() {
            return bonus_rate;
        }

        public void setBonus_rate(String bonus_rate) {
            this.bonus_rate = bonus_rate;
        }

        public String getSum_bvw() {
            return sum_bvw;
        }

        public void setSum_bvw(String sum_bvw) {
            this.sum_bvw = sum_bvw;
        }

        @Override
        public String toString() {
            return "TopModel{" +
                    "address='" + address + '\'' +
                    ", refer_count='" + refer_count + '\'' +
                    ", bonus_rate='" + bonus_rate + '\'' +
                    ", sum_bvw='" + sum_bvw + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "TopResponse{" +
                "data=" + data +
                '}';
    }
}
