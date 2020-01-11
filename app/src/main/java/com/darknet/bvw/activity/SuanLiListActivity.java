package com.darknet.bvw.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.SuanLiListItemAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.SuanLiResponse;
import com.darknet.bvw.util.TimeUtil;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

public class SuanLiListActivity extends BaseActivity {

    private ImageView backView;
    private TextView titleView;

    private LinearLayout noDataLayout;

    LRecyclerView recyclerView;
    private static final int REQUEST_COUNT = 10;
    private int pageNumber = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean hasMore = true;
    //该页面是否准备完毕
    private boolean isPrepared;
    private SuanLiListItemAdapter fenLieOrderAdapter;

    private TextView timeView;


    @Override
    public void initView() {
        backView = (ImageView) this.findViewById(R.id.back_sign_view);
        titleView = (TextView) this.findViewById(R.id.msg_title_view);


        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleView.setText(getString(R.string.suanli_wakuang_list_title));


        recyclerView = findViewById(R.id.order_listview);
        noDataLayout = findViewById(R.id.layNoData);

        timeView = findViewById(R.id.suanli_item_time_view);

        timeView.setText(TimeUtil.getYesDay());

        fenLieOrderAdapter = new SuanLiListItemAdapter(SuanLiListActivity.this);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mLRecyclerViewAdapter = new LRecyclerViewAdapter(fenLieOrderAdapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);
//        fenLieOrderAdapter.setOnItemClick(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(SuanLiListActivity.this));

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
            }
        });

        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadMoreEnabled(false);

//        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//
//                if (hasMore) {
//                    pageNumber++;
//                    loadMoreData();
//                } else {
//                    recyclerView.setNoMore(true);
//                }
//            }
//        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_suanli_list;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

        recyclerView.refresh();
        getData();

    }

    @Override
    public void configViews() {

    }


    private void setVal(List<SuanLiResponse.SunLiItemModel> powerHistoryList) {

        try {
            noDataLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            fenLieOrderAdapter.addData(powerHistoryList);
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


    private void getData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.SUANLI_FIRST_URL)
                .tag(SuanLiListActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    SuanLiResponse response = gson.fromJson(backVal, SuanLiResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().getPowerHistoryList() != null && response.getData().getPowerHistoryList().size() > 0) {
                                            setVal(response.getData().getPowerHistoryList());
                                        } else {
//                                            Toast.makeText(SuanLiListActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                            setNoData();
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
                        setNoData();
                    }
                });
    }


}
