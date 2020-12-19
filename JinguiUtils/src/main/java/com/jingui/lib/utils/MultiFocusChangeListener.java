package com.jingui.lib.utils;

import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/24 14:43
 * <br>desc: 
 */
public class MultiFocusChangeListener implements View.OnFocusChangeListener {

	private List<View.OnFocusChangeListener> mListeners = new LinkedList<>();

	public void addListener(View.OnFocusChangeListener listener){
		mListeners.add(listener);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		for (View.OnFocusChangeListener listener : mListeners) {
			listener.onFocusChange(v, hasFocus);
		}
	}
}