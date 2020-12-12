package com.darknet.bvw.mall.vm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.adapter.BaseViewHolder;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.model.response.NoticeResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import io.reactivex.Observable;

/**
 * @ClassName MallViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:54
 */
public class MallViewModel extends BaseViewModel {
    public MallViewModel(@NonNull Application application) {
        super(application);
    }

}
