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
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.net.retrofit.errorhandler.ExceptionHandlerUtil;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.util.ToastUtils;
import com.vector.update_app.listener.ExceptionHandler;

public class PayViewModel extends BaseViewModel {
    public MutableLiveData<String> couponAddress = new MutableLiveData<>();
    public MutableLiveData<Boolean> tradeSuccessLive = new MutableLiveData<>();
    public MutableLiveData<SendTx> mSendTxMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<OrderResp> mOrderDetailLiveData = new MutableLiveData<>();

    public PayViewModel(@NonNull Application application) {
        super(application);
    }


    public void getPayAddress(String key) {
        showLoading();
        apiService.dictByKey(key).compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<DictByKeyResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<DictByKeyResponse> t, boolean isFromCache) {
                        hideLoading();
                        couponAddress.setValue(t.getData().getV());
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

    public void sendDiscountsTrade(String afterSignVal, String price, int coupon_template_id) {
        showLoading();
        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(price);
        sendTradeRequest.setRaw(afterSignVal);
        sendTradeRequest.setSymbol("BIW");
        sendTradeRequest.setTo_address(couponAddress.getValue());
        sendTradeRequest.setType(16);
        sendTradeRequest.setCoupon_template_id(coupon_template_id);
        sendTradeRequest.setDemo("购买优惠券");
        senTradeRequest(sendTradeRequest);
    }
    public void sendOrderTrade(String afterSignVal, String price, String address, int id) {
        showLoading();
        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(price);
        sendTradeRequest.setRaw(afterSignVal);
        sendTradeRequest.setSymbol("USDT");
        sendTradeRequest.setOrder_id(id);
        sendTradeRequest.setTo_address(address);
        sendTradeRequest.setType(15);
        sendTradeRequest.setDemo("订单支付");
        senTradeRequest(sendTradeRequest);
    }

    //余币宝转入
    public void sendTransferInTrade(String afterSignVal, String price, String address, String symbol) {
        showLoading();
        SendTradeRequest sendTradeRequest = new SendTradeRequest();
        sendTradeRequest.setAmount(price);
        sendTradeRequest.setRaw(afterSignVal);
        sendTradeRequest.setSymbol(symbol);
        sendTradeRequest.setOrder_id(0);
        sendTradeRequest.setTo_address(address);
        sendTradeRequest.setType(17);
        senTradeRequest(sendTradeRequest);
    }
    private void senTradeRequest(SendTradeRequest sendTradeRequest) {
        apiService.sendRawTx(new RequestBodyBuilder().build(sendTradeRequest))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        hideLoading();
                        tradeSuccessLive.setValue(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void getOrderDetail(int id) {
        showLoading();
        apiService.getOrderDetail(id).compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<OrderResp>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderResp> t, boolean isFromCache) {
                        mOrderDetailLiveData.setValue(t.getData());
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
