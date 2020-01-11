package com.darknet.bvw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.CoinsModel;
import com.darknet.bvw.util.ArithmeticUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class CoinsAdapter extends BAdapter<CoinsModel.DataBean> {


    public CoinsAdapter(Context context, List<CoinsModel.DataBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_coins_type;
    }

    @Override
    public void onInitView(View view, int position) {

        TextView moneyType = view.findViewById(R.id.money_type_view);
        TextView symbleTv = view.findViewById(R.id.symble_tv);
        TextView priceTv = view.findViewById(R.id.price_tv);
        CoinsModel.DataBean coinsModel = getList().get(position);
        moneyType.setText(coinsModel.getBase_token_symbol());
        symbleTv.setText(coinsModel.getQuote_token_symbol());

        priceTv.setText(coinsModel.getThumb().getCloseStr());
        BigDecimal bigDecimal = ArithmeticUtils.minus(coinsModel.getThumb().getClose(), coinsModel.getThumb().getLastDayClose());
        if (bigDecimal.toBigInteger().compareTo(BigInteger.ZERO) < -1) {
            //小于0  红色
            priceTv.setTextColor(Color.parseColor("#41b37d"));
        } else {
            //大于0  绿色
            priceTv.setTextColor(Color.parseColor("#17b975"));
        }
    }
}
