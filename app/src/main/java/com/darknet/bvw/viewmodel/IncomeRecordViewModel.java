package com.darknet.bvw.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.MineralBonusListResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class IncomeRecordViewModel extends AndroidViewModel {

    public MutableLiveData<List<MineralBonusListResponse.ItemsBean>> itemsLive = new MutableLiveData<>();
    public IncomeRecordViewModel(@NonNull Application application) {
        super(application);
    }

    public void getIncomeRecord(int type) {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Gson gson = new Gson();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", 50);
        map.put("page", 0);
        map.put("type", type);
        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(map));
        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.GET_MINERAL_BONUS_LIST)
                .tag(MineralViewModel.class)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<MineralBonusListResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<MineralBonusListResponse>>() {
                                    }.getType());
                                    if (status != null && status.isSuccess()) {
                                        List<MineralBonusListResponse.ItemsBean> items = status.getData().getItems();
                                        itemsLive.setValue(items);
                                    } else {
                                        Toast.makeText(getApplication(), "获取收益列表失败~", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }
}
