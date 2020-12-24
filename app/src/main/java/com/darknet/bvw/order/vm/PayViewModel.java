package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.model.DictByKeyResponse;
import com.darknet.bvw.model.request.CreateTradeRequest;
import com.darknet.bvw.model.request.SendTradeRequest;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.model.response.SendTradeResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;

public class PayViewModel extends BaseViewModel {
    public MutableLiveData<String> couponAddress = new MutableLiveData<>();
    public MutableLiveData<Boolean> tradeSuccessLive = new MutableLiveData<>();
    public MutableLiveData<SendTx> mSendTxMutableLiveData = new MutableLiveData<>();

    public PayViewModel(@NonNull Application application) {
        super(application);
    }


    public void getPayAddress(String key) {
        showLoading();
        apiService.dictByKey(key).compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<DictByKeyResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<DictByKeyResponse> t, boolean isFromCache) {
                        couponAddress.setValue(t.getData().getV());
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void createTrade(String num, String address, String walletType) {
        showLoading();
        CreateTradeRequest tradeRequest = new CreateTradeRequest();
        tradeRequest.setAmount(num);
        tradeRequest.setSymbol(walletType);
        tradeRequest.setTo_address(address);
        apiService.createRawTx(new RequestBodyBuilder().build(tradeRequest))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<SendTx>>() {
                    @Override
                    public void onSuccess(BaseResponse<SendTx> t, boolean isFromCache) {
                        mSendTxMutableLiveData.setValue(t.getData());
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void sendTrade(String afterSignVal,String price) {
        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(price);
        sendTradeRequest.setRaw(afterSignVal);
        sendTradeRequest.setSymbol("BIW");
        sendTradeRequest.setTo_address(couponAddress.getValue());
        sendTradeRequest.setType(16);
        sendTradeRequest.setDemo("购买优惠券");
        apiService.sendRawTx(new RequestBodyBuilder().build(sendTradeRequest))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<SendTradeResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<SendTradeResponse> t, boolean isFromCache) {
                        tradeSuccessLive.setValue(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
