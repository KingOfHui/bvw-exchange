package com.darknet.bvw.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.MessageCenterAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.TypefaceTextView;
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

public class MessageCenterActivity extends BaseActivity implements View.OnClickListener, MessageCenterAdapter.OnItemClick {

    private RelativeLayout layBack;
    private TypefaceTextView title;
//    private RefreshLayout mRefreshLayout;
    private MessageCenterAdapter messageCenterThreeAdapter;
    private LinearLayout noDataLayout;

    private LRecyclerView recyclerView;
    private static final int REQUEST_COUNT = 10;
    private int pageNumber = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean hasMore = true;


    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
//        mRefreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.msg_listview);
        noDataLayout = findViewById(R.id.layNoData);

        layBack.setOnClickListener(this);
        title.setText(getString(R.string.trade_list_title));

        messageCenterThreeAdapter = new MessageCenterAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(messageCenterThreeAdapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
        messageCenterThreeAdapter.setOnItemClick(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                    getData();
                } else {
                    recyclerView.setNoMore(true);
                }
            }
        });


    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        recyclerView.refresh();
        getRefresh();
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layBack:
                MessageCenterActivity.this.finish();
                break;
        }
    }


    private void getRefresh(){
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

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
                .tag(MessageCenterActivity.this)
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
                                    TradeListResponse response = gson.fromJson(backVal, TradeListResponse.class);
                                    if(response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null && response.getData().getItems().size() > 0){
                                        setVal(response.getData().getItems());
                                    }else {
                                        setNoData();
                                    }

                                }catch (Exception e){
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


    private void getData() {

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


//        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
                .tag(MessageCenterActivity.this)
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
                                    TradeListResponse response = gson.fromJson(backVal, TradeListResponse.class);
                                    if(response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null && response.getData().getItems().size() > 0){
                                        setMoreVal(response.getData().getItems());
                                    }else {
//                                        setNoData();
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
//                                    setNoData();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        setNoData();
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


    private void setNoData(){
        noDataLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
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

        noDataLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        messageCenterThreeAdapter.addData(listVal);
        messageCenterThreeAdapter.notifyDataSetChanged();
    }


    @Override
    public void itemClick(TradeListResponse.TradeListModel tradeListModel) {
        Intent detailIntent = new Intent(MessageCenterActivity.this, TradeDetailActivity.class);
        detailIntent.putExtra("tradeModel",(Serializable) tradeListModel);
        startActivity(detailIntent);
    }
}
