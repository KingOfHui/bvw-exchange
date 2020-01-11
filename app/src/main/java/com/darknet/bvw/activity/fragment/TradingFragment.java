package com.darknet.bvw.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.KlineTwoActivity;
import com.darknet.bvw.activity.WeiTuoListActivity;
import com.darknet.bvw.adapter.CurrentOrderAdapter;
import com.darknet.bvw.adapter.InAdapter;
import com.darknet.bvw.adapter.OutAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.AccountResponse;
import com.darknet.bvw.model.CoinsModel;
import com.darknet.bvw.model.CreateOrderRequest;
import com.darknet.bvw.model.CurrentOrderModel;
import com.darknet.bvw.model.DepthResponse;
import com.darknet.bvw.model.RequestEntity;
import com.darknet.bvw.model.TokenCoinResponse;
import com.darknet.bvw.model.event.RefreshThreeEvent;
import com.darknet.bvw.model.event.RefreshTwoEvent;
import com.darknet.bvw.model.request.CurrentWeiTuoRequest;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.model.response.BidStateResponse;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.util.NumberEnum;
import com.darknet.bvw.util.UserSPHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BidDialogView;
import com.darknet.bvw.view.CancelOrderDialogView;
import com.darknet.bvw.view.DialogLoadding;
import com.darknet.bvw.view.MyListView;
import com.darknet.bvw.view.TypefaceTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

@SuppressWarnings("all")
public class TradingFragment extends Fragment {

    public DialogLoadding dialogLoadding;

    private static String TAG = TradingFragment.class.getSimpleName();
    private MyListView my_exchage_lv_01;
    private MyListView my_exchage_lv_02;
    private EditText mPriceEt;
    private TextView mBtnBuyOrSell;//买入或者卖出按钮

    private String buyOrsell = "buy";
    private TypefaceTextView mCoinsType;//显示的币种
    private TypefaceTextView mCoinsUsdtType;//对标的币种

    //滚动
    private List<DepthResponse.DataBean.AsksBean> mInList = new ArrayList<>(7);
    private List<DepthResponse.DataBean.BidsBean> mOutList = new ArrayList<>(7);
    private InAdapter mInAdapter;
    private OutAdapter mOutAdapter;
    private int first = 0;
    private String price = "0";// 兑换ETH价格
    private TextView mPirceUsdtTv;

    private MyListView mListView;
    private CurrentOrderAdapter mCurrentOrderAdapter;
    private List<CurrentOrderModel.DataBean.ItemsBean> mCurrentOrderModelList = new ArrayList();

    private LooperHandler mHandler = new LooperHandler(this);


    private TextView weiTuoAllView;


    private LinearLayout mairuLayout, maichuLayout;
    private TextView mairuContentView, maichuContentView;
    private View mairuLine, maichuLine;


    private TextView mCanUseCoinsCountTv;
    private TextView mCanUseCoinsCountTvType;
    private TextView mCostCoinsTv;
    private EditText mCountNumETv;

    private TextView m25PresentTv;
    private TextView m50PresentTv;
    private TextView m75PresentTv;
    private TextView m100PresentTv;

    private boolean isPrice = true;//第一次进入页面
    private String marketId = "BTC-USDT";//交易对

    private LinearLayout noWeiTuoDataLayout;

    private CoinsModel model;//币种列表

    private TextView shenduPriceType;
    private TextView shenduNumType;


    private TextView lastPriceView;
    private TextView yueDengyuPriceView;

    private ImageView addZixuanView;

    private TextView totalMoneyView;
    private TextView totalMoneyTypeView;

    private TextView inputNumMoneyTypeView;

    private LinearLayout menuFragmentLayout;

    //0 未搜出，1收藏
    private int isCollection;

    private String usdRateVal;

    private ImageView kLineImgView;


    @SuppressLint("HandlerLeak")
    public class LooperHandler extends Handler {
        WeakReference<TradingFragment> mWeakReference;

        public LooperHandler(TradingFragment fragment) {
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    DepthResponse depth = (DepthResponse) msg.obj;
                    try {
                        getDepthDatas(depth);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.what = 1;
                    mHandler.sendMessageDelayed(message, 3 * 1000);
                    break;
                case 1:
                    //todo 深度数据循环
                    initData();
                    break;

            }
        }
    }

    /**
     * 深度数据
     *
     * @param depth
     */
    private synchronized void getDepthDatas(DepthResponse depth) {

        if (depth != null && depth.getCode() == 0) {
            String base = "0";
            String binbase = "0";
            mInList.clear();
            mOutList.clear();
            int askSize = depth.getData().getAsks().size();
            int bidsSize = depth.getData().getBids().size();

            if (isPrice) {
                isPrice = false;
                if (buyOrsell.equals("buy")) {
                    if (askSize > 0) {
                        mPriceEt.setText(depth.getData().getAsks().get(askSize-1).getPrice());
                    } else {
                        mPriceEt.setText("0");
                    }
                } else {
                    if (bidsSize > 0) {
                        mPriceEt.setText(depth.getData().getBids().get(0).getPrice());
                    } else {
                        mPriceEt.setText("0");
                    }
                }
                hideLoading();
            }

            //ask
            for (int i = 0; i < askSize; i++) {
                DepthResponse.DataBean.AsksBean asksBean = depth.getData().getAsks().get(i);
                if (i == 0) {
                    asksBean.setTotal(asksBean.getAmount());
                    price = asksBean.getPrice();
                } else {
                    String askBuy = ArithmeticUtils.plus(asksBean.getAmount(),
                            depth.getData().getAsks().get(i - 1).getTotal())
                            .setScale(0, RoundingMode.UP)
                            .stripTrailingZeros().toPlainString();
                    asksBean.setTotal(askBuy);
                }
            }


//            try {
//                if (!ArithmeticUtils.compare(base, depth.getData().getAsks().get(askSize - 1).getTotal())) {
//                    base = depth.getData().getAsks().get(askSize - 1).getTotal();
//                }
//                Log.e(TAG, "getDepthDatas: " + base);
//
//            }catch (Exception e){
//                e.printStackTrace();

            try {
                if (askSize >= 1) {
                    if (!ArithmeticUtils.compare(base, depth.getData().getAsks().get(askSize - 1).getTotal())) {
                        base = depth.getData().getAsks().get(askSize - 1).getTotal();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                for (int i = 0; i < askSize; i++) {
                    String askP = ArithmeticUtils.multiply(ArithmeticUtils.divide(depth.getData().getAsks().get(i).getTotal(), base, 5), "100").setScale(0, RoundingMode.UP).toPlainString();
                    depth.getData().getAsks().get(i).setPrecent(askP);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (depth.getData().getAsks() == null || depth.getData().getAsks().size() == 0) {
                for (int i = 0; i < 7; i++) {
                    depth.getData().getAsks().add(new DepthResponse.DataBean.AsksBean());
                }
                mInList.addAll(depth.getData().getAsks());
                mInAdapter.notifyDataSetChanged();
            } else {
                Collections.reverse(depth.getData().getAsks());
                if (askSize < 7) {
                    for (int i = 0; i < (7 - askSize); i++) {
                        depth.getData().getAsks().add(new DepthResponse.DataBean.AsksBean());
                    }
                }
                for (int i = 0; i < 7; i++) {
                    mInList.add(depth.getData().getAsks().get(i));
                }
                Collections.reverse(mInList);
                mInAdapter.notifyDataSetChanged();
            }

            //bids
            for (int i = 0; i < bidsSize; i++) {

                DepthResponse.DataBean.BidsBean bidsBean = depth.getData().getBids().get(i);
                if (i == 0) {
                    bidsBean.setTotal(bidsBean.getAmount());
                } else {
                    String bidSell = ArithmeticUtils.plus(bidsBean.getAmount(),
                            depth.getData().getBids().get(i - 1).getTotal()).setScale(0, RoundingMode.UP)
                            .stripTrailingZeros().toPlainString();
                    bidsBean.setTotal(bidSell);

                }
            }
            if (bidsSize >= 1) {
                if (!ArithmeticUtils.compare(binbase, depth.getData().getBids().get(bidsSize - 1).getTotal())) {
                    binbase = depth.getData().getBids().get(bidsSize - 1).getTotal();
                }
            }

            Log.e(TAG, "getDepthDatas: " + binbase);
            try {
                for (int i = 0; i < bidsSize; i++) {
                    String bidP = ArithmeticUtils.multiply(ArithmeticUtils.divide(depth.getData().getBids().get(i).getTotal(), binbase, 5), "100").setScale(0, RoundingMode.UP).toPlainString();
                    depth.getData().getBids().get(i).setPrecent(bidP);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (depth.getData().getBids() == null || depth.getData().getBids().size() == 0) {
                for (int i = 0; i < 7; i++) {
                    depth.getData().getBids().add(new DepthResponse.DataBean.BidsBean());
                }
                mOutList.addAll(depth.getData().getBids());
                mOutAdapter.notifyDataSetChanged();
            } else {
                if (bidsSize < 7) {
                    for (int i = 0; i < (7 - bidsSize); i++) {
                        depth.getData().getBids().add(new DepthResponse.DataBean.BidsBean());
                    }
                }
                for (int i = 0; i < 7; i++) {
                    mOutList.add(depth.getData().getBids().get(i));
                }
//                mOutList.addAll(depth.getData().getBids());
                mOutAdapter.notifyDataSetChanged();
            }


        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trading, container, false);

        EventBus.getDefault().register(this);
        initView(view);
        initAdapter();
        initData();

        getAccount();
        getTokenList();
        //当前委托
        getCurrentWeiTuo();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //当前委托
        getCurrentWeiTuo();
        Log.e("xxxxxxxx", ".....lazyLoad...do...moneyFragment....");
    }


    private String choseCoin;
    private CoinsModel.DataBean panKouRight;


    private void getTokenList() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.TOKEN_LIST_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {

                            try {
                                Gson gson = new Gson();
                                TokenCoinResponse coinResponse = gson.fromJson(response.body(), TokenCoinResponse.class);
                                if (coinResponse.getCode() == 0) {
                                    choseCoin = coinResponse.getData().get(0);
                                    getCoinsList();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    /**
     * 获取币种列表
     */
    private void getCoinsList() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.COIN_LIST_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {

                            try {
                                Gson gson = new Gson();
                                CoinsModel model = gson.fromJson(response.body(), CoinsModel.class);
//                                Log.e("TAG", "getCoinsList: " + choseCoin);choseCoin
                                for (CoinsModel.DataBean datum : model.getData()) {
                                    if (choseCoin != null) {
                                        if (datum.getQuote_token_symbol().equals(choseCoin)) {
                                            panKouRight = datum;
                                            setPanKouSignData();
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    private void setPanKouSignData() {

        try {
            if (panKouRight != null) {
                isPrice = true;
                marketId = panKouRight.getBase_token_symbol() + "-" + panKouRight.getQuote_token_symbol();
                lastPriceView.setText(panKouRight.getThumb().getCloseStr());
                yueDengyuPriceView.setText("" + panKouRight.getThumb().getClose());
                inputNumMoneyTypeView.setText(panKouRight.getBase_token_symbol());
                mCoinsType.setText(panKouRight.getBase_token_symbol());
                mCoinsUsdtType.setText(panKouRight.getQuote_token_symbol());

                usdRateVal = panKouRight.getThumb().getUsdRate();

                if (panKouRight.isIs_favor_market()) {

                    isCollection = 1;
                    addZixuanView.setImageResource(R.mipmap.shoucang_select_img);
                } else {
                    isCollection = 0;
                    addZixuanView.setImageResource(R.mipmap.img_exchange_star);
                }

                if (marketId != null) {
                    shenduPriceType.setText(marketId.split("-")[1]);
                    shenduNumType.setText(marketId.split("-")[0]);
                }

                getCurrentWeiTuo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        mInAdapter = new InAdapter(getActivity(), mInList);
        my_exchage_lv_01.setAdapter(mInAdapter);
        mOutAdapter = new OutAdapter(getActivity(), mOutList);
        my_exchage_lv_02.setAdapter(mOutAdapter);

        for (int i = 0; i < 7; i++) {
            mInList.add(new DepthResponse.DataBean.AsksBean());
        }
        mInAdapter.notifyDataSetChanged();
        for (int i = 0; i < 7; i++) {
            mOutList.add(new DepthResponse.DataBean.BidsBean());
        }
        mOutAdapter.notifyDataSetChanged();

        my_exchage_lv_01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DepthResponse.DataBean.AsksBean asksBean = mInList.get(position);
                if (!TextUtils.isEmpty(asksBean.getPrice())) {
                    mPriceEt.setText(asksBean.getPrice());
                }
            }
        });

        my_exchage_lv_02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DepthResponse.DataBean.BidsBean bidsBean = mOutList.get(position);
                if (!TextUtils.isEmpty(bidsBean.getPrice())) {
                    mPriceEt.setText(bidsBean.getPrice());
                }
            }
        });

        mHandler.sendEmptyMessage(1);
    }

    private void initView(View view) {
        dialogLoadding = new DialogLoadding(getActivity());
        my_exchage_lv_01 = view.findViewById(R.id.my_exchage_lv_01);
        my_exchage_lv_02 = view.findViewById(R.id.my_exchage_lv_02);
        mBtnBuyOrSell = view.findViewById(R.id.change_buy_idt);
        mPriceEt = view.findViewById(R.id.change_price_et);
        noWeiTuoDataLayout = view.findViewById(R.id.no_data_layout);

        weiTuoAllView = view.findViewById(R.id.jiaoyi_all_weituo_history);
        mPirceUsdtTv = view.findViewById(R.id.change_cny_price_tv);

        mCanUseCoinsCountTv = view.findViewById(R.id.idt_eth_amount_can_use_tv);
        mCanUseCoinsCountTvType = view.findViewById(R.id.idt_eth_amount_can_use_tv_type);

        mCoinsType = view.findViewById(R.id.coins_type_ttv);
        mCoinsUsdtType = view.findViewById(R.id.coins_usdt_ttv);
        mairuLayout = view.findViewById(R.id.mairu_layout);
        maichuLayout = view.findViewById(R.id.maichu_layout);
        mairuContentView = view.findViewById(R.id.mairu_txt_content);
        maichuContentView = view.findViewById(R.id.maichu_txt_content);
        mairuLine = view.findViewById(R.id.mairu_line_view);
        maichuLine = view.findViewById(R.id.maichu_line_view);
        mCostCoinsTv = view.findViewById(R.id.cost_coins_num_tv);
        mCountNumETv = view.findViewById(R.id.change_idt_amount_et);

        shenduPriceType = view.findViewById(R.id.trade_price_type_view);
        shenduNumType = view.findViewById(R.id.trade_num_type_view);

        lastPriceView = view.findViewById(R.id.last_price);
        yueDengyuPriceView = view.findViewById(R.id.yue_dengyu_price);

        addZixuanView = view.findViewById(R.id.fragment_exchange_star_iv);

        totalMoneyView = view.findViewById(R.id.idt_eth_amount_tv);
        totalMoneyTypeView = view.findViewById(R.id.total_money_count_view);

        menuFragmentLayout = view.findViewById(R.id.fragment_exchange_menu_layout);

        kLineImgView = view.findViewById(R.id.fragment_exchange_menu_iv);


        kLineImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent kLineIntent = new Intent(getActivity(),KlineActivity.class);

                Intent kLineIntent = new Intent(getActivity(), KlineTwoActivity.class);
                kLineIntent.putExtra("markid",marketId);
                startActivity(kLineIntent);
            }
        });

        view.findViewById(R.id.fragment_exchange_subtraction_iv).setOnClickListener(v -> {
            //todo 减法
            String price = mPriceEt.getText().toString();

            if (isDigits(price)) {
                //整数
                price = ArithmeticUtils.minus(price, "1").toPlainString();
                if (price.equals("0")) {
                    mPriceEt.setText("0");
                    mPirceUsdtTv.setText("0");
                    return;
                }
            } else {
                //不是整数
                int length = price.split("\\.")[1].length();
                if (length == NumberEnum.Zero1.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero1.getS()).toPlainString();
                } else if (length == NumberEnum.Zero2.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero2.getS()).toPlainString();
                } else if (length == NumberEnum.Zero3.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero3.getS()).toPlainString();
                } else if (length == NumberEnum.Zero4.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero4.getS()).toPlainString();
                } else if (length == NumberEnum.Zero5.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero5.getS()).toPlainString();
                } else if (length == NumberEnum.Zero6.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero6.getS()).toPlainString();
                } else if (length == NumberEnum.Zero7.getI()) {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero7.getS()).toPlainString();
                } else {
                    price = ArithmeticUtils.minus(price, NumberEnum.Zero8.getS()).toPlainString();
                }
            }
            mPriceEt.setText(price);
            mPirceUsdtTv.setText(price);
            String priceViewVal = mPriceEt.getText().toString().trim();
            String numViewVal = mCountNumETv.getText().toString().trim();
            setTotalValues(priceViewVal, numViewVal);
        });
        view.findViewById(R.id.fragment_exchange_add_iv).setOnClickListener(v -> {
            //todo 加法
            String price = mPriceEt.getText().toString();
            if (isDigits(price)) {
                //整数
                price = ArithmeticUtils.plus(price, "1").toPlainString();
            } else {
                //不是整数
                int length = price.split("\\.")[1].length();
                if (length == NumberEnum.Zero1.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero1.getS()).toPlainString();
                } else if (length == NumberEnum.Zero2.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero2.getS()).toPlainString();
                } else if (length == NumberEnum.Zero3.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero3.getS()).toPlainString();
                } else if (length == NumberEnum.Zero4.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero4.getS()).toPlainString();
                } else if (length == NumberEnum.Zero5.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero5.getS()).toPlainString();
                } else if (length == NumberEnum.Zero6.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero6.getS()).toPlainString();
                } else if (length == NumberEnum.Zero7.getI()) {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero7.getS()).toPlainString();
                } else {
                    price = ArithmeticUtils.plus(price, NumberEnum.Zero8.getS()).toPlainString();
                }
            }
            mPriceEt.setText(price);
            mPirceUsdtTv.setText(price);
            //todo 设置联动总额
            String priceViewVal = mPriceEt.getText().toString().trim();
            String numViewVal = mCountNumETv.getText().toString().trim();
            setTotalValues(priceViewVal, numViewVal);
        });

        inputNumMoneyTypeView = view.findViewById(R.id.trade_money_type_view);

        m25PresentTv = view.findViewById(R.id.change_precent_25);
        m50PresentTv = view.findViewById(R.id.change_precent_50);
        m75PresentTv = view.findViewById(R.id.change_precent_75);
        m100PresentTv = view.findViewById(R.id.change_precent_100);
        m25PresentTv.setOnClickListener(v -> {
            String price = mPriceEt.getText().toString().trim();
            String total = mCanUseCoinsCountTv.getText().toString();
            if (buyOrsell.equals("buy")) {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(ArithmeticUtils.divide(total, price, 8), "0.25").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            } else {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(total, "0.25").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            }

            changePresentBackGroudResource(R.id.change_precent_25);
        });
        m50PresentTv.setOnClickListener(v -> {
            String price = mPriceEt.getText().toString().trim();
            String total = mCanUseCoinsCountTv.getText().toString();
            if (buyOrsell.equals("buy")) {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(ArithmeticUtils.divide(total, price, 8), "0.5").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            } else {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(total, "0.5").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            }

            changePresentBackGroudResource(R.id.change_precent_50);
        });
        m75PresentTv.setOnClickListener(v -> {
            String price = mPriceEt.getText().toString().trim();
            String total = mCanUseCoinsCountTv.getText().toString();
            if (buyOrsell.equals("buy")) {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(ArithmeticUtils.divide(total, price, 8), "0.75").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            } else {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(total, "0.75").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            }
            changePresentBackGroudResource(R.id.change_precent_75);
        });
        m100PresentTv.setOnClickListener(v -> {
            String price = mPriceEt.getText().toString().trim();
            String total = mCanUseCoinsCountTv.getText().toString();
            if (buyOrsell.equals("buy")) {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(ArithmeticUtils.divide(total, price, 8), "1").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            } else {
                if (!TextUtils.isEmpty(price) && Double.valueOf(price) != 0) {
                    String count = ArithmeticUtils.multiply(total, "1").setScale(2, RoundingMode.HALF_EVEN).toPlainString();
                    mCountNumETv.setText(count);
                } else {
                    mCountNumETv.setText("0");
                }
            }
            changePresentBackGroudResource(R.id.change_precent_100);
        });





        view.findViewById(R.id.fragment_exchange_menu_layout).setOnClickListener(v -> {
            PopwindowsLeftFragment fragment = new PopwindowsLeftFragment();

            fragment.setCoinsListener((String coinsSyblm, String closeStr, String close, boolean isSel, String usdRa) -> {

                try {
                    marketId = coinsSyblm;
                    //todo 刷新整个页面
                    mCoinsType.setText(marketId.split("-")[0]);
                    mCoinsUsdtType.setText(marketId.split("-")[1]);

                    shenduPriceType.setText(marketId.split("-")[1]);
                    shenduNumType.setText(marketId.split("-")[0]);

                    inputNumMoneyTypeView.setText(marketId.split("-")[0]);

                    lastPriceView.setText(closeStr);
                    yueDengyuPriceView.setText("" + close);

                    if (isSel) {
                        isCollection = 1;
                        addZixuanView.setImageResource(R.mipmap.shoucang_select_img);
                    } else {
                        isCollection = 0;
                        addZixuanView.setImageResource(R.mipmap.img_exchange_star);
                    }

                    usdRateVal = usdRa;

                    isPrice = true;
                    mPriceEt.setText("0");
                    mCountNumETv.setText("0");
                    totalMoneyView.setText("0");
                    mPirceUsdtTv.setText("0");
//                    showLoading();
                    getAccount();
                    getCurrentWeiTuo();
                    initPresentBackGroudResource();

                    fragment.dismiss();
                    setRateViewContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            fragment.show(getFragmentManager(), "pop");
        });

        mListView = view.findViewById(R.id.trading_mlv);

        mCurrentOrderAdapter = new CurrentOrderAdapter(getActivity(), mCurrentOrderModelList);
        mListView.setAdapter(mCurrentOrderAdapter);


        mCurrentOrderAdapter.setListener(bean -> {
            //todo 取消操作
            canCelOrder(bean);
        });

        weiTuoAllView.setOnClickListener(v -> {
            Intent hisToryAll = new Intent(getActivity(), WeiTuoListActivity.class);
            hisToryAll.putExtra("markid", marketId);
            startActivity(hisToryAll);
        });


        addZixuanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isCollection == 1) {
                    cancelZiXuan();
                } else {
                    addZiXuan();
                }


            }
        });

        mairuLayout.setOnClickListener(v -> changeMaiRu());

        maichuLayout.setOnClickListener(v -> changeMaichu());

        mBtnBuyOrSell.setOnClickListener(v -> {
            getBidStateData();
        });


        mPriceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String priceViewVal = s.toString();
                String numViewVal = mCountNumETv.getText().toString().trim();
                setTotalValues(priceViewVal, numViewVal);
                setRateViewContent();
            }
        });


        mCountNumETv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //todo 根据输入的数量 去更新费用字段
                String priceViewVal = mPriceEt.getText().toString().trim();
                String numViewVal = s.toString();
                setTotalValues(priceViewVal, numViewVal);
            }
        });

    }


    private void setRateViewContent() {

        try {
            String currentPriceVal = mPriceEt.getText().toString();
            if (currentPriceVal == null || currentPriceVal.trim().length() == 0 || usdRateVal == null || usdRateVal.trim().length() == 0) {
                mPirceUsdtTv.setText("0");
            } else {
                mPirceUsdtTv.setText(ArithmeticUtils.multiply(usdRateVal, currentPriceVal).stripTrailingZeros().toPlainString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据输入的数量 去更新费用字段
     */
    private void setTotalValues(String priceViewVal, String numViewVal) {
        try {
            String baskSymble = marketId.split("-")[1];
            int setScan = 8;
            if (!TextUtils.isEmpty(priceViewVal) && !TextUtils.isEmpty(numViewVal)) {
                if (baskSymble.equals("USDT")) {
                    setScan = 3;
                } else if (baskSymble.equals("BVW")) {
                    setScan = 2;
                } else if (baskSymble.equals("BTC")) {
                    setScan = 8;
                } else if (baskSymble.equals("ETH")) {
                    setScan = 4;
                }
                totalMoneyView.setText(ArithmeticUtils.multiply(priceViewVal, numViewVal)
                        .setScale(setScan, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
            } else {
                totalMoneyView.setText("0");
            }
            totalMoneyTypeView.setText(marketId.split("-")[1]);
        } catch (Exception e) {
            e.printStackTrace();
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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.DEL_ZIXUAN_URL + marketId)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                BaseResponse depth = gson.fromJson(response.body(), BaseResponse.class);
                                if (depth != null && depth.getCode() == 0) {
                                    UserSPHelper.setParam(getActivity(), "shoucang", 0);
                                    Toast.makeText(getActivity(), depth.getMsg(), Toast.LENGTH_SHORT).show();
                                    addZixuanView.setImageResource(R.mipmap.img_exchange_star);
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
     * 判断是否是整数
     *
     * @param str
     * @return
     */
    public static boolean isDigits(String str) {
        return str.matches("[-+]?[0-9]*");
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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.ADD_ZIXUAN_URL + marketId)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                BaseResponse depth = gson.fromJson(response.body(), BaseResponse.class);
                                if (depth != null && depth.getCode() == 0) {
                                    UserSPHelper.setParam(getActivity(), "shoucang", 1);
                                    Toast.makeText(getActivity(), depth.getMsg(), Toast.LENGTH_SHORT).show();
                                    addZixuanView.setImageResource(R.mipmap.shoucang_select_img);
                                    isCollection = 1;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    //todo 更换点击后的页面效果
    private void changePresentBackGroudResource(int id) {
        m25PresentTv.setBackgroundResource(id == R.id.change_precent_25 ? R.drawable.shape_corner_editext_green : R.drawable.shape_corner_editext);
        m50PresentTv.setBackgroundResource(id == R.id.change_precent_50 ? R.drawable.shape_corner_editext_green : R.drawable.shape_corner_editext);
        m75PresentTv.setBackgroundResource(id == R.id.change_precent_75 ? R.drawable.shape_corner_editext_green : R.drawable.shape_corner_editext);
        m100PresentTv.setBackgroundResource(id == R.id.change_precent_100 ? R.drawable.shape_corner_editext_green : R.drawable.shape_corner_editext);
    }

    //todo 初始化
    private void initPresentBackGroudResource() {
        m25PresentTv.setBackgroundResource(R.drawable.shape_corner_editext);
        m50PresentTv.setBackgroundResource(R.drawable.shape_corner_editext);
        m75PresentTv.setBackgroundResource(R.drawable.shape_corner_editext);
        m100PresentTv.setBackgroundResource(R.drawable.shape_corner_editext);
    }

    //todo 获取资产页面
    private void getAccount() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.GET_ACCOUNT_LIST_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.body() != null) {
                            Log.e(TAG, "Account: " + response.body());
                            Gson gson = new Gson();
                            AccountResponse mAccount = gson.fromJson(response.body(), AccountResponse.class);
                            if (mAccount != null && mAccount.getCode() == 0) {
                                for (int i = 0; i < mAccount.getData().size(); i++) {
                                    String coinsType = mCoinsType.getText().toString();
                                    String jiajiaType = mCoinsUsdtType.getText().toString();

                                    AccountResponse.DataBean dataBean = mAccount.getData().get(i);

                                    if (buyOrsell.equalsIgnoreCase("buy")) {
                                        if (dataBean.getSymbol().equals(jiajiaType)) {
                                            if (marketId != null) {
                                                mCanUseCoinsCountTv.setText(dataBean.getBalance().stripTrailingZeros().setScale(3, BigDecimal.ROUND_DOWN).toPlainString());
                                                mCanUseCoinsCountTvType.setText(marketId.split("-")[1]);
                                            }
                                        }
                                    } else {
                                        if (dataBean.getSymbol().equals(coinsType)) {
                                            if (marketId != null) {
                                                mCanUseCoinsCountTv.setText(dataBean.getBalance().stripTrailingZeros().setScale(3, BigDecimal.ROUND_DOWN).toPlainString());
                                                mCanUseCoinsCountTvType.setText(marketId.split("-")[0]);
                                            }
                                            mPriceEt.setText(dataBean.getPrice().stripTrailingZeros().toPlainString());
                                            mPirceUsdtTv.setText(dataBean.getValue_usd().toPlainString());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }


    private void changeMaiRu() {
        mairuContentView.setTextSize(18);
        mairuContentView.setTextColor(getResources().getColor(R.color._72f8db));
        maichuContentView.setTextColor(getResources().getColor(R.color.white));
        maichuContentView.setTextSize(15);
        mairuLine.setVisibility(View.VISIBLE);
        maichuLine.setVisibility(View.GONE);
        //todo 买入
        buyOrsell = "buy";
        mBtnBuyOrSell.setText(getString(R.string.trade_trade_buy));
        mBtnBuyOrSell.setBackgroundResource(R.color._01FCDA);
        mBtnBuyOrSell.setTextColor(getResources().getColor(R.color.black));
        isPrice = true;
        initPresentBackGroudResource();
        getAccount();
    }

    private void changeMaichu() {
        maichuContentView.setTextSize(18);
        maichuContentView.setTextColor(getResources().getColor(R.color._72f8db));
        mairuContentView.setTextColor(getResources().getColor(R.color.white));
        mairuContentView.setTextSize(15);
        mairuLine.setVisibility(View.GONE);
        maichuLine.setVisibility(View.VISIBLE);
        //todo 卖出
        buyOrsell = "sell";
        mBtnBuyOrSell.setText(getString(R.string.trade_trade_sell));
        mBtnBuyOrSell.setBackgroundResource(R.color._FFFC6767);
        mBtnBuyOrSell.setTextColor(getResources().getColor(R.color.white));
        isPrice = true;
        initPresentBackGroudResource();
        getAccount();


    }


    //获取bid状态
    private void getBidStateData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FIND_BID_STATE_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BidStateResponse response = gson.fromJson(backVal, BidStateResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setStateVal(response.getData().getStatus());
                                        }
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
//                        dismissDialog();
                    }
                });
    }


    private void setStateVal(int stateVal) {
        if (stateVal == 0) {
            //未开通
            new BidDialogView().showTips(getActivity(), getString(R.string.find_invest_notice));
//            Intent suanLiIntent = new Intent(activity, SuanLiWaKuangActivity.class);
//            startActivity(suanLiIntent);
        } else if (stateVal == 1) {
            //已开通
            String amount = mCountNumETv.getText().toString();
            String marketId = mCoinsType.getText().toString() + "-" + mCoinsUsdtType.getText().toString();
            String buyPrice = mPriceEt.getText().toString();
            if (!TextUtils.isEmpty(amount)) {
                showLoading();
                sendBuyRequest(amount, marketId, buyPrice, buyOrsell, "limit");
            } else {
                //todo 数量不能为空
                Toast.makeText(getActivity(), getString(R.string.trade_num_trade_sign), Toast.LENGTH_SHORT).show();
            }
        } else if (stateVal == 2) {
            //开通中
//            Intent suanLiIntent = new Intent(activity, SuanLiWaKuangActivity.class);
//            startActivity(suanLiIntent);
        }
    }


    /**
     * @param amount
     * @param marketId
     * @param buyPrice
     * @param sell
     * @param market
     */
    private void sendBuyRequest(String amount, String marketId, String buyPrice, String sell, String market) {
        //创建买入和卖出订单
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setAmount(amount);
        orderRequest.setMarketId(marketId);
        orderRequest.setPrice(buyPrice);
        orderRequest.setSide(sell);
        orderRequest.setType(market);
        orderRequest.setUserId("");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(orderRequest);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.CREATE_ORDER_URL)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        hideLoading();

                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse response = gson.fromJson(backVal, BaseResponse.class);
                                    if (response != null) {
                                        //refresh order
                                        //todo 下单成功提示及更新订单和资产
                                        if (response.getCode() == 0) {
                                            getAccount();
                                            getCurrentWeiTuo();
                                        }
                                        Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    //网络请求数据
    private void initData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        RequestEntity request = new RequestEntity();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(request);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonVal);

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.TRADE_DEPTH_URL + marketId)
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
//                        Log.e("TAG", "onSuccess: " + response.body());
                        if (response.body() != null) {
                            Gson gson = new Gson();
                            DepthResponse depth = gson.fromJson(response.body(), DepthResponse.class);

                            try {
                                Message msg = Message.obtain();
                                msg.what = 0;
                                msg.obj = depth;
                                mHandler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
        mHandler.removeMessages(0);
        mHandler = null;
        EventBus.getDefault().unregister(this);
    }


    //获取当前委托
    private void getCurrentWeiTuo() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        CurrentWeiTuoRequest tradeRequest = new CurrentWeiTuoRequest();


        tradeRequest.setLimit(100);
        tradeRequest.setPage(1);
        tradeRequest.setMarketId(marketId);
        tradeRequest.setStatus("pending");

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.WEITUO_URL)
                .tag(getActivity())
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

                                    CurrentOrderModel model = gson.fromJson(backVal, CurrentOrderModel.class);
                                    if (model != null && model.getCode() == 0 && model.getData() != null && model.getData().getItems() != null && model.getData().getItems().size() > 0) {
                                        mCurrentOrderModelList.clear();
                                        mCurrentOrderModelList.addAll(model.getData().getItems());
                                        mCurrentOrderAdapter.notifyDataSetChanged();
                                        noWeiTuoDataLayout.setVisibility(View.GONE);
                                        mListView.setVisibility(View.VISIBLE);
                                    } else {
                                        noWeiTuoDataLayout.setVisibility(View.VISIBLE);
                                        mListView.setVisibility(View.GONE);
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
                        noWeiTuoDataLayout.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });

    }

    public void showLoading() {
        if (dialogLoadding == null) {
            dialogLoadding = new DialogLoadding(getActivity());
        }
        dialogLoadding.showDialog();
    }

    public void hideLoading() {
        if (dialogLoadding != null)
            dialogLoadding.closeDialog();
    }

    //取消委托
    private void canCelOrder(CurrentOrderModel.DataBean.ItemsBean itemsBean) {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        try {
            showLoading();
        }catch (Exception e){
            e.printStackTrace();
        }



        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CANCEL_WEITUO_URL + "/" + itemsBean.getId())
                .tag(getActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse response = gson.fromJson(backVal, BaseResponse.class);
                                    if (response != null) {
                                        //refresh order
                                        if (response.getCode() == 0) {
//                                            new CancelOrderDialogView().showTips(getActivity(), response.getMsg());
                                            getCurrentWeiTuo();
                                            getAccount();
                                        } else {
//                                            Toast.makeText(getActivity(),response.getMsg(),Toast.LENGTH_SHORT).show();
//                                            new CancelOrderDialogView().showTips(getActivity(), response.getMsg());
                                        }
                                        Toast.makeText(getActivity(),response.getMsg(),Toast.LENGTH_SHORT).show();

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
                        try {
                            hideLoading();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(RefreshTwoEvent refreshEvent) {
        getAccount();
        getTokenList();
        //当前委托
//        getCurrentWeiTuo();
    }


}
