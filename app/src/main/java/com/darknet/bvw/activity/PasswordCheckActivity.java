package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.BuildConfig;
import com.darknet.bvw.R;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.util.language.LocalManageUtil;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author zhangchen
 */
public class PasswordCheckActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title, txtAccount, txtWalletName, txtPassword;
    private EditText editPassword;
    private TextView btnLogin, btnChange;

    private String name;
    private String pwd;

    private TextView pwdWalletNameView;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        LocalManageUtil.getSelectLanguage(this);
        name = getIntent().getStringExtra("name");
        pwd = getIntent().getStringExtra("pwd");

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        txtWalletName = findViewById(R.id.txtWalletName);
        txtAccount = findViewById(R.id.txtAccount);
        txtPassword = findViewById(R.id.txtPassword);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnChange = findViewById(R.id.btnChange);

        pwdWalletNameView = findViewById(R.id.pwd_check_content_view);


        title.setText(getResources().getString(R.string.pwd_check_title));
        layBack.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


//        SpannableStringBuilder spannable = new SpannableStringBuilder(name);

        int languageSign = SPUtil.getInstance(PasswordCheckActivity.this).getSelectLanguage();
        if (languageSign == 1) {
//            txtAccount.setText("请输入,钱包名叫: ");
//            txtWalletName.setText(name);
//            txtPassword.setText(" 的密码");

            pwdWalletNameView.setText("请输入,钱包名叫: " + name + " 的密码");

        } else if (languageSign == 0) {

            pwdWalletNameView.setText("Please input, wallet name " + name + " security password");

//            txtAccount.setText("Please input, wallet name ");
//            txtWalletName.setText(name);
//            txtPassword.setText(" security password");
        } else {
            pwdWalletNameView.setText("Please input, wallet name " + name + " security password");
        }


//        else if (languageSign == 2) {
//
//            pwdWalletNameView.setText("Please input, wallet name "+name+" security password");
//            try {
//
//                String language = getStystemLanguage();
//
//                if ("zh".equals(language)) {
//                    txtAccount.setText("请输入,钱包名叫: ");
//                    txtWalletName.setText(name);
//                    txtPassword.setText("的密码");
//                } else if ("en".equals(language)) {
//                    txtAccount.setText("Please input, wallet name ");
//                    txtWalletName.setText(name);
//                    txtPassword.setText("security password");
//                } else {
//                    txtAccount.setText("Please input, wallet name ");
//                    txtWalletName.setText(name);
//                    txtPassword.setText("security password");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                txtAccount.setText("Please input, wallet name ");
//                txtWalletName.setText(name);
//                txtPassword.setText("security password");
//            }
//        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_check;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

//    private String getStystemLanguage() {
//        String language = getSystemLocale(this).getLanguage();
//        return language;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                PasswordCheckActivity.this.finish();
                break;
            case R.id.btnLogin:
                String inputPwd = editPassword.getText().toString();
                if(BuildConfig.DEBUG && inputPwd.isEmpty()) {
                    inputPwd = "1234567";
                }
                hintKeyBoard();
                if (inputPwd != null && inputPwd.trim().length() != 0) {
                    if (pwd.equals(inputPwd)) {

                        showDialog(getString(R.string.load_data));

//                        AtyContainer.getInstance().finishAllActivity();
                        

                        Intent mainIntent = new Intent(PasswordCheckActivity.this, XchainMainThreeActivity.class);
                        startActivity(mainIntent);

                        dismissDialog();

                        finish();

                    } else {
                        Toast.makeText(PasswordCheckActivity.this, getResources().getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(PasswordCheckActivity.this, getResources().getString(R.string.pwd_check_input_pwd), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnChange:
                Intent intent = new Intent(PasswordCheckActivity.this, AccountManageActivity.class);
                startActivity(intent);
//                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveClose(CloseViewEvent nameEvent) {
        finish();
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

}
