package com.darknet.bvw.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.darknet.bvw.view.CustomDialog;

import cn.jpush.android.api.JPushInterface;

import static com.darknet.bvw.util.language.LocalManageUtil.getSystemLocale;

public abstract class BaseActivity extends AppCompatActivity {


    protected Context mContext;
    private CustomDialog dialog;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        AtyContainer.getInstance().addActivity(this);

//        mContext = this;
//        ImmersionBar.with(this).init();
//        unbinder = ButterKnife.bind(this);
//
//        mCommonToolbar = ButterKnife.findById(this, R.id.common_toolbar);
//        if (mCommonToolbar != null) {
//            ImmersionBar.with(this)
//                    .titleBar(mCommonToolbar, false)
//                    .transparentStatusBar()
//                    .statusBarDarkFont(true, 1f)
//                    .navigationBarColor(R.color.white)
//                    .init();
//            initToolBar();
//            setSupportActionBar(mCommonToolbar);
//        }
//        LinearLayout llyBack = (LinearLayout) findViewById(R.id.lly_back);
//        if (llyBack != null) {
//            llyBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }
//        initDatas();
//        configViews();
        initView();
        initDatas();
    }

    public abstract void initView();

    public abstract int getLayoutId();

    public abstract void initToolBar();

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();


    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog(String progressTip) {
        getDialog().show();
        if (progressTip != null) {
            getDialog().setTvProgress(progressTip);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public String getStystemLanguage() {
        String language = getSystemLocale(this).getLanguage();
        return language;
    }


    public String getClientId() {
        String jiguangId = JPushInterface.getRegistrationID(this);
//        String jiguangId = "0000";
        return jiguangId;
    }


}
