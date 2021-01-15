package com.darknet.bvw.order.bean;

import android.content.Context;

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
     * //订单合并状态 0->待付款；1->待发货 2->已发货 3->已完成 4->已关闭|已取消 5->无效订单 6待退款 7已退款 8待评价 9已评价 10链上确认中
     */
//    ORDER_TO_BE_PAID(0, R.mipmap.img_to_be_paid, MyApp.getInstance().getString(R.string.order_to_be_paid)),
    ORDER_TO_BE_PAID(0, R.mipmap.img_to_be_paid, "待支付"),
    ORDER_TO_BE_DELIVERY(1, R.mipmap.img_to_be_delivery, MyApp.getInstance().getString(R.string.order_to_be_delivered)),
    ORDER_TO_BE_TAKEN(2, R.mipmap.img_to_be_taken, MyApp.getInstance().getString(R.string.order_to_be_taken)),
    ORDER_COMPLETED(3, R.mipmap.img_completed, MyApp.getInstance().getString(R.string.completed)),
    ORDER_CLOSED(4, R.mipmap.img_closed, MyApp.getInstance().getString(R.string.closed)),
    ORDER_INVALID(5, R.mipmap.img_closed, MyApp.getInstance().getString(R.string.invalid_order)),
    ORDER_CHAINING(10, R.mipmap.img_to_be_paid, MyApp.getInstance().getString(R.string.invalid_order));

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

    //     * //订单合并状态 0->待付款；1->待发货 2->已发货 3->已完成 4->已关闭|已取消 5->无效订单 6待退款 7已退款 8待评价 9已评价 10链上确认中
    public static String getOrderStatusText(Context context, int orderStatus) {
        String text = "";
        switch (orderStatus) {
            case 0:
                text = context.getString(R.string.status_to_be_paid);
                break;
            case 1:
                text = context.getString(R.string.status_to_be_delivered);
                break;
            case 2:
                text = context.getString(R.string.status_to_be_taken);
                break;
            case 3:
                text = context.getString(R.string.completed);
                break;
            case 4:
                text = context.getString(R.string.cancelled);
                break;
            case 5:
                text = context.getString(R.string.invalid_order);
                break;
            case 6:
                text = context.getString(R.string.to_be_refunded);
                break;
            case 7:
                text = context.getString(R.string.refunded);
                break;
            case 8:
                text = context.getString(R.string.to_be_comment);
                break;
            case 9:
                text = context.getString(R.string.commened);
                break;
            case 10:
                text = context.getString(R.string.confirm_on_the_chain);
                break;

            default:
                break;
        }
        return text;
    }
}
