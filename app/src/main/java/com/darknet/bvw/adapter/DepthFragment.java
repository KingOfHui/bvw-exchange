package com.darknet.bvw.adapter;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.DataRequest20;
import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.ChangeDepthResponse;
import com.darknet.bvw.model.DepthResponse;
import com.darknet.bvw.model.RequestEntity;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：created by albert on 2019-12-23 21:18
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class DepthFragment extends Fragment {


    private ListView depth_out_lv;
    private ListView depth_in_lv;

    //卖
    private InDepthAdapter mInAdapter;
    //买
    private OutDepthAdapter mOutAdapter;

    private List<DepthResponse.DataBean.AsksBean> mInList = new ArrayList<>();
    private List<DepthResponse.DataBean.BidsBean> mOutList = new ArrayList<>();
    private int first = 0;

    private static String marketId;

    private TextView biTypeView;
    private TextView biTypeTwoView;
    private TextView priceTypeView;


    public static DepthFragment newInstance(String market) {
        DepthFragment fragment = new DepthFragment();
        marketId = market;
        return fragment;
    }

//    public void setMarketId(String market){
//        this.marketId = market;
//        initDataDeapData();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depth, container, false);
        initView(view);
//        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataDeapData();
    }

    private void initView(View view) {
        depth_out_lv = view.findViewById(R.id.depth_out_lv);
        depth_in_lv = view.findViewById(R.id.depth_in_lv);

        biTypeView = view.findViewById(R.id.depth_bi_type);
        biTypeTwoView = view.findViewById(R.id.depth_bi_type_two);
        priceTypeView = view.findViewById(R.id.depth_price_type);

        mInAdapter = new InDepthAdapter(getActivity(), mInList);
        depth_in_lv.setAdapter(mInAdapter);
        mOutAdapter = new OutDepthAdapter(getActivity(), mOutList);
        depth_out_lv.setAdapter(mOutAdapter);


        try {
            biTypeView.setText(marketId.split("-")[0]);
            priceTypeView.setText(marketId.split("-")[1]);
            biTypeTwoView.setText(marketId.split("-")[0]);
        }catch (Exception e){
            e.printStackTrace();
        }



    }


//    private void initData() {
//        ChangeDepthResponse dataBean = DataRequest20.getALL(getActivity());
//        String base = "0";
//        mInList.clear();
//        mOutList.clear();
//
//        for (int i = 0; i < 20; i++) {
//            if (i == 0) {
//                dataBean.getAsks().get(i).setTotal(dataBean.getAsks().get(i).getAmount());
//                dataBean.getBids().get(i).setTotal(dataBean.getBids().get(i).getAmount());
//            } else {
//                String askBuy = ArithmeticUtils.plus(dataBean.getAsks().get(i).getAmount(),
//                        dataBean.getAsks().get(i - 1).getTotal())
//                        .setScale(0, RoundingMode.UP)
//                        .stripTrailingZeros().toPlainString();
//                dataBean.getAsks().get(i).setTotal(askBuy);
//                String bidSell = ArithmeticUtils.plus(dataBean.getBids().get(i).getAmount(),
//                        dataBean.getBids().get(i - 1).getTotal()).setScale(0, RoundingMode.UP)
//                        .stripTrailingZeros().toPlainString();
//                dataBean.getBids().get(i).setTotal(bidSell);
//            }
//        }
//        if (!ArithmeticUtils.compare(base, dataBean.getAsks().get(4).getTotal())) {
//            base = dataBean.getAsks().get(19).getTotal();
//        }
//        if (!ArithmeticUtils.compare(base, dataBean.getBids().get(4).getTotal())) {
//            base = dataBean.getBids().get(19).getTotal();
//        }
//        for (int i = 0; i < 20; i++) {
//            String askP = ArithmeticUtils.multiply(ArithmeticUtils.divide(dataBean.getAsks().get(i).getTotal(), base, 5), "100").setScale(0, RoundingMode.UP).toPlainString();
//            dataBean.getAsks().get(i).setPrecent(askP);
//            String bidP = ArithmeticUtils.multiply(ArithmeticUtils.divide(dataBean.getBids().get(i).getTotal(), base, 5), "100").setScale(0, RoundingMode.UP).toPlainString();
//            dataBean.getBids().get(i).setPrecent(bidP);
//        }
//
//        mInList.addAll(dataBean.getAsks());
//        Collections.reverse(mInList);
//        mOutList.addAll(dataBean.getBids());
//
//        mOutAdapter.notifyDataSetChanged();
//        mInAdapter.notifyDataSetChanged();
//
//
//    }


    //网络请求数据
    private void initDataDeapData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        RequestEntity request = new RequestEntity();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(request);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_DEPTH_URL + marketId)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("TAG", "onSuccess: " + response.body());
                        if (response.body() != null) {
                            Gson gson = new Gson();
                            try {
                                DepthResponse depth = gson.fromJson(response.body(), DepthResponse.class);

                                if (depth != null && depth.getData() != null) {
                                    setDepData(depth.getData());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


//                            try {
//                                Message msg = Message.obtain();
//                                msg.what = 0;
//                                msg.obj = depth;
//                                mHandler.sendMessage(msg);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                });
    }

    public void setDepData(DepthResponse.DataBean data) {

        List<DepthResponse.DataBean.AsksBean> listAsk = data.getAsks();

        List<DepthResponse.DataBean.BidsBean> listBid = data.getBids();

        if(listAsk.size() > listBid.size()){
            int countSize = listAsk.size() - listBid.size();
            for (int i=0;i<countSize;i++){
                DepthResponse.DataBean.BidsBean  bidsBean = new DepthResponse.DataBean.BidsBean();
                bidsBean.setPrice("--");
                bidsBean.setAmount("--");
                listBid.add(bidsBean);
            }
        }

        if(listBid.size() > listAsk.size()){
            int countSize = listBid.size() - listAsk.size();
            for (int i=0;i<countSize;i++){
                DepthResponse.DataBean.AsksBean  asksBean = new DepthResponse.DataBean.AsksBean();
                asksBean.setPrice("--");
                asksBean.setAmount("--");
                listAsk.add(asksBean);
            }
        }



        mInAdapter.setData(listAsk);



        mOutAdapter.setMoreData(listBid);
    }


}
