package com.darknet.bvw.activity.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.model.Event;
import com.darknet.bvw.model.event.KLineTradeEvent;
import com.darknet.bvw.model.event.RefreshEvent;
import com.darknet.bvw.model.event.RefreshThreeEvent;
import com.darknet.bvw.model.event.RefreshTwoEvent;
import com.darknet.bvw.model.event.SellAndBuyEvent;
import com.darknet.bvw.model.event.TradeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collection;

public class ExchangeFragment extends Fragment implements View.OnClickListener {

    private TextView mMarketTv;

    private TextView mExchageTv;

    private TextView mAmountTv;

    private Typeface mType01;

    private Typeface mType02;

    private LinearLayout mLinearLayout;

    private MarketFragment mMarketFragment;

    private TradingFragment mTradingFragment;

    private AmountFragment mAmountFragment;


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SellAndBuyEvent(SellAndBuyEvent event) {
        showTradeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        initView(view);
        initTypeface();
        initFragment();
        EventBus.getDefault().register(this);

        return view;
    }

    private void initFragment() {
        mMarketFragment = new MarketFragment();
        mTradingFragment = new TradingFragment();
        mAmountFragment = new AmountFragment();

        getChildFragmentManager().beginTransaction()
                .add(R.id.exchange_ll, mMarketFragment, "market")
                .add(R.id.exchange_ll, mTradingFragment, "trading")
                .add(R.id.exchange_ll, mAmountFragment, "amount")
                .show(mTradingFragment)
                .hide(mMarketFragment)
                .hide(mAmountFragment)
                .commitAllowingStateLoss();

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    private void initTypeface() {
        mType02 = Typeface.createFromAsset(getActivity().getAssets(), "font/PingFangRegular.ttf");
        mType01 = Typeface.createFromAsset(getActivity().getAssets(), "font/PingFangBold.ttf");
    }

    private void initView(View view) {


        mLinearLayout = view.findViewById(R.id.exchange_ll);

        mMarketTv = view.findViewById(R.id.fragment_exchange_market_tv);
        mExchageTv = view.findViewById(R.id.fragment_exchange_exchange_tv);
        mAmountTv = view.findViewById(R.id.fragment_exchange_amount_tv);


        mMarketTv.setOnClickListener(this);
        mExchageTv.setOnClickListener(this);
        mAmountTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_exchange_market_tv:
                changeOneColor();
                getChildFragmentManager().beginTransaction()
                        .show(mMarketFragment)
                        .hide(mAmountFragment)
                        .hide(mTradingFragment)
                        .commitAllowingStateLoss();

                TradeEvent tradeEvent = new TradeEvent();
                tradeEvent.setType(0);
                EventBus.getDefault().post(tradeEvent);

                EventBus.getDefault().post(new RefreshEvent());

                Log.e("fragemtn", ".........change...one..do..........");

                break;
            case R.id.fragment_exchange_exchange_tv:
                showTradeFragment();
                break;
            case R.id.fragment_exchange_amount_tv:
                changeThreeColor();
                getChildFragmentManager().beginTransaction()
                        .show(mAmountFragment)
                        .hide(mMarketFragment)
                        .hide(mTradingFragment)
                        .commitAllowingStateLoss();

                TradeEvent tradeEvent2 = new TradeEvent();
                tradeEvent2.setType(0);
                EventBus.getDefault().post(tradeEvent2);

                EventBus.getDefault().post(new RefreshThreeEvent());

                Log.e("fragemtn", ".........change..three...do..........");
                break;
        }
    }

    /**
     * 显示交易模块
     */
    private void showTradeFragment() {
        changeTwoColor();
        getChildFragmentManager().beginTransaction()
                .show(mTradingFragment)
                .hide(mAmountFragment)
                .hide(mMarketFragment)
                .commitAllowingStateLoss();

        TradeEvent tradeEvent1 = new TradeEvent();
        tradeEvent1.setType(1);
        EventBus.getDefault().post(tradeEvent1);

        EventBus.getDefault().post(new RefreshTwoEvent());

        Log.e("fragemtn", ".........change...two..do..........");
    }

    private void changeOneColor() {
        mMarketTv.setTextColor(getResources().getColor(R.color.white));
        mExchageTv.setTextColor(getResources().getColor(R.color.gray2));
        mAmountTv.setTextColor(getResources().getColor(R.color.gray2));
    }

    private void changeTwoColor() {
        mMarketTv.setTextColor(getResources().getColor(R.color.gray2));
        mExchageTv.setTextColor(getResources().getColor(R.color.white));
        mAmountTv.setTextColor(getResources().getColor(R.color.gray2));
    }

    private void changeThreeColor() {
        mMarketTv.setTextColor(getResources().getColor(R.color.gray2));
        mExchageTv.setTextColor(getResources().getColor(R.color.gray2));
        mAmountTv.setTextColor(getResources().getColor(R.color.white));
    }


    private void itemTurnColor(int id) {
        mMarketTv.setTypeface(id == R.id.fragment_exchange_market_tv ? mType01 : mType02);
        mExchageTv.setTypeface(id == R.id.fragment_exchange_exchange_tv ? mType01 : mType02);
        mAmountTv.setTypeface(id == R.id.fragment_exchange_amount_tv ? mType01 : mType02);
    }
}
