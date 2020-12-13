package com.darknet.bvw.util.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseViewModel;

import java.util.List;

/**
 * @ClassName RecyclerViewBindingAdapter
 * @Description RecyclerView扩展属性
 * @Author dinghui
 * @Date 2020/11/25 0025 14:10
 */
public class RecyclerViewBindingAdapter {

    @BindingAdapter("linearItemDecoration")
    public static void setLineManager(
            RecyclerView recyclerView,
            LineDivideManagers.LineManagerFactory lineManagerFactory
    ) {
        recyclerView.addItemDecoration(lineManagerFactory.create(recyclerView));
    }

    @BindingAdapter("itemAnimator")
    public static void setItemAnimator(RecyclerView recyclerView, RecyclerView.ItemAnimator itemAnimator) {
        recyclerView.setItemAnimator(itemAnimator);
    }

    @BindingAdapter(value = {"adapter", "submitList", "autoScrollToTopWhenInsert", "autoScrollToBottomWhenInsert"}, requireAll = false)
    public static <T, VH extends BaseViewHolder> void bindList(RecyclerView recyclerView, BaseQuickAdapter<T, VH> adapter, List<T> list,
                                                               boolean autoScrollToTopWhenInsert, boolean autoScrollToBottomWhenInsert) {

        if (recyclerView != null && list != null) {

            if (recyclerView.getAdapter() == null) {
                recyclerView.setAdapter(adapter);

                adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        if (autoScrollToTopWhenInsert) {
                            recyclerView.scrollToPosition(0);
                        } else if (autoScrollToBottomWhenInsert) {
                            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
                        }
                    }
                });
            }

            if (list!=null && list.size()>0) {
                adapter.setNewData(list);
            } else {
                View view = View.inflate(recyclerView.getContext(), R.layout.empty_view, null);
                adapter.setEmptyView(view);
            }
        }
    }


    @BindingAdapter(value = {"notifyCurrentListChanged"})
    public static void notifyListChanged(RecyclerView recyclerView, boolean notify) {
        if (notify && recyclerView != null && recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}
