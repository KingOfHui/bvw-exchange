package com.darknet.bvw.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.NoteAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChaimTestActivity extends AppCompatActivity {


    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    private NoteAdapter mAdapter;
    private StaggeredGridLayoutManager gridLayoutManager;

    private List<String> list = new ArrayList<>();

    private Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        unbinder = ButterKnife.bind(this);

        for (int i = 0; i < 10; i++) {
            list.add(i + "item");
        }

        //开始下拉
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(true);//启用加载

        gridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new NoteAdapter(this, list);
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

}
