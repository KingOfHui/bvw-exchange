package com.darknet.bvw.viewmodel;

import android.app.Application;
import android.util.FloatProperty;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.InviteDataResponse;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import okhttp3.RequestBody;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class InviteViewModel extends AndroidViewModel {
    private MutableLiveData<InviteDataResponse> mInviteDataLive;
    public MutableLiveData<Boolean> isEmptyLive = new MutableLiveData<>();

    public InviteViewModel(@NonNull Application application) {
        super(application);
    }

    public void getInviteStatus() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", 20);
        map.put("page", 0);
        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(map));
        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.GET_USER_INVITE_DATA)
                .tag(InviteViewModel.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            Log.e("dhdhdh", "onSuccess: " + backVal);

                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<InviteDataResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<InviteDataResponse>>() {
                                    }.getType());
                                    if (status != null && status.isSuccess()) {
                                        getInviteDataLive().setValue(status.getData());
                                        isEmptyLive.setValue(false);
                                    } else {
                                        isEmptyLive.setValue(true);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    isEmptyLive.setValue(true);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        getInviteDataLive().setValue(null);
                        isEmptyLive.setValue(true);
                        Log.e("dhdhdh", "onError: " + response.getException().toString());
                    }
                });

    }

    public MutableLiveData<InviteDataResponse> getInviteDataLive() {
        if (mInviteDataLive == null) {
            mInviteDataLive = new MutableLiveData<>();
        }
        return mInviteDataLive;
    }
}
