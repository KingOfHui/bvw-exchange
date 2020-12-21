package com.darknet.bvw.mall.bean;

import java.io.Serializable;

import androidx.annotation.Keep;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/21 15:02
 * <br>desc: TODO
 */
@Keep
public class CategoryBean implements Serializable {

	public static final CategoryBean HOME = new CategoryBean("首页");

	public CategoryBean() {
	}

	public CategoryBean(String name) {
		this.name = name;
	}

	/**
	 * id : 131
	 * name : 计生情趣
	 * lang : zh-CN
	 * parent_id : 0
	 * level : 1
	 * orders : null
	 * icon_url : null
	 */

	private Integer id;
	private String name;
	private String lang;
	private Integer parent_id;
	private Integer level;
	private String orders;
	private String icon_url;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLang() {
		return lang;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public Integer getLevel() {
		return level;
	}

	public String getOrders() {
		return orders;
	}

	public String getIcon_url() {
		return icon_url;
	}
}
