package com.darknet.bvw.order.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SubmitOrderResp
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 14:05
 */
public class OrderResp implements Serializable {

    /**
     * auto_confirm_day : 0
     * bill_content :
     * bill_header :
     * bill_receiver_email :
     * bill_receiver_phone :
     * bill_type : 0
     * comment_state : 0
     * comment_time :
     * confirm_state : 0
     * coupon_amount : 0
     * coupon_id : 0
     * create_time :
     * delete_state : 0
     * delivery_company :
     * delivery_no :
     * delivery_time :
     * finish_time :
     * freight_amount : 0
     * id : 0
     * mall_id : 0
     * mall_pay_address :
     * note :
     * orderHandleOption : {"address":0,"cancel":0,"comment":0,"delivery":0,"pay":0,"remind":0,"shipping":0}
     * orderState : 0
     * order_item_list : [{"amount":0,"coupon_amount":0,"create_time":"","id":0,"mall_id":0,"order_id":0,"order_no":"","pay_amount":0,"product_attr":"","product_category_id":0,"product_id":0,"product_img_url":"","product_name":"","product_no":"","product_price":0,"product_quantity":0,"product_sku_id":0,"product_sku_img_url":"","sp1":"","sp2":"","sp3":"","user_id":0}]
     * order_no :
     * pay_amount : 0
     * pay_state : 0
     * pay_time :
     * pay_tx_hash :
     * pay_type : 0
     * receive_time :
     * receiver_city :
     * receiver_county :
     * receiver_detail_address :
     * receiver_name :
     * receiver_nation :
     * receiver_phone :
     * receiver_post_code :
     * receiver_province :
     * shipping_state : 0
     * state : 0
     * submit_type : 0
     * total_amount : 0
     * update_time :
     * user_address :
     * user_id : 0
     */

    private int auto_confirm_day;
    private String bill_content;
    private String bill_header;
    private String bill_receiver_email;
    private String bill_receiver_phone;
    private int bill_type;
    private int comment_state;
    private String comment_time;
    private int confirm_state;
    private String coupon_amount;
    private int coupon_id;
    private String create_time;
    private int delete_state;
    private String delivery_company;
    private String delivery_no;
    private String delivery_time;
    private String finish_time;
    private String freight_amount;
    private int id;
    private int mall_id;
    private String mall_pay_address;
    private String note;
    private OrderHandleOptionBean orderHandleOption;
     //订单合并状态 0->待付款；1->待发货 2->已发货 3->已完成 4->已关闭|已取消 5->无效订单 6待退款 7已退款 8待评价 9已评价
    private int orderState;
    private String order_no;
    private String pay_amount;
    private int pay_state;
    private String pay_time;
    private String pay_tx_hash;
    private int pay_type;
    private String receive_time;
    private String receiver_city;
    private String receiver_county;
    private String receiver_detail_address;
    private String receiver_name;
    private String receiver_nation;
    private String receiver_phone;
    private String receiver_post_code;
    private String receiver_province;
    private int shipping_state;
    private int state;
    private int submit_type;
    private String total_amount;
    private String update_time;
    private String user_address;
    private int user_id;
    private List<OrderItemListBean> order_item_list;

    public String getFreight_amount() {
        return freight_amount;
    }

    public void setFreight_amount(String freight_amount) {
        this.freight_amount = freight_amount;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getAuto_confirm_day() {
        return auto_confirm_day;
    }

    public void setAuto_confirm_day(int auto_confirm_day) {
        this.auto_confirm_day = auto_confirm_day;
    }

    public String getBill_content() {
        return bill_content;
    }

    public void setBill_content(String bill_content) {
        this.bill_content = bill_content;
    }

    public String getBill_header() {
        return bill_header;
    }

    public void setBill_header(String bill_header) {
        this.bill_header = bill_header;
    }

    public String getBill_receiver_email() {
        return bill_receiver_email;
    }

    public void setBill_receiver_email(String bill_receiver_email) {
        this.bill_receiver_email = bill_receiver_email;
    }

    public String getBill_receiver_phone() {
        return bill_receiver_phone;
    }

    public void setBill_receiver_phone(String bill_receiver_phone) {
        this.bill_receiver_phone = bill_receiver_phone;
    }

    public int getBill_type() {
        return bill_type;
    }

    public void setBill_type(int bill_type) {
        this.bill_type = bill_type;
    }

    public int getComment_state() {
        return comment_state;
    }

    public void setComment_state(int comment_state) {
        this.comment_state = comment_state;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getConfirm_state() {
        return confirm_state;
    }

    public void setConfirm_state(int confirm_state) {
        this.confirm_state = confirm_state;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getDelete_state() {
        return delete_state;
    }

    public void setDelete_state(int delete_state) {
        this.delete_state = delete_state;
    }

    public String getDelivery_company() {
        return delivery_company;
    }

    public void setDelivery_company(String delivery_company) {
        this.delivery_company = delivery_company;
    }

    public String getDelivery_no() {
        return delivery_no;
    }

    public void setDelivery_no(String delivery_no) {
        this.delivery_no = delivery_no;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
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

    public String getMall_pay_address() {
        return mall_pay_address;
    }

    public void setMall_pay_address(String mall_pay_address) {
        this.mall_pay_address = mall_pay_address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderHandleOptionBean getOrderHandleOption() {
        return orderHandleOption;
    }

    public void setOrderHandleOption(OrderHandleOptionBean orderHandleOption) {
        this.orderHandleOption = orderHandleOption;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getPay_state() {
        return pay_state;
    }

    public void setPay_state(int pay_state) {
        this.pay_state = pay_state;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_tx_hash() {
        return pay_tx_hash;
    }

    public void setPay_tx_hash(String pay_tx_hash) {
        this.pay_tx_hash = pay_tx_hash;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_county() {
        return receiver_county;
    }

    public void setReceiver_county(String receiver_county) {
        this.receiver_county = receiver_county;
    }

    public String getReceiver_detail_address() {
        return receiver_detail_address;
    }

    public void setReceiver_detail_address(String receiver_detail_address) {
        this.receiver_detail_address = receiver_detail_address;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_nation() {
        return receiver_nation;
    }

    public void setReceiver_nation(String receiver_nation) {
        this.receiver_nation = receiver_nation;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_post_code() {
        return receiver_post_code;
    }

    public void setReceiver_post_code(String receiver_post_code) {
        this.receiver_post_code = receiver_post_code;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public int getShipping_state() {
        return shipping_state;
    }

    public void setShipping_state(int shipping_state) {
        this.shipping_state = shipping_state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSubmit_type() {
        return submit_type;
    }

    public void setSubmit_type(int submit_type) {
        this.submit_type = submit_type;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<OrderItemListBean> getOrder_item_list() {
        return order_item_list;
    }

    public void setOrder_item_list(List<OrderItemListBean> order_item_list) {
        this.order_item_list = order_item_list;
    }

    public static class OrderHandleOptionBean implements Serializable{
        /**
         * address : 0
         * cancel : 0
         * comment : 0
         * delivery : 0
         * pay : 0
         * remind : 0
         * shipping : 0
         */

        private int address;
        private int cancel;
        private int comment;
        private int delivery;
        private int pay;
        private int remind;
        private int shipping;

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public int getCancel() {
            return cancel;
        }

        public void setCancel(int cancel) {
            this.cancel = cancel;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getDelivery() {
            return delivery;
        }

        public void setDelivery(int delivery) {
            this.delivery = delivery;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public int getRemind() {
            return remind;
        }

        public void setRemind(int remind) {
            this.remind = remind;
        }

        public int getShipping() {
            return shipping;
        }

        public void setShipping(int shipping) {
            this.shipping = shipping;
        }
    }

    public static class OrderItemListBean  implements Serializable{
        /**
         * amount : 0
         * coupon_amount : 0
         * create_time :
         * id : 0
         * mall_id : 0
         * order_id : 0
         * order_no :
         * pay_amount : 0
         * product_attr :
         * product_category_id : 0
         * product_id : 0
         * product_img_url :
         * product_name :
         * product_no :
         * product_price : 0
         * product_quantity : 0
         * product_sku_id : 0
         * product_sku_img_url :
         * sp1 :
         * sp2 :
         * sp3 :
         * user_id : 0
         */

        private String amount;
        private String coupon_amount;
        private String create_time;
        private int id;
        private int mall_id;
        private int order_id;
        private String order_no;
        private String pay_amount;
        private String product_attr;
        private int product_category_id;
        private int product_id;
        private String product_img_url;
        private String product_name;
        private String product_no;
        private String product_price;
        private String product_quantity;
        private int product_sku_id;
        private String product_sku_img_url;
        private String sp1;
        private String sp2;
        private String sp3;
        private int user_id;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(String coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getProduct_quantity() {
            return product_quantity;
        }

        public void setProduct_quantity(String product_quantity) {
            this.product_quantity = product_quantity;
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

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }


        public String getProduct_attr() {
            return product_attr;
        }

        public void setProduct_attr(String product_attr) {
            this.product_attr = product_attr;
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

        public String getProduct_sku_img_url() {
            return product_sku_img_url;
        }

        public void setProduct_sku_img_url(String product_sku_img_url) {
            this.product_sku_img_url = product_sku_img_url;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
