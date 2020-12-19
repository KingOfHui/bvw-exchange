package com.darknet.bvw.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.recyclerview.widget.LinearLayoutManager;
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

	public static void start(Context context) {
	    Intent starter = new Intent(context, CategoryActivity.class);
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
		mBack.setOnClickListener(v -> finish());
		mCategory.setLayoutManager(new LinearLayoutManager(this));
		mCategory.setAdapter(mCategoryAdapter = new CategoryAdapter());
		mCategoryAdapter.setNewData(new ArrayList<>(Arrays.asList(
				"美食"
				,"美妆护肤"
				,"防护鞋 帽"
				,"珠宝首饰"
				,"酒水零食"
				,"母婴用品"
				,"生鲜果品"
				,"面膜"
		)));
		mVp2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
//		mVp2.setAdapter();
	}

	@Override
	public boolean immerse() {
		return true;
	}

	@Override
	public void initDatas() {

	}

	private static class CategoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

		private int selected = 0;

		public CategoryAdapter() {
			super(R.layout.item_category);
		}

		@Override
		protected void convert(BaseViewHolder helper, String item) {
			TextView tv = (TextView) helper.itemView;
			tv.setSelected(selected == helper.getAdapterPosition());
			tv.setText(item);
			tv.setOnClickListener(v -> {
				int old = selected;
				selected = helper.getAdapterPosition();
				if(old == selected) return;
				notifyItemChanged(old);
				notifyItemChanged(selected);
			});
		}
	}
}
