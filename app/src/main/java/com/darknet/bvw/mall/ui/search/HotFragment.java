package com.darknet.bvw.mall.ui.search;

import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseFragment;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by guoshiwen on 2020/12/23.
 */
public class HotFragment extends BaseFragment {

	private MutableLiveData<String> searchTrigger;
	private QMUIFloatLayout mFloatLayout;

	public static HotFragment newInstance(MutableLiveData<String> searchTrigger) {
		HotFragment fragment = new HotFragment();
		fragment.searchTrigger = searchTrigger;
		return fragment;
	}


	@Override
	public void initView(View view) {
		mFloatLayout = findViewById(R.id.float_layout);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_hot;
	}

	@Override
	public void initDatas() {

	}

	@Override
	public void initEvent() {

	}
}