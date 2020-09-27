package com.darknet.bvw.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.JiangLiItemAdapter;
import com.darknet.bvw.adapter.LuckyItemAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.BidStateResponse;
import com.darknet.bvw.model.response.FenLieFirstResponse;
import com.darknet.bvw.model.response.FenLieSecResponse;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.model.response.LuckResponse;
import com.darknet.bvw.model.response.TopResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.view.MyListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.List;

public class BidJiangLiActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout backImgView;
    private TextView titleContentView;

    private LinearLayout topLayout;
    private LinearLayout luckyLayout;

    private TextView topContentView;
    private View topLineView;

    private TextView luckyContentView;
    private View luckyLineView;
//    private JiangLiItemAdapter jiangLiItemAdapter;

//    private AutoRunnableRecyclerView recyclerView;
//    private JiangLiItemTwoAdapter jiangLiItemTwoAdapter;

    // 0 为 top,1为lucky
    private int type = 0;

    private TextView bidStateView;

    private LinearLayout buyView;
    private String leftMoneyVal = "0";

    private MyListView myListView;
    private MyListView luckyListView;
    JiangLiItemAdapter jiangLiItemAdapter;
    LuckyItemAdapter luckyItemAdapter;


    private NestedScrollView scrollView;

    private TextView stateName;
    private TextView mingciName;


    private TextView totalBtcView;
    private TextView totalEthView;

    private TextView hisBtcView;
    private TextView hisEthView;

    private LinearLayout noListData;

    private TextView noDataContentView;

    @Override
    public void initView() {

        backImgView = (RelativeLayout) this.findViewById(R.id.layBack);
        titleContentView = (TextView) this.findViewById(R.id.title);

        topLayout = (LinearLayout) this.findViewById(R.id.jiangli_top_layout);
        luckyLayout = (LinearLayout) this.findViewById(R.id.jiangli_lucky_layout);

        topContentView = (TextView) this.findViewById(R.id.jiangli_top_content_view);
        topLineView = (View) this.findViewById(R.id.jiangli_top_line_view);

        luckyContentView = (TextView) this.findViewById(R.id.jiangli_lucky_content_view);
        luckyLineView = (View) this.findViewById(R.id.jiangli_lucky_line_view);

        bidStateView = (TextView) this.findViewById(R.id.jiangli_bid_state_view);
        buyView = (LinearLayout) this.findViewById(R.id.bid_info_buy_view);

        myListView = (MyListView) this.findViewById(R.id.jiangli_listview);
        luckyListView = (MyListView) this.findViewById(R.id.jiangli_lucky_listview);

        scrollView = (NestedScrollView) this.findViewById(R.id.jiangli_scroolview);


        stateName = (TextView) this.findViewById(R.id.bid_jiangli_header_two);
        mingciName = (TextView) this.findViewById(R.id.bid_jiangli_header_five);

        totalBtcView = (TextView) this.findViewById(R.id.bid_jiangli_total_btc_num);
        totalEthView = (TextView) this.findViewById(R.id.bid_jiangli_total_eth_num);

        hisBtcView = (TextView) this.findViewById(R.id.bid_jiangli_his_btc_num);
        hisEthView = (TextView) this.findViewById(R.id.bid_jiangli_his_eth_num);

        noListData = (LinearLayout) this.findViewById(R.id.bid_jiangli_no_list_data);

        noDataContentView = (TextView) this.findViewById(R.id.no_data_sign_content);


//        recyclerView  = (AutoRunnableRecyclerView) this.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));//设置LinearLayoutManager.HORIZONTAL  则水平滚动
        jiangLiItemAdapter = new JiangLiItemAdapter(BidJiangLiActivity.this);

        luckyItemAdapter = new LuckyItemAdapter(BidJiangLiActivity.this);

        luckyListView.setAdapter(luckyItemAdapter);
        myListView.setAdapter(jiangLiItemAdapter);


        backImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleContentView.setText(getString(R.string.bid_jiangli_title_sign));


        buyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent buyIntent = new Intent(BidJiangLiActivity.this, BidBuyActivity.class);
//                buyIntent.putExtra("leftVal", leftMoneyVal);
//                startActivity(buyIntent);
                Intent bidInfo = new Intent(BidJiangLiActivity.this, BidIntroActivity.class);
                startActivity(bidInfo);
            }
        });

        topLayout.setOnClickListener(this);
        luckyLayout.setOnClickListener(this);
        changeState(0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bid_jiangli;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
//        setData();
//        getLeftMoneyRequest();
        getPublicAddress();

        scrollView.scrollTo(0, 0);


        getHorindData();
        getTopData();

//        getLuckyData();
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jiangli_top_layout:
                type = 0;
                changeState(type);
                getTopData();
//                setData();
//                scrollView.scrollTo(0,0);
                break;
            case R.id.jiangli_lucky_layout:
                type = 1;
                changeState(type);
                getLuckyData();
//                setData();
                break;
        }
    }


    private void getHorindData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FenLie_SECOND_URL)
                .tag(BidJiangLiActivity.this)
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
                                            Toast.makeText(BidJiangLiActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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

    FenLieSecResponse.FenLieSendModel fenLieSendModel;

    private void setHorData(List<FenLieSecResponse.FenLieSendModel> data) {

        if (data == null || data.size() == 0) {
            return;
        }

        fenLieSendModel = data.get(0);

    }


    private void changeState(int type) {

        if (type == 0) {
            topContentView.setTextColor(getResources().getColor(R.color._01FCDA));
            luckyContentView.setTextColor(getResources().getColor(R.color.white));
            topLineView.setVisibility(View.VISIBLE);
            luckyLineView.setVisibility(View.GONE);
        } else {
            topContentView.setTextColor(getResources().getColor(R.color.white));
            luckyContentView.setTextColor(getResources().getColor(R.color._01FCDA));
            topLineView.setVisibility(View.GONE);
            luckyLineView.setVisibility(View.VISIBLE);
        }
    }

//    private void setData() {
//
//        List<String> valList = new ArrayList<>();
//
//
//        for (int i = 0; i < 10; i++) {
//            valList.add((i + 1) + "");
////            View itemLayout = (View) LayoutInflater.from(BidJiangLiActivity.this).inflate(R.layout.item_jiangli_content, null);
////            jiangliContentLayout.addView(itemLayout);
//        }
//
//
////        jiangLiItemTwoAdapter = new JiangLiItemTwoAdapter(BidJiangLiActivity.this);
////        recyclerView.setAdapter(jiangLiItemTwoAdapter);
////        recyclerView.start();
//
//        jiangLiItemAdapter.addData(valList);
//
//        scrollView.scrollTo(0, 0);
//    }


    //获取bid状态
    private void getPublicAddress() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FIND_BID_STATE_URL)
                .tag(BidJiangLiActivity.this)
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
                        dismissDialog();
                    }
                });
    }


    private void setStateVal(int stateVal) {
        if (stateVal == 0) {
            //未开通
            bidStateView.setText(getString(R.string.bid_info_sub_content));
        } else if (stateVal == 1) {
            //已开通
            bidStateView.setText(getString(R.string.bid_info_sub_content_two));
            buyView.setEnabled(false);
        } else if (stateVal == 2) {
            //开通中
            bidStateView.setText(getString(R.string.bid_info_sub_content_three));
            buyView.setEnabled(false);
        }
    }

    private void getLeftMoneyRequest() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(BidJiangLiActivity.this)
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
                    if (zcMoneyModel.getName().equalsIgnoreCase("BTW")) {
                        if (TextUtils.isEmpty(zcMoneyModel.getValue_qty()) || zcMoneyModel.getValue_qty().equals("0") || zcMoneyModel.getValue_qty().equals("0.000000")) {
//                            leftMoneyView.setText("  0 BTW");
                            leftMoneyVal = "0";
                        } else {
//                            leftMoneyView.setText("  " + zcMoneyModel.getValue_qty() + " BTW");
                            leftMoneyVal = zcMoneyModel.getValue_qty();
                        }
                    }
                }
            }
        }
    }


    private void getTopData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FENLIE_TOP_URL)
                .tag(BidJiangLiActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    TopResponse response = gson.fromJson(backVal, TopResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().size() > 0) {
                                            noListData.setVisibility(View.GONE);
                                            setTopData(response.getData());

                                        } else {
                                            noListData.setVisibility(View.VISIBLE);
                                            noDataContentView.setText(getResources().getString(R.string.bid_jiangli_no_data_top_sign));
//                                            Toast.makeText(BidJiangLiActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
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
                        noListData.setVisibility(View.VISIBLE);
                        noDataContentView.setText(getResources().getString(R.string.bid_jiangli_no_data_top_sign));
                    }
                });
    }


    private void getLuckyData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FENLIE_LUCKY_URL)
                .tag(BidJiangLiActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    LuckResponse response = gson.fromJson(backVal, LuckResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().size() > 0) {
                                            noListData.setVisibility(View.GONE);
                                            setLuckyData(response.getData());
                                        } else {
                                            noListData.setVisibility(View.VISIBLE);
                                            noDataContentView.setText(getResources().getString(R.string.bid_jiangli_no_data_lucky_sign));

//                                            Toast.makeText(BidJiangLiActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
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
                        noListData.setVisibility(View.VISIBLE);
                        noDataContentView.setText(getResources().getString(R.string.bid_jiangli_no_data_lucky_sign));
                    }
                });
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
                .tag(BidJiangLiActivity.this)
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
                                            setStateData(response.getData());
                                        } else {
//                                            Toast.makeText(BidJiangLiActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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


    private void setTopData(List<TopResponse.TopModel> data) {


        myListView.setVisibility(View.VISIBLE);
        luckyListView.setVisibility(View.GONE);
        Log.e("topdata", "size=" + data.size());
        jiangLiItemAdapter.addData(data);

    }


    private void setLuckyData(List<LuckResponse.LuckModel> data) {

        myListView.setVisibility(View.GONE);
        luckyListView.setVisibility(View.VISIBLE);
        Log.e("topdata", "size=" + data.size());
        luckyItemAdapter.addData(data);

    }


    private void setStateData(FenLieFirstResponse.FenLieFirstData fenLieFirstData) {


        if (fenLieSendModel.getStatus() == 1) {

            mingciName.setText(fenLieFirstData.getHeight());

            int lanType = SPUtil.getInstance(this).getSelectLanguage();
            if (lanType == 0) {
                //英文
                stateName.setText(fenLieFirstData.getStage_name_en());
            } else if (lanType == 1) {
                //中文
                stateName.setText(fenLieFirstData.getStage_name());
            } else {
                stateName.setText(fenLieFirstData.getStage_name_en());
            }
        } else {
            mingciName.setText(fenLieFirstData.getNext_stage_height());

            int lanType = SPUtil.getInstance(this).getSelectLanguage();
            if (lanType == 0) {
                //英文
                stateName.setText(fenLieFirstData.getNext_stage_tip_en());
            } else if (lanType == 1) {
                //中文
                stateName.setText(fenLieFirstData.getNext_stage_tip_cn());
            } else {
                stateName.setText(fenLieFirstData.getNext_stage_tip_en());
            }
        }


//        int lanType = SPUtil.getInstance(this).getSelectLanguage();
//
//        if (lanType == 1) {
//            //英文
//            stateName.setText(fenLieFirstData.getStage_name_en());
//        } else if (lanType == 0) {
//            //中文
//            stateName.setText(fenLieFirstData.getStage_name());
//        } else {
//            stateName.setText(fenLieFirstData.getStage_name_en());
//        }
//
//
//        mingciName.setText(fenLieFirstData.getNext_stage_height());


        totalBtcView.setText(fenLieFirstData.getBtc().stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).doubleValue() + "");
        totalEthView.setText(fenLieFirstData.getEth().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString());

        hisBtcView.setText(fenLieFirstData.getBtc_total().stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).doubleValue() + "");
        hisEthView.setText(fenLieFirstData.getEth_total().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString());


    }

}
