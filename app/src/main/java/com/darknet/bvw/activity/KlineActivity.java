package com.darknet.bvw.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.MyFragmentPagerAdapter;
import com.darknet.bvw.model.KlineItemBean;
import com.darknet.bvw.socket.SocketTool;
import com.github.fujianlian.klinechart.DataHelper;
import com.github.fujianlian.klinechart.KLineChartAdapter;
import com.github.fujianlian.klinechart.KLineChartView;
import com.github.fujianlian.klinechart.KLineEntity;
import com.github.fujianlian.klinechart.formatter.DateFormatter;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class KlineActivity extends BaseActivity implements View.OnClickListener {

    private List<KLineEntity> mKLineEntities;
    private KLineChartAdapter mAdapter;
    private KLineChartView mKLineChartView;
    private TextView mFenText;


    private LinearLayout fenLayout;
    private TextView fenTxtView;
    private View fenLineView;

    private LinearLayout fenText15Layout;
    private TextView fenText15TxtView;
    private View fenText15LineView;

    private LinearLayout fenText60Layout;
    private TextView fenText60TxtView;
    private View fenText60LineView;

    private LinearLayout fenText4hourLayout;
    private TextView fenText4hourTxtView;
    private View fenText4hourLineView;

    private LinearLayout fenTextdayLayout;
    private TextView fenTextdayTxtView;
    private View fenTextdayLineView;

    private TextView moreView;

    private SocketTool socketTool;


    // 0 为分时，1为15分钟，2为1小时，3为4小时，4为日线
    private int type;


    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        //搜索广告
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        //TabLayout
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //显示当前那个标签页
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        type = 0;

        mKLineChartView = findViewById(R.id.kLineChartView);
        mFenText = findViewById(R.id.fenText);


        fenLayout = findViewById(R.id.kline_fen_layout);
        fenTxtView = findViewById(R.id.fenText);
        fenLineView = findViewById(R.id.kline_fen_line);

        fenText15Layout = findViewById(R.id.kline_fenText15_layout);
        fenText15TxtView = findViewById(R.id.fenText15);
        fenText15LineView = findViewById(R.id.kline_fenText15_line);

        fenText60Layout = findViewById(R.id.kline_fenText60_layout);
        fenText60TxtView = findViewById(R.id.fenText60);
        fenText60LineView = findViewById(R.id.kline_fenText60_line);

        fenText4hourLayout = findViewById(R.id.kline_fenText4hour_layout);
        fenText4hourTxtView = findViewById(R.id.fenText4hour);
        fenText4hourLineView = findViewById(R.id.kline_fenText4hour_line);

        fenTextdayLayout = findViewById(R.id.kline_fenTextday_layout);
        fenTextdayTxtView = findViewById(R.id.fenTextday);
        fenTextdayLineView = findViewById(R.id.kline_fenTextday_line);

        moreView = findViewById(R.id.fenTextmore);

        fenLayout.setOnClickListener(this);
        fenText15Layout.setOnClickListener(this);
        fenText60Layout.setOnClickListener(this);
        fenText4hourLayout.setOnClickListener(this);
        fenTextdayLayout.setOnClickListener(this);
        moreView.setOnClickListener(this);


        findViewById(R.id.kiline_back_iv).setOnClickListener(v -> finish());
        mKLineEntities = new ArrayList<>();
        mAdapter = new KLineChartAdapter();
        mKLineChartView.setAdapter(mAdapter);
        mKLineChartView.setDateTimeFormatter(new DateFormatter());
        mKLineChartView.setGridRows(4);
        mKLineChartView.setGridColumns(4);
        mKLineChartView.justShowLoading();
        mKLineChartView.setBackgroundColor(Color.parseColor("#1F1B2C"));

        initViewPager();

        //websocket
        socketTool = new SocketTool();
        socketTool.init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketTool.disConnect();
        socketTool.disconnectStomp();
        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reciverMessage(KlineItemBean klineItemBean) {
        KLineEntity entity = new KLineEntity();
        entity.setOpen(klineItemBean.getOpenPrice());
        entity.setClose(klineItemBean.getClosePrice());
        entity.setDate(klineItemBean.getTime()+"");
        entity.setHigh(klineItemBean.getHighestPrice());
        entity.setLow(klineItemBean.getLowestPrice());
        entity.setVolume(klineItemBean.getVolume());
        mKLineEntities.add(entity);

        DataHelper.calculate(mKLineEntities);
        mAdapter.addFooterData(mKLineEntities);
        mAdapter.notifyDataSetChanged();
        mKLineChartView.startAnimation();
        mKLineChartView.refreshEnd();

        mKLineChartView.hideSelectData();
        mKLineChartView.setMainDrawLine(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kline;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
//        mKLineEntities = DataRequestK.getALL(this);
//        DataHelper.calculate(mKLineEntities);
//        mAdapter.addFooterData(mKLineEntities);
//        mAdapter.notifyDataSetChanged();
//        mKLineChartView.startAnimation();
//        mKLineChartView.refreshEnd();
//
//        mKLineChartView.hideSelectData();
//        mKLineChartView.setMainDrawLine(false);
//        mFenText.setOnClickListener(v -> {
//            mKLineChartView.setMainDrawLine(true);
//        });
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kline_fen_layout:
                type = 0;
                changeState();
                break;
            case R.id.kline_fenText15_layout:
                type = 1;
                changeState();
                break;
            case R.id.kline_fenText60_layout:
                type = 2;
                changeState();
                break;
            case R.id.kline_fenText4hour_layout:
                type = 3;
                changeState();
                break;
            case R.id.kline_fenTextday_layout:
                type = 4;
                changeState();
                break;
            case R.id.fenTextmore:
                break;
        }
    }


    private void changeState() {
        fenTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
        fenLineView.setVisibility(View.GONE);

        fenText15TxtView.setTextColor(getResources().getColor(R.color.colorAccent));
        fenText15LineView.setVisibility(View.GONE);

        fenText60TxtView.setTextColor(getResources().getColor(R.color.colorAccent));
        fenText60LineView.setVisibility(View.GONE);

        fenText4hourTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
        fenText4hourLineView.setVisibility(View.GONE);

        fenTextdayTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
        fenTextdayLineView.setVisibility(View.GONE);

        if (type == 0) {
            fenTxtView.setTextColor(getResources().getColor(R.color._01FCDA));
            fenLineView.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            fenText15TxtView.setTextColor(getResources().getColor(R.color._01FCDA));
            fenText15LineView.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            fenText60TxtView.setTextColor(getResources().getColor(R.color._01FCDA));
            fenText60LineView.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            fenText4hourTxtView.setTextColor(getResources().getColor(R.color._01FCDA));
            fenText4hourLineView.setVisibility(View.VISIBLE);
        } else if (type == 4) {
            fenTextdayTxtView.setTextColor(getResources().getColor(R.color._01FCDA));
            fenTextdayLineView.setVisibility(View.VISIBLE);
        }
    }

}
