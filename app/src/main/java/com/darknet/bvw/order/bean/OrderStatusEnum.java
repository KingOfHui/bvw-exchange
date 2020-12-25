package com.darknet.bvw.order.bean;

import androidx.annotation.DrawableRes;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.R;

/**
 * @ClassName OrderStatusEnmu
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 8:53
 */
public enum OrderStatusEnum {
    /**
     * 订单状态：0->待付款；1->待发货 2->已发货 3->已完成 4->已关闭 5->无效订单
     */
//    ORDER_TO_BE_PAID(0, R.mipmap.img_to_be_paid, MyApp.getInstance().getString(R.string.order_to_be_paid)),
    ORDER_TO_BE_PAID(0, R.mipmap.img_to_be_paid, "待支付"),
    ORDER_TO_BE_DELIVERY(1, R.mipmap.img_to_be_delivery, MyApp.getInstance().getString(R.string.order_to_be_delivered)),
    ORDER_TO_BE_TAKEN(2, R.mipmap.img_to_be_taken, MyApp.getInstance().getString(R.string.order_to_be_taken)),
    ORDER_COMPLETED(3, R.mipmap.img_completed, MyApp.getInstance().getString(R.string.completed)),
    ORDER_CLOSED(4, R.mipmap.img_closed, MyApp.getInstance().getString(R.string.closed)),
    ORDER_INVALID(5, R.mipmap.img_closed, MyApp.getInstance().getString(R.string.invalid_order));

    private int state;
    @DrawableRes
    private int drawable;
    private String text;
    OrderStatusEnum(int state, @DrawableRes int drawable, String text) {
        this.state = state;
        this.drawable = drawable;
        this.text = text;
    }

    public static OrderStatusEnum getOrderStatus(int state) {
        for (OrderStatusEnum value : values()) {
            if (value.getState() == state) {
                return value;
            }
        }
        return ORDER_INVALID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
