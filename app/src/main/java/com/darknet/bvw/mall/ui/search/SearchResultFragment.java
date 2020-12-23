package com.darknet.bvw.mall.ui.search;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseBindingViewHolder;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.databinding.ItemGoodsBinding;
import com.darknet.bvw.mall.bean.GoodsBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.presenter.SearchPresenter;
import com.darknet.bvw.mall.ui.DetailActivity;
import com.darknet.bvw.mall.ui.GoodsDetailActivity;
import com.darknet.bvw.util.EnvironmentUtil;
import com.darknet.bvw.util.loadmore.SmartLoadmoreHolder;
import com.darknet.bvw.util.refresh.SmartRefreshHolder;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.jingui.lib.utils.DensityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by guoshiwen on 2020/12/23.
 */
public class SearchResultFragment extends BaseFragment {

	private MutableLiveData<String> searchTrigger;

	private SearchPresenter mPresenter = new SearchPresenter(this);
	private RecyclerView mRv;
	private SmartRefreshLayout mRefresh;
	private Adapter mAdapter;

	private SmartRefreshHolder<GoodsDetailBean> mRefreshHolder;
	private SmartLoadmoreHolder<GoodsDetailBean> mLoadmoreHolder;

	public static SearchResultFragment newInstance(MutableLiveData<String> searchTrigger) {
		SearchResultFragment fragment = new SearchResultFragment();
		fragment.searchTrigger = searchTrigger;
		return fragment;
	}

	@Override
	public void initView(View view) {
		mRv = findViewById(R.id.rv);
		mRefresh = findViewById(R.id.refresh);
		mRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
		mRv.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
				int dp10 = EnvironmentUtil.dp2px(view.getContext(), 10);
				int dp5 = EnvironmentUtil.dp2px(view.getContext(), 5);
				if (itemPosition < 0) {
					return;
				}
				if (itemPosition % 2 == 1) {
					outRect.left = dp5;
					outRect.right = dp10;
				} else {
					outRect.left = dp10;
					outRect.right = dp5;
				}
			}
		});
		mAdapter = new Adapter();
		mRv.setAdapter(mAdapter);
		mRefreshHolder = new SmartRefreshHolder<>(mRefresh, mAdapter);
		mLoadmoreHolder = new SmartLoadmoreHolder<>(mRefresh, mAdapter);
		mRefreshHolder.setRefreshListener(mPresenter);
		mLoadmoreHolder.setLoadmoreListener(mPresenter);
		mRefreshHolder.bind(mLoadmoreHolder);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_search_result;
	}

	@Override
	public void initDatas() {
		searchTrigger.observe(this, text -> mPresenter.search(text));
	}

	public SmartRefreshHolder<GoodsDetailBean> getRefreshHolder() {
		return mRefreshHolder;
	}

	@Override
	public void initEvent() {

	}

	public static class Adapter extends BaseQuickAdapter<GoodsDetailBean, BaseViewHolder> {

		public Adapter() {
			super(R.layout.item_goods_search_result);
		}

		@Override
		protected void convert(BaseViewHolder helper, GoodsDetailBean item) {
			TextView textView = (TextView) helper.getView(R.id.tv_original_price);
			textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			Glide.with(helper.itemView.getContext())
					.load(item.getImg_url())
					.apply(RequestOptions.centerCropTransform())
					.placeholder(R.mipmap.default_item)
					.into((ImageView)helper.getView(R.id.iv_icon));
			helper.setGone(R.id.tag, item.getCoupon_discount() > 0);
			helper.setText(R.id.tag, "现金卷"+item.getCoupon_discount());
			helper.setText(R.id.tv_name, item.getName());
			helper.setText(R.id.tv_price, "USTD "+item.getPrice());
			helper.setGone(R.id.tv_original_price, !item.getPrice().equals(item.getOriginal_price()));
			helper.setText(R.id.tv_original_price, "USTD "+item.getOriginal_price());
//            binding.tvNumber TODO 没字段
			helper.itemView.setOnClickListener(v -> {
				DetailActivity.start(v.getContext());
			});

		}
	}
}