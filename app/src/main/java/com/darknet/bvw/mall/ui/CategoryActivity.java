package com.darknet.bvw.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseActivity;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.bean.GoodsBannerBean;
import com.darknet.bvw.mall.bean.GoodsBean;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.mall.presenter.CategoryPresenter;
import com.darknet.bvw.mall.ui.search.SearchActivity;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.order.ui.activity.OrderListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/18 12:50
 * <br>desc: TODO
 */
public class CategoryActivity extends BaseActivity {

	private android.widget.ImageView mBack;
	private android.widget.ImageView mSearch;
	private com.darknet.bvw.view.CornerTextView mBtnOrder;
	private androidx.recyclerview.widget.RecyclerView mCategory;
	private androidx.viewpager2.widget.ViewPager2 mVp2;
	private CategoryAdapter mCategoryAdapter;

	private CategoryPresenter mPresenter = new CategoryPresenter(this);
	private Integer mId;
	private CategoryBean mFirstCategory;

	public static void start(Context context, CategoryBean category) {
	    Intent starter = new Intent(context, CategoryActivity.class);
	    starter.putExtra("category", category);
	    context.startActivity(starter);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_category;
	}

	@Override
	public void initView() {
		mBack = findViewById(R.id.back);
		mSearch = findViewById(R.id.search);
		mBtnOrder = findViewById(R.id.btn_order);
		mCategory = findViewById(R.id.category);
		mVp2 = findViewById(R.id.vp2);
		findViewById(R.id.search).setOnClickListener(view -> SearchActivity.start(this));
		findViewById(R.id.btn_order).setOnClickListener(view -> OrderListActivity.start(this,0));
		mBack.setOnClickListener(v -> finish());
		mCategory.setLayoutManager(new LinearLayoutManager(this));
		mCategory.setAdapter(mCategoryAdapter = new CategoryAdapter());
		mVp2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
	}

	@Override
	public boolean immerse() {
		return true;
	}

	@Override
	public void initDatas() {
		mFirstCategory = (CategoryBean) getIntent().getSerializableExtra("category");
		mPresenter.loadCategory(mFirstCategory);
	}

	public void initViewPager(BaseResponse<List<CategoryBean>> response) {
		mCategoryAdapter.setNewData(response.getData());
		mVp2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
			@NonNull
			@Override
			public Fragment createFragment(int position) {
				if(CategoryBean.isHome(mFirstCategory)){
					return CategoryGoodsFragment.newInstance(mCategoryAdapter.getItem(position).getId(), null);
				}
				return CategoryGoodsFragment.newInstance(mFirstCategory.getId(), mCategoryAdapter.getItem(position).getId());
			}

			@Override
			public int getItemCount() {
				return mCategoryAdapter.getItemCount();
			}
		});
		mVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				mCategoryAdapter.select(position);
				mCategory.scrollToPosition(position);
			}
		});
//		mVp2.setUserInputEnabled(fa);
	}

	private class CategoryAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

		private int selected = 0;

		public CategoryAdapter() {
			super(R.layout.item_category);
		}

		@Override
		protected void convert(BaseViewHolder helper, CategoryBean item) {
			TextView tv = (TextView) helper.itemView;
			tv.setSelected(selected == helper.getAdapterPosition());
			tv.setText(item.getName());
			tv.setOnClickListener(v -> {
				if(select(helper.getAdapterPosition())) {
					mVp2.setCurrentItem(selected);
				}
			});
		}

		public boolean select(int position){
			int old = selected;
			selected = position;
			if(old == selected) return false;
			notifyItemChanged(old);
			notifyItemChanged(selected);
			return true;
		}
	}
}
