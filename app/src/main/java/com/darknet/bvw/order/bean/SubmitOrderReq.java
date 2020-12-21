package com.darknet.bvw.order.bean;

/**
 * @ClassName SubmitOrderReq
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 14:03
 */
public class SubmitOrderReq {

    /**
     * address_id : 0
     * coupon : {"coupon_id":0,"mall_id":0}
     * note :
     * product_sku_id : 0
     * quantity : 0
     */

    private int address_id;
    private CouponBean coupon;
    private String note;
    private int product_sku_id;
    private int quantity;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getProduct_sku_id() {
        return product_sku_id;
    }

    public void setProduct_sku_id(int product_sku_id) {
        this.product_sku_id = product_sku_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class CouponBean {
        /**
         * coupon_id : 0
         * mall_id : 0
         */

        private int coupon_id;
        private int mall_id;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getMall_id() {
            return mall_id;
        }

        public void setMall_id(int mall_id) {
            this.mall_id = mall_id;
        }
    }
}
