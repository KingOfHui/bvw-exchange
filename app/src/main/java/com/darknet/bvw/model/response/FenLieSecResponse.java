package com.darknet.bvw.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class FenLieSecResponse extends BaseResponse {


    private List<FenLieSendModel> data;

    public List<FenLieSendModel> getData() {
        return data;
    }

    public void setData(List<FenLieSendModel> data) {
        this.data = data;
    }

    public static class FenLieSendModel implements Serializable {

        private String id;
        private String level;
        private BigDecimal eth;
        private BigDecimal eth_bvw;
        private BigDecimal btc;
        private BigDecimal btc_bvw;
        private String address;
        private int status;
        private String create_time;
        private String update_time;
        private String complete_time;
        private BigDecimal bvw;
        private BigDecimal bvw_fl;
        private String stage;
        private String stage_name;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public BigDecimal getEth() {
            return eth;
        }

        public void setEth(BigDecimal eth) {
            this.eth = eth;
        }

        public BigDecimal getEth_bvw() {
            return eth_bvw;
        }

        public void setEth_bvw(BigDecimal eth_bvw) {
            this.eth_bvw = eth_bvw;
        }

        public BigDecimal getBtc() {
            return btc;
        }

        public void setBtc(BigDecimal btc) {
            this.btc = btc;
        }

        public BigDecimal getBtc_bvw() {
            return btc_bvw;
        }

        public void setBtc_bvw(BigDecimal btc_bvw) {
            this.btc_bvw = btc_bvw;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getComplete_time() {
            return complete_time;
        }

        public void setComplete_time(String complete_time) {
            this.complete_time = complete_time;
        }

        public BigDecimal getBvw() {
            return bvw;
        }

        public void setBvw(BigDecimal bvw) {
            this.bvw = bvw;
        }

        public BigDecimal getBvw_fl() {
            return bvw_fl;
        }

        public void setBvw_fl(BigDecimal bvw_fl) {
            this.bvw_fl = bvw_fl;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getStage_name() {
            return stage_name;
        }

        public void setStage_name(String stage_name) {
            this.stage_name = stage_name;
        }

        @Override
        public String toString() {
            return "FenLieSendModel{" +
                    "id='" + id + '\'' +
                    ", level='" + level + '\'' +
                    ", eth=" + eth +
                    ", eth_bvw=" + eth_bvw +
                    ", btc=" + btc +
                    ", btc_bvw=" + btc_bvw +
                    ", address='" + address + '\'' +
                    ", status=" + status +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", complete_time='" + complete_time + '\'' +
                    ", bvw=" + bvw +
                    ", bvw_fl=" + bvw_fl +
                    ", stage='" + stage + '\'' +
                    ", stage_name='" + stage_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FenLieSecResponse{" +
                "data=" + data +
                '}';
    }
}
