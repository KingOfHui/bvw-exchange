package com.darknet.bvw.activity;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.WalletManageAdapter;
import com.darknet.bvw.view.TypefaceTextView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class WalletManageActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout layBack;
    private TypefaceTextView title;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private WalletManageAdapter mAdapter;
    private List<String> list = new ArrayList<>();
    private Button btnExportWallet;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                WalletManageActivity.this.finish();
                break;
            case R.id.btnExportWallet:

                break;
        }

    }

    @Override
    public void initView() {

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.rv);
        btnExportWallet = findViewById(R.id.btnExportWallet);

        layBack.setOnClickListener(this);
        btnExportWallet.setOnClickListener(this);


        title.setText("钱包管理");

        for (int i = 0; i < 10; i++) {
            list.add(i + "item");
        }

        //开始下拉
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(true);//启用加载

        gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new WalletManageAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);

        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishLoadMore();
            }
        });

        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_manage;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }
}
