package com.darknet.bvw.mall.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentGoodsListBinding;
import com.darknet.bvw.databinding.ItemGoodsBinding;
import com.darknet.bvw.mall.vm.GoodsListViewModel;
import com.darknet.bvw.util.EnvironmentUtil;

public class GoodsListFragment extends BaseBindingFragment<GoodsListViewModel, FragmentGoodsListBinding> {

    private View mHeader;

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
                        if(itemPosition % 2 == 0){
                            outRect.left = dp10;
                            outRect.right = dp10;
                        }else {
                            outRect.left = dp10;
                            outRect.right = 0;
                        }
                    }
                }
        );
        mHeader = View.inflate(getContext(), R.layout.goods_list_header, null);
        mDataBinding.getAdapter().addHeaderView(mHeader);
        mViewModel.getListLive().observe(this
                , objects -> mDataBinding.getAdapter().setNewData(objects));
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
    }

    public static class Adapter extends BaseDataBindingAdapter<Object, ItemGoodsBinding> {

        public Adapter() {
            super(R.layout.item_goods);
        }

        @Override
        protected void convert(ItemGoodsBinding itemGoodsBinding, Object item) {

        }
    }
}
