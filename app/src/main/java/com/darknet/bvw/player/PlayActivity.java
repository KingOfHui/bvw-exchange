//package com.darknet.bvw.player;
//
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//
//import com.darknet.bvw.R;
//import com.darknet.bvw.activity.BaseActivity;
//import com.kk.taurus.playerbase.assist.InterEvent;
//import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler;
//import com.kk.taurus.playerbase.entity.DataSource;
//import com.kk.taurus.playerbase.event.OnPlayerEventListener;
//import com.kk.taurus.playerbase.player.IPlayer;
//import com.kk.taurus.playerbase.receiver.ReceiverGroup;
//import com.kk.taurus.playerbase.widget.BaseVideoView;
//
//
///**
// * Created by wanghaofei on 2019/4/2.
// * e-mail : xxx@xx
// * time   : 2019/04/02
// * desc   :
// * version: 1.0
// */
//
//public class PlayActivity extends BaseActivity implements OnPlayerEventListener {
//
//    private ImageView backView;
//    private TextView titleContentView;
//    private String titleVal;
//    private String videoPath;
//    BaseVideoView mVideoView;
//    private ReceiverGroup mReceiverGroup;
//    private boolean userPause;
//    private boolean isLandscape;
//    private int margin;
//
//    private boolean hasStart;
//    private int isLandType = 0;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play_layout);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
//                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        titleVal = getIntent().getStringExtra("sharecontent");
//        videoPath = getIntent().getStringExtra("videoPath");
//        isLandType = getIntent().getIntExtra("island",0);
//
//        if(isLandType == 1){
//            isLandscape = true;
//        }
//
//        initView();
//    }
//
//
//
//    private void initView() {
//
////        backView = getView(R.id.common_back);
////        titleContentView = getView(R.id.common_title);
////        titleContentView.setTextColor(getResources().getColor(R.color.white));
////        titleContentView.setTextSize(18);
////        titleContentView.setText(titleVal);
//
//        mVideoView = (BaseVideoView) findViewById(R.id.play_videoView);
//        margin = PUtil.dip2px(this, 2);
////        updateVideo(false);
//        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this);
//        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true);
//        mVideoView.setReceiverGroup(mReceiverGroup);
//        mVideoView.setEventHandler(onVideoViewEventHandler);
//        mVideoView.setOnPlayerEventListener(this);
//
//
////
////        mVideoView.setDataSource(new DataSource("http://1258450754.vod2.myqcloud.com/1dec67abvodcq1258450754/a95ce1785285890784714675085/o4px8WwVgpoA.mp4"));
////        mVideoView.start();
//    }
//
//    private OnVideoViewEventHandler onVideoViewEventHandler = new OnVideoViewEventHandler() {
//        @Override
//        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
//            super.onAssistHandle(assist, eventCode, bundle);
//            switch (eventCode) {
//                case InterEvent.CODE_REQUEST_PAUSE:
//                    userPause = true;
//                    break;
//                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
//                    if (isLandscape) {
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    } else {
//                        finish();
//                    }
//                    break;
//                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
//                    setRequestedOrientation(isLandscape ?
//                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
//                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    break;
//                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
//                    mVideoView.stop();
//                    break;
//                case DataInter.Event.EVENT_CODE_REQUEST_SHARE:
//                    shareHandler();
//                    break;
//            }
//        }
//
//        @Override
//        public void requestRetry(BaseVideoView videoView, Bundle bundle) {
//            if (PUtil.isTopActivity(PlayActivity.this)) {
//                super.requestRetry(videoView, bundle);
//            }
//        }
//    };
//
//    private void shareHandler(){
//        if (titleVal == null) {
//            titleVal = "视频解读";
//        }
//
////        UmengUtils.commonEvent(PlayActivity.this, "video_share_btn", "视频播放分享按钮");
//
//        ShareUtils.doSimpleShare(ShareConstant.SHARE_TITLE, PlayActivity.this, "松果健康", titleVal, "http://file.sungohealth.com/pic/logo.png", videoPath, null, null);
//    }
//
//    @Override
//    public void onPlayerEvent(int eventCode, Bundle bundle) {
//        switch (eventCode) {
//            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
////                mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
////                SettingAdapter mAdapter = new SettingAdapter(this, SettingItem.initSettingList());
////                mAdapter.setOnItemClickListener(this);
////                mRecycler.setAdapter(mAdapter);
//                break;
//        }
//    }
//
//    private void updateVideo(boolean landscape) {
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
//        if (landscape) {
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.setMargins(0, 0, 0, 0);
//        } else {
//
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.setMargins(0, 0, 0, 0);
//
////            layoutParams.width = PUtil.getScreenW(this) - (margin*2);
////            layoutParams.height = layoutParams.width * 3/4;
////            layoutParams.setMargins(margin, margin, margin, margin);
//        }
//        mVideoView.setLayoutParams(layoutParams);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (isLandscape) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            isLandscape = true;
//            updateVideo(true);
//        } else {
//            isLandscape = false;
//            updateVideo(false);
//        }
//        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandscape);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        int state = mVideoView.getState();
//        if (state == IPlayer.STATE_PLAYBACK_COMPLETE)
//            return;
//        if (mVideoView.isInPlaybackState()) {
//            mVideoView.pause();
//        } else {
//            mVideoView.stop();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        int state = mVideoView.getState();
//        if (state == IPlayer.STATE_PLAYBACK_COMPLETE)
//            return;
//        if (mVideoView.isInPlaybackState()) {
//            if (!userPause)
//                mVideoView.resume();
//        } else {
//            mVideoView.rePlay(0);
//        }
//        initPlay();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mVideoView.stopPlayback();
//    }
//
//    private void initPlay() {
//        if (!hasStart) {
//            DataSource dataSource = new DataSource();
//            dataSource.setData(videoPath);
//            dataSource.setTitle(titleVal);
//            mVideoView.setDataSource(dataSource);
//            mVideoView.start();
//            hasStart = true;
//        }
//    }
//
//
//}
