package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.view.TypefaceTextView;

public class BackupPrivateKeyThreeActivity extends BaseActivity implements View.OnClickListener {

    private Button btnBackUp, btnCopy;
    private RelativeLayout layBack;
    private TypefaceTextView title;
    private TextView editPrivateKey;
    private TextView publicView;

//    private String wordsVals;
//    BtcDo btcDo;
//    private String tnameVal;
//    private String tpwdVal;

    private Long walletId;
    ETHWalletModel walletModel;

    @Override
    public void initView() {

        walletId = (Long) getIntent().getLongExtra("id", 0);

        if (walletId != 0) {
            walletModel = WalletDaoUtils.getWalletById(walletId);
        }

//        btcDo = (BtcDo) getIntent().getSerializableExtra("walletModel");
//        wordsVals = getIntent().getStringExtra("words");
//        tnameVal = getIntent().getStringExtra("usrName");
//        tpwdVal = getIntent().getStringExtra("pwd");


        btnBackUp = findViewById(R.id.btnBackUp);
        btnCopy = findViewById(R.id.btnCopy);
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        editPrivateKey = findViewById(R.id.editPrivateKey);
        publicView = findViewById(R.id.txtPublicKey);
        title.setText(getResources().getString(R.string.back_up_wallet_private_key));


        publicView.setText(walletModel.getPublicKey());
        editPrivateKey.setText(walletModel.getPrivateKey());

        btnBackUp.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        layBack.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_back_up_wallet_three;
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
            case R.id.btnCopy:
//                String shoukuanVal = shouKuanView.getText().toString();

                String privateVals = walletModel.getPrivateKey();

                if (!TextUtils.isEmpty(privateVals)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                    ClipData clipData = ClipData.newPlainText(null, privateVals);
                    // 把数据集设置（复制）到剪贴板
                    clipboard.setPrimaryClip(clipData);
                    Toast.makeText(BackupPrivateKeyThreeActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                    rockAction();
                }
                break;
            case R.id.layBack:
                BackupPrivateKeyThreeActivity.this.finish();
                break;
        }
    }





    //震动
    private void rockAction() {
        TipHelper.Vibrate(BackupPrivateKeyThreeActivity.this, new long[]{200, 300}, false);
    }
}
