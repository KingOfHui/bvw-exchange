package com.darknet.bvw.order.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

public class CartData {

    /**
     * cart_item_list : [{"check":0,"create_time":"","id":0,"mall_id":0,"original_price":0,"price":0,"product_attr":"","product_brand":"","product_category_id":0,"product_id":0,"product_img_url":"","product_name":"","product_no":"","product_sku_id":0,"quantity":0,"sp1":"","sp2":"","sp3":"","update_time":"","user_id":0}]
     * checked_product_amount : 0
     * checked_product_count : 0
     * mall_list : [{}]
     * product_total_amount : 0
     * product_total_count : 0
     */

    private BigDecimal checked_product_amount;
    private int checked_product_count;
    private BigDecimal product_total_amount;
    private int product_total_count;
    private List<CartItemListBean> cart_item_list;
    private List<MallListBean> mall_list;

    public BigDecimal getChecked_product_amount() {
        return checked_product_amount;
    }

    public void setChecked_product_amount(BigDecimal checked_product_amount) {
        this.checked_product_amount = checked_product_amount;
    }

    public int getChecked_product_count() {
        return checked_product_count;
    }

    public void setChecked_product_count(int checked_product_count) {
        this.checked_product_count = checked_product_count;
    }

    public BigDecimal getProduct_total_amount() {
        return product_total_amount;
    }

    public void setProduct_total_amount(BigDecimal product_total_amount) {
        this.product_total_amount = product_total_amount;
    }

    public int getProduct_total_count() {
        return product_total_count;
    }

    public void setProduct_total_count(int product_total_count) {
        this.product_total_count = product_total_count;
    }

    public List<CartItemListBean> getCart_item_list() {
        return cart_item_list;
    }

    public void setCart_item_list(List<CartItemListBean> cart_item_list) {
        this.cart_item_list = cart_item_list;
    }

    public List<MallListBean> getMall_list() {
        return mall_list;
    }

    public void setMall_list(List<MallListBean> mall_list) {
        this.mall_list = mall_list;
    }

    public boolean isAllSelected() {
        boolean isAllSelect = true;
        if (CollectionUtil.isNotEmpty(cart_item_list)) {
            for (CartData.CartItemListBean datum : cart_item_list) {
                if (datum.getCheck() == 0) {
                    isAllSelect = false;
                    break;
                }
            }
        } else {
            isAllSelect = false;
        }
        return isAllSelect;
    }

    public static class CartItemListBean implements Parcelable {
        /**
         * check : 0
         * create_time :
         * id : 0
         * mall_id : 0
         * original_price : 0
         * price : 0
         * product_attr :
         * product_brand :
         * product_category_id : 0
         * product_id : 0
         * product_img_url :
         * product_name :
         * product_no :
         * product_sku_id : 0
         * quantity : 0
         * sp1 :
         * sp2 :
         * sp3 :
         * update_time :
         * user_id : 0
         */

        private int check;
        private String create_time;
        private int id;
        private int mall_id;
        private BigDecimal original_price;
        private BigDecimal price;
        private String product_attr;
        private String product_brand;
        private int product_category_id;
        private int product_id;
        private String product_img_url;
        private String product_name;
        private String product_no;
        private int product_sku_id;
        private int quantity;
        private String sp1;
        private String sp2;
        private String sp3;
        private String update_time;
        private int user_id;

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMall_id() {
            return mall_id;
        }

        public void setMall_id(int mall_id) {
            this.mall_id = mall_id;
        }

        public BigDecimal getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(BigDecimal original_price) {
            this.original_price = original_price;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getProduct_attr() {
            return product_attr;
        }

        public void setProduct_attr(String product_attr) {
            this.product_attr = product_attr;
        }

        public String getProduct_brand() {
            return product_brand;
        }

        public void setProduct_brand(String product_brand) {
            this.product_brand = product_brand;
        }

        public int getProduct_category_id() {
            return product_category_id;
        }

        public void setProduct_category_id(int product_category_id) {
            this.product_category_id = product_category_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_img_url() {
            return product_img_url;
        }

        public void setProduct_img_url(String product_img_url) {
            this.product_img_url = product_img_url;
        }

        public String getProduct_name() {
            if (product_name != null) {
                product_name = product_name.trim();
            }
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_no() {
            return product_no;
        }

        public void setProduct_no(String product_no) {
            this.product_no = product_no;
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

        public String getSp1() {
            return sp1;
        }

        public void setSp1(String sp1) {
            this.sp1 = sp1;
        }

        public String getSp2() {
            return sp2;
        }

        public void setSp2(String sp2) {
            this.sp2 = sp2;
        }

        public String getSp3() {
            return sp3;
        }

        public void setSp3(String sp3) {
            this.sp3 = sp3;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.check);
            dest.writeString(this.create_time);
            dest.writeInt(this.id);
            dest.writeInt(this.mall_id);
            dest.writeSerializable(this.original_price);
            dest.writeSerializable(this.price);
            dest.writeString(this.product_attr);
            dest.writeString(this.product_brand);
            dest.writeInt(this.product_category_id);
            dest.writeInt(this.product_id);
            dest.writeString(this.product_img_url);
            dest.writeString(this.product_name);
            dest.writeString(this.product_no);
            dest.writeInt(this.product_sku_id);
            dest.writeInt(this.quantity);
            dest.writeString(this.sp1);
            dest.writeString(this.sp2);
            dest.writeString(this.sp3);
            dest.writeString(this.update_time);
            dest.writeInt(this.user_id);
        }

        public CartItemListBean() {
        }

        protected CartItemListBean(Parcel in) {
            this.check = in.readInt();
            this.create_time = in.readString();
            this.id = in.readInt();
            this.mall_id = in.readInt();
            this.original_price = (BigDecimal) in.readSerializable();
            this.price = (BigDecimal) in.readSerializable();
            this.product_attr = in.readString();
            this.product_brand = in.readString();
            this.product_category_id = in.readInt();
            this.product_id = in.readInt();
            this.product_img_url = in.readString();
            this.product_name = in.readString();
            this.product_no = in.readString();
            this.product_sku_id = in.readInt();
            this.quantity = in.readInt();
            this.sp1 = in.readString();
            this.sp2 = in.readString();
            this.sp3 = in.readString();
            this.update_time = in.readString();
            this.user_id = in.readInt();
        }

        public static final Parcelable.Creator<CartItemListBean> CREATOR = new Parcelable.Creator<CartItemListBean>() {
            @Override
            public CartItemListBean createFromParcel(Parcel source) {
                return new CartItemListBean(source);
            }

            @Override
            public CartItemListBean[] newArray(int size) {
                return new CartItemListBean[size];
            }
        };
    }

    public static class MallListBean {
    }
}
