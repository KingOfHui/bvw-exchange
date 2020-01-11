package com.darknet.bvw.model.response;

import java.math.BigDecimal;

public class FenLieFirstResponse extends BaseResponse {


    private FenLieFirstData data;

    public FenLieFirstData getData() {
        return data;
    }

    public void setData(FenLieFirstData data) {
        this.data = data;
    }

    public static class FenLieFirstData {

        private BigDecimal btc;
        private BigDecimal btc_fl;
        private BigDecimal btc_left;
        private BigDecimal btc_total;
        private BigDecimal bvw;
        private BigDecimal bvw_destroy;
        private BigDecimal eth;
        private BigDecimal bvw_left;
        private BigDecimal eth_left;
        private BigDecimal bvw_fl;
        private BigDecimal eth_fl;
        private BigDecimal bvw_total;
        private BigDecimal eth_total;
        private String stage_name;
        private String stage_name_en;
        private String stage_scale;

        private String next_stage_scale;
        private String next_stage_height;
        private String next_stage_tip_cn;
        private String next_stage_tip_en;

        private String stage;
        private String height;

        public String getStage_scale() {
            return stage_scale;
        }

        public void setStage_scale(String stage_scale) {
            this.stage_scale = stage_scale;
        }

        public String getNext_stage_scale() {
            return next_stage_scale;
        }

        public void setNext_stage_scale(String next_stage_scale) {
            this.next_stage_scale = next_stage_scale;
        }

        public String getNext_stage_height() {
            return next_stage_height;
        }

        public void setNext_stage_height(String next_stage_height) {
            this.next_stage_height = next_stage_height;
        }

        public String getNext_stage_tip_cn() {
            return next_stage_tip_cn;
        }

        public void setNext_stage_tip_cn(String next_stage_tip_cn) {
            this.next_stage_tip_cn = next_stage_tip_cn;
        }

        public String getNext_stage_tip_en() {
            return next_stage_tip_en;
        }

        public void setNext_stage_tip_en(String next_stage_tip_en) {
            this.next_stage_tip_en = next_stage_tip_en;
        }

        public String getStage_name_en() {
            return stage_name_en;
        }

        public void setStage_name_en(String stage_name_en) {
            this.stage_name_en = stage_name_en;
        }

        public String getStage_name() {
            return stage_name;
        }

        public void setStage_name(String stage_name) {
            this.stage_name = stage_name;
        }

        public BigDecimal getBtc() {
            return btc;
        }

        public void setBtc(BigDecimal btc) {
            this.btc = btc;
        }

        public BigDecimal getBtc_fl() {
            return btc_fl;
        }

        public void setBtc_fl(BigDecimal btc_fl) {
            this.btc_fl = btc_fl;
        }

        public BigDecimal getBtc_left() {
            return btc_left;
        }

        public void setBtc_left(BigDecimal btc_left) {
            this.btc_left = btc_left;
        }

        public BigDecimal getBtc_total() {
            return btc_total;
        }

        public void setBtc_total(BigDecimal btc_total) {
            this.btc_total = btc_total;
        }

        public BigDecimal getBvw() {
            return bvw;
        }

        public void setBvw(BigDecimal bvw) {
            this.bvw = bvw;
        }

        public BigDecimal getBvw_destroy() {
            return bvw_destroy;
        }

        public void setBvw_destroy(BigDecimal bvw_destroy) {
            this.bvw_destroy = bvw_destroy;
        }

        public BigDecimal getEth() {
            return eth;
        }

        public void setEth(BigDecimal eth) {
            this.eth = eth;
        }

        public BigDecimal getBvw_left() {
            return bvw_left;
        }

        public void setBvw_left(BigDecimal bvw_left) {
            this.bvw_left = bvw_left;
        }

        public BigDecimal getEth_left() {
            return eth_left;
        }

        public void setEth_left(BigDecimal eth_left) {
            this.eth_left = eth_left;
        }

        public BigDecimal getBvw_fl() {
            return bvw_fl;
        }

        public void setBvw_fl(BigDecimal bvw_fl) {
            this.bvw_fl = bvw_fl;
        }

        public BigDecimal getEth_fl() {
            return eth_fl;
        }

        public void setEth_fl(BigDecimal eth_fl) {
            this.eth_fl = eth_fl;
        }

        public BigDecimal getBvw_total() {
            return bvw_total;
        }

        public void setBvw_total(BigDecimal bvw_total) {
            this.bvw_total = bvw_total;
        }

        public BigDecimal getEth_total() {
            return eth_total;
        }

        public void setEth_total(BigDecimal eth_total) {
            this.eth_total = eth_total;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "FenLieFirstData{" +
                    "btc=" + btc +
                    ", btc_fl=" + btc_fl +
                    ", btc_left=" + btc_left +
                    ", btc_total=" + btc_total +
                    ", bvw=" + bvw +
                    ", bvw_destroy=" + bvw_destroy +
                    ", eth=" + eth +
                    ", bvw_left=" + bvw_left +
                    ", eth_left=" + eth_left +
                    ", bvw_fl=" + bvw_fl +
                    ", eth_fl=" + eth_fl +
                    ", bvw_total=" + bvw_total +
                    ", eth_total=" + eth_total +
                    ", stage='" + stage + '\'' +
                    ", height='" + height + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FenLieFirstResponse{" +
                "data=" + data +
                '}';
    }
}
