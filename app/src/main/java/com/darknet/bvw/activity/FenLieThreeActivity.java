package com.darknet.bvw.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.Entity.ZcMoneyModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.db.ZcDaoUtils;
import com.darknet.bvw.model.response.FenLieFirstResponse;
import com.darknet.bvw.model.response.FenLieScrollviewResponse;
import com.darknet.bvw.model.response.FenLieSecResponse;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.view.HorProBar;
import com.darknet.bvw.view.HorProBarFour;
import com.darknet.bvw.view.HorProBarThree;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sunfusheng.marqueeview.MarqueeView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class FenLieThreeActivity extends BaseActivity {


    private RelativeLayout backImg;
    private TextView titleContentView;
    private TextView jumpFenLieView;


    private TextView headerOneView;
    private TextView headerTwoView;


    private TextView middleOneView;
    private TextView middleTwoView;
    private TextView middleThreeView;
    private TextView middleFourView;
    private TextView middleFiveView;


    private TextView histoyOneView;
    private TextView histoyTwoView;
    private TextView histoyThreeView;
    private TextView histoyFourView;
    private TextView histoyFiveView;

    private TextView leftMoneyView;

    private HorProBarFour horProBarOne;
    private HorProBarThree horProBarTWo;
    private HorProBarThree horProBarThree;
    private HorProBarThree horProBarFour;
    private HorProBarThree horProBarFive;
    private HorProBarThree horProBarSix;


    private FenLieSecResponse.FenLieSendModel fenLieSendModel;

    private String btcVal;
    private String btcLfVal;
    private String ethVal;
    private String ethLfVal;

    private MarqueeView marqueeView;

    private TextView stateView;

    private TextView headerTotalMoenyView;

    private LinearLayout headerTotalLayout;

    @Override
    public void initView() {

        backImg = (RelativeLayout) this.findViewById(R.id.layBack);
        titleContentView = (TextView) this.findViewById(R.id.title);
        jumpFenLieView = (TextView) this.findViewById(R.id.fenlie_jump_two_view);


        headerOneView = (TextView) this.findViewById(R.id.fenlie_header_one_view);
        headerTwoView = (TextView) this.findViewById(R.id.fenlie_header_two_view);


        middleOneView = (TextView) this.findViewById(R.id.fenlie_middle_one_view);
        middleTwoView = (TextView) this.findViewById(R.id.fenlie_middle_two_view);
        middleThreeView = (TextView) this.findViewById(R.id.fenlie_middle_three_view);
        middleFourView = (TextView) this.findViewById(R.id.fenlie_middle_four_view);
        middleFiveView = (TextView) this.findViewById(R.id.fenlie_middle_five_view);


        histoyOneView = (TextView) this.findViewById(R.id.fenlie_middle_history_one_view);
        histoyTwoView = (TextView) this.findViewById(R.id.fenlie_middle_history_two_view);
        histoyThreeView = (TextView) this.findViewById(R.id.fenlie_middle_history_three_view);
        histoyFourView = (TextView) this.findViewById(R.id.fenlie_middle_history_four_view);
        histoyFiveView = (TextView) this.findViewById(R.id.fenlie_middle_history_five_view);

        headerTotalLayout = (LinearLayout) this.findViewById(R.id.fenlie_header_total_layout);

        headerTotalMoenyView = (TextView) this.findViewById(R.id.fenlie_header_total_money_view);

        stateView = (TextView) this.findViewById(R.id.fenlie_state_view);

        leftMoneyView = (TextView) this.findViewById(R.id.fenlie_left_money_view);

        marqueeView = (MarqueeView) this.findViewById(R.id.marqueeView);


//

        horProBarOne = (HorProBarFour) this.findViewById(R.id.fenlie_baifenbi_one_view);
        horProBarOne.setNeedDrawProgressText(true);
        horProBarTWo = (HorProBarThree) this.findViewById(R.id.fenlie_baifenbi_two_view);
        horProBarThree = (HorProBarThree) this.findViewById(R.id.fenlie_baifenbi_three_view);
        horProBarFour = (HorProBarThree) this.findViewById(R.id.fenlie_baifenbi_four_view);
        horProBarFive = (HorProBarThree) this.findViewById(R.id.fenlie_baifenbi_five_view);
        horProBarSix = (HorProBarThree) this.findViewById(R.id.fenlie_baifenbi_six_view);


        titleContentView.setText(getResources().getString(R.string.fenlie_order_header_title_sign));

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jumpFenLieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (fenLieSendModel == null) {
                    Intent jumpIntent = new Intent(FenLieThreeActivity.this, FindFenLieActivity.class);
                    jumpIntent.putExtra("btcBvw", "0");
                    jumpIntent.putExtra("ethBvw", "0");

                    jumpIntent.putExtra("btcVal", btcVal);
                    jumpIntent.putExtra("btcLfVal", btcLfVal);
                    jumpIntent.putExtra("ethVal", ethVal);
                    jumpIntent.putExtra("ethLfVal", ethLfVal);
                    jumpIntent.putExtra("model", (Serializable) fenLieSendModel);
                    startActivity(jumpIntent);
                } else {
                    Intent jumpIntent = new Intent(FenLieThreeActivity.this, FindFenLieActivity.class);
                    jumpIntent.putExtra("btcBvw", fenLieSendModel.getBtc_bvw() + "");
                    jumpIntent.putExtra("ethBvw", fenLieSendModel.getEth_bvw() + "");

                    jumpIntent.putExtra("btcVal", btcVal);
                    jumpIntent.putExtra("btcLfVal", btcLfVal);
                    jumpIntent.putExtra("ethVal", ethVal);
                    jumpIntent.putExtra("ethLfVal", ethLfVal);
                    jumpIntent.putExtra("model", (Serializable) fenLieSendModel);
                    startActivity(jumpIntent);
                }


//                if (fenLieSendModel != null) {
//                    if (fenLieSendModel.getStatus() == 0) {
//                        Toast.makeText(FenLieThreeActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
////                        Intent jumpIntent = new Intent(FenLieThreeActivity.this, FindFenLieActivity.class);
////                        jumpIntent.putExtra("btcBvw",fenLieSendModel.getBtc_bvw()+"");
////                        jumpIntent.putExtra("ethBvw",fenLieSendModel.getEth_bvw()+"");
////
////                        jumpIntent.putExtra("btcVal",btcVal);
////                        jumpIntent.putExtra("btcLfVal",btcLfVal);
////                        jumpIntent.putExtra("ethVal",ethVal);
////                        jumpIntent.putExtra("ethLfVal",ethLfVal);
////                        startActivity(jumpIntent);
//                    } else if (fenLieSendModel.getStatus() == 1) {
//
////                        btcVal = fenLieData.getBtc_left().toString();
////                        btcLfVal = fenLieData.getBtc_fl().toString();
////                        ethVal = fenLieData.getEth_left().toString();
////                        ethLfVal = fenLieData.getEth_fl().toString();
//
//                        Intent jumpIntent = new Intent(FenLieThreeActivity.this, FindFenLieActivity.class);
//                        jumpIntent.putExtra("btcBvw",fenLieSendModel.getBtc_bvw()+"");
//                        jumpIntent.putExtra("ethBvw",fenLieSendModel.getEth_bvw()+"");
//
//                        jumpIntent.putExtra("btcVal",btcVal);
//                        jumpIntent.putExtra("btcLfVal",btcLfVal);
//                        jumpIntent.putExtra("ethVal",ethVal);
//                        jumpIntent.putExtra("ethLfVal",ethLfVal);
//                        startActivity(jumpIntent);
//
////                        finish();
//                    } else if (fenLieSendModel.getStatus() == 2) {
//                        Toast.makeText(FenLieThreeActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(FenLieThreeActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(FenLieThreeActivity.this, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fenlie_three;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getHorindData();
        getLeftMoneyRequest();
        getScrollData();
//        showLeftMoney();
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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_FIRST_URL)
                .tag(FenLieThreeActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    FenLieFirstResponse response = gson.fromJson(backVal, FenLieFirstResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setFenLieData(response.getData());
                                        } else {
                                            Toast.makeText(FenLieThreeActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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


    private void getHorindData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_SECOND_URL)
                .tag(FenLieThreeActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    FenLieSecResponse response = gson.fromJson(backVal, FenLieSecResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().size() > 0) {
                                            setHorData(response.getData());
                                        } else {
                                            Toast.makeText(FenLieThreeActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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
                        getData();
                    }
                });
    }


    private void getScrollData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_SCROLLVIEW_URL)
                .tag(FenLieThreeActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    FenLieScrollviewResponse response = gson.fromJson(backVal, FenLieScrollviewResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null) {
                                            setScrollData(response.getData());
                                        } else {
                                            Toast.makeText(FenLieThreeActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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


    private void setScrollData(List<String> scrollData) {
//        List<String> info = new ArrayList<>();
//        info.add("通知：   11111111111111");
//        info.add("通知：   22222222222222");
//        info.add("通知：    33333333333333");
//        info.add("通知：    44444444444444");
//        info.add("通知：    55555555555555");
//        info.add("通知：    66666666666666");
        marqueeView.startWithList(scrollData);
        // 在代码里设置自己的动画
        marqueeView.startWithList(scrollData, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }


    private void setFenLieData(FenLieFirstResponse.FenLieFirstData fenLieData) {


//        headerOneView.setText(fenLieData.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
//        headerTwoView.setText(fenLieData.getHeight());
//        stateView.setText(getResources().getString(R.string.fenlie_header_one_one)+fenLieData.getStage()+getResources().getString(R.string.fenlie_header_one_one_one));


        setHeaderData(fenLieData);

        middleOneView.setText(fenLieData.getBvw_left().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");

//        System.out.println(new BigDecimal(0.293432).setScale(1, BigDecimal.ROUND_DOWN).doubleValue());// 0.2
//
//        System.out.println(new BigDecimal(10.293432).setScale(0, BigDecimal.ROUND_DOWN));// 0.2


        middleTwoView.setText(fenLieData.getBtc().setScale(1, BigDecimal.ROUND_DOWN).doubleValue() + "");

        middleThreeView.setText(fenLieData.getEth().setScale(0, BigDecimal.ROUND_DOWN) + "");

        middleFourView.setText(fenLieData.getBtc_left().setScale(1, BigDecimal.ROUND_DOWN).doubleValue() + "");
        middleFiveView.setText(fenLieData.getEth_left().setScale(0, BigDecimal.ROUND_DOWN) + "");


        histoyOneView.setText(fenLieData.getBvw_destroy().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
        histoyTwoView.setText(fenLieData.getBtc_total().stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).doubleValue() + "BTC");
        histoyThreeView.setText(fenLieData.getEth_total().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "ETH");
        histoyFourView.setText(fenLieData.getBvw_fl().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).doubleValue() + "BTW");
        histoyFiveView.setText(fenLieData.getEth_fl().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "ETH");

        btcVal = fenLieData.getBtc_left().toString();
        btcLfVal = fenLieData.getBtc_fl().toString();
        ethVal = fenLieData.getEth_left().toString();
        ethLfVal = fenLieData.getEth_fl().toString();

    }


    private void setHeaderData(FenLieFirstResponse.FenLieFirstData fenLieData) {

        if (fenLieSendModel != null) {

            if (fenLieSendModel.getStatus() == 1) {

                headerOneView.setText(fenLieData.getHeight());
                headerTwoView.setText(fenLieData.getStage_scale());
                int lanType = SPUtil.getInstance(this).getSelectLanguage();
                if (lanType == 0) {
                    //英文
                    stateView.setText(fenLieData.getStage_name_en());
                } else if (lanType == 1) {
                    //中文
                    stateView.setText(fenLieData.getStage_name());
                } else {
                    stateView.setText(fenLieData.getStage_name_en());
                }
                headerTotalLayout.setVisibility(View.GONE);
                headerTotalMoenyView.setText(fenLieData.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
            } else {
                headerOneView.setText(fenLieData.getNext_stage_height());
                headerTwoView.setText(fenLieData.getNext_stage_scale());
                int lanType = SPUtil.getInstance(this).getSelectLanguage();
//                headerTotalLayout.setVisibility(View.GONE);
                if (lanType == 0) {
                    //英文
                    stateView.setText(fenLieData.getNext_stage_tip_en());
                } else if (lanType == 1) {
                    //中文
                    stateView.setText(fenLieData.getNext_stage_tip_cn());
                } else {
                    stateView.setText(fenLieData.getNext_stage_tip_en());
                }

                headerTotalLayout.setVisibility(View.GONE);
                headerTotalMoenyView.setText(fenLieData.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");

            }


        }
    }


    private String btcLeftVal = "0";
    private String ethLeftVal = "0";

    private void showLeftMoney() {

        List<ZcMoneyModel> allZcMoneys = ZcDaoUtils.getAllZcData();

        for (int k = 0; k < allZcMoneys.size(); k++) {
            ZcMoneyModel tempZcDel = allZcMoneys.get(k);
            if (tempZcDel.getSymbol().equalsIgnoreCase("BTC")) {
                btcLeftVal = tempZcDel.getValue_qty();
            }

            if (tempZcDel.getSymbol().equalsIgnoreCase("ETH")) {
                ethLeftVal = tempZcDel.getValue_qty();
            }
//            MoneyModel moneyModel = new MoneyModel();
//            moneyModel.setAddress(tempZcDel.getAddress());
//            moneyModel.setAssetref(tempZcDel.getAssetref());
//            moneyModel.setIcon(tempZcDel.getIcon());
//            moneyModel.setPrice(tempZcDel.getPrice());
//            moneyModel.setValue_qty(tempZcDel.getValue_qty());
//            moneyModel.setSymbol(tempZcDel.getSymbol());
//            moneyModel.setValue_cny(new BigDecimal(tempZcDel.getValue_cny()));
//            moneyModel.setValue_usd(new BigDecimal(tempZcDel.getValue_usd()));
//            moneyModel.setChain_deposit_address(tempZcDel.getChain_deposit_address());
        }


//        leftMoneyView.setText(" BTC：" + btcLeftVal + "    " + "ETH：" + new BigDecimal(ethLeftVal).stripTrailingZeros().setScale(3, BigDecimal.ROUND_DOWN).doubleValue());

    }


    private void setHorData(List<FenLieSecResponse.FenLieSendModel> data) {

        if (data == null) {
            return;
        }

        fenLieSendModel = data.get(0);

        FenLieSecResponse.FenLieSendModel oneModel = data.get(0);
        FenLieSecResponse.FenLieSendModel twoModel = data.get(1);
        FenLieSecResponse.FenLieSendModel threeModel = data.get(2);
        FenLieSecResponse.FenLieSendModel fourModel = data.get(3);
        FenLieSecResponse.FenLieSendModel fiveModel = data.get(4);
        FenLieSecResponse.FenLieSendModel sixModel = data.get(5);

        horProBarOne.setFirstText("1:" + oneModel.getBtc_bvw().intValue());
        horProBarOne.setSecondText("1:" + oneModel.getEth_bvw().intValue());
        horProBarOne.setLastText(oneModel.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");


        int bvwflval = oneModel.getBvw_fl().intValue();
        int bvwval = oneModel.getBvw().intValue();

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) bvwflval / (float) bvwval * 100);


//        String oneProgressVal = (oneModel.getBvw_fl().divide(oneModel.getBvw())).multiply(new BigDecimal(100)).toString();


        float oneFloat = Float.valueOf(result);
//        oneFloat = 0;
        if (oneFloat == 0) {
//            horProBarOne.setExcetionTime(1);
            horProBarOne.setCurrentProgress(0);

        } else {
            horProBarOne.setCurrentProgress(oneFloat);
        }


//        Log.e("fenlieval", "oneval=" + oneModel.getBtc_bvw().intValue() + ";twoval=" + oneModel.getEth_bvw().intValue() + ";total=" + oneModel.getBvw() + "；bi=" + oneFloat);
//        horProBarOne.setShowPercent(1);

        horProBarTWo.setFirstText("1:" + twoModel.getBtc_bvw().intValue());
        horProBarTWo.setSecondText("1:" + twoModel.getEth_bvw().intValue());
        horProBarTWo.setLastText(twoModel.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
//        horProBarTWo.setCurrentProgress(40);
//        horProBarTWo.setShowPercent(0);

        horProBarThree.setFirstText("1:" + threeModel.getBtc_bvw().intValue());
        horProBarThree.setSecondText("1:" + threeModel.getEth_bvw().intValue());
        horProBarThree.setLastText(threeModel.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
//        horProBarThree.setCurrentProgress(40);
//        horProBarThree.setShowPercent(0);


        horProBarFour.setFirstText("1:" + fourModel.getBtc_bvw().intValue());
        horProBarFour.setSecondText("1:" + fourModel.getEth_bvw().intValue());
        horProBarFour.setLastText(fourModel.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
//        horProBarFour.setCurrentProgress(40);
//        horProBarFour.setShowPercent(0);

        horProBarFive.setFirstText("1:" + fiveModel.getBtc_bvw().intValue());
        horProBarFive.setSecondText("1:" + fiveModel.getEth_bvw().intValue());
        horProBarFive.setLastText(fiveModel.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");
//        horProBarFive.setCurrentProgress(40);
//        horProBarFive.setShowPercent(0);

        horProBarSix.setFirstText("1:" + sixModel.getBtc_bvw().intValue());
        horProBarSix.setSecondText("1:" + sixModel.getEth_bvw().intValue());
        horProBarSix.setLastText(sixModel.getBvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "BTW");

    }


    private void getLeftMoneyRequest() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(FenLieThreeActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                try {
                                    Gson gson = new Gson();
                                    LeftMoneyResponse response = gson.fromJson(backVal, LeftMoneyResponse.class);
                                    if (response != null && response.getCode() == 0 && response.getData() != null && response.getData().size() > 0) {
                                        getLeftMoney(response.getData());
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

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }


    private void getLeftMoney(List<LeftMoneyResponse.LeftMoneyModel> allMoney) {
//        List<ZcMoneyModel> allMoney = ZcDaoUtils.getAllZcData();


        if (allMoney != null && allMoney.size() > 0) {
            for (int i = 0; i < allMoney.size(); i++) {
                LeftMoneyResponse.LeftMoneyModel zcMoneyModel = allMoney.get(i);
                if (!TextUtils.isEmpty(zcMoneyModel.getName())) {
                    if (zcMoneyModel.getName().equalsIgnoreCase("BTC")) {
                        if (TextUtils.isEmpty(zcMoneyModel.getValue_qty()) || zcMoneyModel.getValue_qty().equals("0") || zcMoneyModel.getValue_qty().equals("0.000000")) {
//                            leftMoneyView.setText("  0 BTW");
                            btcLeftVal = "0";
                        } else {
//                            leftMoneyView.setText("  " + zcMoneyModel.getValue_qty() + " BTW");
                            btcLeftVal = zcMoneyModel.getValue_qty();
                        }
                    } else if (zcMoneyModel.getName().equalsIgnoreCase("ETH")) {
                        if (TextUtils.isEmpty(zcMoneyModel.getValue_qty()) || zcMoneyModel.getValue_qty().equals("0") || zcMoneyModel.getValue_qty().equals("0.000000")) {
//                            leftMoneyView.setText("  0 BTW");
                            ethLeftVal = "0";
                        } else {
//                            leftMoneyView.setText("  " + zcMoneyModel.getValue_qty() + " BTW");
                            ethLeftVal = zcMoneyModel.getValue_qty();
                        }
                    }
                }
            }
        }

        leftMoneyView.setText(" BTC：" + btcLeftVal + "    " + "ETH：" + new BigDecimal(ethLeftVal).stripTrailingZeros().setScale(3, BigDecimal.ROUND_DOWN).doubleValue());


    }

}
