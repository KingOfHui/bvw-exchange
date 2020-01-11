package com.darknet.bvw.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.darknet.bvw.R;
import com.darknet.bvw.model.BtcDo;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

public class BackupPrivateKeyTwoActivity extends BaseActivity implements View.OnClickListener {

    private Button nextView;
    private RelativeLayout layBack;
    private TypefaceTextView title;
    private EditText editPrivateKey;

    private String wordsVals;
    BtcDo btcDo;
    private String tnameVal;
    private String tpwdVal;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        btcDo = (BtcDo) getIntent().getSerializableExtra("walletModel");
        wordsVals = getIntent().getStringExtra("words");
        tnameVal = getIntent().getStringExtra("usrName");
        tpwdVal = getIntent().getStringExtra("pwd");


        nextView = findViewById(R.id.next_view_btn);
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        editPrivateKey = findViewById(R.id.editPrivateKey);

        title.setText(getResources().getString(R.string.back_up_wallet_private_key));

        nextView.setOnClickListener(this);
        layBack.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_back_up_wallet_two;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_view_btn:
                Intent backIntnet = new Intent(BackupPrivateKeyTwoActivity.this, BackupPrivateKeyActivity.class);
                backIntnet.putExtra("walletModel",(Serializable)btcDo);
                backIntnet.putExtra("words", wordsVals);
                backIntnet.putExtra("usrName",tnameVal);
                backIntnet.putExtra("pwd",tpwdVal);
                startActivity(backIntnet);
                break;
            case R.id.layBack:
                BackupPrivateKeyTwoActivity.this.finish();
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

}
