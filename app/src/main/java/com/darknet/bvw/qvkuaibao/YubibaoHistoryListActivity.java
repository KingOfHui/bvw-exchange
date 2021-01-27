package com.darknet.bvw.qvkuaibao;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.YbbInOutHistoryBinding;
import com.darknet.bvw.mall.vm.MallViewModel;
import com.darknet.bvw.qvkuaibao.adapter.BonusListAdapter;
import com.darknet.bvw.qvkuaibao.adapter.YbbHistoryAdapter;
import com.darknet.bvw.qvkuaibao.vm.YbbHistoryViewModel;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by guoshiwen on 2021/1/28.
 */
public class YubibaoHistoryListActivity extends BaseBindingActivity<YbbInOutHistoryBinding> {

	public static void start(Context context, String symbol) {
	    Intent starter = new Intent(context, YubibaoHistoryListActivity.class);
	    starter.putExtra("symbol", symbol);
	    context.startActivity(starter);
	}
	@Override
	public int getLayoutId() {
		return R.layout.ybb_in_out_history;
	}

	@Override
	public void initView() {
		String symbol = getIntent().getStringExtra("symbol");

		mBinding.titleLayout.layBack.setOnClickListener(view -> finish());
		mBinding.titleLayout.title.setText("历史记录");
		mBinding.setAdapter(new YbbHistoryAdapter());
		YbbHistoryViewModel viewModel = getViewModel(YbbHistoryViewModel.class);
		viewModel.symbolLive.setValue(symbol);
		viewModel.refresh();
	}

	@Override
	public void initDatas() {

	}
}