package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class SuanLiResponse extends BaseResponse{

    private SunLiData data;

    public SunLiData getData() {
        return data;
    }

    public void setData(SunLiData data) {
        this.data = data;
    }

    public static class SunLiData{

        private String userId;
        private BigDecimal power;
        private BigDecimal powerRate;
        private BigDecimal sumPower;
        private BigDecimal sumBonus;
        private List<SunLiItemModel> powerHistoryList;

        public List<SunLiItemModel> getPowerHistoryList() {
            return powerHistoryList;
        }

        public BigDecimal getPower() {
            return power;
        }

        public void setPower(BigDecimal power) {
            this.power = power;
        }

        public BigDecimal getPowerRate() {
            return powerRate;
        }

        public void setPowerRate(BigDecimal powerRate) {
            this.powerRate = powerRate;
        }

        public BigDecimal getSumPower() {
            return sumPower;
        }

        public void setSumPower(BigDecimal sumPower) {
            this.sumPower = sumPower;
        }

        public BigDecimal getSumBonus() {
            return sumBonus;
        }

        public void setSumBonus(BigDecimal sumBonus) {
            this.sumBonus = sumBonus;
        }

        public void setPowerHistoryList(List<SunLiItemModel> powerHistoryList) {
            this.powerHistoryList = powerHistoryList;
        }

        @Override
        public String toString() {
            return "SunLiData{" +
                    "userId='" + userId + '\'' +
                    ", power=" + power +
                    ", powerRate=" + powerRate +
                    ", sumPower=" + sumPower +
                    ", sumBonus=" + sumBonus +
                    ", powerHistoryList=" + powerHistoryList +
                    '}';
        }
    }

    public static class SunLiItemModel{

        private BigDecimal bidPower;
        private BigDecimal inviterPower;
        private BigDecimal holdPower;
        private BigDecimal signPower;
        private BigDecimal additionTimePower;
        private BigDecimal levelPower;
        private String date;
        private BigDecimal dayPower;
        private BigDecimal dayBonus;


        public BigDecimal getBidPower() {
            return bidPower;
        }

        public void setBidPower(BigDecimal bidPower) {
            this.bidPower = bidPower;
        }

        public BigDecimal getInviterPower() {
            return inviterPower;
        }

        public void setInviterPower(BigDecimal inviterPower) {
            this.inviterPower = inviterPower;
        }

        public BigDecimal getHoldPower() {
            return holdPower;
        }

        public void setHoldPower(BigDecimal holdPower) {
            this.holdPower = holdPower;
        }

        public BigDecimal getSignPower() {
            return signPower;
        }

        public void setSignPower(BigDecimal signPower) {
            this.signPower = signPower;
        }

        public BigDecimal getAdditionTimePower() {
            return additionTimePower;
        }

        public void setAdditionTimePower(BigDecimal additionTimePower) {
            this.additionTimePower = additionTimePower;
        }

        public BigDecimal getLevelPower() {
            return levelPower;
        }

        public void setLevelPower(BigDecimal levelPower) {
            this.levelPower = levelPower;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public BigDecimal getDayPower() {
            return dayPower;
        }

        public void setDayPower(BigDecimal dayPower) {
            this.dayPower = dayPower;
        }

        public BigDecimal getDayBonus() {
            return dayBonus;
        }

        public void setDayBonus(BigDecimal dayBonus) {
            this.dayBonus = dayBonus;
        }

        @Override
        public String toString() {
            return "SunLiItemModel{" +
                    "bidPower=" + bidPower +
                    ", inviterPower=" + inviterPower +
                    ", holdPower=" + holdPower +
                    ", signPower=" + signPower +
                    ", additionTimePower=" + additionTimePower +
                    ", levelPower=" + levelPower +
                    ", date='" + date + '\'' +
                    ", dayPower=" + dayPower +
                    ", dayBonus=" + dayBonus +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SuanLiResponse{" +
                "data=" + data +
                '}';
    }
}
