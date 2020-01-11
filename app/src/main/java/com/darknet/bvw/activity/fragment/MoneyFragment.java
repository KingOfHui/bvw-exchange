package com.darknet.bvw.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BidJiangLiActivity;
import com.darknet.bvw.activity.MyQrActivity;
import com.darknet.bvw.activity.SettingActivity;
import com.darknet.bvw.activity.SuanLiWaKuangActivity;
import com.darknet.bvw.activity.TradeListActivity;
import com.darknet.bvw.adapter.ZCAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.Entity.ZcMoneyModel;
import com.darknet.bvw.db.Entity.ZcTotalMoneyModel;
import com.darknet.bvw.db.TotalMoneyUtils;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.db.ZcDaoUtils;
import com.darknet.bvw.model.MoneyModel;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.darknet.bvw.util.language.LocalManageUtil.getSystemLocale;

/**
 * 资产fragment
 */
public class MoneyFragment extends Fragment implements ZCAdapter.OnItemClick {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    //    @BindView(R.id.refreshLayout)
//    RefreshLayout mRefreshLayout;
    private ZCAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;

//    private List<String> list = new ArrayList<>();

    private Unbinder unbinder;

//    private BaseActivity baseActivity;

    private TextView addressView;
    private LinearLayout jumpLayout;
    private TextView totalMoneyView;
    private TextView totalTypeView;
    private TextView totalTypeZnView;
    private ImageView setView;
    PullToRefreshScrollView pullToRefreshScrollView;

    private TextView suanliView;

    private List<MoneyModel> moneyModels = new CopyOnWriteArrayList<>();


    //该页面是否准备完毕
    private boolean isPrepared;
    //是否已加载过懒加载
    private boolean isLayLoad;



    private String addRessValss;


    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public MoneyFragment() {
    }

//    public MoneyFragment(BaseActivity bactivity) {
////        super(bactivity);
//        baseActivity = bactivity;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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

            Log.e("xxxxxxxx", ".....lazyLoad...do...moneyFragment....");

            initData();
            setAddressVal();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        intRefreshScrollView();

    }

    @Override
    public void onResume() {
        super.onResume();


//        initData();
    }

    private void intRefreshScrollView() {
        //1.设置模式
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(false);
        pullToRefreshScrollView.setPullToRefreshOverScrollEnabled(false);


        //2.通过调用getLoadingLayoutProxy方法，设置下拉刷新状况布局中显示的文字 ，第一个参数为true,代表下拉刷新
        ILoadingLayout headLables = pullToRefreshScrollView.getLoadingLayoutProxy(true, false);
        headLables.setPullLabel(getString(R.string.refresh_one_view));
        headLables.setRefreshingLabel(getString(R.string.refresh_two_view));
        headLables.setReleaseLabel(getString(R.string.refresh_three_view));


        //3.设置监听事件
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                addToTop();//请求网络数据，并更新listview组件

                getLeftMoney();
//                DateEvent dateEvent = new DateEvent();
//                dateEvent.setDate(TimeUtil.currentDay());
//                EventBus.getDefault().post(dateEvent);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                addToBottom()//请求网络数据，并更新listview组件
//                refreshComplete();//数据加载完成后，关闭header,footer
            }
        });

    }


    private void initView(View view) {

//        for (int i = 0; i < 10; i++) {
//            list.add(i + "item");
//        }

        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);


        addressView = (TextView) view.findViewById(R.id.zc_address_view);
        jumpLayout = (LinearLayout) view.findViewById(R.id.zc_jump_layout);
        totalMoneyView = (TextView) view.findViewById(R.id.total_money_view);
        setView = (ImageView) view.findViewById(R.id.zc_set_view);
        totalTypeView = (TextView) view.findViewById(R.id.total_money_type_view);
        totalTypeZnView = (TextView) view.findViewById(R.id.total_money_type_view_zn);
        suanliView = (TextView) view.findViewById(R.id.zc_suanli_view);






        //开始下拉
//        mRefreshLayout.setEnableRefresh(true);//启用刷新
//        mRefreshLayout.setEnableLoadMore(true);//启用加载

        gridLayoutManager = new GridLayoutManager(activity, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mAdapter = new ZCAdapter(getActivity(), moneyModels);
        mAdapter = new ZCAdapter(activity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClick(this);


        //加载更多
//        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mRefreshLayout.finishLoadMore();
//            }
//        });
//
//        //刷新
//        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mRefreshLayout.finishRefresh();
//                initData();
//            }
//        });

        suanliView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent suanLiIntent = new Intent(activity, SuanLiWaKuangActivity.class);
                startActivity(suanLiIntent);
            }
        });


        addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent suanLiIntent = new Intent(activity, BidJiangLiActivity.class);
                startActivity(suanLiIntent);
            }
        });

        jumpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shouIntent = new Intent(activity, MyQrActivity.class);
                shouIntent.putExtra("brcAddress",addRessValss);
                startActivity(shouIntent);
            }
        });


        setView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(activity, SettingActivity.class);
                startActivity(setIntent);
            }
        });

    }


    private void setAddressVal() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String addressVals = walletModel.getAddress();

        addRessValss = addressVals;

        StringBuilder sb = new StringBuilder();
        if (addressVals != null) {
            sb.append(addressVals.substring(0, 5));
            sb.append("...");
            sb.append(addressVals.substring((addressVals.length() - 5), addressVals.length()));
        }

        String tempAddrss = sb.toString();

//        addressView.setText(tempAddrss);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }


    //加载数据
    private void initData() {
        //获取币种
        moneyModels.clear();
        List<ZcMoneyModel> allZcMoneys = ZcDaoUtils.getAllZcData();

        if (allZcMoneys != null && allZcMoneys.size() > 0) {
            loadFromLocal();
            getLeftMoney();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getLeftMoney();
//                }
//            }, 1000);

        } else {
            getLeftMoney();
        }


//        if (allZcMoneys == null || allZcMoneys.size() == 0) {
//            getType();
//        } else {
//            //走本地缓存
//            loadFromLocal();
//        }
    }


//    private void getType() {
//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//
//        String privateKey = walletModel.getPrivateKey();
//        String addressVals = walletModel.getAddress();
//
//        String msg = "" + System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
//
//        showDialog(getString(R.string.load_data));
//
//        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.MONEY_TYPE_URL)
//                .tag(baseActivity)
//                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
//                            Log.e("backVal", "backVal=" + backVal);
//                            if (backVal != null) {
//                                Gson gson = new Gson();
//                                MoneyTypeResponse response = gson.fromJson(backVal, MoneyTypeResponse.class);
//                                if (response != null && response.getCode() == 0 && response.getData() != null) {
//
//                                    List<MoneyTypeResponse.MoneyTypeModel> data = response.getData();
//
//                                    moneyModels.clear();
//                                    for (int i = 0; i < data.size(); i++) {
//                                        MoneyTypeResponse.MoneyTypeModel tempModel = data.get(i);
//                                        MoneyModel moneyModel = new MoneyModel();
//                                        moneyModel.setId(tempModel.getId());
//                                        moneyModel.setIcon(tempModel.getIcon());
//                                        moneyModel.setSymbol(tempModel.getSymbol());
//                                        moneyModel.setPrice(tempModel.getPrice());
//                                        moneyModels.add(moneyModel);
//                                    }
//
//                                    Log.e("money", "moneyModels.size 1...=" + moneyModels.size());
//
//                                    getLeftMoney();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//
//                    }
//                });
//    }


    private void getLeftMoney() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(activity)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal", "backVal=" + backVal);
                            if (backVal != null) {
                                Gson gson = new Gson();
                                LeftMoneyResponse response = gson.fromJson(backVal, LeftMoneyResponse.class);
//                                Log.e("backVal", "backVal=" + response.toString());

                                if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().size() > 0) {
                                    setListVal(response.getData());

//                                    for (int i = 0; i < response.getData().size(); i++) {
//                                        LeftMoneyResponse.LeftMoneyModel leftMoneyModel = response.getData().get(i);
//                                        if(moneyTypeVal.equals(leftMoneyModel.getName())){
//                                            if (leftMoneyModel.getQty().equals("0")) {
//                                                Toast.makeText(TransferAccountsActivity.this, "当前账号暂无余额，无法进行转账", Toast.LENGTH_SHORT).show();
//                                                return;
//                                            }else {
//                                                //创建交易
//                                                createTrade(num,address,moneyTypeVal);
//                                            }
//                                        }
//                                    }


                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("moneyFragemt","...net...connect..out...");
                        pullToRefreshScrollView.onRefreshComplete();//数据加载完成后，关闭header,footer

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        dismissDialog();
                        pullToRefreshScrollView.onRefreshComplete();//数据加载完成后，关闭header,footer
                    }
                });
    }


    private void setListVal(List<LeftMoneyResponse.LeftMoneyModel> data) {


//        Log.e("money", "moneyModels.size 2...=" + moneyModels.size());

        moneyModels.clear();

        for (int i = 0; i < data.size(); i++) {
            LeftMoneyResponse.LeftMoneyModel leftMoneyModel = data.get(i);
            MoneyModel moneyModel = new MoneyModel();
            moneyModel.setAddress(leftMoneyModel.getAddress());
            moneyModel.setAssetref(leftMoneyModel.getAssetref());
            moneyModel.setValue_cny(leftMoneyModel.getValue_cny());
            moneyModel.setValue_usd(leftMoneyModel.getValue_usd());
            moneyModel.setValue_qty(leftMoneyModel.getValue_qty());
            moneyModel.setIcon(leftMoneyModel.getIcon());
            moneyModel.setSymbol(leftMoneyModel.getName());
            moneyModel.setChain_deposit_address(leftMoneyModel.getChain_deposit_address());
            moneyModels.add(moneyModel);

        }

//        Log.e("money", "moneyModels.size 3...=" + moneyModels.size());

        //移除本地数据，重新存储
        List<ZcMoneyModel> allZcMoneys = ZcDaoUtils.getAllZcData();
        if (allZcMoneys != null && allZcMoneys.size() > 0) {
            for (int d = 0; d < allZcMoneys.size(); d++) {
                ZcMoneyModel zModel = allZcMoneys.get(d);
                ZcDaoUtils.delZcModel(zModel);
            }
        }

        for (int m = 0; m < moneyModels.size(); m++) {
            MoneyModel tempMoneMode = moneyModels.get(m);
            ZcMoneyModel zcMoneyModel = new ZcMoneyModel();

            zcMoneyModel.setIcon(tempMoneMode.getIcon());
            zcMoneyModel.setPrice(tempMoneMode.getPrice());
            zcMoneyModel.setSymbol(tempMoneMode.getSymbol());

            if (!TextUtils.isEmpty(tempMoneMode.getValue_qty())) {
                zcMoneyModel.setValue_qty(tempMoneMode.getValue_qty());
            } else {
                zcMoneyModel.setValue_qty("0");
            }

            if (!TextUtils.isEmpty(tempMoneMode.getAddress())) {
                zcMoneyModel.setAddress(tempMoneMode.getAddress());
            } else {
                zcMoneyModel.setAddress("");
            }

//            if(!TextUtils.isEmpty(tempMoneMode.getChain_deposit_address())){
//                zcMoneyModel.setChain_deposit_address(tempMoneMode.getChain_deposit_address());
//            }else {
//                zcMoneyModel.setChain_deposit_address("");
//            }

            if (!TextUtils.isEmpty(tempMoneMode.getAssetref())) {
                zcMoneyModel.setAssetref(tempMoneMode.getAssetref());
            } else {
                zcMoneyModel.setAssetref("");
            }

            if (tempMoneMode.getValue_cny() != null) {
                zcMoneyModel.setValue_cny(tempMoneMode.getValue_cny().toString());
            } else {
                zcMoneyModel.setValue_cny("0.0000");
            }

            if (tempMoneMode.getValue_usd() != null) {
                zcMoneyModel.setValue_usd(tempMoneMode.getValue_usd().toString());
            } else {
                zcMoneyModel.setValue_usd("0.0000");
            }



            ZcDaoUtils.insertZcModel(zcMoneyModel);
        }


        List<ZcTotalMoneyModel> listTotalModel = TotalMoneyUtils.getAllTotalData();

        if (listTotalModel != null && listTotalModel.size() > 0) {
            for (int a = 0; a < listTotalModel.size(); a++) {
                TotalMoneyUtils.delTotalModel(listTotalModel.get(a));
            }
        }

        double num = 0.00;
        BigDecimal totalMoneyCny = new BigDecimal(num);
        BigDecimal totalMoneyUsd = new BigDecimal(num);

        //中文
        for (int k = 0; k < data.size(); k++) {
            LeftMoneyResponse.LeftMoneyModel leftMoneyModel = data.get(k);
            totalMoneyCny = totalMoneyCny.add(leftMoneyModel.getValue_cny());
            totalMoneyUsd = totalMoneyUsd.add(leftMoneyModel.getValue_usd());
        }

        ZcTotalMoneyModel zcTotalMoneyModel = new ZcTotalMoneyModel();
        zcTotalMoneyModel.setTotalCny(totalMoneyCny.toString());
        zcTotalMoneyModel.setTotalUsd(totalMoneyUsd.toString());
        TotalMoneyUtils.insertZcModel(zcTotalMoneyModel);

        //加载本地缓存
        loadFromLocal();

        //下面是无缓存情况下，正确数据
//        double num = 0.00;
//        BigDecimal totalMoney = new BigDecimal(num);
//
//        int lanType = SPUtil.getInstance(getActivity()).getSelectLanguage();
//
//        if (lanType == 0) {
//            //中文
//            for (int k = 0; k < data.size(); k++) {
//                LeftMoneyResponse.LeftMoneyModel leftMoneyModel = data.get(k);
//                totalMoney = totalMoney.add(leftMoneyModel.getValue_cny());
//
//            }
//            totalTypeView.setText("￥");
//        } else {
//            //英文
//            for (int i = 0; i < data.size(); i++) {
//                LeftMoneyResponse.LeftMoneyModel leftMoneyModel = data.get(i);
//                totalMoney = totalMoney.add(leftMoneyModel.getValue_usd());
//
//            }
//            totalTypeView.setText("$");
//        }
//
//        if (totalMoney == null) {
//            totalMoneyView.setText("~");
//        } else {
//            totalMoneyView.setText(totalMoney + "");
//        }
    }


    //加载本地缓存数据
    private void loadFromLocal() {
        List<ZcMoneyModel> allZcMoneys = ZcDaoUtils.getAllZcData();

//        Log.e("money","localdatas.size()="+allZcMoneys.size());
//        Log.e("money","localdatas.size()="+allZcMoneys.toString());

        moneyModels.clear();
        for (int k = 0; k < allZcMoneys.size(); k++) {
            MoneyModel moneyModel = new MoneyModel();
            ZcMoneyModel tempZcDel = allZcMoneys.get(k);
            moneyModel.setAddress(tempZcDel.getAddress());
            moneyModel.setAssetref(tempZcDel.getAssetref());
            moneyModel.setIcon(tempZcDel.getIcon());
            moneyModel.setPrice(tempZcDel.getPrice());
            moneyModel.setValue_qty(tempZcDel.getValue_qty());
            moneyModel.setSymbol(tempZcDel.getSymbol());
            moneyModel.setValue_cny(new BigDecimal(tempZcDel.getValue_cny()));
            moneyModel.setValue_usd(new BigDecimal(tempZcDel.getValue_usd()));
//            moneyModel.setChain_deposit_address(tempZcDel.getChain_deposit_address());
            moneyModels.add(moneyModel);
        }


//        Log.e("money","moneyModels.size()="+moneyModels.toString());

        //设置数据给列表
        mAdapter.setData(moneyModels);
        mAdapter.notifyDataSetChanged();

        List<ZcTotalMoneyModel> listTotalModel = TotalMoneyUtils.getAllTotalData();

        if (listTotalModel != null && listTotalModel.size() > 0) {
            ZcTotalMoneyModel zcTotalMoneyModel = listTotalModel.get(0);

            //英文
            totalTypeZnView.setVisibility(View.VISIBLE);
            totalTypeView.setVisibility(View.GONE);

            if (zcTotalMoneyModel.getTotalUsd() == null) {
                totalTypeZnView.setText("$");
                totalMoneyView.setText("0.0000");
            } else {
                totalTypeZnView.setText("$");
                if (new BigDecimal(zcTotalMoneyModel.getTotalUsd().toString()).compareTo(BigDecimal.ZERO) == 0) {
                    totalMoneyView.setText("0.0000");
                } else {
                    totalMoneyView.setText(zcTotalMoneyModel.getTotalUsd());
                }
            }

            //下面是语言判断逻辑
//            int lanType = SPUtil.getInstance(getActivity()).getSelectLanguage();
//            if (lanType == 0) {
//                totalTypeZnView.setVisibility(View.VISIBLE);
//                totalTypeView.setVisibility(View.GONE);
//                //中文
//                if (zcTotalMoneyModel.getTotalCny() == null) {
//                    totalMoneyView.setText("0.0000");
//                    totalTypeZnView.setText("￥");
//                } else {
//                    totalTypeZnView.setText("￥");
//                    if (new BigDecimal(zcTotalMoneyModel.getTotalCny().toString()).compareTo(BigDecimal.ZERO) == 0) {
//                        totalMoneyView.setText("0.0000");
//                    } else {
//                        totalMoneyView.setText(zcTotalMoneyModel.getTotalCny());
//                    }
//                }
//            } else if (lanType == 1) {
//                //英文
//                totalTypeZnView.setVisibility(View.VISIBLE);
//                totalTypeView.setVisibility(View.GONE);
//
//                if (zcTotalMoneyModel.getTotalUsd() == null) {
//                    totalTypeZnView.setText("$");
//                    totalMoneyView.setText("0.0000");
//                } else {
//                    totalTypeZnView.setText("$");
//                    if (new BigDecimal(zcTotalMoneyModel.getTotalUsd().toString()).compareTo(BigDecimal.ZERO) == 0) {
//                        totalMoneyView.setText("0.0000");
//                    } else {
//                        totalMoneyView.setText(zcTotalMoneyModel.getTotalUsd());
//                    }
//                }
//            } else if (lanType == 2) {
//                try {
//
//                    String language = getStystemLanguage();
//
//                    if ("zh".equals(language)) {
//                        totalTypeZnView.setVisibility(View.VISIBLE);
//                        totalTypeView.setVisibility(View.GONE);
//                        //中文
//                        if (zcTotalMoneyModel.getTotalCny() == null) {
//                            totalMoneyView.setText("0.0000");
//                            totalTypeZnView.setText("￥");
//                        } else {
//                            totalTypeZnView.setText("￥");
//                            if (new BigDecimal(zcTotalMoneyModel.getTotalCny().toString()).compareTo(BigDecimal.ZERO) == 0) {
//                                totalMoneyView.setText("0.0000");
//                            } else {
//                                totalMoneyView.setText(zcTotalMoneyModel.getTotalCny());
//                            }
//                        }
//                    } else if ("en".equals(language)) {
//                        //英文
//                        totalTypeZnView.setVisibility(View.VISIBLE);
//                        totalTypeView.setVisibility(View.GONE);
//
//                        if (zcTotalMoneyModel.getTotalUsd() == null) {
//                            totalTypeZnView.setText("$");
//                            totalMoneyView.setText("0.0000");
//                        } else {
//                            totalTypeZnView.setText("$");
//                            if (new BigDecimal(zcTotalMoneyModel.getTotalUsd().toString()).compareTo(BigDecimal.ZERO) == 0) {
//                                totalMoneyView.setText("0.0000");
//                            } else {
//                                totalMoneyView.setText(zcTotalMoneyModel.getTotalUsd());
//                            }
//                        }
//                    } else {
//                        //英文
//                        totalTypeZnView.setVisibility(View.VISIBLE);
//                        totalTypeView.setVisibility(View.GONE);
//
//                        if (zcTotalMoneyModel.getTotalUsd() == null) {
//                            totalTypeZnView.setText("$");
//                            totalMoneyView.setText("0.0000");
//                        } else {
//                            totalTypeZnView.setText("$");
//                            if (new BigDecimal(zcTotalMoneyModel.getTotalUsd().toString()).compareTo(BigDecimal.ZERO) == 0) {
//                                totalMoneyView.setText("0.0000");
//                            } else {
//                                totalMoneyView.setText(zcTotalMoneyModel.getTotalUsd());
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    //英文
//                    totalTypeZnView.setVisibility(View.VISIBLE);
//                    totalTypeView.setVisibility(View.GONE);
//
//                    if (zcTotalMoneyModel.getTotalUsd() == null) {
//                        totalTypeZnView.setText("$");
//                        totalMoneyView.setText("0.0000");
//                    } else {
//                        totalTypeZnView.setText("$");
//                        if (new BigDecimal(zcTotalMoneyModel.getTotalUsd().toString()).compareTo(BigDecimal.ZERO) == 0) {
//                            totalMoneyView.setText("0.0000");
//                        } else {
//                            totalMoneyView.setText(zcTotalMoneyModel.getTotalUsd());
//                        }
//                    }
//                }
//            }


        }
    }


    private String getStystemLanguage() {

        try {

            String language = getSystemLocale(activity).getLanguage();
            return language;
        } catch (Exception e) {
            e.printStackTrace();
            return "en";
        }

    }


//    private void getTotalMoney() {
//
//        double totalMoney = 0;
//
//        for (int i = 0; i < moneyModels.size(); i++) {
//            MoneyModel moneyModel = moneyModels.get(i);
//            if (!TextUtils.isEmpty(moneyModel.getQty()) && !TextUtils.isEmpty(moneyModel.getPrice())) {
//                totalMoney = totalMoney + Double.parseDouble(moneyModel.getQty()) * Double.parseDouble(moneyModel.getPrice());
//            }
//        }
//
//        if (totalMoney == 0) {
//            totalMoneyView.setText("~");
//        } else {
//            totalMoneyView.setText(totalMoney + "");
//        }
//    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//
//
//
//    }

    @Override
    public void itemClick(MoneyModel moneyModel) {

        Intent goIntent = new Intent(activity, TradeListActivity.class);
        goIntent.putExtra("type", moneyModel.getSymbol());
        goIntent.putExtra("leftval", moneyModel.getValue_qty());
        goIntent.putExtra("toZnMoney", moneyModel.getValue_cny() + "");
        goIntent.putExtra("toEnMoney", moneyModel.getValue_usd() + "");
        goIntent.putExtra("mainAddress", moneyModel.getChain_deposit_address());
        goIntent.putExtra("address", moneyModel.getAddress());

        startActivity(goIntent);
    }
}
