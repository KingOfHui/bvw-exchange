package com.darknet.bvw.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.util.EnvironmentUtil;
import com.darknet.bvw.util.language.SPUtil;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends BaseActivity {

    private TextView versionView;

    private static final int MSG_CODE_TWO = 15;

    private ImageView welcom_view;

    private DelayHandler delayHandler;

    @Override
    public void initView() {
        delayHandler = new DelayHandler(this);
        welcom_view = findViewById(R.id.welcom_view);

        versionView = findViewById(R.id.welcome_version_view);

        versionView.setText(getString(R.string.app_version_name, EnvironmentUtil.getCurVersionName(mAppContext)));

        int type = SPUtil.getInstance(WelcomeActivity.this).getSelectLanguage();


        if(type == 1){
            welcom_view.setImageResource(R.mipmap.welcome_default_big_img_cn);
        }else {
            welcom_view.setImageResource(R.mipmap.welcome_default_big_img_en);
        }


//        if (type == 0) {
//            welcom_view.setImageResource(R.mipmap.welcome_default_big_img_cn);
//        } else if (type == 1) {
//            welcom_view.setImageResource(R.mipmap.welcome_default_big_img_en);
//        } else if (type == 2) {
//            try {
//
//                String language = getStystemLanguage();
//
//                if ("zh".equals(language)) {
//                    welcom_view.setImageResource(R.mipmap.welcome_default_big_img_cn);
//                } else if ("en".equals(language)) {
//                    welcom_view.setImageResource(R.mipmap.welcome_default_big_img_en);
//                } else {
//                    welcom_view.setImageResource(R.mipmap.welcome_default_big_img_en);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                welcom_view.setImageResource(R.mipmap.welcome_default_big_img_en);
//            }
//        }

        Message message = Message.obtain();
        message.what = MSG_CODE_TWO;
        //第一次发送message
        delayHandler.sendMessageDelayed(message, 1500);
    }

    static class DelayHandler extends Handler {
        private WeakReference<WelcomeActivity> reference;

        public DelayHandler(WelcomeActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity welcomeActivity = reference.get();
            if (welcomeActivity == null) {
                return;
            }
            if (msg.what == MSG_CODE_TWO) {
                welcomeActivity.checkVal();
            }
        }
    }


    @Override
    protected void onDestroy() {
        delayHandler.removeMessages(MSG_CODE_TWO);
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {


    }


//    private String getStystemLanguage() {
//        String language = getSystemLocale(this).getLanguage();
//        return language;
//    }


    @Override
    public void configViews() {

    }


    private void checkVal() {

        try {
            ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

            if (walletModel == null) {

//                AtyContainer.getInstance().finishAllActivity();

                Intent createIntent = new Intent(WelcomeActivity.this, WalletSelectActivity.class);

                startActivity(createIntent);
                finish();
            } else {

//                AtyContainer.getInstance().finishAllActivity();

                Intent createIntent = new Intent(WelcomeActivity.this, PasswordCheckActivity.class);
                createIntent.putExtra("name", walletModel.getName());
                createIntent.putExtra("pwd", walletModel.getPassword());
                startActivity(createIntent);
                finish();
            }
        } catch (Exception e) {
//            AtyContainer.getInstance().finishAllActivity();

            Intent createIntent = new Intent(WelcomeActivity.this, WalletSelectActivity.class);

            startActivity(createIntent);
            finish();
            e.printStackTrace();
        }


    }


}
