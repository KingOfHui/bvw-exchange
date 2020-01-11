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
import com.darknet.bvw.model.event.WalletNameEvent;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;

public class UpdateNickNameActivity extends BaseActivity {
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
        title.setText(getString(R.string.update_wallet_name));
        pwdContent = findViewById(R.id.update_nickname_content_view);

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
        return R.layout.activity_update_nickname;
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
            Toast.makeText(UpdateNickNameActivity.this, getString(R.string.wallet_create_sign_name), Toast.LENGTH_SHORT).show();
            return;
        } else {

            walletModel.setName(newPwdVal);
            WalletDaoUtils.updateWallet(walletModel);
            Toast.makeText(UpdateNickNameActivity.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();

            WalletNameEvent nameEvent = new WalletNameEvent();
            nameEvent.setName(newPwdVal);
            EventBus.getDefault().post(nameEvent);

            finish();


        }
    }

}
