package com.darknet.bvw.model;

public class WordsModel {

    //0代表没有选中，1选中
    private int sign;

    private String content;

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
