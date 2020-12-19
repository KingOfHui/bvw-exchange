package com.jingui.lib.utils.wrap;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/21 16:34
 * <br>desc: TODO
 */
public abstract class TextWatcherWrap implements TextWatcher {
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
