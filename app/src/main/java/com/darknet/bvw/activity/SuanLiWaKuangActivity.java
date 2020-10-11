package com.darknet.bvw.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.SuanLiResponse;
import com.darknet.bvw.util.ArithmeticUtils;
import com.darknet.bvw.util.TimeUtil;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import java.text.DecimalFormat;

import java.math.BigDecimal;

public class SuanLiWaKuangActivity extends BaseActivity {

    private RelativeLayout backView;
    private TextView titleView;
    private RelativeLayout suanliListView;


    private TextView headerOneView;
    private TextView headerTwoView;
    private TextView headerThreeView;
    private TextView headerFourView;


    private TextView itemTimeView;

    private TextView itemOneView;
    private TextView itemTwoView;
    private TextView itemThreeView;
    private TextView itemFourView;
    private TextView itemFiveView;
    private TextView itemSixView;

    private TextView totalOneView;
    private TextView totalTwoView;
//
    private LinearLayout firstLayout;


    @Override
    public void initView() {
        backView = (RelativeLayout) this.findViewById(R.id.layBack);
        titleView = (TextView) this.findViewById(R.id.title);

        headerOneView = (TextView) this.findViewById(R.id.suanli_one_view);
        headerTwoView = (TextView) this.findViewById(R.id.suanli_two_view);
        headerThreeView = (TextView) this.findViewById(R.id.suanli_three_view);
        headerFourView = (TextView) this.findViewById(R.id.suanli_four_view);

        itemOneView = (TextView) this.findViewById(R.id.suanli_item_one_view);
        itemTwoView = (TextView) this.findViewById(R.id.suanli_item_two_view);
        itemThreeView = (TextView) this.findViewById(R.id.suanli_item_three_view);
        itemFourView = (TextView) this.findViewById(R.id.suanli_item_four_view);
        itemFiveView = (TextView) this.findViewById(R.id.suanli_item_five_view);
        itemSixView = (TextView) this.findViewById(R.id.suanli_item_six_view);

        itemTimeView = (TextView) this.findViewById(R.id.suanli_item_time_view);

        totalOneView = (TextView) this.findViewById(R.id.suanli_item_bom_one_view);
        totalTwoView = (TextView) this.findViewById(R.id.suanli_item_bom_two_view);

        firstLayout = (LinearLayout)this.findViewById(R.id.sunli_first_item_layout);


        itemTimeView.setText(TimeUtil.getYesDay()+" ");

        suanliListView = (RelativeLayout) this.findViewById(R.id.suanli_midle_layout);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleView.setText(getString(R.string.suanli_wakuang_title));

        suanliListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent suanliListIntent = new Intent(SuanLiWaKuangActivity.this, SuanLiListActivity.class);
                startActivity(suanliListIntent);

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_suanli;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getData();
    }

    @Override
    public void configViews() {

    }


    private void getData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.SUANLI_FIRST_URL)
                .tag(SuanLiWaKuangActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    SuanLiResponse response = gson.fromJson(backVal, SuanLiResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setSunLiData(response.getData());
                                        } else {
                                            Toast.makeText(SuanLiWaKuangActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }


    private void setSunLiData(SuanLiResponse.SunLiData sunLiData) {

        try {

            headerOneView.setText(sunLiData.getPower().stripTrailingZeros().toPlainString()+"kH/S");
//        headerTwoView.setText(sunLiData.getPowerRate().stripTrailingZeros().toPlainString()+"%");

            headerTwoView.setText(ArithmeticUtils.multiply(sunLiData.getPowerRate().toPlainString(),"100").stripTrailingZeros().setScale(2, BigDecimal.ROUND_DOWN).toPlainString()+"%");


            headerThreeView.setText(sunLiData.getSumPower().stripTrailingZeros().toPlainString()+"kH/S");
            headerFourView.setText(sunLiData.getSumBonus().stripTrailingZeros().toPlainString()+"BIW");

//        totalOneView.setText(sunLiData.getSumPower());
//        totalTwoView.setText(sunLiData.getSumBonus());

            if(sunLiData.getPowerHistoryList() != null && sunLiData.getPowerHistoryList().size() > 0){
                firstLayout.setVisibility(View.VISIBLE);
                setItemVal(sunLiData.getPowerHistoryList().get(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

//        else {
//            firstLayout.setVisibility(View.GONE);
//        }

    }

    private void setItemVal(SuanLiResponse.SunLiItemModel sunLiItemModel){
        itemTimeView.setText(sunLiItemModel.getDate()+" ");
        itemOneView.setText(sunLiItemModel.getHoldPower().stripTrailingZeros().toPlainString()+"kH/S");
        itemTwoView.setText(sunLiItemModel.getAdditionTimePower().stripTrailingZeros().toPlainString()+"kH/S");
        itemThreeView.setText(sunLiItemModel.getSignPower().stripTrailingZeros().toPlainString()+"kH/S");
        itemFourView.setText(sunLiItemModel.getLevelPower().stripTrailingZeros().toPlainString()+"kH/S");
        itemFiveView.setText(sunLiItemModel.getInviterPower().stripTrailingZeros().toPlainString()+"kH/S");
        itemSixView.setText(sunLiItemModel.getBidPower().stripTrailingZeros().toPlainString()+"kH/S");

        totalOneView.setText(sunLiItemModel.getDayPower().stripTrailingZeros().toPlainString()+"kH/S");
        totalTwoView.setText(sunLiItemModel.getDayBonus().stripTrailingZeros().toPlainString()+"BIW");

    }


}
