package com.darknet.bvw.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author zhangchen
 * <p>
 * 创建钱包页面
 */
public class WalletCreateActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title, txtXy;
    private EditText editWalletName, editWalletPassword, editRepeatPassword, editPasswordInfo;
    private CheckBox checkbox;
    private Button btnNext;

    private LinearLayout layCheck;

    private TextView leadInView;

//    private String wordsVals;

//    private String publicKey;
//    private String addressVals;
//    private String privateKey;

//    BtcDo btcDo;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

//        btcDo = (BtcDo) getIntent().getSerializableExtra("walletModel");
//        wordsVals = getIntent().getStringExtra("words");

//        publicKey = getIntent().getStringExtra("publicKey");
//        addressVals = getIntent().getStringExtra("addressVals");
//        privateKey = getIntent().getStringExtra("privateKey");

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        editWalletName = findViewById(R.id.editWalletName);
        editWalletPassword = findViewById(R.id.editWalletPassword);
        editRepeatPassword = findViewById(R.id.editRepeatPassword);
        editPasswordInfo = findViewById(R.id.editPasswordInfo);
        checkbox = findViewById(R.id.checkbox);
        btnNext = findViewById(R.id.btnNext);
        layCheck = findViewById(R.id.layCheck);
        txtXy = findViewById(R.id.txtXy);
        leadInView = findViewById(R.id.wallet_select_import_view);


        title.setText(getString(R.string.wallet_create_title));

//        editWalletPassword.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
//        editRepeatPassword.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        layBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        layCheck.setOnClickListener(this);
        txtXy.setOnClickListener(this);
        leadInView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wallet_select_import_view:
                Intent leadIntent = new Intent(WalletCreateActivity.this,LeadInAccountActivity.class);
                startActivity(leadIntent);
                finish();
                break;
            case R.id.layBack:
                WalletCreateActivity.this.finish();
                break;
            case R.id.btnNext:
                if (!checkbox.isChecked()) {
                    Toast.makeText(WalletCreateActivity.this, getString(R.string.wallet_create_xieyi), Toast.LENGTH_SHORT).show();
                    return;
                }
                String walletName = editWalletName.getText().toString().trim();
                String walletPasswrod = editWalletPassword.getText().toString().trim();
                String repeatPassword = editRepeatPassword.getText().toString().trim();
                String passwordInfo = editPasswordInfo.getText().toString().trim();

                if (TextUtils.isEmpty(walletName)) {
                    Toast.makeText(this, getString(R.string.wallet_create_sign_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(walletPasswrod)) {
                    Toast.makeText(this, getString(R.string.wallet_pwd_six), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (walletPasswrod == null || walletPasswrod.length() < 6) {
                    Toast.makeText(this, getString(R.string.wallet_pwd_six), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(repeatPassword) || walletPasswrod.length() < 6) {
                    Toast.makeText(this, getString(R.string.wallet_pwd_six), Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!repeatPassword.equals(walletPasswrod)) {
                    Toast.makeText(this, getString(R.string.wallet_create_sign_pwd_not), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent mainIntent = new Intent(WalletCreateActivity.this, WalletZjcActivity.class);
                mainIntent.putExtra("usrName",walletName);
                mainIntent.putExtra("pwd",walletPasswrod);
                startActivity(mainIntent);

                break;
            case R.id.layCheck:
                if (checkbox.isChecked()) {
                    checkbox.setChecked(false);
                } else {
                    checkbox.setChecked(true);
                }
                break;
            case R.id.txtXy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_create;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveClose(CloseViewEvent nameEvent) {
        finish();
    }


}
