package com.darknet.bvw.mall.ui.search;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseActivity;
import com.darknet.bvw.util.ToastUtils;
import com.lxj.xpopup.util.KeyboardUtils;

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
	private MutableLiveData<String> searchHint = new MutableLiveData<>();
	private MutableLiveData<String> searchTrigger = new MutableLiveData<>();

	private boolean resultIsShowing;

	public static void start(Context context) {
	    Intent starter = new Intent(context, SearchActivity.class);
	    context.startActivity(starter);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_search;
	}

	@Override
	public void initView() {
		overridePendingTransition(0, 0);
		mEditText = findViewById(R.id.edit_text);
		mCancel = findViewById(R.id.cancel);

		mSearchResultFragment = SearchResultFragment.newInstance(searchTrigger);
		mHotFragment = HotFragment.newInstance(searchTrigger, searchHint);

		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.container, mHotFragment, "hot")
				.add(R.id.container, mSearchResultFragment, "result")
				.commit();

		mCancel.setOnClickListener(v -> onBackPressed());
		mEditText.setOnEditorActionListener((v, actionId, event) -> {
			String input = mEditText.getText().toString();
			String keyword = searchHint.getValue();
			if(TextUtils.isEmpty(input)){
				if(TextUtils.isEmpty(keyword)) {
					ToastUtils.showToast("请输入内容");
					return true;
				}else {
					input = keyword;
				}
			}
			if(!TextUtils.isEmpty(input)) {
				searchTrigger.postValue(input);
			}
			KeyboardUtils.hideSoftInput(mEditText);
			return true;
		});
		searchHint.observe(this, hint -> mEditText.setHint(hint));
		searchTrigger.observe(this, keyword -> {
			mEditText.setText(keyword);
			KeyboardUtils.hideSoftInput(mEditText);
			showSearchResult();
		});
		showHotSearch();
	}

	@Override
	public void onBackPressed() {
		if(resultIsShowing){
			showHotSearch();
		}else {
			finish();
		}
	}

	private void showHotSearch(){
		resultIsShowing = false;
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
