package com.darknet.bvw.qvkuaibao.vm;

import android.app.Application;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.qvkuaibao.bean.PosBonus;
import com.darknet.bvw.util.ToastUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by guoshiwen on 2021/1/28.
 */
public class YbbHistoryViewModel extends BaseListViewModel<PosBonus> {
	public MutableLiveData<String> symbolLive = new MutableLiveData<>();

	public YbbHistoryViewModel(@NonNull Application application) {
		super(application);
	}

	@Override
	protected void loadData(int pageNum, boolean isClear) {
		apiService.getInOutList(symbolLive.getValue(), 500, pageNum)
				.compose(getCompose())
				.subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<PosBonus>>>() {
					@Override
					public void onSuccess(BaseResponse<BaseListBean<PosBonus>> t, boolean isFromCache) {
						notifyResultToTopViewModel(BaseListBean.getItems(t.getData()));
					}

					@Override
					public void onFailure(Throwable throwable) {
						ToastUtils.showToast(throwable.getMessage());
					}
				}));
	}
}