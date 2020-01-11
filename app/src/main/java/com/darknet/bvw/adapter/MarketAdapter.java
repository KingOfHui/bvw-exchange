package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.TradeZxResponse;
import com.darknet.bvw.util.ArithmeticUtils;

import java.math.BigDecimal;
import java.util.List;

public class MarketAdapter extends BAdapter<TradeZxResponse.ZxDataModel> {

    private Context context;

    public MarketAdapter(Context context, List<TradeZxResponse.ZxDataModel> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_coins;
    }

    @Override
    public void onInitView(View view, int position) {

        TradeZxResponse.ZxDataModel zxModel = getList().get(position);

        TradeZxResponse.ZxModel marketModel = zxModel.getRecommend();

        TextView moneyType = (TextView) view.findViewById(R.id.item_coins_money_type);
        TextView usdtType = (TextView) view.findViewById(R.id.item_coins_money_usdt);
        TextView tfNum = (TextView) view.findViewById(R.id.item_num24_num);
        TextView closeView = (TextView) view.findViewById(R.id.item_usdt_close);
        TextView priceView = (TextView) view.findViewById(R.id.item_cny_price);
        TextView stateView = (TextView) view.findViewById(R.id.item_persent_state);

        String[] moneyTeval = marketModel.getMarketId().split("-");
        moneyType.setText(moneyTeval[0]);
        usdtType.setText("/" + moneyTeval[1]);
        tfNum.setText(marketModel.getVolume().stripTrailingZeros().toPlainString());
        closeView.setText(marketModel.getCloseStr());
//        priceView.setText("" + marketModel.getClose().multiply(marketModel.getUsdRate()));

        priceView.setText("$"+ArithmeticUtils.multiply(marketModel.getClose().toPlainString(),marketModel.getUsdRate().toPlainString()).stripTrailingZeros().toPlainString());


        int i = marketModel.getChange().compareTo(BigDecimal.ZERO);
        if (i == -1) {
            //red
            stateView.setBackgroundResource(R.color._FFFC6767);
            stateView.setTextColor(context.getResources().getColor(R.color.white));
//            stateView.setText(marketModel.getChange().multiply(new BigDecimal(100)).stripTrailingZeros()+"%");
            stateView.setText(ArithmeticUtils.multiply(marketModel.getChg().toPlainString(),"100").stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).toPlainString()+"%");




        } else {
            //green
            stateView.setBackgroundResource(R.color._01FCDA);
            stateView.setTextColor(context.getResources().getColor(R.color._333333));
//            stateView.setText("+"+marketModel.getChange().multiply(new BigDecimal(100)).stripTrailingZeros()+"%");
            stateView.setText("+"+ArithmeticUtils.multiply(marketModel.getChg().toPlainString(),"100").stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).toPlainString()+"%");
        }



    }
}
