package com.darknet.bvw.util.loadmore;

import java.util.List;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/11 14:07
 * <br>desc: 加载更多监听器
 * <br>注意: 不支持自动加载更多
 */
public interface LoadmoreListener<T> {

	/**
	 * 加载更多, 在方法内进行加载
	 *
	 * @param page    页码, 最小为1 最大为 maxPage
	 * @param emitter 触发器
	 */
	void onLoadMore(int page, Emitter<T> emitter);

	/**
	 * 触发器, 用于加载完成后设置数据
	 *
	 * @param <T> 数据类型
	 */
	interface Emitter<T> {

		/**
		 * 重要: 必须设置最大页数, 不然最多只能加载1页
		 *
		 * @param maxPage 最大页数
		 */
		void setMaxPage(int maxPage);

		/**
		 * 数据加载成功后必须调用, 否则将不会执行下次刷新
		 *
		 * @param page 加载数据使用的页码
		 *             (用于增加页码前校验页码是否一致)
		 */
		void loadSuccessFinish(int page);

		/**
		 * 加载失败后必须调用, 否则将不会执行下次刷新
		 *
		 * @param page 加载数据使用的页码
		 */
		void loadErrorFinish(int page);

		/**
		 * @param isSuccess 加载成功与否, 注意: 加载失败时 isSuccess 必须传入 false, 否则页码会+1
		 */
		void loadFinish(int page, boolean isSuccess);

		/**
		 * 添加数据
		 *
		 * @param datas 数据 T 的集合
		 */
		void addData(List<T> datas);

		void setNewData(List<T> datas);

		void addData(T data);

		/**
		 * 已废弃, 可以根据页面自动调用
		 * 如果拿不到总页数, 当加载数据为空时(即为最后一页)调用一下
		 *
		 * @deprecated 不对外提供, 但是子类必须实现
		 */
		@Deprecated
		void nomore();
	}
}
