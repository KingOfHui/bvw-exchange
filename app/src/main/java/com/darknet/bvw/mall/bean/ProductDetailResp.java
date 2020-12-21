package com.darknet.bvw.mall.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ProductDetailResp
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 14:12
 */
public class ProductDetailResp {

    /**
     * attr_list : [{"category_id":0,"id":0,"input_list":"","name":"","orders":0,"product_id":0,"required":0,"show_type":0,"type":0,"value":""}]
     * brand_id : 0
     * brand_name :
     * category_id : 0
     * category_name :
     * collect_count : 0
     * comment_count : 0
     * comment_score : 0
     * create_time :
     * delete_state : 0
     * detail_html :
     * id : 0
     * img_url :
     * img_url_list :
     * instruction :
     * lang :
     * mall_id : 0
     * name :
     * orders : 0
     * original_price : 0
     * price : 0
     * product_no :
     * publish_state : 0
     * root_category_id : 0
     * root_category_name :
     * sale : 0
     * sku_list : [{"id":0,"img_url":"","img_url_list":"","orders":0,"original_price":0,"price":0,"product_id":0,"sale":0,"sp1":"","sp2":"","sp3":"","stock":0}]
     * stock : 0
     * update_time :
     * verify_state : 0
     */

    private int brand_id;
    private String brand_name;
    private int category_id;
    private String category_name;
    private int collect_count;
    private int comment_count;
    private int comment_score;
    private String create_time;
    private int delete_state;
    private String detail_html;
    private int id;
    private String img_url;
    private String img_url_list;
    private String instruction;
    private String lang;
    private int mall_id;
    private String name;
    private int orders;
    private BigDecimal original_price;
    private BigDecimal price;
    private String product_no;
    private int publish_state;
    private int root_category_id;
    private String root_category_name;
    private int sale;
    private int stock;
    private String update_time;
    private int verify_state;
    private List<AttrListBean> attr_list;
    private List<SkuListBean> sku_list;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getComment_score() {
        return comment_score;
    }

    public void setComment_score(int comment_score) {
        this.comment_score = comment_score;
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

    public String getDetail_html() {
        return detail_html;
    }

    public void setDetail_html(String detail_html) {
        this.detail_html = detail_html;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_url_list() {
        return img_url_list;
    }

    public void setImg_url_list(String img_url_list) {
        this.img_url_list = img_url_list;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getMall_id() {
        return mall_id;
    }

    public void setMall_id(int mall_id) {
        this.mall_id = mall_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
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

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public int getPublish_state() {
        return publish_state;
    }

    public void setPublish_state(int publish_state) {
        this.publish_state = publish_state;
    }

    public int getRoot_category_id() {
        return root_category_id;
    }

    public void setRoot_category_id(int root_category_id) {
        this.root_category_id = root_category_id;
    }

    public String getRoot_category_name() {
        return root_category_name;
    }

    public void setRoot_category_name(String root_category_name) {
        this.root_category_name = root_category_name;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getVerify_state() {
        return verify_state;
    }

    public void setVerify_state(int verify_state) {
        this.verify_state = verify_state;
    }

    public List<AttrListBean> getAttr_list() {
        return attr_list;
    }

    public void setAttr_list(List<AttrListBean> attr_list) {
        this.attr_list = attr_list;
    }

    public List<SkuListBean> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<SkuListBean> sku_list) {
        this.sku_list = sku_list;
    }

    public static class AttrListBean {
        /**
         * category_id : 0
         * id : 0
         * input_list :
         * name :
         * orders : 0
         * product_id : 0
         * required : 0
         * show_type : 0
         * type : 0
         * value :
         */

        private int category_id;
        private int id;
        private String input_list;
        private String name;
        private int orders;
        private int product_id;
        private int required;
        private int show_type;
        private int type;
        private String value;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInput_list() {
            return input_list;
        }

        public void setInput_list(String input_list) {
            this.input_list = input_list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getRequired() {
            return required;
        }

        public void setRequired(int required) {
            this.required = required;
        }

        public int getShow_type() {
            return show_type;
        }

        public void setShow_type(int show_type) {
            this.show_type = show_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SkuListBean {
        /**
         * id : 0
         * img_url :
         * img_url_list :
         * orders : 0
         * original_price : 0
         * price : 0
         * product_id : 0
         * sale : 0
         * sp1 :
         * sp2 :
         * sp3 :
         * stock : 0
         */

        private int id;
        private String img_url;
        private String img_url_list;
        private int orders;
        private BigDecimal original_price;
        private BigDecimal price;
        private int product_id;
        private int sale;
        private String sp1;
        private String sp2;
        private String sp3;
        private int stock;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getImg_url_list() {
            return img_url_list;
        }

        public void setImg_url_list(String img_url_list) {
            this.img_url_list = img_url_list;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
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

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getSale() {
            return sale;
        }

        public void setSale(int sale) {
            this.sale = sale;
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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
