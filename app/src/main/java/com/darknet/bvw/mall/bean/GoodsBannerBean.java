package com.darknet.bvw.mall.bean;

/**
 * Created by guoshiwen on 2020/12/21.
 */
public class GoodsBannerBean {


	/**
	 * id : 40
	 * product_id : 117
	 * banner_img : https://bighealth-pro.oss-cn-beijing.aliyuncs.com/backstage/commodity/1573466198564b4y5ej.jpeg
	 * orders : 1
	 */

	private int id;
	private int product_id;
	private String banner_img;
	private int orders;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getBanner_img() {
		return banner_img;
	}

	public void setBanner_img(String banner_img) {
		this.banner_img = banner_img;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}
}