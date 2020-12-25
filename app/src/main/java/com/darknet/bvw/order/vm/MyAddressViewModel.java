package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.R;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.util.ToastUtils;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName MyAddressViewModle
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 16:43
 */
public class MyAddressViewModel extends BaseListViewModel<ShippingAddress> {

    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    public MutableLiveData<ShippingAddress> selectAddress = new MutableLiveData<>();

    public MyAddressViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        apiService.getAddressList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<List<ShippingAddress>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<ShippingAddress>> t, boolean isFromCache) {
                        List<ShippingAddress> data = t.getData();
                        notifyResultToTopViewModel(data);
                        if (CollectionUtil.isNotEmpty(data)) {
                            for (ShippingAddress address : data) {
                                if (address.getDefault_state() == 1) {
                                    selectAddress.setValue(address);
                                    break;
                                }
                            }
                        }
                        selectAddress.setValue(null);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        loadFailed(throwable.getMessage());
                        selectAddress.setValue(null);
                    }
                }));
    }

    public void addAddress(ShippingAddress shippingAddress) {
        BIWNetworkApi.getService(ApiInterface.class).addAddress(new RequestBodyBuilder().build(shippingAddress))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        isSuccess.setValue(true);
                        ToastUtils.showToast(getApplication().getString(R.string.add_address_success));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));

    }

    public void updateAddress(ShippingAddress shippingAddress) {
        apiService.updateAddress(new RequestBodyBuilder().build(shippingAddress))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        isSuccess.setValue(true);
                        ToastUtils.showToast(getApplication().getString(R.string.update_address_success));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }
}
