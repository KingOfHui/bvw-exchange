package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.TradeZfResponse;
import com.darknet.bvw.util.ArithmeticUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class MarketZfAdapter extends BAdapter<TradeZfResponse.ZfModel> {

    private Context context;

    public MarketZfAdapter(Context context, List<TradeZfResponse.ZfModel> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_coins_zf;
    }


    @Override
    public void onInitView(View view, int position) {

        TradeZfResponse.ZfModel marketModel = getList().get(position);
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
//        priceView.setText(""+marketModel.getClose().multiply(marketModel.getUsdRate()));

        priceView.setText("$" + ArithmeticUtils.multiply(marketModel.getClose().toPlainString(), marketModel.getUsdRate().toPlainString()).stripTrailingZeros().toPlainString());


        int i = marketModel.getChg().compareTo(BigDecimal.ZERO);
        if (i == -1) {
            //red
            stateView.setBackgroundResource(R.drawable.common_red_bg);
            stateView.setTextColor(context.getResources().getColor(R.color.white));
//            stateView.setText(marketModel.getChange().stripTrailingZeros().multiply(new BigDecimal(100)).stripTrailingZeros()+"%");


            stateView.setText(ArithmeticUtils.multiply(marketModel.getChg().toPlainString(), "100").stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).toPlainString() + "%");
        } else {
            //green
            stateView.setBackgroundResource(R.drawable.common_green_bg);
            stateView.setTextColor(context.getResources().getColor(R.color.white));
            BigDecimal BAI = new BigDecimal(BigInteger.valueOf(100), 0);
            int v = marketModel.getChg().compareTo(BAI);
            if (v == 0) {
                stateView.setText("+100%");
            } else {
                stateView.setText("+" + ArithmeticUtils.multiply(marketModel.getChg().toPlainString(), "100").stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).toPlainString() + "%");
            }
//            stateView.setText("+"+marketModel.getChange().stripTrailingZeros().multiply(new BigDecimal(100)).stripTrailingZeros()+"%");
        }


//        moneyType.setText(marketModel.getSymbol());

    }
}
