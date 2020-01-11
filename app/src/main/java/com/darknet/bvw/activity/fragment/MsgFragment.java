package com.darknet.bvw.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.TradeDetailActivity;
import com.darknet.bvw.adapter.MessageCenterThreeAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 资产fragment
 */
public class MsgFragment extends Fragment implements MessageCenterThreeAdapter.OnItemClick {


    private TextView titleView;
    private LinearLayout noDataLayout;
    private LRecyclerView recyclerView;
    private static final int REQUEST_COUNT = 10;
    private int pageNumber = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean hasMore = true;
    MessageCenterThreeAdapter messageCenterThreeAdapter;

    //该页面是否准备完毕
    private boolean isPrepared;
    //是否已加载过懒加载
    private boolean isLayLoad;
    private Context activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = context;
    }


    public MsgFragment() {
    }

//    public MsgFragment(BaseActivity bactivity) {
////        super(bactivity);
//        baseActivity = bactivity;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fund_two, container, false);
//        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    //懒加载
    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared) {

            Log.e("xxxxxxxx", ".....lazyLoad...do...msgFragment....");
            recyclerView.refresh();
            getRefresh();
        }
    }


    private void initView(View view) {

        recyclerView = view.findViewById(R.id.msg_listview);
        messageCenterThreeAdapter = new MessageCenterThreeAdapter(activity);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(messageCenterThreeAdapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        messageCenterThreeAdapter.setOnItemClick(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);


        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLRecyclerViewAdapter.notifyDataSetChanged();
//                mCurrentCounter = 0;
//                isRefresh = true;
                hasMore = true;
                pageNumber = 1;
                recyclerView.setNoMore(false);
                getRefresh();
            }
        });

        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        recyclerView.setLoadMoreEnabled(true);

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (hasMore) {
                    pageNumber++;
                    loadMoreData();
                } else {
                    recyclerView.setNoMore(true);
                }
            }
        });


        noDataLayout = (LinearLayout) view.findViewById(R.id.layNoData);
        titleView = (TextView) view.findViewById(R.id.msg_title_view);
        titleView.setText(getString(R.string.mine_msg_center));
    }


    @Override
    public void onDestroyView() {
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
        super.onDestroyView();
    }


    private void getRefresh() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);


        TradeListRequest tradeRequest = new TradeListRequest();
        tradeRequest.setType(0);
        tradeRequest.setPage(1);
        tradeRequest.setLimit(10);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

//        showDialog("加载中");

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
                .tag(activity)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal","backVal="+backVal);
                            if (backVal != null) {

                                try {
                                    Gson gson = new Gson();
                                    TradeListResponse response = gson.fromJson(backVal, TradeListResponse.class);
//                                    Log.e("backVal","backVal="+response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getCode() == 0 && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                                        setVal(response.getData().getItems());
                                    } else {
                                        setNoData();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    setNoData();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        setNoData();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        try {
                            recyclerView.refreshComplete(REQUEST_COUNT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    private void loadMoreData() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        TradeListRequest tradeRequest = new TradeListRequest();
        tradeRequest.setType(0);
        tradeRequest.setPage(pageNumber);
        tradeRequest.setLimit(10);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
                .tag(activity)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal","backVal="+backVal);
                            if (backVal != null) {

                                try {
                                    Gson gson = new Gson();
                                    TradeListResponse response = gson.fromJson(backVal, TradeListResponse.class);
//                                    Log.e("backVal","backVal="+response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getCode() == 0 && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                                        setMoreVal(response.getData().getItems());
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
                        try {
//                            pullToRefreshScrollView.onRefreshComplete();//数据加载完成后，关闭header,footer
                            recyclerView.refreshComplete(REQUEST_COUNT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        dismissDialog();
                    }

                });
    }


    private void setMoreVal(List<TradeListResponse.TradeListModel> listVal) {
        try {

            if (listVal.size() < 10) {
                hasMore = false;
                recyclerView.setNoMore(true);
            }
            messageCenterThreeAdapter.addMoreData(listVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setVal(List<TradeListResponse.TradeListModel> listVal) {

        try {
            noDataLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            messageCenterThreeAdapter.addData(listVal);
            messageCenterThreeAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setNoData() {

        try {
            noDataLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemClick(TradeListResponse.TradeListModel tradeListModel) {
        Intent detailIntent = new Intent(activity, TradeDetailActivity.class);
        detailIntent.putExtra("tradeModel", (Serializable) tradeListModel);
        startActivity(detailIntent);
    }


}
