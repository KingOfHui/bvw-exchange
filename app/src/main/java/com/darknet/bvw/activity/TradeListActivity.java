package com.darknet.bvw.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.TradeListTwoAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.TradeSuccessEvent;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BottomZhuanZDialogView;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TradeListActivity extends BaseActivity implements TradeListTwoAdapter.OnItemClick {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    private LRecyclerView contentList;
    private TextView zzView;
    private TextView skView;
    private TradeListTwoAdapter tradeListAdapter;
    private String moneyType;
    private String leftVal;

    private TextView numView;
    private TypefaceTextView valView;
    private String znMoney;
    private String enMoney;

    private String main_address;
    private String brcAddress;

    //转账
    private LinearLayout zzLayout;
    //充值
    private LinearLayout czLayout;
    //划账
    private LinearLayout hzLayout;


    private static final int REQUEST_COUNT = 10;
    private int pageNumber = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean hasMore = true;


//    PullToRefreshScrollView pullToRefreshScrollView;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        moneyType = getIntent().getStringExtra("type");
        leftVal = getIntent().getStringExtra("leftval");
        znMoney = getIntent().getStringExtra("toZnMoney");
        enMoney = getIntent().getStringExtra("toEnMoney");
        main_address= getIntent().getStringExtra("mainAddress");
        brcAddress = getIntent().getStringExtra("address");

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        contentList = findViewById(R.id.trade_list_view);
        zzView = findViewById(R.id.trade_zz_view);
        skView = findViewById(R.id.trade_sk_view);

        numView = findViewById(R.id.trade_num_view);
        valView = findViewById(R.id.trade_val_view);

        zzLayout = findViewById(R.id.trade_zz_layout);

        czLayout = findViewById(R.id.trade_cz_layout);
        hzLayout = findViewById(R.id.trade_hz_layout);

//        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);

        numView.setText(leftVal);
        valView.setText("≈$" + enMoney);

        //注销，中英状态下的判断
//        int lanType = SPUtil.getInstance(TradeListActivity.this).getSelectLanguage();
//        if (lanType == 0) {
//            valView.setText("≈￥" + znMoney);
//        } else if (lanType == 1) {
//            valView.setText("≈$" + enMoney);
//        } else if (lanType == 2) {
//            try {
//
//                String language = getStystemLanguage();
//
//                if ("zh".equals(language)) {
//                    valView.setText("≈￥" + znMoney);
//                } else if ("en".equals(language)) {
//                    valView.setText("≈$" + enMoney);
//                } else {
//                    valView.setText("≈$" + enMoney);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//                valView.setText("≈$" + enMoney);
//            }
//        }


        tradeListAdapter = new TradeListTwoAdapter(TradeListActivity.this);

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(tradeListAdapter);
        contentList.setAdapter(mLRecyclerViewAdapter);
        tradeListAdapter.setOnItemClick(this);

        contentList.setLayoutManager(new LinearLayoutManager(TradeListActivity.this));

        contentList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        contentList.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        contentList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        contentList.setPullRefreshEnabled(false);

        contentList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLRecyclerViewAdapter.notifyDataSetChanged();
//                mCurrentCounter = 0;
//                isRefresh = true;
                hasMore = true;
                pageNumber = 1;
                contentList.setNoMore(false);
//                getRefresh();
            }
        });

        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        contentList.setLoadMoreEnabled(true);

        contentList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (hasMore) {
                    pageNumber++;
                    loadMoreData();
                } else {
                    contentList.setNoMore(true);
                }
            }
        });







        title.setText(moneyType);
        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        zzView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(moneyType.equalsIgnoreCase("BTW")){
                    Intent zIntent = new Intent(TradeListActivity.this, TransferAccountsThreeActivity.class);
                    zIntent.putExtra("type", moneyType);
                    zIntent.putExtra("leftval", leftVal);
                    startActivity(zIntent);
                }else {
                    if(main_address != null && main_address.trim().length() != 0){
                        new BottomZhuanZDialogView(TradeListActivity.this,moneyType){

                            @Override
                            public void btnPickBySelect() {
                                //BRC 20
                                Intent zIntent = new Intent(TradeListActivity.this, TransferAccountsActivity.class);
                                zIntent.putExtra("type", moneyType);
                                zIntent.putExtra("leftval", leftVal);
                                startActivity(zIntent);
                            }

                            @Override
                            public void btnPickByTake() {
                                //主链
                                Intent zIntent = new Intent(TradeListActivity.this, TransferAccountsTwoActivity.class);
                                zIntent.putExtra("type", moneyType);
                                zIntent.putExtra("leftval", leftVal);
                                startActivity(zIntent);
                            }
                        }.show();
                    }else {
                        //BRC 20
                        Intent zIntent = new Intent(TradeListActivity.this, TransferAccountsActivity.class);
                        zIntent.putExtra("type", moneyType);
                        zIntent.putExtra("leftval", leftVal);
                        startActivity(zIntent);
                    }
                }





//                Intent zIntent = new Intent(TradeListActivity.this, TransferAccountsActivity.class);
//                zIntent.putExtra("type", moneyType);
//                zIntent.putExtra("leftval", leftVal);
//                startActivity(zIntent);
            }
        });


        skView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(moneyType.equalsIgnoreCase("BTW")){
                    Intent zIntent = new Intent(TradeListActivity.this, MyQrThreeActivity.class);
                    zIntent.putExtra("brcAddress",brcAddress);
                    startActivity(zIntent);

                }else {
                    if(main_address != null && main_address.trim().length() != 0){
                        new BottomZhuanZDialogView(TradeListActivity.this,moneyType){

                            @Override
                            public void btnPickBySelect() {
                                //BRC 20
                                Intent zIntent = new Intent(TradeListActivity.this, MyQrActivity.class);
                                zIntent.putExtra("brcAddress",brcAddress);
                                zIntent.putExtra("moneyType",moneyType);
                                startActivity(zIntent);
                            }

                            @Override
                            public void btnPickByTake() {
                                //主链
                                Intent zIntent = new Intent(TradeListActivity.this, MyQrTwoActivity.class);
                                zIntent.putExtra("mainAddress",main_address);
                                zIntent.putExtra("moneyType",moneyType);
                                startActivity(zIntent);
                            }
                        }.show();
                    }else {
                        //BRC 20
                        Intent zIntent = new Intent(TradeListActivity.this, MyQrActivity.class);
                        zIntent.putExtra("brcAddress",brcAddress);
                        startActivity(zIntent);
                    }
                }
            }
        });



        zzLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先弹框，选择

                new BottomZhuanZDialogView(TradeListActivity.this,moneyType){

                    @Override
                    public void btnPickBySelect() {
                        Intent zIntent = new Intent(TradeListActivity.this, TransferAccountsActivity.class);
                        zIntent.putExtra("type", moneyType);
                        zIntent.putExtra("leftval", leftVal);
                        startActivity(zIntent);
                    }

                    @Override
                    public void btnPickByTake() {

                    }
                }.show();



            }
        });


        czLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BottomZhuanZDialogView(TradeListActivity.this,moneyType){

                    @Override
                    public void btnPickBySelect() {
                        //BRC 20
                        Intent zIntent = new Intent(TradeListActivity.this, MyQrActivity.class);
                        startActivity(zIntent);
                    }

                    @Override
                    public void btnPickByTake() {
                        //主链
                        Intent zIntent = new Intent(TradeListActivity.this, MyQrTwoActivity.class);
                        startActivity(zIntent);
                    }
                }.show();
            }
        });

        hzLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(TradeListActivity.this,getString(R.string.find_no_open),Toast.LENGTH_SHORT).show();
                Intent zIntent = new Intent(TradeListActivity.this, HuaZhangActivity.class);
                startActivity(zIntent);
            }
        });

    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_list;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initDatas() {
        getData();
    }


    //获取剩余金额
    private void getLeftMoney() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);


        Log.e("paydialog", ".....find...left...money....");

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(TradeListActivity.this)
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
                                    LeftMoneyResponse response = gson.fromJson(backVal, LeftMoneyResponse.class);
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().size() > 0) {
//                                        int find = 0;
                                        for (int i = 0; i < response.getData().size(); i++) {
                                            LeftMoneyResponse.LeftMoneyModel leftMoneyModel = response.getData().get(i);
                                            if (moneyType.equals(leftMoneyModel.getName())) {
                                                setLefMoney(leftMoneyModel);
                                                break;
                                            }
                                        }
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
//                        try {
//                            dismissDialog();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        try {
//                            dismissDialog();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                });

    }


    private void setLefMoney(LeftMoneyResponse.LeftMoneyModel leftMoneyModel) {

//        Log.e("tradelist", ".....tradelist.....");

        leftVal = leftMoneyModel.getValue_qty();
        znMoney = leftMoneyModel.getValue_cny() + "";
        enMoney = leftMoneyModel.getValue_usd() + "";

        numView.setText(leftVal);
        valView.setText("≈$" + enMoney);

    }


    private void getData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        TradeListRequest tradeRequest = new TradeListRequest();
        tradeRequest.setType(0);
        tradeRequest.setSymbol(moneyType);
        tradeRequest.setLimit(10);
        tradeRequest.setPage(pageNumber);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
                .tag(TradeListActivity.this)
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
//                                    Log.e("backVal", "backVal=" + response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null) {
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
                        try {
                            dismissDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        getLeftMoney();
                        try {
                            dismissDialog();
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

        tradeRequest.setSymbol(moneyType);
        tradeRequest.setLimit(10);
        tradeRequest.setPage(pageNumber);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_LIST_URL)
                .tag(TradeListActivity.this)
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
//                                    Log.e("backVal", "backVal=" + response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().getItems() != null) {
                                        setMoreVal(response.getData().getItems());
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
//                        getLeftMoney();
                        try {
//                            pullToRefreshScrollView.onRefreshComplete();//数据加载完成后，关闭header,footer
                            contentList.refreshComplete(REQUEST_COUNT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    @Override
    public void configViews() {

    }

    private void setMoreVal(List<TradeListResponse.TradeListModel> listVal) {
        try {

            if (listVal.size() < 10) {
                hasMore = false;
                contentList.setNoMore(true);
            }
            tradeListAdapter.addMoreData(listVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(TradeSuccessEvent nameEvent) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setVal(List<TradeListResponse.TradeListModel> listVal) {
        if (listVal.size() == 0) {
            return;
        }
        tradeListAdapter.addData(listVal);
        tradeListAdapter.notifyDataSetChanged();
    }


    @Override
    public void itemClick(TradeListResponse.TradeListModel tradeListModel) {
        Intent detailIntent = new Intent(TradeListActivity.this, TradeDetailActivity.class);
        detailIntent.putExtra("tradeModel", (Serializable) tradeListModel);

        startActivity(detailIntent);
    }
}
