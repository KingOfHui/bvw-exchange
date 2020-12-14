package com.darknet.bvw.util;

import com.darknet.bvw.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * createBy dinghui
 * createTime: 2020/12/12 11:07
 * desc: 用于初始化下拉刷新控件SmartRefreshLayout
 */
public class SmartRefreshLayoutInitializer {

	public static void init() {
		SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
			layout.setHeaderHeight(80);
			layout.setHeaderTriggerRate(0.8f);
			layout.setDisableContentWhenLoading(false);
			layout.setEnableAutoLoadMore(true);
			layout.setEnableOverScrollDrag(false);
			layout.setEnableOverScrollBounce(true);
			layout.setEnableLoadMoreWhenContentNotFull(true);
			layout.setEnableScrollContentWhenRefreshed(true);
			layout.setPrimaryColorsId(R.color.controller_bg_light, R.color.white);
		});

		SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));

		//全局设置默认的 Header
		SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context));
	}
}
