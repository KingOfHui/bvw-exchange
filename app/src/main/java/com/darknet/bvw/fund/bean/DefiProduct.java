package com.darknet.bvw.fund.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DefiProduct implements Parcelable {

    /**
     * amount : 0
     * id : 0
     * min_lock_days : 0
     * min_lock_release_rate : 0
     * name :
     * name_en :
     * orders : 0
     * state : 0
     * symbol :
     */

    private String amount;
    private int id;
    private int min_lock_days;
    private String min_lock_release_rate;
    private String name;
    private String name_en;
    private int orders;
    private int state;
    private String symbol;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMin_lock_days() {
        return min_lock_days;
    }

    public void setMin_lock_days(int min_lock_days) {
        this.min_lock_days = min_lock_days;
    }

    public String getMin_lock_release_rate() {
        return min_lock_release_rate;
    }

    public void setMin_lock_release_rate(String min_lock_release_rate) {
        this.min_lock_release_rate = min_lock_release_rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeInt(this.id);
        dest.writeInt(this.min_lock_days);
        dest.writeString(this.min_lock_release_rate);
        dest.writeString(this.name);
        dest.writeString(this.name_en);
        dest.writeInt(this.orders);
        dest.writeInt(this.state);
        dest.writeString(this.symbol);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public DefiProduct() {
    }

    protected DefiProduct(Parcel in) {
        this.amount = in.readString();
        this.id = in.readInt();
        this.min_lock_days = in.readInt();
        this.min_lock_release_rate = in.readString();
        this.name = in.readString();
        this.name_en = in.readString();
        this.orders = in.readInt();
        this.state = in.readInt();
        this.symbol = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DefiProduct> CREATOR = new Parcelable.Creator<DefiProduct>() {
        @Override
        public DefiProduct createFromParcel(Parcel source) {
            return new DefiProduct(source);
        }

        @Override
        public DefiProduct[] newArray(int size) {
            return new DefiProduct[size];
        }
    };
}
