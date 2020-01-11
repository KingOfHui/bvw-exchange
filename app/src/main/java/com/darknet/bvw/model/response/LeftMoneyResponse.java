package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class LeftMoneyResponse extends BaseResponse {

    private List<LeftMoneyModel> data;

    public List<LeftMoneyModel> getData() {
        return data;
    }

    public void setData(List<LeftMoneyModel> data) {
        this.data = data;
    }

    //    public static class LeftMoneyData{
//
//
//
//
//    }

    public static class LeftMoneyModel {
        private String name;
        private String assetref;
        //        private String qty;
        private String address;
        private BigDecimal value_usd;//美元
        private BigDecimal value_cny;//人民币
        private String value_qty;//数量
        private String icon;
        private String chain_deposit_address;

        public String getChain_deposit_address() {
            return chain_deposit_address;
        }

        public void setChain_deposit_address(String chain_deposit_address) {
            this.chain_deposit_address = chain_deposit_address;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getValue_qty() {
            return value_qty;
        }

        public void setValue_qty(String value_qty) {
            this.value_qty = value_qty;
        }

        public BigDecimal getValue_usd() {
            return value_usd;
        }

        public void setValue_usd(BigDecimal value_usd) {
            this.value_usd = value_usd;
        }

        public BigDecimal getValue_cny() {
            return value_cny;
        }

        public void setValue_cny(BigDecimal value_cny) {
            this.value_cny = value_cny;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAssetref() {
            return assetref;
        }

        public void setAssetref(String assetref) {
            this.assetref = assetref;
        }

//        public String getQty() {
//            return qty;
//        }
//
//        public void setQty(String qty) {
//            this.qty = qty;
//        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "LeftMoneyModel{" +
                    "name='" + name + '\'' +
                    ", assetref='" + assetref + '\'' +
                    ", address='" + address + '\'' +
                    ", value_usd=" + value_usd +
                    ", value_cny=" + value_cny +
                    ", value_qty='" + value_qty + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "LeftMoneyResponse{" +
                "data=" + data +
                '}';
    }
}
