package com.darknet.bvw.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.WalletNameEvent;
import com.darknet.bvw.view.CommonInputPwdDialogView;
import com.darknet.bvw.view.TypefaceTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class WalletDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    private Button btnRight;
    private SimpleDraweeView imgHead;
    private TypefaceTextView txtMoney;
    private RelativeLayout layWalletName, layWalletChangePassword, layExportPrivateKey, layExportKeystore;

    private TextView walletNameSign;

    String walletName;
    Long walletId;
    ETHWalletModel walletModel;

    private Button delBtn;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                WalletDetailActivity.this.finish();
                break;
            case R.id.layWalletName:
                Intent privateIntent = new Intent(WalletDetailActivity.this, UpdateNickNameActivity.class);
                privateIntent.putExtra("id", walletId);
                startActivity(privateIntent);
                break;

            case R.id.layWalletChangePassword:
                //修改密码
                new CommonInputPwdDialogView().showTips(WalletDetailActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
                    @Override
                    public void closeClick() {

                    }

                    @Override
                    public void sureClick(String contentVal) {
                        if (null == contentVal || contentVal.length() == 0) {
                            Toast.makeText(WalletDetailActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (contentVal.equals(walletModel.getPassword())) {
                                Intent privateIntent = new Intent(WalletDetailActivity.this, UpdatePwdActivity.class);
                                privateIntent.putExtra("id", walletId);
                                startActivity(privateIntent);
                            } else {
                                Toast.makeText(WalletDetailActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

//                new CommonDialogView().showTips(WalletDetailActivity.this, new CommonDialogView.OnCloseListener() {
//                    @Override
//                    public void closeClick() {
//
//                    }
//
//                    @Override
//                    public void sureClick(String contentVal) {
//                        if (null == contentVal || contentVal.length() == 0) {
//                            Toast.makeText(WalletDetailActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
//                            return;
//                        } else {
//                            if (contentVal.length() < 6) {
//                                Toast.makeText(WalletDetailActivity.this, getString(R.string.pwd_not_encough), Toast.LENGTH_SHORT).show();
//                                return;
//                            } else {
//                                showDialog(getString(R.string.update));
//                                updatePwd(contentVal);
//                            }
//                        }
//                    }
//                });
                break;
            case R.id.layExportPrivateKey:

                new CommonInputPwdDialogView().showTips(WalletDetailActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
                    @Override
                    public void closeClick() {

                    }

                    @Override
                    public void sureClick(String contentVal) {

                        if (null == contentVal || contentVal.length() == 0) {
                            Toast.makeText(WalletDetailActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (contentVal.equals(walletModel.getPassword())) {
                                Intent privateIntent = new Intent(WalletDetailActivity.this, BackupPrivateKeyThreeActivity.class);
                                privateIntent.putExtra("id", walletId);
                                startActivity(privateIntent);
                            } else {
                                Toast.makeText(WalletDetailActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;
            case R.id.layExportKeystore:
                Intent storeIntent = new Intent(WalletDetailActivity.this, ExportKeyStoreActivity.class);
                storeIntent.putExtra("id", walletId);
                startActivity(storeIntent);
                break;
            case R.id.btnRight:

                break;
            case R.id.btnDeleteWallet:

                new CommonInputPwdDialogView().showTips(WalletDetailActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
                    @Override
                    public void closeClick() {

                    }

                    @Override
                    public void sureClick(String contentVal) {
                        if (null == contentVal || contentVal.length() == 0) {
                            Toast.makeText(WalletDetailActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (walletModel != null) {

                                if (contentVal.equals(walletModel.getPassword())) {
                                    List<ETHWalletModel> allWallet = WalletDaoUtils.loadAll();
                                    if (allWallet.size() == 1) {
                                        AtyContainer.getInstance().finishAllActivity();
                                        WalletDaoUtils.deleteWallet(walletModel);
                                        Intent mangeIntent = new Intent(WalletDetailActivity.this, WalletSelectActivity.class);
                                        startActivity(mangeIntent);
                                        finish();
                                    } else {

                                        WalletDaoUtils.deleteWallet(walletModel);
                                        //等待几秒
                                        //把下一个元素，设置为当前钱包

                                        List<ETHWalletModel> allWallets = WalletDaoUtils.loadAll();
                                        if (allWallets != null && allWallets.size() > 0) {
                                            ETHWalletModel tempModel = allWallets.get(0);
                                            tempModel.setCurrentSelect(1);
                                            WalletDaoUtils.updateWallet(tempModel);
                                        }

//                                        new SuccessDialogView().showTips(WalletDetailActivity.this, getString(R.string.wallet_delete_sign));
//                                        AtyContainer.getInstance().finishAllActivity();


                                        Toast.makeText(WalletDetailActivity.this, getString(R.string.wallet_delete_sign), Toast.LENGTH_SHORT).show();

//                                        Intent mangeIntent = new Intent(WalletDetailActivity.this, XchainMainThreeActivity.class);
//                                        startActivity(mangeIntent);

                                        XchainMainThreeActivity.reStart(WalletDetailActivity.this);

//                                        AtyContainer.getInstance().finishAllActivity();
//                                        Intent mangeIntent = new Intent(WalletDetailActivity.this, AccountManageActivity.class);
//                                        startActivity(mangeIntent);
//                                        finish();
                                    }
                                } else {
                                    Toast.makeText(WalletDetailActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

//                new CommonInputPwdDialogView().showTips(WalletDetailActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
//                    @Override
//                    public void closeClick() {
//
//                    }
//
//                    @Override
//                    public void sureClick(String contentVal) {
//                        if (null == contentVal || contentVal.length() == 0) {
//                            Toast.makeText(WalletDetailActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
//                            return;
//                        } else {
//                            ETHWalletModel currentModel = WalletDaoUtils.getCurrent();
//                            if (contentVal.equals(currentModel.getPassword())) {
//                                List<ETHWalletModel> allWallet = WalletDaoUtils.loadAll();
//                                if (allWallet.size() == 1) {
//                                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//                                    WalletDaoUtils.deleteWallet(walletModel);
//                                    Intent mangeIntent = new Intent(WalletDetailActivity.this, WalletSelectActivity.class);
//                                    startActivity(mangeIntent);
//                                    finish();
//                                } else {
//
//                                    ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//                                    WalletDaoUtils.deleteWallet(walletModel);
//                                    AtyContainer.getInstance().finishAllActivity();
//                                    Intent mangeIntent = new Intent(WalletDetailActivity.this, AccountManageActivity.class);
//                                    startActivity(mangeIntent);
//                                    finish();
//                                }
//                            } else {
//                                Toast.makeText(WalletDetailActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });


                break;

        }
    }


    private void updatePwd(String pwd) {
        walletModel.setPassword(pwd);
        WalletDaoUtils.updateWallet(walletModel);
        dismissDialog();
        Toast.makeText(WalletDetailActivity.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        walletName = (String) getIntent().getStringExtra("walletName");
        walletId = (Long) getIntent().getLongExtra("id", 0);

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        btnRight = findViewById(R.id.btnRight);
        txtMoney = findViewById(R.id.txtMoney);
        imgHead = findViewById(R.id.imgHead);
        layWalletName = findViewById(R.id.layWalletName);
        layWalletChangePassword = findViewById(R.id.layWalletChangePassword);
        layExportPrivateKey = findViewById(R.id.layExportPrivateKey);
        layExportKeystore = findViewById(R.id.layExportKeystore);
        walletNameSign = findViewById(R.id.wallet_name_sign);
        delBtn = findViewById(R.id.btnDeleteWallet);
        btnRight.setVisibility(View.GONE);

        title.setText( getString(R.string.wallet_detail_name_two));

        if (walletName != null) {

            walletNameSign.setText(walletName);
        }

        if (walletId != 0) {
            walletModel = WalletDaoUtils.getWalletById(walletId);
        }


        layWalletName.setOnClickListener(this);
        layBack.setOnClickListener(this);
        layWalletChangePassword.setOnClickListener(this);
        layExportPrivateKey.setOnClickListener(this);
        layExportKeystore.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        delBtn.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_detail;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(WalletNameEvent nameEvent) {
        String updateNameVal = nameEvent.getName();
//        title.setText(updateNameVal + getString(R.string.wallet_detail_name));
        walletNameSign.setText(updateNameVal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
