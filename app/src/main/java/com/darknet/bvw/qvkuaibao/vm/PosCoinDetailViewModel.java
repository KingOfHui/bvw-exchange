package com.darknet.bvw.qvkuaibao.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.qvkuaibao.bean.PosBonus;
import com.darknet.bvw.qvkuaibao.bean.PosWalletData;
import com.darknet.bvw.util.ToastUtils;

import java.util.List;

public class PosCoinDetailViewModel extends BaseListViewModel<PosBonus> {

	public MutableLiveData<PosWalletData> mPosWalletDataMutableLiveData = new MutableLiveData<>();
	private MutableLiveData<String> symbolLive = new MutableLiveData<>();

	public PosCoinDetailViewModel(@NonNull Application application) {
		super(application);
	}

	public void getWalletData(String symbol) {
		symbolLive.setValue(symbol);
		apiService.getWalletData(symbol).compose(getCompose())
				.subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<PosWalletData>>() {
					@Override
					public void onSuccess(BaseResponse<PosWalletData> t, boolean isFromCache) {
						mPosWalletDataMutableLiveData.setValue(t.getData());
						refresh();
					}

					@Override
					public void onFailure(Throwable throwable) {
						ToastUtils.showToast(throwable.getMessage());
					}
				}));

	}

	public String getSymbol(){
		return symbolLive.getValue();
	}

	public void out(String amount, String password, Runnable successCallback) {
		showLoading();
		apiService.outBySymbol(
				new RequestBodyBuilder()
						.addParams("amount", amount)
						.addParams("symbol", symbolLive.getValue())
						.build()
		).compose(getCompose()).subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Boolean>>() {
			@Override
			public void onSuccess(BaseResponse<Boolean> t, boolean isFromCache) {
			    if(t.isSuccess()) {
                    refresh();
                    getWalletData(symbolLive.getValue());
                    successCallback.run();
					ToastUtils.showToast("转出成功");
                }else {
			        ToastUtils.showToast(t.getMsg());
                }
			    hideLoading();
			}

			@Override
			public void onFailure(Throwable throwable) {
				ToastUtils.showToast(throwable.getMessage());
				hideLoading();
			}
		}));

	}

	public void toggleVisible() {
		PosWalletData value = mPosWalletDataMutableLiveData.getValue();
		mPosWalletDataMutableLiveData.setValue(value);
	}

	@Override
	protected void loadData(int pageNum, boolean isClear) {
		apiService.getBonusList(symbolLive.getValue(), 500, pageNum)
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
