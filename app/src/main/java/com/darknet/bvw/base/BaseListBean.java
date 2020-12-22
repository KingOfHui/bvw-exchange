package com.darknet.bvw.base;

import java.util.List;

public class BaseListBean<Item>{

	/**
	 * items : [{xxx}]
	 * limit : 0
	 * page : 0
	 * totalCount : 0
	 * totalPage : 0
	 */

	private int limit;
	private int page;
	private int totalCount;
	private int totalPage;
	private List<Item> items;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	private List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public static<Item> List<Item> getItems(BaseListBean<Item> source){
		if(source == null) return null;
		return source.getItems();
	}
}
