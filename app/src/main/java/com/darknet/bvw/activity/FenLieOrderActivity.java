package com.darknet.bvw.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.FenLieOrderAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.FenLieOrderResponse;
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

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FenLieOrderActivity extends BaseActivity {

    private ImageView backView;
    private TextView titleContent;
    private LinearLayout noDataLayout;

    LRecyclerView recyclerView;
    private static final int REQUEST_COUNT = 10;
    private int pageNumber = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean hasMore = true;
    //该页面是否准备完毕
    private boolean isPrepared;
    private FenLieOrderAdapter fenLieOrderAdapter;


    @Override
    public void initView() {

        titleContent = findViewById(R.id.msg_title_view);
        backView = findViewById(R.id.back_sign_view);
        titleContent.setText(getString(R.string.fenlie_order_title));


        recyclerView = findViewById(R.id.order_listview);
        noDataLayout = findViewById(R.id.layNoData);

        fenLieOrderAdapter = new FenLieOrderAdapter(FenLieOrderActivity.this);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mLRecyclerViewAdapter = new LRecyclerViewAdapter(fenLieOrderAdapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(FenLieOrderActivity.this));

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


    }


    private void getRefresh() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);


        TradeListRequest tradeRequest = new TradeListRequest();
//        tradeRequest.setType(0);
        tradeRequest.setPage(1);
        tradeRequest.setLimit(10);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

//        showDialog("加载中");

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_FIRST_ORDER_URL)
                .tag(FenLieOrderActivity.this)
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
                                    FenLieOrderResponse response = gson.fromJson(backVal, FenLieOrderResponse.class);
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
//        tradeRequest.setType(0);
        tradeRequest.setPage(pageNumber);
        tradeRequest.setLimit(10);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

//        showDialog("加载中");

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_FIRST_ORDER_URL)
                .tag(FenLieOrderActivity.this)
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
                                    FenLieOrderResponse response = gson.fromJson(backVal, FenLieOrderResponse.class);
//                                    Log.e("backVal","backVal="+response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getCode() == 0 && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                                        setMoreVal(response.getData().getItems());
                                    } else {
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
                            recyclerView.refreshComplete(REQUEST_COUNT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void setMoreVal(List<FenLieOrderResponse.FLOrderItemModel> listVal) {
        try {

            if (listVal.size() < 10) {
                hasMore = false;
                recyclerView.setNoMore(true);
            }
            fenLieOrderAdapter.addMoreData(listVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setVal(List<FenLieOrderResponse.FLOrderItemModel> listVal) {

        try {
            noDataLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            fenLieOrderAdapter.addData(listVal);
            fenLieOrderAdapter.notifyDataSetChanged();
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
    public int getLayoutId() {
        return R.layout.activity_ding_order;
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


}
