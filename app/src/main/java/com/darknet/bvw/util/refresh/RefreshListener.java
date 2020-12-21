package com.darknet.bvw.util.refresh;


import java.util.List;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/10 00:33
 * <br>desc: 下拉刷新监听器
 */
public interface RefreshListener<T> {

	void onRefresh(Emitter<T> emitter);

	/**
	 * 触发器, 用于刷新完成后设置数据
	 *
	 * @param <T> 数据类型
	 */
	interface Emitter<T> {
		/**
		 * 数据加载完成后必须调用, 否则将不会执行下次刷新
		 */
		void refreshFinish();

		/**
		 * 设置新的数据
		 *
		 * @param datas 数据 T 的集合
		 */
		void setNewData(List<T> datas);

		/**
		 * 为加载更多设置最大页数, 如果未绑定加载更多, 此方法将无效
		 *
		 * @param maxPage 最大页数
		 */
		void setMaxPage(int maxPage);
	}
}
