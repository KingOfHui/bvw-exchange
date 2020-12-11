package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.BtcDo;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.view.CommonInputPwdDialogView;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BackupPrivateKeyActivity extends BaseActivity implements View.OnClickListener {

    private Button btnBackUp, btnCopy;
    private RelativeLayout layBack;
    private TypefaceTextView title;
    private TextView editPrivateKey;
    private TextView publicView;

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


        btnBackUp = findViewById(R.id.btnBackUp);
        btnCopy = findViewById(R.id.btnCopy);
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        editPrivateKey = findViewById(R.id.editPrivateKey);
        publicView = findViewById(R.id.txtPublicKey);
        title.setText(getResources().getString(R.string.back_up_wallet_private_key));


        publicView.setText(btcDo.getAddress());
        editPrivateKey.setText(btcDo.getPrivateKey());

        btnBackUp.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        layBack.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_back_up_wallet;
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
            case R.id.btnBackUp:
                pwdCheck();
                break;
            case R.id.btnCopy:
//                String shoukuanVal = shouKuanView.getText().toString();

                String privateVals = btcDo.getPrivateKey();

                if (!TextUtils.isEmpty(privateVals)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                    ClipData clipData = ClipData.newPlainText(null, privateVals);
                    // 把数据集设置（复制）到剪贴板
                    clipboard.setPrimaryClip(clipData);
                    Toast.makeText(BackupPrivateKeyActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                    rockAction();
                }
                break;
            case R.id.layBack:
                BackupPrivateKeyActivity.this.finish();
                break;
        }
    }


    private void pwdCheck() {
        new CommonInputPwdDialogView().showTips(BackupPrivateKeyActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
            @Override
            public void closeClick() {

            }

            @Override
            public void sureClick(String contentVal) {
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(BackupPrivateKeyActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (contentVal.equals(tpwdVal)) {

                        //将数据插入数据库
                        showDialog(getString(R.string.loading_wallet_tip));
                        ETHWalletModel walletModel = new ETHWalletModel();
                        walletModel.setAddress(btcDo.getAddress());
                        walletModel.setMnemonic(wordsVals);
                        walletModel.setName(tnameVal);
                        walletModel.setPassword(tpwdVal);
                        walletModel.setKeyStoreVal(btcDo.getKeyStoreVal());
                        walletModel.setPrivateKey(btcDo.getPrivateKey());
                        walletModel.setPublicKey(btcDo.getPublicKey());
                        walletModel.setImportType(0);
                        walletModel.setCurrentSelect(1);
                        WalletDaoUtils.insertNewWallet(walletModel);
                        dismissDialog();
//                        AtyContainer.getInstance().finishAllActivity();
                        EventBus.getDefault().post(walletModel);
                        Intent detailIntent = new Intent(BackupPrivateKeyActivity.this, StopActivity.class);
                        startActivity(detailIntent);
                        finish();
                    } else {
                        Toast.makeText(BackupPrivateKeyActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    //震动
    private void rockAction() {
        TipHelper.Vibrate(BackupPrivateKeyActivity.this, new long[]{200, 300}, false);
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
