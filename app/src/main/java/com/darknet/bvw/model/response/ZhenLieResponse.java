package com.darknet.bvw.model.response;

import java.math.BigDecimal;

public class ZhenLieResponse extends BaseResponse {


    private ZhenLieData data;

    public ZhenLieData getData() {
        return data;
    }

    public void setData(ZhenLieData data) {
        this.data = data;
    }

    public static class ZhenLieData{
        private ZhenLieLeftModel root;

        public ZhenLieLeftModel getRoot() {
            return root;
        }

        public void setRoot(ZhenLieLeftModel root) {
            this.root = root;
        }

        @Override
        public String toString() {
            return "ZhenLieData{" +
                    "root=" + root +
                    '}';
        }
    }


    public static class ZhenLieLeftModel{


        private String leftTreeCount;
        private String rightTreeCount;
        private String additionTimePower;
        private String bidPower;
        private BigDecimal bidRate;
        private BigDecimal leftBidShowPower;
        private BigDecimal rightBidShowPower;
        private BigDecimal bidShowPower;


        public BigDecimal getLeftBidShowPower() {
            return leftBidShowPower;
        }

        public void setLeftBidShowPower(BigDecimal leftBidShowPower) {
            this.leftBidShowPower = leftBidShowPower;
        }

        public BigDecimal getRightBidShowPower() {
            return rightBidShowPower;
        }

        public void setRightBidShowPower(BigDecimal rightBidShowPower) {
            this.rightBidShowPower = rightBidShowPower;
        }

        public BigDecimal getBidShowPower() {
            return bidShowPower;
        }

        public void setBidShowPower(BigDecimal bidShowPower) {
            this.bidShowPower = bidShowPower;
        }

        public BigDecimal getBidRate() {
            return bidRate;
        }

        public void setBidRate(BigDecimal bidRate) {
            this.bidRate = bidRate;
        }

        public String getLeftTreeCount() {
            return leftTreeCount;
        }

        public void setLeftTreeCount(String leftTreeCount) {
            this.leftTreeCount = leftTreeCount;
        }

        public String getRightTreeCount() {
            return rightTreeCount;
        }

        public void setRightTreeCount(String rightTreeCount) {
            this.rightTreeCount = rightTreeCount;
        }

        public String getAdditionTimePower() {
            return additionTimePower;
        }

        public void setAdditionTimePower(String additionTimePower) {
            this.additionTimePower = additionTimePower;
        }

        public String getBidPower() {
            return bidPower;
        }

        public void setBidPower(String bidPower) {
            this.bidPower = bidPower;
        }

        @Override
        public String toString() {
            return "ZhenLieLeftModel{" +
                    "leftTreeCount='" + leftTreeCount + '\'' +
                    ", rightTreeCount='" + rightTreeCount + '\'' +
                    ", additionTimePower='" + additionTimePower + '\'' +
                    ", bidPower='" + bidPower + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ZhenLieResponse{" +
                "data=" + data +
                '}';
    }
}
