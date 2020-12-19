package com.jingui.lib.utils.wrap;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/28 21:03
 */
public class NoneScrollLinearLayoutManager extends LinearLayoutManager {
	public NoneScrollLinearLayoutManager(Context context) {
		super(context);
	}

	public NoneScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
		super(context, orientation, reverseLayout);
	}

	public NoneScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean canScrollVertically() {
		return false;
	}

	@Override
	public boolean canScrollHorizontally() {
		return false;
	}
}
