package com.darknet.bvw.mall.bean;

import java.util.List;

/**
 * Created by guoshiwen on 2020/12/21.
 */
public class ShopHomeBean {

	private List<GoodsBean> recommends;
	private List<GoodsBannerBean> banners;

	public List<GoodsBean> getRecommends() {
		return recommends;
	}

	public void setRecommends(List<GoodsBean> recommends) {
		this.recommends = recommends;
	}

	public List<GoodsBannerBean> getBanners() {
		return banners;
	}

	public void setBanners(List<GoodsBannerBean> banners) {
		this.banners = banners;
	}
}