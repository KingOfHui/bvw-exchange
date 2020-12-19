package com.darknet.bvw.mall.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseFragment;
import com.jingui.lib.utils.DensityUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/19 17:11
 * <br>desc: TODO
 */
public class CategoryGoodsFragment extends BaseFragment {


	private RecyclerView mRv;
	private Adapter mAdapter;

	public static CategoryGoodsFragment newInstance(String title) {

		Bundle args = new Bundle();
		args.putString("title", title);
		CategoryGoodsFragment fragment = new CategoryGoodsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void initView(View view) {
		mRv = findViewById(R.id.rv);
		mRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
		mRv.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.left = DensityUtils.dip2px(2);
				outRect.right = DensityUtils.dip2px(2);
			}
		});
		mRv.setAdapter(mAdapter = new Adapter());
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_category_goods;
	}

	@Override
	public void initDatas() {
		ArrayList<Object> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add(new Object());
		}
		mAdapter.setNewData(list);
	}

	@Override
	public void initEvent() {

	}

	private class Adapter extends BaseQuickAdapter<Object, BaseViewHolder> {

		public Adapter() {
			super(R.layout.item_category_goods);
		}

		@Override
		protected void convert(BaseViewHolder helper, Object item) {

		}
	}
}
