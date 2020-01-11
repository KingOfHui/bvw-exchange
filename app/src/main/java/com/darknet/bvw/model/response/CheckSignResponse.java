package com.darknet.bvw.model.response;

import java.math.BigDecimal;

public class CheckSignResponse extends BaseResponse {


    private SignModel data;

    public SignModel getData() {
        return data;
    }

    public void setData(SignModel data) {
        this.data = data;
    }

    public static class SignModel{

        private BigDecimal signPower;

        public BigDecimal getSignPower() {
            return signPower;
        }

        public void setSignPower(BigDecimal signPower) {
            this.signPower = signPower;
        }

        //        "userId": 0,
//                "left": false,
//                "existChild": false,
//                "address": null,
//                "identityId": "",
//                "addressLayer": 0,
//                "bidPower": 0,
//                "inviterPower": 0,
//                "holdPower": 0,
//                "sumPower": 0,
//                "": 0,
//                "additionTimePower": 0,
//                "referBid": null,
//                "referCount": 0,
//                "treeCount": 0,
//                "leftTreeCount": 0,
//                "rightTreeCount": 0,
//                "powerDate": "2020-01-06",
//                "powerDateBonus": 0,
//                "bidRate": 0

    }



}
