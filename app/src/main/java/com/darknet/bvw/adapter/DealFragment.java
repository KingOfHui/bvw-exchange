package com.darknet.bvw.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.MessageCenterActivity;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.DealModel;
import com.darknet.bvw.model.request.DealRequest;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.JiaoYiResponse;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：created by albert on 2019-12-23 21:22
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class DealFragment extends Fragment {

    private MyListView mListView;
    private DealAdapter mDealAdapter;
    private List<JiaoYiResponse.JiaoYiModel> mList = new ArrayList<>();

    private TextView priceTypeView;
    private TextView biTypeView;

    private static String markId;

    public static DealFragment newInstance(String val) {
        DealFragment fragment = new DealFragment();
        markId = val;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deal, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDealData();
    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.deal_lv);
        priceTypeView = view.findViewById(R.id.chenjiao_price_type);
        biTypeView = view.findViewById(R.id.chengjiao_bi_type);


        if (markId != null) {
            priceTypeView.setText(markId.split("-")[1]);
            biTypeView.setText(markId.split("-")[0]);
        }

        mDealAdapter = new DealAdapter(getActivity(), mList);
        mListView.setAdapter(mDealAdapter);

    }


    private void getDealData() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        DealRequest tradeRequest = new DealRequest();
        tradeRequest.setPage(1);
        tradeRequest.setLimit(50);
        tradeRequest.setMarketId(markId);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.DEAL_LIST_URL)
                .tag(getActivity())
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal", "backVal=" + backVal);
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    JiaoYiResponse response = gson.fromJson(backVal, JiaoYiResponse.class);
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                                        setVal(response.getData().getItems());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }
                });
    }


    private void setVal(List<JiaoYiResponse.JiaoYiModel> items) {
        mDealAdapter.addAllJiaoYiData(items);
    }


}
