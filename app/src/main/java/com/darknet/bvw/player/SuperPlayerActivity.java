package com.darknet.bvw.player;//package com.zqh.player;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.tencent.liteav.basic.log.TXCLog;
//import com.tencent.liteav.demo.play.SuperPlayerConst;
//import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
//import com.tencent.liteav.demo.play.SuperPlayerModel;
//import com.tencent.liteav.demo.play.SuperPlayerUrl;
//import com.tencent.liteav.demo.play.SuperPlayerView;
//import com.tencent.rtmp.TXLiveBase;
//import com.tencent.rtmp.TXLiveConstants;
//import com.zqh.R;
//import com.zqh.constant.ShareConstant;
//import com.zqh.radio.activity.FoodDetailActivity;
//import com.zqh.tool.share.ShareUtils;
//import com.zqh.ui.HelpActivity;
//import com.zqh.util.UmengUtils;
//import com.zqh.util.log.ZqhLogUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static android.view.View.GONE;
//import static android.view.View.VISIBLE;
//
///**
// * Created by wanghaofei on 2019/1/7.
// * e-mail : xxx@xx
// * time   : 2019/01/07
// * desc   :
// * version: 1.0
// */
//
//public class SuperPlayerActivity extends Activity implements SuperPlayerView.PlayerViewCallback {
//    // 新手引导的标记
//
//    private static final String TAG = "SuperPlayerActivity";
//    private static final int LIST_TYPE_LIVE = 0;
//
//    //超级播放器View
//    private SuperPlayerView mSuperPlayerView;
//    //播放列表
//
//
//    private String videoPath;
//    private String shareContent;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_vod_player);
//        videoPath = getIntent().getStringExtra("videoPath");
//        shareContent = getIntent().getStringExtra("sharecontent");
//
////        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        setBright();
//
//        checkPermission();
//        initView();
//        initData();
//
//
//
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//    }
//
//    private void checkPermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            List<String> permissions = new ArrayList<>();
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
//                permissions.add(Manifest.permission.CAMERA);
//            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }
//            if (permissions.size() != 0) {
//                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
//            }
//        }
//    }
//
//
//    private void setBright() {
//        try {
//            int brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
//
//            Window window = this.getWindow();
//
//            WindowManager.LayoutParams lp = window.getAttributes();
//            if (brightness == -1){
//                lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
//            }else {
//                lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
//            }
//            window.setAttributes(lp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void initView() {
//
//        mSuperPlayerView = (SuperPlayerView) findViewById(R.id.supervodlayerView);
//        mSuperPlayerView.setPlayerViewCallback(this);
//        mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_FULLSCREEN);
//        mSuperPlayerView.setVideoShareClick(new SuperPlayerView.VideoShareClick() {
//            @Override
//            public void shareClick() {
//
//                if (shareContent == null) {
//                    shareContent = "视频解读";
//                }
//
//                UmengUtils.commonEvent(SuperPlayerActivity.this, "video_share_btn", "视频播放分享按钮");
//
//                ShareUtils.doSimpleShare(ShareConstant.SHARE_TITLE, SuperPlayerActivity.this, "松果健康", shareContent, "http://file.sungohealth.com/pic/logo.png", videoPath, null, null);
//            }
//        });
//    }
//
//
//    private void initData() {
//
//        playNewVideo();
//        TXLiveBase.setAppID("1253131631");
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        UmengUtils.onResumeToActivity(SuperPlayerActivity.this, "page_video");
//
//
//        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
//            Log.i(TAG, "onResume state :" + mSuperPlayerView.getPlayState());
//            mSuperPlayerView.onResume();
//            if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
//                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
////                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_FULLSCREEN);
//            }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        UmengUtils.onPauseToActivity(SuperPlayerActivity.this, "page_video");
//
//
//        Log.i(TAG, "onPause state :" + mSuperPlayerView.getPlayState());
//        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
//            mSuperPlayerView.onPause();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSuperPlayerView.release();
//        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
//            mSuperPlayerView.resetPlayer();
//        }
//    }
//
//
//    private void playNewVideo() {
//
//        SuperPlayerModel superPlayerModel = new SuperPlayerModel();
//        superPlayerModel.title = "视频播放";
//        superPlayerModel.videoURL = videoPath;
////        superPlayerModel.videoURL = "http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/e1ab85305285890781763144364/v.f20.mp4";
////        superPlayerModel.placeholderImage = "http://xiaozhibo-10055601.file.myqcloud.com/coverImg.jpg";
//        superPlayerModel.appid = 1135976;
//        mSuperPlayerView.playWithMode(superPlayerModel);
//
//
//    }
//
//
//    @Override
//    public void hideViews() {
//
//    }
//
//    @Override
//    public void showViews() {
//
//    }
//
//    @Override
//    public void onQuit(int playMode) {
//        if (playMode == SuperPlayerConst.PLAYMODE_FLOAT) {
//            mSuperPlayerView.resetPlayer();
//            finish();
//        } else {
//
//            TXCLog.e("SuperVodPlayerView", "playWithURL : superactivity..here..。。。。");
//
////            mSuperPlayerView.resetPlayer();
//            finish();
//        }
//    }
//
//
//}
