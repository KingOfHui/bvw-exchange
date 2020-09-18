package com.darknet.bvw.viewmodel;

import android.app.Application;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.SuanLiListActivity;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.DictByKeyResponse;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.model.response.MineralStatusResponse;
import com.darknet.bvw.model.response.SuanLiResponse;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MineralViewModel extends AndroidViewModel {

    private MutableLiveData<MineralStatusResponse> mMineralStatusResponseLiveData;
    private MutableLiveData<MineralListResponse> mMineralListLive;
    public MutableLiveData<Boolean> isEmptyLive = new MutableLiveData<>();
    public MutableLiveData<Boolean> dismissLoadingLive = new MutableLiveData<>();

    public MutableLiveData<String> address = new MutableLiveData<>();

    public MutableLiveData<MineralStatusResponse> getMineralStatusResponseLiveData() {
        if (mMineralStatusResponseLiveData == null) {
            mMineralStatusResponseLiveData = new MutableLiveData<>();
        }
        return mMineralStatusResponseLiveData;
    }

    public MutableLiveData<MineralListResponse> getMineralListLive() {
        if (mMineralListLive == null) {
            mMineralListLive = new MutableLiveData<>();
        }
        return mMineralListLive;
    }

    public MineralViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * /api/v1/miner/status
     */
    public void getMineralStatus() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.GET_MINERAL_STATUS)
                .tag(MineralViewModel.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        dismissLoadingLive.setValue(true);
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            Log.e("dhdhdh", "onSuccess: " + backVal);

                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<MineralStatusResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<MineralStatusResponse>>(){}.getType());
                                    if (status != null && status.isSuccess()) {
                                        getMineralStatusResponseLiveData().setValue(status.getData());
                                        isEmptyLive.setValue(true);
                                    }
                                    /*SuanLiResponse response = gson.fromJson(backVal, SuanLiResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().getPowerHistoryList() != null && response.getData().getPowerHistoryList().size() > 0) {
                                            setVal(response.getData().getPowerHistoryList());
                                        } else {
//                                            Toast.makeText(SuanLiListActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                            setNoData();
                                        }
                                    }*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("dhdhdh", "onError: " + response.getException().toString());
                    }
                });
    }

    public void getMineralList() {
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
        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.GET_MINERAL_LIST)
                .tag(MineralViewModel.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
//                .params("limit",1000)
//                .params("page",0)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            Log.e("dhdhdh", "onSuccess: " + backVal);

                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<MineralListResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<MineralListResponse>>(){}.getType());
                                    if (status != null && status.isSuccess()) {
                                        getMineralListLive().setValue(status.getData());
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
                        isEmptyLive.setValue(true);
                        Log.e("dhdhdh", "onError: " + response.getException().toString());
                    }
                });
    }

    public void toPledge() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.GET_MINERAL_PAY_ADDRESS)
                .tag(MineralViewModel.class)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<DictByKeyResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<DictByKeyResponse>>(){}.getType());
                                    if (status != null && status.isSuccess()) {
                                        address.setValue(status.getData().getV());
                                    } else {
                                        Toast.makeText(getApplication(), "获取地址失败~", Toast.LENGTH_SHORT).show();
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

    public void btnClick() {
        Toast.makeText(getApplication(), "fjlafd", Toast.LENGTH_SHORT).show();

    }

    public SpannableStringBuilder getStatusText() {

        String  text = getApplication().getString(R.string.gu_zhang_zhong);
        return SpanHelper.start().next(text)
                .setTextSize(14)
                .setTextColor(Color.WHITE)
                .next("\n")
                .next(getApplication().getString(R.string.fix_after_48h))
                .setTextSize(9)
                .setTextColor(Color.WHITE)
                .get();
    }

    public SpannableStringBuilder getSpanString(String s, String symbol) {
        return SpanHelper.start()
                .next(s)
                .setTextColor(Color.parseColor("#01FCDA"))
                .setTextSize(28)
                .next(symbol)
                .setTextSize(15)
                .setTextColor(Color.WHITE)
                .get();
    }
}
