package com.darknet.bvw.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.PopwindowsLeftFragment;
import com.darknet.bvw.adapter.DealFragment;
import com.darknet.bvw.adapter.DepthFragment;
import com.darknet.bvw.commonlib.util.ResourceUtils;
import com.darknet.bvw.commonlib.util.StringUtil;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.config.constant.KLineTypeEnum;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.CoinsModel;
import com.darknet.bvw.model.KLineDataModel;
import com.darknet.bvw.model.event.KLineEvent;
import com.darknet.bvw.model.event.RefreshEvent;
import com.darknet.bvw.model.event.SellAndBuyEvent;
import com.darknet.bvw.model.kLineHisResponse;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.model.response.JiaoYiDuiResponse;
import com.darknet.bvw.service.WorkManagerService;
import com.darknet.bvw.util.AppUtil;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.util.KlineAutoUtil;
import com.darknet.bvw.util.NumberFormatUtil;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.KLineMenuView;
import com.github.fujianlian.klinechart.DataHelper;
import com.github.fujianlian.klinechart.KLineChartAdapter;
import com.github.fujianlian.klinechart.KLineChartView;
import com.github.fujianlian.klinechart.KLineEntity;
import com.github.fujianlian.klinechart.formatter.DateFormatter;
import com.github.fujianlian.klinechart.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

import static com.darknet.bvw.config.constant.KLineTypeEnum.LINE;
import static com.darknet.bvw.config.constant.KLineTypeEnum.daily;
import static com.darknet.bvw.config.constant.KLineTypeEnum.hour1;
import static com.darknet.bvw.config.constant.KLineTypeEnum.min1;
import static com.darknet.bvw.config.constant.KLineTypeEnum.min10;
import static com.darknet.bvw.config.constant.KLineTypeEnum.min15;
import static com.darknet.bvw.config.constant.KLineTypeEnum.min30;
import static com.darknet.bvw.config.constant.KLineTypeEnum.min5;
import static com.darknet.bvw.config.constant.KLineTypeEnum.month;
import static com.darknet.bvw.config.constant.KLineTypeEnum.more;
import static com.darknet.bvw.config.constant.KLineTypeEnum.week;

public class KlineActivity extends BaseActivity implements View.OnClickListener {


    private List<KLineEntity> mKLineEntities = new ArrayList<>();

    private KLineChartAdapter mAdapter;
    @BindView(R.id.kLineChartView)
    KLineChartView mKLineChartView;

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


    @BindView(R.id.kline_two_buy_view)
    TextView buyView;

    @BindView(R.id.kline_two_sell_view)
    TextView sellView;

    @BindView(R.id.ib_star)
    ImageButton startImg;

    @BindView(R.id.kLineMenuView)
    KLineMenuView kLineMenuView;

    @BindView(R.id.kiline_back_iv)
    ImageView ivBack;

    @BindView(R.id.rg)
    RadioGroup rg;

    @BindView(R.id.rb_deep)
    RadioButton rbDeep;

    @BindView(R.id.rb_deal)
    RadioButton rbDeal;

    @BindView(R.id.fl)
    FrameLayout fl;

    @BindView(R.id.ll_period_layout)
    LinearLayout llPeriodLayout;

    @BindView(R.id.tv_1_min)
    TextView tv1Min;
    @BindView(R.id.tv_10_min)
    TextView tv10Min;
    @BindView(R.id.tv_30_min)
    TextView tv30Min;
    @BindView(R.id.tv_5_min)
    TextView tv5Min;

    @BindView(R.id.ivSwitchMarket)
    ImageView ivSwitchMarket;
    /**
     * 0 为分时，1为15分钟，2为1小时，3为日线, 4周线
     */
    private KLineTypeEnum type = hour1;

    private final long MIN_SECOND = 60 * 1000;

    private final long MAX_COUNT = 200;
    /**
     * 是否分时图
     */
    private boolean isLine = false;

    private String markID;

    /**
     * 0 未搜出，1收藏
     */
    private int isCollection;

    private boolean isFirstLoad = true;

    private List<KLineTypeEnum> tabTitles = Arrays.asList(
            LINE,
            min15,
            hour1,
            daily,
            week,
            more);
    private DepthFragment depthFragment;

    private DealFragment dealFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_kline_two;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        isFirstLoad = false;
    }

    @Override
    public void initView() {
        initBus();
        isCollection = getIntent().getIntExtra("shoucang", 0);
        markID = getIntent().getStringExtra("markid");
        initStarView();
        if (!StringUtil.isEmpty(markID)) {
            countCoinTxtView.setText(markID.split("-")[0]);
            priceCoinTxtView.setText(markID.split("-")[1]);
        }
        setFragment(0);
        initKLineMenuView();
        initViewPager();
        initKLineChartView();
        initViewPager();
        getSymbolTicker();
        initClickListener();
    }

    private void setFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (depthFragment == null) {
                    depthFragment = DepthFragment.newInstance(markID);
                    transaction.add(R.id.fl, depthFragment);

                } else {
                    transaction.show(depthFragment);
                }
                break;
            case 1:
                if (dealFragment == null) {
                    dealFragment = DealFragment.newInstance(markID);
                    transaction.add(R.id.fl, dealFragment);
                } else {
                    transaction.show(dealFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 用来隐藏fragment的方法
     *
     * @param fragmentTransaction
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        //如果此fragment不为空的话就隐藏起来
        if (dealFragment != null) {
            fragmentTransaction.hide(dealFragment);
        }
        if (depthFragment != null) {
            fragmentTransaction.hide(depthFragment);
        }
    }

    /**
     * 注册事件
     */
    private void initBus() {
        EventBus.getDefault().register(this);
    }

    private void initKLineMenuView() {
        kLineMenuView.setOnTabSelectListener(new KLineMenuView.OnTabSelectListener() {
            @Override
            public void onTabClick(int position, KLineTypeEnum kLineTypeEnum) {
                if (kLineTypeEnum.getValue() == more.getValue()) {
                    llPeriodLayout.setVisibility(View.VISIBLE);
                } else {
                    getDataBytype(kLineTypeEnum);
                    llPeriodLayout.setVisibility(View.GONE);
                }
            }
        });
        kLineMenuView.setData(tabTitles);
    }

    private void initStarView() {
        if (isCollection == 0) {
            startImg.setImageResource(R.mipmap.img_exchange_star);
        } else {
            startImg.setImageResource(R.mipmap.shoucang_select_img);
        }
    }

    private void initClickListener() {
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

                SellAndBuyEvent kLineEvent = new SellAndBuyEvent();
                kLineEvent.setType(SellAndBuyEvent.BUY);
                kLineEvent.setMarketId(markID);
                EventBus.getDefault().post(kLineEvent);
                finish();
            }
        });

        sellView.setOnClickListener(v -> {
            SellAndBuyEvent kLineEvent = new SellAndBuyEvent();
            kLineEvent.setType(SellAndBuyEvent.SELL);
            kLineEvent.setMarketId(markID);
            EventBus.getDefault().post(kLineEvent);
            finish();
        });
        ivSwitchMarket.setOnClickListener(view -> {
            PopwindowsLeftFragment fragment = new PopwindowsLeftFragment();

            fragment.setCoinsListener((String coinsSyblm, String closeStr, String close, boolean isSel, String usdRa, CoinsModel.DataBean dataBean) -> {
                markID = coinsSyblm;
                initStarView();
                if (!StringUtil.isEmpty(markID)) {
                    countCoinTxtView.setText(markID.split("-")[0]);
                    priceCoinTxtView.setText(markID.split("-")[1]);
                }
                if (depthFragment != null) {
                    depthFragment.setMarketId(markID);
                }
                if (dealFragment != null) {
                    dealFragment.setMarketId(markID);
                }
                setFragment(0);
                initKLineChartView();
                initViewPager();
                getSymbolTicker();
                getDataBytype(type);
                fragment.dismiss();
            });
            fragment.show(getSupportFragmentManager(), "pop");
        });
    }

    private void initKLineChartView() {
        mAdapter = new KLineChartAdapter();
        mKLineChartView.setAdapter(mAdapter);
        mKLineChartView.setDateTimeFormatter(new DateFormatter());
        mKLineChartView.setGridRows(4);
        mKLineChartView.setGridColumns(4);
        mKLineChartView.justShowLoading();
        mKLineChartView.setBackgroundColor(ResourceUtils.getColor(mAppContext, R.color.color_37314D));
    }

    private void initViewPager() {

        rg.check(R.id.rb_deep);//默认选中的RadioButton
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_deep:
                        setFragment(0);
                        break;
                    case R.id.rb_deal:
                        setFragment(1);
                        break;
                }
            }
        });

    }

    private void getSymbolTicker() {

        if (markID == null) {
            return;
        }

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.BIW_USDT_URL + markID)
                .tag(this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {

                        try {
                            JiaoYiDuiResponse response = new GsonBuilder().create().fromJson(backresponse.body(), JiaoYiDuiResponse.class);
                            if (response.getCode() == 0) {
                                JiaoYiDuiResponse.JiaoYiDuiData data = response.getData();
                                if (data == null) {
                                    return;
                                }
                                JiaoYiDuiResponse.KLineTicker coinThumb = data.getCoinThumb();
                                if (coinThumb != null) {
                                    BigDecimal bd2 = new BigDecimal(coinThumb.getClose());
                                    usdPrice.setText(bd2.setScale(3, BigDecimal.ROUND_HALF_UP).toPlainString());

                                    int i = new BigDecimal(coinThumb.getChg()).compareTo(BigDecimal.ZERO);
                                    if (i == -1) {
                                        change.setText(ArithmeticUtils.multiply(coinThumb.getChg(), "100").stripTrailingZeros().setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
                                        //red
                                        change.setTextColor(getResources().getColor(R.color._FFFC6767));
                                    } else {
                                        //green
                                        change.setTextColor(getResources().getColor(R.color._01FCDA));
                                        change.setText("+" + ArithmeticUtils.multiply(coinThumb.getChg(), "100").stripTrailingZeros().setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%");
                                    }

                                    rmbPrice.setText(
                                            "≈$" + ArithmeticUtils.multiply(coinThumb.getClose(), coinThumb.getQuoteUsdRate()).stripTrailingZeros().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());

                                    high.setText(NumberFormatUtil.getNumber("0.000", coinThumb.getHigh()));
                                    low.setText(NumberFormatUtil.getNumber("0.000", coinThumb.getLow()));

                                    String amount = ArithmeticUtils.divide(coinThumb.getVolume(), "1000", 2);
                                    if (Double.valueOf(amount) > 1) {
                                        kline_24h.setText(amount + " k");
                                    } else {
                                        kline_24h.setText(ArithmeticUtils.divide(coinThumb.getVolume(), "1", 1));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void getKlineAllData(String to, String from, String resolution) {
        if (mAdapter != null) {
            mAdapter.clearData();
            mAdapter.notifyDataSetChanged();
        }

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        try {
            if (!isFirstLoad) {
                showDialog(getString(R.string.load_data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("to", to);
        map.put("from", from);
        map.put("resolution", resolution);
        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.KLINE_ALL_DATA_URL + markID)
                .tag(this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        hideDialog();
                        Log.d(TAG, "type---" + type.getPeriod() + "---getKlineAllData--" + backresponse.body());
                        try {
                            kLineHisResponse response = new GsonBuilder().create().fromJson(backresponse.body(), kLineHisResponse.class);
                            List<KLineDataModel> kLineDataList = new ArrayList<>();
                            if (response.isSuccess()) {
                                for (List<String> data : response.getData()) {
                                    KLineDataModel model = new KLineDataModel(data);
                                    kLineDataList.add(model);
                                }
                                JSONArray jsonArray = KlineAutoUtil.auto(kLineDataList, type.getPeriod());
                                kLineDataList.clear();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    String jsonStr = JSONObject.toJSONString(array);
                                    List<String> datas = JSONObject.parseArray(jsonStr, String.class);
                                    KLineDataModel model = new KLineDataModel(datas);
                                    kLineDataList.add(model);
                                }
                                doRefreshKline(kLineDataList);
                            }
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                    }
                });
    }


    private void doRefreshKline(List<KLineDataModel> itemsBeans) {
        mKLineEntities.clear();
        for (int i = 0; i < itemsBeans.size(); i++) {
            KLineDataModel itemsBean = itemsBeans.get(i);
            KLineEntity kLineEntity = new KLineEntity();
            kLineEntity.Volume = itemsBean.getVolume();
            kLineEntity.High = itemsBean.getHigh();
            kLineEntity.Low = itemsBean.getLow();
            if (type == daily || type == month || type == week) {
                kLineEntity.Date = DateUtil.DateFormat.format(new Date(itemsBean.getTime()));
            } else {
                kLineEntity.Date = DateUtil.DateTimeFormat.format(new Date(itemsBean.getTime()));
            }
            kLineEntity.Close = itemsBean.getClose();
            kLineEntity.Open = itemsBean.getOpen();
            mKLineEntities.add(kLineEntity);
        }

        showKLine();
    }

    /**
     * 显示K线
     */
    public void showKLine() {
        DataHelper.calculate(mKLineEntities);
        mAdapter.addHeaderData(mKLineEntities);
        mAdapter.notifyDataSetChanged();
        mKLineChartView.startAnimation();
        mKLineChartView.refreshEnd();
        mKLineChartView.setMainDrawLine(isLine);
    }


    @Override
    public void configViews() {

    }

    @OnClick({R.id.kiline_back_iv, R.id.tv_5_min, R.id.tv_30_min, R.id.tv_10_min, R.id.tv_1_min})
    public void onClick(View v) {
        if (AppUtil.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.kiline_back_iv:
                finish();
                break;
            case R.id.tv_1_min:
                getDataBytype(min1);
                kLineMenuView.selectMoreView(min1);
                llPeriodLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_5_min:
                getDataBytype(min5);
                kLineMenuView.selectMoreView(min5);
                llPeriodLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_10_min:
                getDataBytype(min10);
                kLineMenuView.selectMoreView(min10);
                llPeriodLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_30_min:
                getDataBytype(min30);
                kLineMenuView.selectMoreView(min30);
                llPeriodLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void getDataBytype(KLineTypeEnum kLineTypeEnum) {
        long from, to;
        switch (kLineTypeEnum) {
            case LINE:
                type = LINE;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * MIN_SECOND * 3;
                isLine = true;
                getKlineAllData(String.valueOf(to), String.valueOf(from), min1.getValue());
                break;
            case min1:
                type = min1;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * MIN_SECOND * 3;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), min1.getValue());
                break;
            case min5:
                type = min5;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 5 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), min5.getValue());
                break;
            case min10:
                type = min10;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 10 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), min10.getValue());
                break;
            case min15:
                type = min15;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 15 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), min15.getValue());
                break;
            case min30:
                type = min30;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 30 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), min30.getValue());
                break;
            case hour1:
                type = hour1;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), hour1.getValue());
                break;
            case daily:
                type = daily;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 24 * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), daily.getValue());
                break;
            case week:
                type = week;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 7 * 24 * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), week.getValue());
                break;
            case month:
                type = month;
                to = System.currentTimeMillis();
                from = to - MAX_COUNT * 28 * 24 * 60 * MIN_SECOND;
                isLine = false;
                getKlineAllData(String.valueOf(to), String.valueOf(from), month.getValue());
                break;
            default:
                break;
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
                .tag(KlineActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                BaseResponse depth = gson.fromJson(response.body(), BaseResponse.class);
                                if (depth != null && depth.getCode() == 0) {
//                                    Toast.makeText(KlineActivity.this, depth.getMsg(), Toast.LENGTH_SHORT).show();
                                    startImg.setImageResource(R.mipmap.img_exchange_star);
                                    isCollection = 0;
                                    EventBus.getDefault().post(new RefreshEvent());
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
                .tag(KlineActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                BaseResponse depth = gson.fromJson(response.body(), BaseResponse.class);
                                if (depth != null && depth.getCode() == 0) {
                                    startImg.setImageResource(R.mipmap.shoucang_select_img);
                                    isCollection = 1;
                                    EventBus.getDefault().post(new RefreshEvent());
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
        if (event == null) {
            return;
        }
        Log.d(TAG, "getKlineAllData--" + event.toString());
        if (isFirstLoad) {
            return;
        }
        if (!type.getPeriod().equals(event.getPeriod())) {
            return;
        }
        if (TextUtils.isEmpty(markID) || (!TextUtils.isEmpty(event.getMarketId())&&!markID.equalsIgnoreCase(event.getMarketId()))) {
            return;
        }
        KLineEntity entity = new KLineEntity();
        entity.Volume = event.getVolume();
        entity.High = event.getHighestPrice();
        entity.Low = event.getLowestPrice();
        if (type == daily || type == month || type == week) {
            entity.Date = DateUtil.DateFormat.format(new Date(event.getTime()));
        } else {
            entity.Date = DateUtil.DateTimeFormat.format(new Date(event.getTime()));
        }
        entity.Close = event.getClosePrice();
        entity.Open = event.getOpenPrice();
        if (mKLineEntities.size() > 0) {
            KLineEntity oldKLineEntity = mKLineEntities.get(mKLineEntities.size() - 1);
            if (oldKLineEntity.getDate().equals(entity.getDate())) {
                mKLineEntities.set(mKLineEntities.size() - 1, entity);
                DataHelper.calculate(mKLineEntities);
                mAdapter.changeItem(mKLineEntities.size() - 1, entity);
                return;
            }
        }
        mKLineEntities.add(entity);
        DataHelper.calculate(mKLineEntities);
        mAdapter.addOneData(entity);
        mAdapter.notifyDataSetChanged();

    }

}
