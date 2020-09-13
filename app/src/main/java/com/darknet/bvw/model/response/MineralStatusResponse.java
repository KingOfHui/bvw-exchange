package com.darknet.bvw.model.response;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


public class MineralStatusResponse  extends BaseObservable {

    /**
     * miner_count : 0
     * miner_working_count : 0
     * power_24_hour_avg :
     * power_24_hour_usd_bonus :
     * sum_btc_bonus :
     * sun_btw_bonus :
     * today_btc_bonus :
     * today_btw_bonus :
     */

    private int miner_count;
    private int miner_working_count;
    private String power_24_hour_avg;
    private String power_24_hour_usd_bonus;
    private String sum_btc_bonus;
    private String sun_btw_bonus;
    private String today_btc_bonus;
    private String today_btw_bonus;

    public int getMiner_count() {
        return miner_count;
    }

    public void setMiner_count(int miner_count) {
        this.miner_count = miner_count;
    }

    public int getMiner_working_count() {
        return miner_working_count;
    }

    public void setMiner_working_count(int miner_working_count) {
        this.miner_working_count = miner_working_count;
    }

    @Bindable
    public String getPower_24_hour_avg() {
        return power_24_hour_avg;
    }

    public void setPower_24_hour_avg(String power_24_hour_avg) {
        this.power_24_hour_avg = power_24_hour_avg;
        notifyPropertyChanged(BR.power_24_hour_avg);
    }

    public String getPower_24_hour_usd_bonus() {
        return power_24_hour_usd_bonus;
    }

    public void setPower_24_hour_usd_bonus(String power_24_hour_usd_bonus) {
        this.power_24_hour_usd_bonus = power_24_hour_usd_bonus;
    }

    public String getSum_btc_bonus() {
        return sum_btc_bonus;
    }

    public void setSum_btc_bonus(String sum_btc_bonus) {
        this.sum_btc_bonus = sum_btc_bonus;
    }

    public String getSun_btw_bonus() {
        return sun_btw_bonus;
    }

    public void setSun_btw_bonus(String sun_btw_bonus) {
        this.sun_btw_bonus = sun_btw_bonus;
    }

    public String getToday_btc_bonus() {
        return today_btc_bonus;
    }

    public void setToday_btc_bonus(String today_btc_bonus) {
        this.today_btc_bonus = today_btc_bonus;
    }

    public String getToday_btw_bonus() {
        return today_btw_bonus;
    }

    public void setToday_btw_bonus(String today_btw_bonus) {
        this.today_btw_bonus = today_btw_bonus;
    }
}
