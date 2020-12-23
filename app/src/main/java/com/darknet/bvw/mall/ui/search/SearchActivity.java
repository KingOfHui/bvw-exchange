package com.darknet.bvw.mall.ui.search;

import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseActivity;
import com.darknet.bvw.mall.presenter.SearchPresenter;
import com.darknet.bvw.view.wrap.TextWatcherWrapper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/19 13:09
 * <br>desc: TODO
 */
public class SearchActivity extends BaseActivity {

	private HotFragment mHotFragment;
	private SearchResultFragment mSearchResultFragment;
	private EditText mEditText;
	private TextView mCancel;
	private MutableLiveData<String> searchTrigger = new MutableLiveData<>();

	private boolean resultIsShowing;

	@Override
	public int getLayoutId() {
		return R.layout.activity_search;
	}

	@Override
	public void initView() {
		mEditText = findViewById(R.id.edit_text);
		mCancel = findViewById(R.id.cancel);

		mSearchResultFragment = SearchResultFragment.newInstance(searchTrigger);
		mHotFragment = HotFragment.newInstance(searchTrigger);

		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.container, mHotFragment, "hot")
				.add(R.id.container, mSearchResultFragment, "result")
				.commit();

		mCancel.setOnClickListener(v -> {
			onBackPressed();
		});
		mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				searchTrigger.setValue(mEditText.getText().toString());
				return true;
			}
		});

		showHotSearch();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(resultIsShowing){
			showHotSearch();
		}else {
			finish();
		}
	}

	private void showHotSearch(){
		getSupportFragmentManager()
				.beginTransaction()
				.show(mHotFragment)
				.hide(mSearchResultFragment)
				.commit();
	}


	private void showSearchResult(){
		getSupportFragmentManager()
				.beginTransaction()
				.show(mSearchResultFragment)
				.hide(mHotFragment)
				.commit();
		resultIsShowing = true;
	}

	@Override
	public void initDatas() {

	}
}
