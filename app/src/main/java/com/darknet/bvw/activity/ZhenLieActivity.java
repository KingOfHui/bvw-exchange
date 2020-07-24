package com.darknet.bvw.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.darknet.bvw.model.response.ZhenLieResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.VerticalProgressBarFour;
import com.darknet.bvw.view.VerticalProgressBarTwo;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.NumberFormat;

public class ZhenLieActivity extends BaseActivity {

    private TextView titleContent;

    private RelativeLayout backLayout;

//    private TextView versionName;


    private LinearLayout tiZhenLayout;
    private LinearLayout xiangZhenLayout;

    private VerticalProgressBarFour progressBarOne;
    private VerticalProgressBarTwo progressBarTwo;


    private TextView zhenLieLeftView;
    private TextView zhenLieRightView;


    @Override
    public void initView() {

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
        titleContent.setText(getString(R.string.zhenlie_title_val));
//        versionName = findViewById(R.id.about_version_view);

        tiZhenLayout = findViewById(R.id.zhenlie_one_layout);
        xiangZhenLayout = findViewById(R.id.zhenlie_two_layout);

        progressBarOne = findViewById(R.id.vertical_progress_one);
        progressBarTwo = findViewById(R.id.vertical_progress_two);

        zhenLieLeftView = findViewById(R.id.zhenlie_header_left_view);
        zhenLieRightView = findViewById(R.id.zhenlie_header_right_view);

//        progressBarOne.setProgress(50);
//        progressBarOne.setText("梯阵2000");


//        progressBarTwo.setText("像阵1000");

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        versionName.setText("V" + getCurVersionName(this));


        tiZhenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lieDuiIntent = new Intent(ZhenLieActivity.this, WebLieDuiActivity.class);
                lieDuiIntent.putExtra("type", 0);
                startActivity(lieDuiIntent);
            }
        });

        xiangZhenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lieDuiIntent = new Intent(ZhenLieActivity.this, WebLieDuiActivity.class);
                lieDuiIntent.putExtra("type", 1);
                startActivity(lieDuiIntent);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhenlie;
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

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.ZHENLIE_FIRST_URL)
                .tag(ZhenLieActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    ZhenLieResponse response = gson.fromJson(backVal, ZhenLieResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        if (response.getData() != null && response.getData().getRoot() != null) {
                                            setZhenLieData(response.getData().getRoot());
                                        } else {
                                            Toast.makeText(ZhenLieActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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


    private void setZhenLieData(ZhenLieResponse.ZhenLieLeftModel zhenLieData) {
        try {
            int leftCount = Integer.parseInt(zhenLieData.getLeftTreeCount());
            int rightCount = Integer.parseInt(zhenLieData.getRightTreeCount());
            int totalCount = leftCount + rightCount;

            if (totalCount == 0) {
                leftCount = 10;
                rightCount = 10;
                totalCount = 20;
            }

            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(0);
            String leftResult = numberFormat.format((float) leftCount / (float) totalCount * 100);
            String rightResult = numberFormat.format((float) rightCount / (float) totalCount * 100);

            if (leftCount == 0) {
                progressBarOne.setProgress(Integer.parseInt("40"));
            } else {

                if(Integer.parseInt(leftResult) < 30){
                    progressBarOne.setProgress(25);
                }else {
                    progressBarOne.setProgress(Integer.parseInt(leftResult));
                }
            }


            progressBarOne.setFirstText(getResources().getString(R.string.zhenlie_left_val), 35, Color.BLACK);
            progressBarOne.setSecondText(zhenLieData.getLeftTreeCount(), 55, Color.BLACK);
            progressBarOne.setThirdText(zhenLieData.getLeftBidShowPower()+"KH",35,Color.BLACK);


            if (rightCount == 0) {
                progressBarTwo.setProgress(Integer.parseInt("40"));
            } else {

                if(Integer.parseInt(rightResult) < 30){
                    progressBarTwo.setProgress(25);
                }else {
                    progressBarTwo.setProgress(Integer.parseInt(rightResult));
                }

            }

            progressBarTwo.setFirstText(getResources().getString(R.string.zhenlie_right_val), 35, Color.WHITE);
            progressBarTwo.setSecondText(zhenLieData.getRightTreeCount(), 55, Color.WHITE);
            progressBarTwo.setThirdText(zhenLieData.getRightBidShowPower()+"KH", 35, Color.WHITE);


            try {
                String rightJiaCheng = numberFormat.format(Float.parseFloat(zhenLieData.getBidRate().toString()) * 100);
                zhenLieLeftView.setText(zhenLieData.getBidShowPower().toPlainString());
                zhenLieRightView.setText(rightJiaCheng + "%");
            }catch (Exception e){
                e.printStackTrace();
                zhenLieLeftView.setText("0");
                zhenLieRightView.setText("0%");
            }



        } catch (Exception e) {
            e.printStackTrace();

            progressBarOne.setProgress(Integer.parseInt("40"));
            progressBarOne.setFirstText(getResources().getString(R.string.zhenlie_left_val), 35, Color.BLACK);
            progressBarOne.setSecondText(zhenLieData.getLeftTreeCount(), 65, Color.BLACK);

            progressBarOne.invalidate();

            progressBarTwo.setProgress(Integer.parseInt("40"));
            progressBarTwo.setFirstText(getResources().getString(R.string.zhenlie_right_val), 35, Color.WHITE);
            progressBarTwo.setSecondText(zhenLieData.getRightTreeCount(), 65, Color.WHITE);
            progressBarTwo.setThirdText(zhenLieData.getRightBidShowPower()+"KH", 55, Color.WHITE);

            progressBarTwo.invalidate();

            zhenLieLeftView.setText("0");
            zhenLieRightView.setText("0");
        }


    }

}
