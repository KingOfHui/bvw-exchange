package com.darknet.bvw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.BidSuccessEvent;
import com.darknet.bvw.model.response.BidStateResponse;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.player.DataInter;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.google.gson.Gson;
import com.kk.taurus.playerbase.assist.InterEvent;
import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.widget.BaseVideoView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class BidIntroActivity extends BaseActivity implements OnPlayerEventListener {


    private LinearLayout buyView;
    private TextView leftMoneyView;
    private TextView bidStateView;
    private ImageView exampleView;


    private TextView bidOneView;
    private TextView bidTwoView;
    private TextView bidThreeView;
    private TextView bidFourView;
    private TextView bidFiveView;
    private TextView bidSixView;
    private TextView bidSevenView;
    private TextView bidEightView;
    private TextView bidNineView;
    private TextView bidTenView;
    private TextView bidEleveView;
    private TextView bidTwleveView;

    private BaseVideoView videoView;

    private String leftMoneyVal = "0";

    private ImageView backView;

    private FrameLayout videoFramLayout;

    private ProgressBar progressBar;
    private ImageView videoStopView;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        buyView = (LinearLayout) this.findViewById(R.id.bid_info_buy_view);
        leftMoneyView = (TextView) this.findViewById(R.id.bid_left_money_view);
        bidStateView = (TextView) this.findViewById(R.id.bid_info_buy_state_view);
        exampleView = (ImageView) this.findViewById(R.id.bid_info_example_view);

        backView = (ImageView) this.findViewById(R.id.back_sign_view);

        bidOneView = (TextView) this.findViewById(R.id.bid_info_one);
        bidTwoView = (TextView) this.findViewById(R.id.bid_info_two);
        bidThreeView = (TextView) this.findViewById(R.id.bid_info_three);
        bidFourView = (TextView) this.findViewById(R.id.bid_info_four);
        bidFiveView = (TextView) this.findViewById(R.id.bid_info_five);
        bidSixView = (TextView) this.findViewById(R.id.bid_info_six);
        bidSevenView = (TextView) this.findViewById(R.id.bid_info_seven);
        bidEightView = (TextView) this.findViewById(R.id.bid_info_eight);
        bidNineView = (TextView) this.findViewById(R.id.bid_info_nine);
        bidTenView = (TextView) this.findViewById(R.id.bid_info_ten);
        bidEleveView = (TextView) this.findViewById(R.id.bid_info_eleven);
        bidTwleveView = (TextView) this.findViewById(R.id.bid_info_tweleve);

        videoView = (BaseVideoView) this.findViewById(R.id.videoView);
        videoFramLayout = (FrameLayout) this.findViewById(R.id.bid_info_video_layout_view);

        progressBar = (ProgressBar) this.findViewById(R.id.bid_info_video_load);
        videoStopView = (ImageView) this.findViewById(R.id.bid_info_video_stop_view);

        //设置DataSource

        videoView.setEventHandler(onVideoViewEventHandler);
        videoView.setOnPlayerEventListener(this);
//        videoView.start();


        buyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyIntent = new Intent(BidIntroActivity.this, BidBuyActivity.class);
                buyIntent.putExtra("leftVal", leftMoneyVal);
                startActivity(buyIntent);
            }
        });


//        getLeftMoney();
        getLeftMoneyRequest();

        int lanType = SPUtil.getInstance(BidIntroActivity.this).getSelectLanguage();
        if (lanType == 1) {
            exampleView.setImageResource(R.mipmap.bid_buy_example_img);
        } else if (lanType == 0) {
            exampleView.setImageResource(R.mipmap.bid_buy_example_img_en);
        } else {
            try {
                String language = getStystemLanguage();
                if ("zh".equals(language)) {
                    exampleView.setImageResource(R.mipmap.bid_buy_example_img);
                } else if ("en".equals(language)) {
                    exampleView.setImageResource(R.mipmap.bid_buy_example_img_en);
                } else {
                    exampleView.setImageResource(R.mipmap.bid_buy_example_img_en);
                }
            } catch (Exception e) {
                e.printStackTrace();
                exampleView.setImageResource(R.mipmap.bid_buy_example_img_en);
            }
        }

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        videoFramLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoStopView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                initPlay();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bid_intro;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        getPublicAddress();
    }

    @Override
    public void configViews() {

    }


    private void getLeftMoney(List<LeftMoneyResponse.LeftMoneyModel> allMoney) {
//        List<ZcMoneyModel> allMoney = ZcDaoUtils.getAllZcData();
        if (allMoney != null && allMoney.size() > 0) {
            for (int i = 0; i < allMoney.size(); i++) {
                LeftMoneyResponse.LeftMoneyModel zcMoneyModel = allMoney.get(i);
                if (!TextUtils.isEmpty(zcMoneyModel.getName())) {
                    if (zcMoneyModel.getName().equalsIgnoreCase("BVW")) {
                        if (TextUtils.isEmpty(zcMoneyModel.getValue_qty()) || zcMoneyModel.getValue_qty().equals("0") || zcMoneyModel.getValue_qty().equals("0.000000")) {
                            leftMoneyView.setText("  0 BVW");
                            leftMoneyVal = "0";
                        } else {
                            leftMoneyView.setText("  " + zcMoneyModel.getValue_qty() + " BVW");
                            leftMoneyVal = zcMoneyModel.getValue_qty();
                        }
                    }
                }
            }
        }

    }


    private void getLeftMoneyRequest() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(BidIntroActivity.this)
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


    //获取bid状态
    private void getPublicAddress() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.USER_BID_INFO_URL)
                .tag(BidIntroActivity.this)
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
                                            setStateVal(TextUtils.isEmpty(response.getData().getReferer_id()));
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


    private void setStateVal(boolean stateVal) {
        bidStateView.setText(stateVal?getString(R.string.bid_info_sub_content):getString(R.string.bid_info_sub_content_two));
        buyView.setEnabled(stateVal);
        /*if (stateVal == 0) {
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
        }*/
    }


    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
//                if(mAdapter==null){
//                    mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//                    mAdapter = new SettingAdapter(this, SettingItem.initSettingList());
//                    mAdapter.setOnItemClickListener(this);
//                    mRecycler.setAdapter(mAdapter);
//                }

                videoFramLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Log.e("playState", "....begin..play.....");

                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:
                Log.e("playState", ".....play....over......");
                videoFramLayout.setVisibility(View.VISIBLE);
                videoStopView.setVisibility(View.VISIBLE);
                break;
        }
    }


    private boolean userPause;
    private boolean hasStart;

    private OnVideoViewEventHandler onVideoViewEventHandler = new OnVideoViewEventHandler() {
        @Override
        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode) {
                case InterEvent.CODE_REQUEST_PAUSE:

                    Log.e("playstate", ".....stop..1...");

//                    userPause = true;
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
//                    if(isLandscape){
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    }else{
//                        finish();
//                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
//                    setRequestedOrientation(isLandscape ?
//                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
//                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    Log.e("playstate", ".....stop..3...");
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    Log.e("playstate", ".....stop..2...");
                    videoView.stop();
                    break;
            }
        }

        @Override
        public void requestRetry(BaseVideoView videoView, Bundle bundle) {
//            if(PUtil.isTopActivity(BaseVideoViewActivity.this)){
//                super.requestRetry(videoView, bundle);
//            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        int state = videoView.getState();
        if (state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if (videoView.isInPlaybackState()) {
            if (!userPause)
                videoView.resume();
        } else {
            videoView.rePlay(0);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        videoView.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        int state = videoView.getState();
        if (state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if (videoView.isInPlaybackState()) {
            videoView.pause();
        } else {
            videoView.stop();
        }
    }


    private void initPlay() {
        if (!hasStart) {
            DataSource dataSource = new DataSource();
//            dataSource.setData(videoPath);
//            dataSource.setTitle(titleVal);

            int type = SPUtil.getInstance(BidIntroActivity.this).getSelectLanguage();
            if (type == 1) {
//                videoView.setDataSource(new DataSource("https://bvw.im/assets/docs/Video_B.mp4"));
                videoView.setDataSource(new DataSource("https://btcx-sh.oss-cn-shanghai.aliyuncs.com/btcx.prod/Video_B.mp4"));
            } else {
//                videoView.setDataSource(new DataSource("https://bvw.im/assets/docs/Video_A.mp4"));
                videoView.setDataSource(new DataSource("https://s3-ap-northeast-1.amazonaws.com/btcx.prod/Video_A.mp4"));
            }


//            videoView.setDataSource(dataSource);
            videoView.start();
            hasStart = true;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveClose(BidSuccessEvent nameEvent) {
        finish();
    }


}
