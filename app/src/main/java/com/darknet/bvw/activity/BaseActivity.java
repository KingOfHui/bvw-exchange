package com.darknet.bvw.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.base.IView;
import com.darknet.bvw.view.CustomDialog;
import com.jingui.lib.utils.Immerse2Helper;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

import static com.darknet.bvw.util.language.LocalManageUtil.getSystemLocale;

public abstract class BaseActivity extends AppCompatActivity implements IView {

    protected String TAG = this.getClass().getSimpleName();

    protected Application mAppContext;

    private CustomDialog dialog;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if(immerse()) {
            Immerse2Helper.setup(this, dark());
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AtyContainer.getInstance().addActivity(this);
        mAppContext = MyApp.getInstance();
        initView();
        initDatas();
    }

    public boolean immerse(){
        return false;
    }

    public boolean dark(){
        return false;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void initToolBar(){}

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public void configViews(){};

    @Override
    public void showLoading() {
        showDialog("");
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void showDialog(String progressTip) {
        if (isFinishing()) {
            return;
        }
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


    public final void restart(){
        restart(this, getClass());
        finish();
    }

    public static void restart(Context context, Class clazz){
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
