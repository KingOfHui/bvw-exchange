package com.darknet.bvw.mall.ui;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.databinding.ItemGoodsBinding;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.presenter.CategoryGoodsPresenter;
import com.jingui.lib.utils.DensityUtils;
import com.youth.banner.Banner;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.hutool.core.collection.CollectionUtil;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/19 17:11
 * <br>desc: TODO
 */
public class CategoryGoodsFragment extends BaseFragment {

	private CategoryGoodsPresenter mPresenter = new CategoryGoodsPresenter(this);
	private RecyclerView mRv;
	private Adapter mAdapter;
	private Banner mBanner;
	private TextView mZonghe;
	private TextView mSale;
	private TextView mTvPrice;
	private FrameLayout mClickPrice;

	public static CategoryGoodsFragment newInstance(int firstCategoryId, Integer secondCategoryId) {
		Bundle args = new Bundle();
		args.putInt("firstCategoryId", firstCategoryId);
		if (secondCategoryId != null) {
			args.putInt("secondCategoryId", secondCategoryId);
		}
		CategoryGoodsFragment fragment = new CategoryGoodsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void initView(View view) {
		mRv = findViewById(R.id.rv);
		mBanner = findViewById(R.id.banner);
		mZonghe = findViewById(R.id.zonghe);
		mSale = findViewById(R.id.sale);
		mTvPrice = findViewById(R.id.tv_price);
		mClickPrice = findViewById(R.id.click_price);
		initSortRule();


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

	private void initSortRule() {
		mZonghe.setOnClickListener(v -> {
			mZonghe.setSelected(true);
			mSale.setSelected(false);
			mTvPrice.setSelected(false);
			mPresenter.updateSortRule(null, null);
		});
		mSale.setOnClickListener(v -> {
			mZonghe.setSelected(false);
			mSale.setSelected(true);
			mTvPrice.setSelected(false);
			mPresenter.updateSortRule("sale", null);
		});
		mClickPrice.setOnClickListener(v -> {
			mZonghe.setSelected(false);
			mSale.setSelected(false);
			mTvPrice.setSelected(true);
			if("asc".equals(mPresenter.getSort())){
				Drawable drawable = ContextCompat.getDrawable(requireActivity(), R.mipmap.price_desc);
				if (drawable!=null) {
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
				}
				mTvPrice.setCompoundDrawables(null, null, drawable, null);
				mPresenter.updateSortRule("price", "desc");
			}else {
				Drawable drawable = ContextCompat.getDrawable(requireActivity(), R.mipmap.price_asc);
				if (drawable!=null) {
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
				}
				mTvPrice.setCompoundDrawables(null, null, drawable, null);
				mPresenter.updateSortRule("price", "asc");
			}
		});


	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_category_goods;
	}

	@Override
	public void initDatas() {
		Bundle arg = getArguments();
		int firstCategoryId = arg.getInt("firstCategoryId");
		Integer secondCategoryId = arg.getInt("secondCategoryId", -1);
		if (secondCategoryId == -1) secondCategoryId = null;
		mPresenter.setCategoryId(firstCategoryId, secondCategoryId);
		mPresenter.loadById();
	}

	@Override
	public void onPause() {
		super.onPause();
		mRv.scrollToPosition(0);
	}

	@Override
	public void initEvent() {

	}

	public void refreshUI(List<GoodsDetailBean> data) {
		if (CollectionUtil.isNotEmpty(data)) {
			mAdapter.setNewData(data);
		} else {
			mAdapter.setEmptyView(View.inflate(requireActivity(), R.layout.empty_view, null));
		}
	}

	private static class Adapter extends BaseQuickAdapter<GoodsDetailBean, BaseViewHolder> {

		public Adapter() {
			super(R.layout.item_category_goods);
		}

		@Override
		protected void convert(BaseViewHolder helper, GoodsDetailBean item) {
			Glide.with(helper.itemView.getContext())
					.load(item.getImg_url())
					.apply(RequestOptions.centerCropTransform())
					.into((ImageView) helper.getView(R.id.iv_icon));
//            binding.tag TODO 没字段
			helper.setText(R.id.tv_name, item.getName());
			helper.setText(R.id.tv_price, "USTD "+item.getPrice());
			helper.setText(R.id.tv_number, String.format(mContext.getString(R.string.paid_num), String.valueOf(item.getSale())));
			helper.itemView.setOnClickListener(view -> {
				GoodsDetailActivity.start(mContext,item.getId());
			});
		}
	}
}
