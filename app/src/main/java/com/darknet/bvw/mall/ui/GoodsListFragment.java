package com.darknet.bvw.mall.ui;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentGoodsListBinding;
import com.darknet.bvw.databinding.ItemGoodsBinding;
import com.darknet.bvw.mall.bean.GoodsBannerBean;
import com.darknet.bvw.mall.bean.GoodsBean;
import com.darknet.bvw.mall.vm.GoodsListViewModel;
import com.darknet.bvw.util.EnvironmentUtil;
import com.darknet.bvw.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GoodsListFragment extends BaseBindingFragment<GoodsListViewModel, FragmentGoodsListBinding> {

	private View mHeader;
	private Banner mBanner;

	public static GoodsListFragment newInstance(String titleName) {
		GoodsListFragment fragment = new GoodsListFragment();
		Bundle bundle = new Bundle();
		bundle.putString("title", titleName);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int setLayoutResId() {
		return R.layout.fragment_goods_list;
	}

	@Override
	protected GoodsListViewModel initViewModel() {
		return getFragmentViewModel(GoodsListViewModel.class);
	}

	@Override
	protected void initView() {
		mDataBinding.setAdapter(new Adapter());
		GridLayoutManager lm = new GridLayoutManager(getContext(), 2);
		lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				if (mDataBinding.getAdapter().getItemViewType(position) == BaseQuickAdapter.HEADER_VIEW) {
					return 1;
				}
				return 2;
			}
		});
		mDataBinding.rv.setLayoutManager(lm);
		mDataBinding.rv.addItemDecoration(
				new RecyclerView.ItemDecoration() {
					@Override
					public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
						super.getItemOffsets(outRect, view, parent, state);
						int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
						int dp10 = EnvironmentUtil.dp2px(view.getContext(), 10);
						if (itemPosition < 0) {
							return;
						}
						if (itemPosition % 2 == 0) {
							outRect.left = dp10;
							outRect.right = dp10;
						} else {
							outRect.left = dp10;
							outRect.right = 0;
						}
					}
				}
		);
		mHeader = View.inflate(getContext(), R.layout.goods_list_header, null);
        initBanner();

        mDataBinding.getAdapter().addHeaderView(mHeader);
        mViewModel.getListLive().observe(this
				, objects -> mDataBinding.getAdapter().setNewData(objects));
	}

    private void initBanner() {
        mBanner = mHeader.findViewById(R.id.banner);
        mBanner.setImageLoader(new GlideImageLoader(){
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                GoodsBannerBean data = (GoodsBannerBean) path;
                super.displayImage(context, data.getBanner_img(), imageView);
            }
        });
        mViewModel.getBanner().observe(this, banner -> {
            mBanner.setImages(banner);
        });
        mBanner.setOnBannerListener(position -> {
            List<GoodsBannerBean> value = mViewModel.getBanner().getValue();
            if(value == null) return;
            GoodsBannerBean data = value.get(position);
            // TODO
        });
    }

    @Override
	protected void initData() {
		Bundle bundle = getArguments();
		String title = bundle.getString("title");
	}

	public static class Adapter extends BaseDataBindingAdapter<GoodsBean, ItemGoodsBinding> {

		public Adapter() {
			super(R.layout.item_goods);
		}

		@Override
		protected void convert(ItemGoodsBinding binding, GoodsBean item) {
			binding.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            Glide.with(binding.ivIcon.getContext())
                    .load(item.getProduct_img())
                    .apply(RequestOptions.centerCropTransform())
                    .into(binding.ivIcon);
//            binding.tag TODO 没字段
            binding.tvName.setText(item.getName());
            binding.tvPrice.setText("USTD "+item.getPrice());
            binding.tvOriginalPrice.setText("USTD "+item.getOriginal_price());
//            binding.tvNumber TODO 没字段
		}
	}
}
