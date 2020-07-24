package com.darknet.bvw.model.response;

import java.math.BigDecimal;

public class HZLeftMoneyResponse extends BaseResponse {

//    {"code":0,"msg":"success","data":{"":0,"":0E-8,"":0E-8,
//            "msg":"You can trade today: 0 BVWs input, with 0 remaining","enMsg":"You can trade today: 0 BVWs input, with 0 remaining"},
//        "success":true}





    private HzLeftMoneyModel data;

    public HzLeftMoneyModel getData() {
        return data;
    }

    public void setData(HzLeftMoneyModel data) {
        this.data = data;
    }

    public static class HzLeftMoneyModel {
        private BigDecimal bonusAmountLimit;
        private BigDecimal leftAmountLimit;
        private BigDecimal usedAmountLimit;
        private String msg;
        private String enMsg;

        public BigDecimal getBonusAmountLimit() {
            return bonusAmountLimit;
        }

        public void setBonusAmountLimit(BigDecimal bonusAmountLimit) {
            this.bonusAmountLimit = bonusAmountLimit;
        }

        public BigDecimal getLeftAmountLimit() {
            return leftAmountLimit;
        }

        public void setLeftAmountLimit(BigDecimal leftAmountLimit) {
            this.leftAmountLimit = leftAmountLimit;
        }

        public BigDecimal getUsedAmountLimit() {
            return usedAmountLimit;
        }

        public void setUsedAmountLimit(BigDecimal usedAmountLimit) {
            this.usedAmountLimit = usedAmountLimit;
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
