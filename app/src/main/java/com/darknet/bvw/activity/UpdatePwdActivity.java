package com.darknet.bvw.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.view.TypefaceTextView;

public class UpdatePwdActivity extends BaseActivity {

    private RelativeLayout layBack;
    private Button btnRight;
    private TypefaceTextView title;

    private EditText pwdContent;
    private Long walletId;

    ETHWalletModel walletModel;


    @Override
    public void initView() {

        walletId = (Long) getIntent().getLongExtra("id", 0);

        if (walletId != 0) {
            walletModel = WalletDaoUtils.getWalletById(walletId);
        }

        layBack = findViewById(R.id.layBack);
        btnRight = findViewById(R.id.btnRight);
        title = findViewById(R.id.title);
        btnRight.setText(getString(R.string.add_contact_complete));
        title.setText(getString(R.string.wallet_detail_update_pwd));
        pwdContent = findViewById(R.id.update_pwd_content_view);

        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNewPwd();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
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

    private void checkNewPwd() {
        String newPwdVal = pwdContent.getText().toString();
        if (TextUtils.isEmpty(newPwdVal)) {
            Toast.makeText(UpdatePwdActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (newPwdVal.trim().length() < 6) {
                Toast.makeText(UpdatePwdActivity.this, getString(R.string.pwd_not_encough), Toast.LENGTH_SHORT).show();
                return;
            }

            walletModel.setPassword(newPwdVal);
            WalletDaoUtils.updateWallet(walletModel);
            Toast.makeText(UpdatePwdActivity.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
            finish();

        }
    }

}
