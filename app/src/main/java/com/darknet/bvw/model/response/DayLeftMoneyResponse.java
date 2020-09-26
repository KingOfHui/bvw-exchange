package com.darknet.bvw.model.response;

import java.math.BigDecimal;

public class DayLeftMoneyResponse extends BaseResponse {

//
//    {"code":0,"msg":"success","data":{"":0,"":0,"":0,"":0.000000,
//            "":0.000000,"":0,"msg":"请输入今日可转出额度0USD,剩余0USD,可累计到20BTW*3USD后统一转出",
//            "":"You can " +
//            "transfer today: 0 USDs input, with 0 remaining, can add to 20BTW*3USD to gather transfer"},"success":true}




    private DayLeftMoneyModel data;

    public DayLeftMoneyModel getData() {
        return data;
    }

    public void setData(DayLeftMoneyModel data) {
        this.data = data;
    }

    public static class DayLeftMoneyModel{
        private BigDecimal bonusAmountLimit;
        private BigDecimal smallLogAmountLimit;
        private BigDecimal amountLimit;
        private BigDecimal amountLimitUsd;
        private BigDecimal leftAmountLimitUsd;
        private BigDecimal usedAmountLimitUsd;
        private String msg;
        private String enMsg;


        public BigDecimal getBonusAmountLimit() {
            return bonusAmountLimit;
        }

        public void setBonusAmountLimit(BigDecimal bonusAmountLimit) {
            this.bonusAmountLimit = bonusAmountLimit;
        }

        public BigDecimal getSmallLogAmountLimit() {
            return smallLogAmountLimit;
        }

        public void setSmallLogAmountLimit(BigDecimal smallLogAmountLimit) {
            this.smallLogAmountLimit = smallLogAmountLimit;
        }

        public BigDecimal getAmountLimit() {
            return amountLimit;
        }

        public void setAmountLimit(BigDecimal amountLimit) {
            this.amountLimit = amountLimit;
        }

        public BigDecimal getAmountLimitUsd() {
            return amountLimitUsd;
        }

        public void setAmountLimitUsd(BigDecimal amountLimitUsd) {
            this.amountLimitUsd = amountLimitUsd;
        }

        public BigDecimal getLeftAmountLimitUsd() {
            return leftAmountLimitUsd;
        }

        public void setLeftAmountLimitUsd(BigDecimal leftAmountLimitUsd) {
            this.leftAmountLimitUsd = leftAmountLimitUsd;
        }

        public BigDecimal getUsedAmountLimitUsd() {
            return usedAmountLimitUsd;
        }

        public void setUsedAmountLimitUsd(BigDecimal usedAmountLimitUsd) {
            this.usedAmountLimitUsd = usedAmountLimitUsd;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getEnMsg() {
            return enMsg;
        }

        public void setEnMsg(String enMsg) {
            this.enMsg = enMsg;
        }
    }



}
