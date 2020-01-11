package com.darknet.bvw.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.HuaZhangActivity;
import com.darknet.bvw.adapter.AmountTwoAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.ExchangeZcResponse;
import com.darknet.bvw.model.event.RefreshEvent;
import com.darknet.bvw.model.event.RefreshThreeEvent;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

public class AmountFragment extends Fragment implements AmountTwoAdapter.OnItemClick {

    private LinearLayout noDataLayout;
    private LRecyclerView recyclerView;
    private static final int REQUEST_COUNT = 10;
    private int pageNumber = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean hasMore = true;
    AmountTwoAdapter messageCenterThreeAdapter;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amount, container, false);
//        initView(view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        hideSoftKeyBoard();
    }

    private void hideSoftKeyBoard() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
//        lazyLoad();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        lazyLoad();
//    }
//
//    //懒加载
//    private void lazyLoad() {
//        if (getUserVisibleHint() && isPrepared) {
//
//            Log.e("xxxxxxxx", ".....lazyLoad...do...msgFragment....");
//            recyclerView.refresh();
//
//        }
//    }


    private void initView(View view) {

        recyclerView = view.findViewById(R.id.amount_listview);
        messageCenterThreeAdapter = new AmountTwoAdapter(activity);
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
        recyclerView.setLoadMoreEnabled(false);

//        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//
//                if (hasMore) {
//                    pageNumber++;
////                    loadMoreData();
//                } else {
//                    recyclerView.setNoMore(true);
//                }
//            }
//        });


        noDataLayout = (LinearLayout) view.findViewById(R.id.layNoData);
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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.EXCHANGE_ACCOUNT_URL)
                .tag(activity)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    ExchangeZcResponse response = gson.fromJson(backVal, ExchangeZcResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setVal(response.getData());
                                        } else {
//                                            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_SHORT).show();
                                            setNoData();
                                        }
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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.EXCHANGE_ACCOUNT_URL)
                .tag(activity)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    ExchangeZcResponse response = gson.fromJson(backVal, ExchangeZcResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setMoreVal(response.getData());
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


    private void setMoreVal(List<ExchangeZcResponse.EZcModel> listVal) {
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


    private void setNoData() {

        try {
            noDataLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void itemClick(ExchangeZcResponse.EZcModel tradeListModel) {
        Intent detailIntent = new Intent(activity, HuaZhangActivity.class);
        detailIntent.putExtra("zcModel", (Serializable) tradeListModel);
        startActivity(detailIntent);
    }


    private void getZcData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.EXCHANGE_ACCOUNT_URL)
                .tag(activity)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    ExchangeZcResponse response = gson.fromJson(backVal, ExchangeZcResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setVal(response.getData());
                                        } else {
                                            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });
    }

    private void setVal(List<ExchangeZcResponse.EZcModel> listVal) {

        try {
            noDataLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            messageCenterThreeAdapter.addData(listVal);
            messageCenterThreeAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(RefreshThreeEvent refreshEvent) {
        getRefresh();
    }


}
