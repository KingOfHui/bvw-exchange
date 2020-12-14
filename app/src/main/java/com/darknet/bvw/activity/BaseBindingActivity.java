package com.darknet.bvw.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.view.CustomDialog;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

import static com.darknet.bvw.util.language.LocalManageUtil.getSystemLocale;

public abstract class BaseBindingActivity<BINDING extends ViewDataBinding> extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    protected Application mAppContext;

    private CustomDialog dialog;//进度条
    public BINDING mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
//        setContentView(getLayoutId());
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mBinding.setLifecycleOwner(this);
        AtyContainer.getInstance().addActivity(this);
        mAppContext = MyApp.getInstance();
        initView();
        initDatas();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void initToolBar(){};

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public void configViews(){};

    protected final <T extends BaseViewModel> T getViewModel(Class<T> viewModelClass) {
        T viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(
                viewModelClass);
        return viewModel;
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
