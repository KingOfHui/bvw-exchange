package com.darknet.bvw.util;

/**
 * 作者：created by albert on 2020-01-09 11:35
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public enum NumberEnum {

    Zero1(1, "0.1"),
    Zero2(2, "0.01"),
    Zero3(3, "0.001"),
    Zero4(4, "0.0001"),
    Zero5(5, "0.00001"),
    Zero6(6, "0.000001"),
    Zero7(7, "0.0000001"),
    Zero8(8, "0.00000001");


    private int i;
    private String s;

    NumberEnum(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
