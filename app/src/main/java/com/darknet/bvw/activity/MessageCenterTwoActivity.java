//package com.darknet.bvw.activity;
//
//import android.content.Intent;
//import android.util.Log;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.darknet.bvw.R;
//import com.darknet.bvw.adapter.MessageCenterAdapter;
//import com.darknet.bvw.config.ConfigNetWork;
//import com.darknet.bvw.config.UrlPath;
//import com.darknet.bvw.db.Entity.ETHWalletModel;
//import com.darknet.bvw.db.WalletDaoUtils;
//import com.darknet.bvw.model.request.TradeListRequest;
//import com.darknet.bvw.model.response.TradeListResponse;
//import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
//import com.darknet.bvw.view.TypefaceTextView;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.handmark.pulltorefresh.library.ILoadingLayout;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.callback.StringCallback;
//import com.lzy.okgo.model.Response;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//
//import java.io.Serializable;
//import java.util.List;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//
//public class MessageCenterTwoActivity extends BaseActivity implements View.OnClickListener, MessageCenterAdapter.OnItemClick {
//
//    private RelativeLayout layBack;
//    private TypefaceTextView title;
////    private RefreshLayout mRefreshLayout;
//    private RecyclerView mRecyclerView;
//    private GridLayoutManager gridLayoutManager;
//    private MessageCenterAdapter mAdapter;
//
//    private LinearLayout noDataLayout;
//    private LinearLayout haveDataLayout;
//
//    PullToRefreshScrollView pullToRefreshScrollView;
//
//    @Override
//    public void initView() {
//        layBack = findViewById(R.id.layBack);
//        title = findViewById(R.id.title);
////        mRefreshLayout = findViewById(R.id.refreshLayout);
//        mRecyclerView = findViewById(R.id.rv);
//
//        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
//
//        noDataLayout= findViewById(R.id.layNoData);
//        haveDataLayout= findViewById(R.id.layHaveData);
//
//        layBack.setOnClickListener(this);
//        title.setText(getString(R.string.msg_center_list_title));
//
//        //开始下拉
////        mRefreshLayout.setEnableRefresh(true);//启用刷新
////        mRefreshLayout.setEnableLoadMore(true);//启用加载
//
//        gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//
//        mAdapter = new MessageCenterAdapter(this);
//        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClick(this);
//
//        //加载更多
////        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
////            @Override
////            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
////                mRefreshLayout.finishLoadMore();
////            }
////        });
////
////        //刷新
////        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
////            @Override
////            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
////                mRefreshLayout.finishRefresh();
////            }
////        });
//        intRefreshScrollView();
//        getData();
//    }
//
//
//
//    private void intRefreshScrollView() {
//        //1.设置模式
//        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(false);
//        pullToRefreshScrollView.setPullToRefreshOverScrollEnabled(false);
//
//
//        //2.通过调用getLoadingLayoutProxy方法，设置下拉刷新状况布局中显示的文字 ，第一个参数为true,代表下拉刷新
//        ILoadingLayout headLables = pullToRefreshScrollView.getLoadingLayoutProxy(true, false);
//        headLables.setPullLabel(getString(R.string.refresh_one_view));
//        headLables.setRefreshingLabel(getString(R.string.refresh_two_view));
//        headLables.setReleaseLabel(getString(R.string.refresh_three_view));
//
//
//        //3.设置监听事件
//        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
////                addToTop();//请求网络数据，并更新listview组件
//                getRefresh();
////                getLeftMoney();
////                DateEvent dateEvent = new DateEvent();
////                dateEvent.setDate(TimeUtil.currentDay());
////                EventBus.getDefault().post(dateEvent);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
////                addToBottom()//请求网络数据，并更新listview组件
////                refreshComplete();//数据加载完成后，关闭header,footer
//            }
//        });
//
//    }
//
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_message_center;
//    }
//
//    @Override
//    public void initToolBar() {
//
//    }
//
//    @Override
//    public void initDatas() {
//
//    }
//
//    @Override
//    public void configViews() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.layBack:
//                MessageCenterTwoActivity.this.finish();
//                break;
//        }
//    }
//
//
//    private void getRefresh(){
//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//
//        String privateKey = walletModel.getPrivateKey();
//        String addressVals = walletModel.getAddress();
//
//        String msg = "" + System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
//
//        TradeListRequest tradeRequest = new TradeListRequest();
//        tradeRequest.setType(0);
//        tradeRequest.setPage(1);
//        tradeRequest.setLimit(120);
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String jsonVal = gson.toJson(tradeRequest);
//
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        RequestBody requestBody = RequestBody.create(JSON, jsonVal);
//
//
//
//        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
//                .tag(MessageCenterTwoActivity.this)
//                .upRequestBody(requestBody)
//                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
////                            Log.e("backVal", "backVal=" + backVal);
//                            if (backVal != null) {
//
//                                try {
//                                    Gson gson = new Gson();
//                                    TradeListResponse response = gson.fromJson(backVal, TradeListResponse.class);
////                                Log.e("backVal", "backVal=" + response.toString());
//                                    if(response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null && response.getData().getItems().size() > 0){
//                                        setVal(response.getData().getItems());
//                                    }else {
//                                        setNoData();
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                    setNoData();
//                                }
//                            }
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        setNoData();
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        pullToRefreshScrollView.onRefreshComplete();//数据加载完成后，关闭header,footer
//
//                    }
//                });
//    }
//
//    private void getData() {
//
//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//
//        String privateKey = walletModel.getPrivateKey();
//        String addressVals = walletModel.getAddress();
//
//        String msg = "" + System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
//
//        TradeListRequest tradeRequest = new TradeListRequest();
//        tradeRequest.setType(0);
//        tradeRequest.setPage(1);
//        tradeRequest.setLimit(120);
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String jsonVal = gson.toJson(tradeRequest);
//
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        RequestBody requestBody = RequestBody.create(JSON, jsonVal);
//
//
//        showDialog(getString(R.string.load_data));
//
//        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
//                .tag(MessageCenterTwoActivity.this)
//                .upRequestBody(requestBody)
//                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
////                            Log.e("backVal", "backVal=" + backVal);
//                            if (backVal != null) {
//                                try {
//                                    Gson gson = new Gson();
//                                    TradeListResponse response = gson.fromJson(backVal, TradeListResponse.class);
////                                Log.e("backVal", "backVal=" + response.toString());
//                                    if(response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null && response.getData().getItems().size() > 0){
//                                        setVal(response.getData().getItems());
//                                    }else {
//                                        setNoData();
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                    setNoData();
//                                }
//                            }
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        setNoData();
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        dismissDialog();
//
//                    }
//                });
//    }
//
//
//    private void setNoData(){
//        noDataLayout.setVisibility(View.VISIBLE);
//        haveDataLayout.setVisibility(View.GONE);
//    }
//
//    private void setVal(List<TradeListResponse.TradeListModel> listVal) {
//
//        noDataLayout.setVisibility(View.GONE);
//        haveDataLayout.setVisibility(View.VISIBLE);
//
//        mAdapter.addData(listVal);
//        mAdapter.notifyDataSetChanged();
//    }
//
//
//    @Override
//    public void itemClick(TradeListResponse.TradeListModel tradeListModel) {
//        Intent detailIntent = new Intent(MessageCenterTwoActivity.this, TradeDetailActivity.class);
//        detailIntent.putExtra("tradeModel",(Serializable) tradeListModel);
//        startActivity(detailIntent);
//    }
//}
