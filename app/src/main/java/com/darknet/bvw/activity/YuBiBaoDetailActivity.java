package com.darknet.bvw.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.request.AddYuBiRequest;
import com.darknet.bvw.model.request.TradeListRequest;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.model.response.FenLieFirstResponse;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.model.response.YuBiResponse;
import com.darknet.bvw.util.TimeUtil;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.BottomDialogView;
import com.darknet.bvw.view.CommonInputPwdDialogView;
import com.darknet.bvw.view.FailDialogybView;
import com.darknet.bvw.view.SuccessDialogybView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class YuBiBaoDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView titleContent;

    private RelativeLayout backLayout;


    private TextView bvwOne, bvwTwo, bvwThree, bvwFour, bvwFive;
    private TextView timeOne, timeTwo, timeThree;

    private TextView joinView;

    private String leftMoneyVal = "0";

    // 0 代表 3001，1代表10001，2代表50000
    private int bvwType = 0;
    //0代表 6 ，1代表 12，2代表24
    private int timeType = 0;

    private LinearLayout ybContentLayout;
    private LinearLayout ybContentListLayout;

    @Override
    public void initView() {

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
        titleContent.setText(getString(R.string.yb_title_val));

        bvwOne = (TextView) this.findViewById(R.id.yb_bvw_one);
        bvwTwo = (TextView) this.findViewById(R.id.yb_bvw_two);
        bvwThree = (TextView) this.findViewById(R.id.yb_bvw_three);
        bvwFour = (TextView) this.findViewById(R.id.yb_bvw_four);
        bvwFive = (TextView) this.findViewById(R.id.yb_bvw_five);

        timeOne = (TextView) this.findViewById(R.id.yb_time_one);
        timeTwo = (TextView) this.findViewById(R.id.yb_time_two);
        timeThree = (TextView) this.findViewById(R.id.yb_time_three);
        joinView = (TextView) this.findViewById(R.id.ybb_josin_view);

        ybContentLayout = (LinearLayout) this.findViewById(R.id.yb_content_layout);
        ybContentListLayout = (LinearLayout) this.findViewById(R.id.yb_content_list);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bvwOne.setOnClickListener(this);
        bvwTwo.setOnClickListener(this);
        bvwThree.setOnClickListener(this);
        bvwFour.setOnClickListener(this);
        bvwFive.setOnClickListener(this);

        timeOne.setOnClickListener(this);
        timeTwo.setOnClickListener(this);
        timeThree.setOnClickListener(this);
        joinView.setOnClickListener(this);

        changeBvwState();
        changeTimeState();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yubibao;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getListData();
        getLeftMoneyRequest();
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yb_bvw_one:
                bvwType = 0;
                changeBvwState();
                break;
            case R.id.yb_bvw_two:
                bvwType = 1;
                changeBvwState();
                break;
            case R.id.yb_bvw_three:
                bvwType = 2;
                changeBvwState();
                break;
            case R.id.yb_bvw_four:
                bvwType = 3;
                changeBvwState();
                break;
            case R.id.yb_bvw_five:
                bvwType = 4;
                changeBvwState();
                break;
            case R.id.yb_time_one:
                timeType = 0;
                changeTimeState();
                break;
            case R.id.yb_time_two:
                timeType = 1;
                changeTimeState();
                break;
            case R.id.yb_time_three:
                timeType = 2;
                changeTimeState();
                break;
            case R.id.ybb_josin_view:


                if (new BigDecimal(leftMoneyVal).compareTo(BigDecimal.ZERO) == 0) {
                    Toast.makeText(YuBiBaoDetailActivity.this, getString(R.string.yb_notice_later_try), Toast.LENGTH_SHORT).show();
                } else {
                    BottomDialogView.Builder builder = new BottomDialogView.Builder(YuBiBaoDetailActivity.this);

                    View dialogView = (View) LayoutInflater.from(YuBiBaoDetailActivity.this).inflate(R.layout.dialog_pwd_input_two, null);

                    builder.setContentView(dialogView);
                    BottomDialogView bottomDialog = builder.create();
                    bottomDialog.show();

                    ImageView closeDialog = (ImageView) dialogView.findViewById(R.id.close_dialog_two_view);
                    EditText contentView = (EditText) dialogView.findViewById(R.id.common_content);
                    Button sureBtn = (Button) dialogView.findViewById(R.id.wallet_select_create_view);

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomDialog.dismiss();
                            hintKeyBoard();
                        }
                    });


                    sureBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String contentVal = contentView.getText().toString();
                            if (null == contentVal || contentVal.length() == 0) {
                                Toast.makeText(YuBiBaoDetailActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
                                if (contentVal.equals(walletModel.getPassword())) {
                                    hintKeyBoard();
                                    bottomDialog.dismiss();
                                    sendYuBi();
                                } else {
                                    Toast.makeText(YuBiBaoDetailActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                break;
        }
    }


    private void changeBvwState() {
        if (bvwType == 0) {
            bvwOne.setBackgroundResource(R.drawable.green_bg_two);
            bvwTwo.setBackgroundResource(R.drawable.deep_green_slide);
            bvwThree.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFour.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFive.setBackgroundResource(R.drawable.deep_green_slide);

            bvwOne.setTextColor(getResources().getColor(R.color.black));
            bvwTwo.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwThree.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFour.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFive.setTextColor(getResources().getColor(R.color._FF01FCDA));
        } else if (bvwType == 1) {

            bvwOne.setBackgroundResource(R.drawable.deep_green_slide);
            bvwTwo.setBackgroundResource(R.drawable.green_bg_two);
            bvwThree.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFour.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFive.setBackgroundResource(R.drawable.deep_green_slide);

            bvwOne.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwTwo.setTextColor(getResources().getColor(R.color.black));
            bvwThree.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFour.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFive.setTextColor(getResources().getColor(R.color._FF01FCDA));
        } else if (bvwType == 2) {
            bvwOne.setBackgroundResource(R.drawable.deep_green_slide);
            bvwTwo.setBackgroundResource(R.drawable.deep_green_slide);
            bvwThree.setBackgroundResource(R.drawable.green_bg_two);
            bvwFour.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFive.setBackgroundResource(R.drawable.deep_green_slide);

            bvwOne.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwTwo.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwThree.setTextColor(getResources().getColor(R.color.black));
            bvwFour.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFive.setTextColor(getResources().getColor(R.color._FF01FCDA));
        } else if (bvwType == 3) {
            bvwOne.setBackgroundResource(R.drawable.deep_green_slide);
            bvwTwo.setBackgroundResource(R.drawable.deep_green_slide);
            bvwThree.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFour.setBackgroundResource(R.drawable.green_bg_two);
            bvwFive.setBackgroundResource(R.drawable.deep_green_slide);

            bvwOne.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwTwo.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwThree.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFour.setTextColor(getResources().getColor(R.color.black));
            bvwFive.setTextColor(getResources().getColor(R.color._FF01FCDA));
        } else if (bvwType == 4) {
            bvwOne.setBackgroundResource(R.drawable.deep_green_slide);
            bvwTwo.setBackgroundResource(R.drawable.deep_green_slide);
            bvwThree.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFour.setBackgroundResource(R.drawable.deep_green_slide);
            bvwFive.setBackgroundResource(R.drawable.green_bg_two);

            bvwOne.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwTwo.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwThree.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFour.setTextColor(getResources().getColor(R.color._FF01FCDA));
            bvwFive.setTextColor(getResources().getColor(R.color.black));
        }
    }


    private void changeTimeState() {

        if (timeType == 0) {
            timeOne.setBackgroundResource(R.drawable.green_bg_two);
            timeTwo.setBackgroundResource(R.drawable.deep_green_slide);
            timeThree.setBackgroundResource(R.drawable.deep_green_slide);

            timeOne.setTextColor(getResources().getColor(R.color.black));
            timeTwo.setTextColor(getResources().getColor(R.color._FF01FCDA));
            timeThree.setTextColor(getResources().getColor(R.color._FF01FCDA));
        } else if (timeType == 1) {
            timeOne.setBackgroundResource(R.drawable.deep_green_slide);
            timeTwo.setBackgroundResource(R.drawable.green_bg_two);
            timeThree.setBackgroundResource(R.drawable.deep_green_slide);

            timeOne.setTextColor(getResources().getColor(R.color._FF01FCDA));
            timeTwo.setTextColor(getResources().getColor(R.color.black));
            timeThree.setTextColor(getResources().getColor(R.color._FF01FCDA));
        } else if (timeType == 2) {
            timeOne.setBackgroundResource(R.drawable.deep_green_slide);
            timeTwo.setBackgroundResource(R.drawable.deep_green_slide);
            timeThree.setBackgroundResource(R.drawable.green_bg_two);

            timeOne.setTextColor(getResources().getColor(R.color._FF01FCDA));
            timeTwo.setTextColor(getResources().getColor(R.color._FF01FCDA));
            timeThree.setTextColor(getResources().getColor(R.color.black));
        }
    }


    private void sendYuBi() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        AddYuBiRequest yuBiRequest = new AddYuBiRequest();

        if (bvwType == 0) {
            yuBiRequest.setAmount("201");
        } else if (bvwType == 1) {
            yuBiRequest.setAmount("801");
        } else if (bvwType == 2) {
            yuBiRequest.setAmount("3001");
        } else if (bvwType == 3) {
            yuBiRequest.setAmount("10001");
        } else if (bvwType == 4) {
            yuBiRequest.setAmount("50001");
        }

        if (timeType == 0) {
            yuBiRequest.setDate_limit("6");
        } else if (timeType == 1) {
            yuBiRequest.setDate_limit("12");
        } else if (timeType == 2) {
            yuBiRequest.setDate_limit("24");
        }


        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(yuBiRequest);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);

        showDialog(getResources().getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.ADD_IN_YUBI_URL)
                .tag(YuBiBaoDetailActivity.this)
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
                                    BaseResponse response = gson.fromJson(backVal, BaseResponse.class);
                                    if (response != null && response.getCode() == 0) {

                                        try {
                                            String succesignVal = "";
                                            String bvwVal = "";
                                            String monthVal = "";

                                            if (bvwType == 0) {
                                                bvwVal = "201";
                                            } else if (bvwType == 1) {
                                                bvwVal = "801";
                                            } else if (bvwType == 2) {
                                                bvwVal = "3001";
                                            } else if (bvwType == 3) {
                                                bvwVal = "10001";
                                            } else if (bvwType == 4) {
                                                bvwVal = "50001";
                                            }


                                            if (timeType == 0) {
                                                monthVal = "6";
                                            } else if (timeType == 1) {
                                                monthVal = "12";
                                            } else if (timeType == 2) {
                                                monthVal = "24";
                                            }

                                            succesignVal = getString(R.string.yb_pay_success_sign, bvwVal, monthVal);

                                            new SuccessDialogybView().showTips(YuBiBaoDetailActivity.this, succesignVal);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {

//                                        try {
//                                            String succesignVal = "";
//                                            String bvwVal="";
//                                            String monthVal="";
//
//                                            if (bvwType == 0) {
//                                                bvwVal = "3001";
//                                            } else if (bvwType == 1) {
//                                                bvwVal = "10001";
//                                            } else if (bvwType == 2) {
//                                                bvwVal = "50000";
//                                            }
//
//                                            if (timeType == 0) {
//                                                monthVal = "6";
//                                            } else if (timeType == 1) {
//                                                monthVal = "12";
//                                            } else if (timeType == 2) {
//                                                monthVal = "24";
//                                            }
//
//                                            succesignVal = getString(R.string.yb_pay_success_sign,bvwVal,monthVal);
//
//                                            new SuccessDialogybView().showTips(YuBiBaoDetailActivity.this, succesignVal);
//                                        }catch (Exception e){
//                                            e.printStackTrace();
//                                        }

                                        new FailDialogybView().showTips(YuBiBaoDetailActivity.this, response.getMsg());

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
                        dismissDialog();
                    }
                });
    }


    private void getListData() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.YUBIBAO_LIST_URL)
                .tag(YuBiBaoDetailActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    YuBiResponse response = gson.fromJson(backVal, YuBiResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().size() > 0) {
                                            setContentVal(response.getData());
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


    private void setContentVal(List<YuBiResponse.YuBiListModel> data) {

        try {
            ybContentLayout.setVisibility(View.VISIBLE);
            ybContentListLayout.removeAllViews();
            for (int i = 0; i < data.size(); i++) {
                YuBiResponse.YuBiListModel yuBiListModel = data.get(i);
                View ybItemView = (View) LayoutInflater.from(YuBiBaoDetailActivity.this).inflate(R.layout.yb_list_content_item, null);

                TextView oneItemView = (TextView) ybItemView.findViewById(R.id.yb_list_content_one);
                TextView twoItemView = (TextView) ybItemView.findViewById(R.id.yb_list_content_two);
                TextView threeItemView = (TextView) ybItemView.findViewById(R.id.yb_list_content_three);

                oneItemView.setText(yuBiListModel.getAmount().toPlainString() + "BTW, ");
                twoItemView.setText(TimeUtil.getYHDVal(yuBiListModel.getEnd_time()) + " " + getString(R.string.yb_list_item_month, yuBiListModel.getDate_limit()) + ",");
                threeItemView.setText(getString(R.string.yb_list_bj));

                ybContentListLayout.addView(ybItemView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getLeftMoneyRequest() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(YuBiBaoDetailActivity.this)
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
                            leftMoneyVal = "0";
                        } else {
                            leftMoneyVal = zcMoneyModel.getValue_qty();
                        }
                    }
                }
            }
        }

    }


}
