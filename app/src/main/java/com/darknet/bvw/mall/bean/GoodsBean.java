package com.darknet.bvw.mall.bean;

import androidx.annotation.Keep;

/**
 * Created by guoshiwen on 2020/12/21.
 */
@Keep
public class GoodsBean {

	/**
	 * product_id : 117
	 * product_img : https://bighealth-pro.oss-cn-beijing.aliyuncs.com/backstage/commodity/1573466198564b4y5ej.jpeg
	 * orders : 1
	 * name :  博洋宝贝斜纹四件套-休闲物语BYJT80
	 * price : 1125
	 * original_price : 1125
	 */

	private int product_id;
	private String product_img;
	private int orders;
	private String name;
	private String price;
	private String original_price;
	private String sale;

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public String getOriginal_price() {
		return original_price;
	}
}