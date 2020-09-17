package com.darknet.bvw.bindingadapter;

import android.text.TextUtils;

import androidx.databinding.BindingAdapter;

import com.darknet.bvw.view.HorizontalLineView;

public class ViewAdapter {
    @BindingAdapter(value = {"right_text"}, requireAll = false)
    public static void setRightText(HorizontalLineView lineView, String rightText) {
        if (!TextUtils.isEmpty(rightText)) {
            lineView.setRightText(rightText);
        }
    }

    @BindingAdapter(value = {"onRightClick"}, requireAll = false)
    public static void setOnRightClick(HorizontalLineView lineView, boolean isClick) {
        if (isClick) {
            lineView.setRightImgClick();
        }
    }
}
