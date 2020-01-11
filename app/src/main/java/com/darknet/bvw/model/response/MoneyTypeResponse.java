package com.darknet.bvw.model.response;

import java.util.List;

public class MoneyTypeResponse extends BaseResponse {

    private List<MoneyTypeModel> data;

    public List<MoneyTypeModel> getData() {
        return data;
    }

    public void setData(List<MoneyTypeModel> data) {
        this.data = data;
    }

    public static class MoneyTypeModel{
        private int id;
        private String symbol;
        private String icon;
        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return "MoneyTypeModel{" +
                    "id=" + id +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MoneyTypeResponse{" +
                "data=" + data +
                '}';
    }
}
