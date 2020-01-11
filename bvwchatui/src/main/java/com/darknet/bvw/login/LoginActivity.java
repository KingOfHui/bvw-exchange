package com.darknet.bvw.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.darknet.bvw.Config;
import com.darknet.bvw.chat.ChatManagerHolder;
import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.WfcBaseActivity;
import com.darknet.bvw.chat.net.OKHttpHelper;
import com.darknet.bvw.chat.net.SimpleCallback;
import com.darknet.bvw.login.model.LoginResult;
import com.darknet.bvw.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
//import cn.wildfire.chat.app.Config;
//import cn.wildfire.chat.app.login.model.LoginResult;
//import cn.wildfire.chat.app.main.MainActivity;
//import cn.wildfire.chat.kit.ChatManagerHolder;
//import cn.wildfire.chat.kit.WfcBaseActivity;
//import cn.wildfire.chat.kit.net.OKHttpHelper;
//import cn.wildfire.chat.kit.net.SimpleCallback;
//import cn.wildfirechat.chat.R;

/**
 * use {@link SMSLoginActivity} instead
 */
@Deprecated
public class LoginActivity extends WfcBaseActivity {
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.accountEditText)
    EditText accountEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @Override
    protected int contentLayout() {
        return R.layout.login_activity_account;
    }

    @Override
    protected boolean showHomeMenuItem() {
        return false;
    }

    @OnTextChanged(value = R.id.accountEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputAccount(Editable editable) {
        if (!TextUtils.isEmpty(passwordEditText.getText()) && !TextUtils.isEmpty(editable)) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.passwordEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputPassword(Editable editable) {
        if (!TextUtils.isEmpty(accountEditText.getText()) && !TextUtils.isEmpty(editable)) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }


    @OnClick(R.id.loginButton)
    void login() {
        String account = accountEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        String url = Config.APP_SERVER_ADDRESS + "/api/login";
        Map<String, String> params = new HashMap<>();
        params.put("name", account);
        params.put("password", password);
        try {
            params.put("clientId", ChatManagerHolder.gChatManager.getClientId());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "网络出来问题了。。。", Toast.LENGTH_SHORT).show();
            return;
        }

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content("登录中...")
                .progress(true, 10)
                .cancelable(false)
                .build();
        dialog.show();
        OKHttpHelper.post(url, params, new SimpleCallback<LoginResult>() {
            @Override
            public void onUiSuccess(LoginResult loginResult) {
                if (isFinishing()) {
                    return;
                }
                ChatManagerHolder.gChatManager.connect(loginResult.getUserId(), loginResult.getToken());
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit()
                        .putString("id", loginResult.getUserId())
                        .putString("token", loginResult.getToken())
                        .apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }

            @Override
            public void onUiFailure(int code, String msg) {
                if (isFinishing()) {
                    return;
                }
                dialog.dismiss();
            }
        });
    }
}
