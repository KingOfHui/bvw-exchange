package com.darknet.bvw.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.MyFragmentPagerAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.CoinThumb;
import com.darknet.bvw.model.KLineDataModel;
import com.darknet.bvw.model.KLineTIckerResponse;
import com.darknet.bvw.model.event.KLineEvent;
import com.darknet.bvw.model.event.KLineTradeEvent;
import com.darknet.bvw.model.kLineHisResponse;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.socket.SocketTool;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.github.fujianlian.klinechart.DataHelper;
import com.github.fujianlian.klinechart.KLineChartAdapter;
import com.github.fujianlian.klinechart.KLineChartView;
import com.github.fujianlian.klinechart.KLineEntity;
import com.github.fujianlian.klinechart.formatter.DateFormatter;
import com.github.fujianlian.klinechart.utils.DateUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KlineTwoActivity extends BaseActivity implements View.OnClickListener {


    private List<KLineEntity> mKLineEntities = new ArrayList<>();
    private KLineChartAdapter mAdapter;
    private KLineChartView mKLineChartView;


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

    @BindView(R.id.kline_price_coin)
    TextView priceCoinTxtView;
    @BindView(R.id.kline_count_coin)
    TextView countCoinTxtView;
    @BindView(R.id.kline_usd_price)
    TextView usdPrice;
    @BindView(R.id.kline_rmb_price)
    TextView rmbPrice;
    @BindView(R.id.kline_change)
    TextView change;
    @BindView(R.id.kline_high)
    TextView high;
    @BindView(R.id.kline_low)
    TextView low;
    @BindView(R.id.kline_24h)
    TextView kline_24h;

    private String coinSymbol;


    // 0 为分时，1为15分钟，2为1小时，3为4小时，4为日线
    private int type;
    private final int MIN_SECOND = 60 * 1000;
    private final int MAX_COUNT = 1200;
    private boolean isLine = false;//是否分时图

    private String markID;

    private ImageView startImg;

    //0 未搜出，1收藏
    private int isCollection;

    private TextView buyView;
    private TextView sellView;
    private SocketTool socketTool;


    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        //搜索广告
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, markID);
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
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        isCollection = getIntent().getIntExtra("shoucang", 0);

        markID = getIntent().getStringExtra("markid");

        startImg = (ImageView) this.findViewById(R.id.fragment_exchange_star_iv);

        buyView = (TextView) this.findViewById(R.id.kline_two_buy_view);
        sellView = (TextView) this.findViewById(R.id.kline_two_sell_view);


        if (isCollection == 0) {
            startImg.setImageResource(R.mipmap.img_exchange_star);
        } else {
            startImg.setImageResource(R.mipmap.shoucang_select_img);
        }


        coinSymbol = countCoinTxtView.getText() + "-" + priceCoinTxtView.getText();
        type = 0;
        mKLineChartView = findViewById(R.id.kLineChartView);

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


         socketTool = new SocketTool();
        socketTool.init();
        socketTool.connectStomp(coinSymbol);
        getSymbolTicker(coinSymbol);


        findViewById(R.id.kiline_back_iv).setOnClickListener(v -> finish());



        initViewPager();



        startImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCollection == 1) {
                    cancelZiXuan();
                } else {
                    addZiXuan();
                }
            }
        });


        buyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KLineEvent kLineEvent = new KLineEvent();
                kLineEvent.setType(0);
                EventBus.getDefault().post(kLineEvent);
                finish();
            }
        });


        sellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLineEvent kLineEvent = new KLineEvent();
                kLineEvent.setType(1);
                EventBus.getDefault().post(kLineEvent);
                finish();
            }
        });

    }

    private void getSymbolTicker(String coinSymbol) {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.BVW_USDT_URL)
                .tag(this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        KLineTIckerResponse response = new GsonBuilder().create().fromJson(backresponse.body(), KLineTIckerResponse.class);
                        if (response.getCode() == 0) {
                            KLineTIckerResponse.KLineTickerData data = response.getData();
                            if (data == null) {
                                return;
                            }
                            CoinThumb coinThumb = data.getCoinThumb();
                            if (coinThumb == null) {
                                return;
                            }
                            if (coinThumb.getMarketId().equals(coinSymbol)) {
                                usdPrice.setText(coinThumb.getUsdRate());
                                change.setText(coinThumb.getChange());
                                high.setText(coinThumb.getHigh());
                                low.setText(coinThumb.getLow());
                                kline_24h.setText(coinThumb.getLastDayClose());
                            }
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketTool.disConnect();
        socketTool.disconnectStomp();
        EventBus.getDefault().unregister(this);

    }





    @Override
    public int getLayoutId() {
        return R.layout.activity_kline_two;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        long to = System.currentTimeMillis();
        long from = to - MAX_COUNT * 15 * MIN_SECOND;
        getKlineAllData(String.valueOf(to), String.valueOf(from), "15");
    }
    private void getKlineAllData(String to, String from, String resolution) {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        showDialog(getString(R.string.load_data));
        Map<String, String> map = new HashMap<>();
        map.put("to", to);
        map.put("from", from);
        map.put("resolution", resolution);
        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.KLINE_ALL_DATA_URL + coinSymbol)
                .tag(this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        hideDialog();
                        Log.i("aaaaaa", "getKlineAllData--" + backresponse.body());
                        kLineHisResponse response = new GsonBuilder().create().fromJson(backresponse.body(), kLineHisResponse.class);
                        List<KLineDataModel> kLineDataList = new ArrayList<>();
                        if (response.isSuccess()) {
                            for (List<String> data : response.getData()) {
                                KLineDataModel model = new KLineDataModel(data);
                                kLineDataList.add(model);
                            }
                            doRefreshKline(kLineDataList);
                        }
                    }
                });
    }

    private int klineInterval = 1;
    private void doRefreshKline(List<KLineDataModel> itemsBeans) {
        mKLineEntities.clear();
        if (klineInterval > 1) {
            for (int i = 0; i < itemsBeans.size(); i = i + klineInterval) {
                if (i + klineInterval < itemsBeans.size()) {
                    KLineEntity kLineEntity = new KLineEntity();
                    kLineEntity.Open = itemsBeans.get(i).getOpen();
                    kLineEntity.Close = itemsBeans.get(i + klineInterval).getClose();
                    float High = itemsBeans.get(i).getHigh();
                    float Low = itemsBeans.get(i).getLow();
                    float Volume = 0;
                    for (int j = 0; j < klineInterval; j++) {
                        KLineDataModel itemsBean = itemsBeans.get(i + j);
                        if (itemsBean.getHigh() > High) {
                            High = itemsBean.getHigh();
                        }
                        if (itemsBean.getLow() < Low) {
                            Low = itemsBean.getLow();
                        }
                        Volume += itemsBean.getVolume();
                    }
                    kLineEntity.High = High;
                    kLineEntity.Low = Low;
                    kLineEntity.Volume = Volume;
                    if (type == 4) {
                        kLineEntity.Date = DateUtil.DateFormat.format(new Date(itemsBeans.get(i + klineInterval).getTime()));
                    } else {
                        kLineEntity.Date = DateUtil.DateTimeFormat.format(new Date(itemsBeans.get(i + klineInterval).getTime()));
                    }
                    mKLineEntities.add(kLineEntity);
                }
            }
        } else {
            for (int i = 0; i < itemsBeans.size(); i++) {
                KLineDataModel itemsBean = itemsBeans.get(i);
                KLineEntity kLineEntity = new KLineEntity();
                kLineEntity.Volume = itemsBean.getVolume();
                kLineEntity.High = itemsBean.getHigh();
                kLineEntity.Low = itemsBean.getLow();
                if (type == 4) {
                    kLineEntity.Date = DateUtil.DateFormat.format(new Date(itemsBean.getTime()));
                } else {
                    kLineEntity.Date = DateUtil.DateTimeFormat.format(new Date(itemsBean.getTime()));
                }
                kLineEntity.Close = itemsBean.getClose();
                kLineEntity.Open = itemsBean.getOpen();
                mKLineEntities.add(kLineEntity);
            }
        }
        mKLineChartView.setDateTimeFormatter(new DateFormatter());
        initKLine();
    }
    public void initKLine() {
        DataHelper.calculate(mKLineEntities);
        mAdapter.addFooterData(mKLineEntities);
        mAdapter.notifyDataSetChanged();
        mKLineChartView.startAnimation();
        mKLineChartView.refreshEnd();

        mKLineChartView.hideSelectData();
        mKLineChartView.setMainDrawLine(isLine);
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
                klineInterval = 1;
                long to = System.currentTimeMillis();
                long from = to - MAX_COUNT * MIN_SECOND;
                isLine = true;
                getKlineAllData(String.valueOf(to), String.valueOf(from), "1");

                break;
            case R.id.kline_fenText15_layout:
                type = 1;
                changeState();
                klineInterval = 15;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 15 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), "15");
                break;
            case R.id.kline_fenText60_layout:
                type = 2;
                changeState();
                klineInterval = 1;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), "1h");
                break;
            case R.id.kline_fenText4hour_layout:
                type = 3;
                changeState();
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 4 * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), "4h");
                break;
            case R.id.kline_fenTextday_layout:
                type = 4;
                changeState();
                klineInterval = 1;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 24 * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), "1d");
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


    /**
     * 删除自选
     */
    private void cancelZiXuan() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.DEL_ZIXUAN_URL + markID)
                .tag(KlineTwoActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                BaseResponse depth = gson.fromJson(response.body(), BaseResponse.class);
                                if (depth != null && depth.getCode() == 0) {
                                    Toast.makeText(KlineTwoActivity.this, depth.getMsg(), Toast.LENGTH_SHORT).show();
                                    startImg.setImageResource(R.mipmap.img_exchange_star);
                                    isCollection = 0;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    /**
     * 添加自选
     */
    private void addZiXuan() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.ADD_ZIXUAN_URL + markID)
                .tag(KlineTwoActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                BaseResponse depth = gson.fromJson(response.body(), BaseResponse.class);
                                if (depth != null && depth.getCode() == 0) {
                                    Toast.makeText(KlineTwoActivity.this, depth.getMsg(), Toast.LENGTH_SHORT).show();
                                    startImg.setImageResource(R.mipmap.shoucang_select_img);
                                    isCollection = 1;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void kLineEvent(KLineEvent event) {
        KLineEntity entity = new KLineEntity();
        entity.Volume = event.getVolume();
        entity.High = event.getHighestPrice();
        entity.Low = event.getLowestPrice();
        entity.Date = DateUtil.DateFormat.format(new Date(event.getTime()));

        entity.Close = event.getClosePrice();
        entity.Open = event.getOpenPrice();
        if (mKLineEntities.size() > 0) {
            KLineEntity oldKLineEntity = mKLineEntities.get(mKLineEntities.size() - 1);
            if (oldKLineEntity.getDate().equals(entity.getDate())) {
                mKLineEntities.set(mKLineEntities.size() - 1, entity);
                DataHelper.calculateVolumeMA(mKLineEntities);
                mAdapter.changeItem(mKLineEntities.size() - 1, entity);
                return;
            }
        }
        mKLineEntities.add(entity);
        DataHelper.calculateVolumeMA(mKLineEntities);
        mAdapter.addOneData(entity);
        mAdapter.notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void kLineTradeEvent(KLineTradeEvent event) {
    }
}
