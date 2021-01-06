package com.darknet.bvw.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.DepthResponse;
import com.darknet.bvw.model.RequestEntity;
import com.darknet.bvw.model.event.TradePanKouEvent;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
//    买
    private OutDepthAdapter mOutAdapter;

    private List<DepthResponse.DataBean.AsksBean> mInList = new ArrayList<>();
    private List<DepthResponse.DataBean.BidsBean> mOutList = new ArrayList<>();
    private int first = 0;

    private static String marketId;

    private TextView biTypeView;
    private TextView biTypeTwoView;
    private TextView priceTypeView;
    private int mLimit = 5;

//    private LinearLayout leftContentView;
//    private LinearLayout rightContentView;


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
        EventBus.getDefault().register(this);
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

//        leftContentView = view.findViewById(R.id.depth_out_left_layout);
//        rightContentView = view.findViewById(R.id.depth_out_right_layout);

        mInAdapter = new InDepthAdapter(getActivity(), mInList);
        depth_in_lv.setAdapter(mInAdapter);
        mOutAdapter = new OutDepthAdapter(getActivity(), mOutList);
        depth_out_lv.setAdapter(mOutAdapter);


        try {
            biTypeView.setText("("+marketId.split("-")[0]+")");
            priceTypeView.setText("("+marketId.split("-")[1]+")");
            biTypeTwoView.setText("("+marketId.split("-")[0]+")");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_DEPTH_TWO_URL + marketId + "/50")
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
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


    private String maiRuTotalVal = "0";
    private String maiChuTotalVal = "0";

    private String bigFenMu = "0";


    public void setDepData(DepthResponse.DataBean data) {

        maiRuTotalVal = "0";
        maiChuTotalVal = "0";
        bigFenMu = "0";

        if (data.getBids().size() == 0 && data.getAsks().size() == 0) {
            mInAdapter.clear();
            mOutAdapter.clear();
            return;
        }

        List<DepthResponse.DataBean.AsksBean> listAsk = data.getAsks();

        List<DepthResponse.DataBean.BidsBean> listBid = data.getBids();



        Log.e("depthLine", "listAsk=" + listAsk.size() + ";listBid=" + listBid.size());

        String tempCountVal = "0";

        for (int i = 0; i < listBid.size(); i++) {
            DepthResponse.DataBean.BidsBean tempBid = listBid.get(i);
            maiRuTotalVal = ArithmeticUtils.plusTwo(tempBid.getAmount(), maiRuTotalVal);

            tempCountVal = ArithmeticUtils.plus(tempBid.getAmount(), tempCountVal).toPlainString();
            tempBid.setCurrentCount(tempCountVal);
//            Log.e("mairuTotal22", "tempCountVal=" + tempCountVal);
        }


//        Log.e("mairuTotal22", "tempCountVal=" + maiRuTotalVal);

        tempCountVal = "0";

        for (int i = 0; i < listAsk.size(); i++) {
            DepthResponse.DataBean.AsksBean asksBean = listAsk.get(i);
            maiChuTotalVal = ArithmeticUtils.plusTwo(asksBean.getAmount(), maiChuTotalVal);

            tempCountVal = ArithmeticUtils.plus(asksBean.getAmount(), tempCountVal).toPlainString();
            asksBean.setCurrentCount(tempCountVal);
        }

//        Log.e("mairuTotal", "maiRuTotalVal=" + maiRuTotalVal);
//        Log.e("mairuTotal", "maiChuTotalVal=" + maiChuTotalVal);

        if (ArithmeticUtils.compare(maiRuTotalVal, maiChuTotalVal)) {
            bigFenMu = maiRuTotalVal;
        } else {
            bigFenMu = maiChuTotalVal;
        }

//        Log.e("mairuTotal", "bigFenMu=" + bigFenMu);


        if (listAsk.size() > listBid.size()) {
            int countSize = listAsk.size() - listBid.size();
            for (int i = 0; i < countSize; i++) {
                DepthResponse.DataBean.BidsBean bidsBean = new DepthResponse.DataBean.BidsBean();
                bidsBean.setPrice("--");
                bidsBean.setAmount("--");
                bidsBean.setCurrentCount("0");
                listBid.add(bidsBean);
            }
        }

        if (listBid.size() > listAsk.size()) {
            int countSize = listBid.size() - listAsk.size();
            for (int i = 0; i < countSize; i++) {
                DepthResponse.DataBean.AsksBean asksBean = new DepthResponse.DataBean.AsksBean();
                asksBean.setPrice("--");
                asksBean.setAmount("--");
                asksBean.setCurrentCount("0");
                listAsk.add(asksBean);
            }
        }


//        Log.e("depthLine", "listAsk2=" + listAsk.size() + ";listBid2=" + listBid.size());
        mInAdapter.setData(listAsk, new BigDecimal(bigFenMu).stripTrailingZeros().setScale(6, BigDecimal.ROUND_DOWN));
        mOutAdapter.setMoreData(listBid, new BigDecimal(bigFenMu).stripTrailingZeros().setScale(6, BigDecimal.ROUND_DOWN));


//        setLeftContent(listBid, new BigDecimal(bigFenMu).stripTrailingZeros().setScale(6, BigDecimal.ROUND_DOWN));
//        setRightContent(listAsk, new BigDecimal(bigFenMu).stripTrailingZeros().setScale(6, BigDecimal.ROUND_DOWN));


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveWeiTuo(TradePanKouEvent tradePanKouEvent) {

        initDataDeapData();

//        initData();

//        getCurrentWeiTuo();

//        if (kLineEvent.getType() == 0) {
//            changeMaiRu();
//        } else {
//            changeMaichu();
//        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setMarketId(String markID) {
        marketId = markID;
        try {
            biTypeView.setText("("+marketId.split("-")[0]+")");
            priceTypeView.setText("("+marketId.split("-")[1]+")");
            biTypeTwoView.setText("("+marketId.split("-")[0]+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initDataDeapData();
    }

    public void setLimit(int quote_symbol_scale) {
        mLimit = quote_symbol_scale;
        if (mInAdapter != null) {
            mInAdapter.setLimit(quote_symbol_scale);
        }
        if (mOutAdapter != null) {
            mOutAdapter.setLimit(quote_symbol_scale);
        }
    }


    //    private void setLeftContent(List<DepthResponse.DataBean.BidsBean> listBid, BigDecimal bigVal) {
//        leftContentView.removeAllViews();
//
//        for (int i = 0; i < listBid.size(); i++) {
//
//            View leftView = LayoutInflater.from(getActivity()).inflate(R.layout.item_change_depth_out, null);
//            DepthResponse.DataBean.BidsBean bean = listBid.get(i);
//            TextView mPrice = leftView.findViewById(R.id.item_change_out_price);
//            TextView mAmount = leftView.findViewById(R.id.item_change_out_amount);
//            TextView mPostion = leftView.findViewById(R.id.item_change_position_price);
//
//            ProgressBar mPro = leftView.findViewById(R.id.progress02);
//
//            mPostion.setText((i + 1) + "");
////        mPrice.setText(bean.getAmount());
//
//
//            if (bean.getAmount().contains("-")) {
//                mPrice.setText(bean.getAmount());
//            } else {
//                mPrice.setText(new BigDecimal(bean.getAmount()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
//            }
//
//
//            if (bean.getPrice().contains("-")) {
//                mAmount.setText(bean.getPrice());
//            } else {
//                mAmount.setText(new BigDecimal(bean.getPrice()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
//            }
//
//            String percentVal = ArithmeticUtils.divide(bean.getCurrentCount(), bigVal.toPlainString(), 6);
//
//            String lastVal = ArithmeticUtils.multiply(percentVal, "100", 0);
//
//
//            if (Integer.valueOf(lastVal) > 100) {
//                mPro.setProgress(100);
//            } else {
//
//                if (ArithmeticUtils.compare(lastVal, "1")) {
//
//                    mPro.setProgress(Integer.valueOf(lastVal));
//                } else {
//                    mPro.setProgress(Integer.valueOf(1));
//                }
//            }
//
////            DepthResponse.DataBean.BidsBean tempBidBean = listBid.get(i);
//
//            Log.e("depthLine", "bids=" + i);
//
//            leftContentView.addView(leftView);
//        }
//
//    }
//
//    private void setRightContent(List<DepthResponse.DataBean.AsksBean> listAsk, BigDecimal bigVal) {
//        rightContentView.removeAllViews();
//
//        for (int i = 0; i < listAsk.size(); i++) {
//
//            View rightView = LayoutInflater.from(getActivity()).inflate(R.layout.item_change_depth_in, null);
//
//            DepthResponse.DataBean.AsksBean bean = listAsk.get(i);
//            ProgressBar mPro = rightView.findViewById(R.id.progress01);
//            TextView mAmount = rightView.findViewById(R.id.item_change_amount);
//            TextView mPrice = rightView.findViewById(R.id.item_change_price);
//            TextView mPosition = rightView.findViewById(R.id.item_change_position);
//            mPosition.setText((i + 1) + "");
//
//            if (bean.getAmount().contains("-")) {
//                mAmount.setText(bean.getAmount());
//            } else {
//                mAmount.setText(new BigDecimal(bean.getAmount()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
//            }
//
//
//            if (bean.getPrice().contains("-")) {
//                mPrice.setText(bean.getPrice());
//            } else {
//                mPrice.setText(new BigDecimal(bean.getPrice()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
//            }
//
//            String percentVal = ArithmeticUtils.divide(bean.getCurrentCount(), bigVal.toPlainString(), 6);
//            String lastVal = ArithmeticUtils.multiply(percentVal, "100", 0);
//            if (Integer.valueOf(lastVal) > 100) {
//                mPro.setProgress(100);
//            } else {
//
//                if (ArithmeticUtils.compare(lastVal, "1")) {
//
//                    mPro.setProgress(Integer.valueOf(lastVal));
//                } else {
//                    mPro.setProgress(Integer.valueOf(1));
//                }
//            }
//
//            Log.e("depthLine", "ask=" + i);
//            rightContentView.addView(rightView);
//        }
//
//
//    }


}
