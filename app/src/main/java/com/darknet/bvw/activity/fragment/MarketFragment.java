package com.darknet.bvw.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.KlineActivity;
import com.darknet.bvw.adapter.MarketAdapter;
import com.darknet.bvw.adapter.MarketZfAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.MarketModel;
import com.darknet.bvw.model.event.RefreshEvent;
import com.darknet.bvw.model.event.TwoFourEvent;
import com.darknet.bvw.model.response.TradeZfResponse;
import com.darknet.bvw.model.response.TradeZxResponse;
import com.darknet.bvw.service.WorkManagerService;
import com.darknet.bvw.util.AppUtil;
import com.darknet.bvw.util.GlideImageLoader;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MarketFragment extends Fragment implements View.OnClickListener {

    private Banner bannerLayout;
    private ListView mListView;
    private ListView zfListView;

    private MarketAdapter mAdapter;

    private MarketZfAdapter zfAdapter;

    private List<MarketModel> mList = new ArrayList<>();

    private List<TradeZxResponse.ZxModel> zfList = new ArrayList<>();

    private TextView zxContentView;
    private View zxLineView;
    private LinearLayout zxLayout;

    private TextView zfContentView;
    private View zfLineView;
    private LinearLayout zfLayout;

    //0 为自选，1位涨幅
    private int type = 0;

    private Context activity;

    //该页面是否准备完毕
    private boolean isPrepared;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        EventBus.getDefault().register(this);

        initView(view);
        initDatas();
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

            type = 1;
            changeSelect();
            zfListView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    }


    private void initDatas() {

        getZxData();
        getZFBData();

        List<Integer> urls = new ArrayList<>();

        int lanType = SPUtil.getInstance(activity).getSelectLanguage();

        if (lanType == 1) {
            //中文
            urls.add(R.mipmap.hq_banner_one);
            urls.add(R.mipmap.hq_banner_two);
            urls.add(R.mipmap.hq_banner_three);
        } else {
            urls.add(R.mipmap.hq_banner_one_en);
            urls.add(R.mipmap.hq_banner_two_en);
            urls.add(R.mipmap.hq_banner_three_en);
        }

        bannerLayout.setBannerAnimation(Transformer.Default);
        bannerLayout.setImages(urls);
        bannerLayout.setImageLoader(new GlideImageLoader());
        bannerLayout.start();

    }

    private void initView(View view) {
        bannerLayout = view.findViewById(R.id.banner);
        mListView = view.findViewById(R.id.market_lv);

        zxContentView = view.findViewById(R.id.market_zixuan_content_view);
        zxLineView = view.findViewById(R.id.market_zixuan_line_view);
        zxLayout = view.findViewById(R.id.market_zixuan_layout_view);

        zfContentView = view.findViewById(R.id.market_zf_content_view);
        zfLineView = view.findViewById(R.id.market_zf_line_view);
        zfLayout = view.findViewById(R.id.market_zf_layout_view);
        zfListView = view.findViewById(R.id.market_zf_lv);

        type = 0;

        zxContentView.setTextColor(getResources().getColor(R.color._01FCDA));
        zxContentView.setTextSize(18);
        zxLineView.setVisibility(View.VISIBLE);

        zfContentView.setTextColor(getResources().getColor(R.color.gray1));
        zfContentView.setTextSize(15);
        zfLineView.setVisibility(View.GONE);

        zxLayout.setOnClickListener(this);
        zfLayout.setOnClickListener(this);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.market_zixuan_layout_view:
                type = 0;
                changeSelect();
                mListView.setVisibility(View.VISIBLE);
                zfListView.setVisibility(View.GONE);
                break;
            case R.id.market_zf_layout_view:
                type = 1;
                changeSelect();
                zfListView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                break;
        }
    }

    private void changeSelect() {

        if (type == 0) {
            zxContentView.setTextColor(getResources().getColor(R.color._01FCDA));
            zxContentView.setTextSize(18);
            zxLineView.setVisibility(View.VISIBLE);

            zfContentView.setTextColor(getResources().getColor(R.color.gray1));
            zfContentView.setTextSize(15);
            zfLineView.setVisibility(View.GONE);

            getZxData();

        } else {
            zxContentView.setTextColor(getResources().getColor(R.color.gray1));
            zxContentView.setTextSize(15);
            zxLineView.setVisibility(View.GONE);

            zfContentView.setTextColor(getResources().getColor(R.color._01FCDA));
            zfContentView.setTextSize(18);
            zfLineView.setVisibility(View.VISIBLE);

            getZFBData();
        }
    }


    private void getZxData() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.HANGQING_ZIXUAN_URL)
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
                                    TradeZxResponse response = gson.fromJson(backVal, TradeZxResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().size() > 0) {
                                            setZxData(response.getData());
                                        } else {
//                                            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_SHORT).show();
//                                            setNoData();
                                            setZxData(new ArrayList<>());
                                        }
                                    } else {
//                                        setNoData();
                                        setZxData(new ArrayList<>());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    setNoData();
                                    setZxData(new ArrayList<>());
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


    private void getZFBData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.HANGQING_ZFB_URL)
                .tag(activity)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            Log.e("backVal", "backVal=" + backVal);
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    TradeZfResponse response = gson.fromJson(backVal, TradeZfResponse.class);
//                                    Log.e("backVal","backVal="+response.toString());
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getCode() == 0 && response.getData().getChangeRank() != null
                                            && response.getData().getChangeRank().size() > 0) {
                                        setZfData(response.getData().getChangeRank());
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
                    }
                });
    }


    private void setZfData(List<TradeZfResponse.ZfModel> list) {
        zfAdapter = new MarketZfAdapter(getActivity(), list);
        zfListView.setAdapter(zfAdapter);
        zfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppUtil.isFastClick()) {
                    return;
                }
                TradeZfResponse.ZfModel zfModel = (TradeZfResponse.ZfModel) parent.getItemAtPosition(position);
                checkIsCollection(zfModel);
            }
        });

    }


    private void checkIsCollection(TradeZfResponse.ZfModel zfModel) {

        try {
            int tmpCollection = 0;

            for (int i = 0; i < zfList.size(); i++) {
                TradeZxResponse.ZxModel tempZxDataModel = zfList.get(i);
                if (tempZxDataModel.getMarketId().equalsIgnoreCase(zfModel.getMarketId())) {
                    tmpCollection = 1;
                }
            }
            Bundle data = new Bundle();
            data.putString(WorkManagerService.EXTRA_DATA, zfModel.getMarketId());
            WorkManagerService.startService(activity, data);
            Intent kLineIntent = new Intent(getActivity(), KlineActivity.class);
            kLineIntent.putExtra("markid", zfModel.getMarketId());
            kLineIntent.putExtra("shoucang", tmpCollection);
            startActivity(kLineIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setZxData(List<TradeZxResponse.ZxModel> list) {
        zfList.clear();
        zfList.addAll(list);
        mAdapter = new MarketAdapter(getActivity(), list);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TradeZxResponse.ZxModel zxDataModel = (TradeZxResponse.ZxModel) parent.getItemAtPosition(position);
                Bundle data = new Bundle();
                data.putString(WorkManagerService.EXTRA_DATA, zxDataModel.getMarketId());
                WorkManagerService.startService(activity, data);

                Intent kLineIntent = new Intent(getActivity(), KlineActivity.class);
                kLineIntent.putExtra("markid", zxDataModel.getMarketId());
                kLineIntent.putExtra("shoucang", 1);
                startActivity(kLineIntent);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(RefreshEvent refreshEvent) {
//        viewPager.setCurrentItem(2);
        getZxData();
        getZFBData();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveTwoFourEvent(TwoFourEvent twoFourEvent) {
//        viewPager.setCurrentItem(2);
//        getZxData();
        getZFBData();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
