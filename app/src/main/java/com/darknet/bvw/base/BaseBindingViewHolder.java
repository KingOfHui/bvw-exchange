package com.darknet.bvw.base;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * DataBinding的BaseViewHolder
 */

public class BaseBindingViewHolder<B extends ViewDataBinding> extends BaseViewHolder {
    private B mB;

    public BaseBindingViewHolder(View view) {
        super(view);
    }

    public B getBinding() {
        return mB;
    }

    public void setBinding(B b) {
        mB = b;
    }
}
