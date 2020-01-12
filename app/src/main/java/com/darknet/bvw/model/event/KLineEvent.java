package com.darknet.bvw.model.event;

public class KLineEvent {

    //0买入，1卖出
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
