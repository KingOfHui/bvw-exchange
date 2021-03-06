package com.darknet.bvw.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;


/**
 * 使用DataBinding的BaseQuickAdapter
 */

public abstract class BaseDataBindingAdapter<T, B extends ViewDataBinding> extends BaseQuickAdapter<T, BaseBindingViewHolder<B>> {


    public BaseDataBindingAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseDataBindingAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseDataBindingAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected BaseBindingViewHolder<B> createBaseViewHolder(View view) {
        return new BaseBindingViewHolder<>(view);
    }

    @Override
    protected BaseBindingViewHolder<B> createBaseViewHolder(ViewGroup parent, int layoutResId) {
        B b = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        View view;
        if (b == null) {
            view = getItemView(layoutResId, parent);
        } else {
            view = b.getRoot();
        }
        BaseBindingViewHolder<B> holder = new BaseBindingViewHolder<>(view);
        holder.setBinding(b);
        return holder;
    }

    @Override
    protected void convert(@NonNull BaseBindingViewHolder<B> helper, T item) {
        convert(helper.getBinding(), item);
        helper.getBinding().executePendingBindings();
    }

    protected abstract void convert(B b, T item);
}
