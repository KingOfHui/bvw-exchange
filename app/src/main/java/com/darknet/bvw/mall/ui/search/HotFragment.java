package com.darknet.bvw.mall.ui.search;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.view.CornerTextView;
import com.jingui.lib.utils.DensityUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by guoshiwen on 2020/12/23.
 */
public class HotFragment extends BaseFragment {

	private MutableLiveData<String> searchTrigger;
	private MutableLiveData<String> searchHint;
	private QMUIFloatLayout mFloatLayout;

	public static HotFragment newInstance(MutableLiveData<String> searchTrigger, MutableLiveData<String> searchHint) {
		HotFragment fragment = new HotFragment();
		fragment.searchTrigger = searchTrigger;
		fragment.searchHint = searchHint;
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
		BIWNetworkApi.getService(ApiInterface.class)
				.hotKeyword()
				.compose(BIWNetworkApi.getInstance().applySchedulers())
				.subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<String>>() {
					@Override
					public void onSuccess(BaseResponse<String> response, boolean isFromCache) {
						String data = response.getData();
						String[] keywords = data.split(",");
						updateHotKeywords(keywords);
					}

					@Override
					public void onFailure(Throwable throwable) {

					}
				}));
	}

	private void updateHotKeywords(String[] keywords) {
		if(keywords.length <= 0) return;
		searchHint.setValue(keywords[0]);
		for (int i = 1; i < keywords.length; i++) {
			addKeywordToLayout(keywords[i], i <= 3);
		}
	}

	private void addKeywordToLayout(String keyword, boolean isHot){
		CornerTextView tv = new CornerTextView(getContext());
		tv.setColor(Color.parseColor("#272436"));
		tv.setCorner(DensityUtils.dip2px(2));
		if(isHot){
			tv.setTextColor(Color.parseColor("#01FCDA"));
			Drawable drawable = tv.getResources().getDrawable(R.mipmap.icon_hot);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			tv.setCompoundDrawables(drawable, null, null, null);
			tv.setCompoundDrawablePadding(DensityUtils.dip2px(5));
		}else {
			tv.setTextColor(Color.parseColor("#FFFFFF"));
		}
		int padding = DensityUtils.dip2px(10);
		tv.setPadding(padding, padding, padding, padding);
		tv.setTextSize(12);
		tv.setText(keyword);
		tv.setOnClickListener(v ->{
			searchTrigger.postValue(keyword);
		});
		mFloatLayout.addView(tv);
	}

	@Override
	public void initEvent() {

	}
}