package com.darknet.bvw.order.vm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.bean.OrderResp;
import com.darknet.bvw.util.ToastUtils;

import java.util.List;

/**
 * @ClassName OrderListViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 16:51
 */
public class OrderListViewModel extends BaseListViewModel<OrderResp> {
    public OrderListViewModel(@NonNull Application application) {
        super(application);
    }

    //类型 -1全部 0->待付款；1->待发货 2->已发货|待收货 3->已完成 4->已取消 8待评价,可用值:-1,0,1,2,3,8
    private MutableLiveData<Integer> tradeStateLive;

    public MutableLiveData<Integer> getTradeStateLive() {
        if (tradeStateLive == null) {
            tradeStateLive = new MutableLiveData<>();
            tradeStateLive.setValue(-1);
        }
        return tradeStateLive;
    }

    @Override
    @SuppressLint("CheckResult")
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        BIWNetworkApi.getService(ApiInterface.class).getOrderList(getTradeStateLive().getValue(), 20, pageNum)
                .compose(BIWNetworkApi.getInstance()
                        .applySchedulers())
                .subscribe(new BaseObserver<>(this,
                        new MvvmNetworkObserver<BaseResponse<BaseListBean<OrderResp>>>() {
                            @Override
                            public void onSuccess(BaseResponse<BaseListBean<OrderResp>> t, boolean isFromCache) {
                                List<OrderResp> items = BaseListBean.getItems(t.getData());
                                notifyResultToTopViewModel(items, 20);
                                hideLoading();
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                loadFailed(throwable.getMessage());
                                hideLoading();
                            }
                        }
                ));
    }

    public void cancelOrder(int id) {
        showLoading();
        apiService.cancelOrder(new RequestBodyBuilder().addParams("id",id).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        refresh();
                        ToastUtils.showToast(getApplication().getString(R.string.cancel_order_success));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void tipDelivery(int id) {
        showLoading();
        apiService.tipDelivery(new RequestBodyBuilder().addParams("id",id).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        refresh();
                        ToastUtils.showToast(getApplication().getString(R.string.tip_success));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void confirmReceive(int id) {
        showLoading();
        apiService.confirmReceive(new RequestBodyBuilder().addParams("id",id).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        refresh();
                        ToastUtils.showToast(getApplication().getString(R.string.order_complete));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
