package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.view.ChangeWalletSureDialogView;
import com.darknet.bvw.view.CommonInputPwdDialogView;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangchen
 */
public class AccountManageActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    private RecyclerView mRecyclerView;
    private List<ETHWalletModel> walletList = new ArrayList<>();

    private Button importBtn;
    private LinearLayout walletLayout;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);

        importBtn = findViewById(R.id.wallet_content_import);
        walletLayout = findViewById(R.id.wallet_content_list);

        title.setText(getString(R.string.account_manage_title));
        layBack.setOnClickListener(this);
        importBtn.setOnClickListener(this);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_manage;
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
            case R.id.layBack:
                AccountManageActivity.this.finish();
                break;
            case R.id.wallet_content_import:
                Intent importIntent = new Intent(AccountManageActivity.this, LeadInAccountActivity.class);
                startActivity(importIntent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWallet();
    }

    //加载钱包数据
    private void loadWallet() {
        List<ETHWalletModel> allWallets = WalletDaoUtils.loadAll();

//        Log.e("wallet", allWallets.toString());

        walletLayout.removeAllViews();
        for (int i = 0; i < allWallets.size(); i++) {
            View walletItem = (View) LayoutInflater.from(AccountManageActivity.this).inflate(R.layout.item_account, null);
            TypefaceTextView nickNameView = (TypefaceTextView) walletItem.findViewById(R.id.wallet_item_nickname);
            TypefaceTextView addressView = (TypefaceTextView) walletItem.findViewById(R.id.wallet_item_address);
            Button btnManage = (Button) walletItem.findViewById(R.id.btnManage);
            TypefaceTextView txtCurrentAccount = (TypefaceTextView) walletItem.findViewById(R.id.txt_current_account);
            ETHWalletModel ethWalletModel = allWallets.get(i);

//            Log.e("wallet", ethWalletModel.getName());


            nickNameView.setText(TextUtils.isEmpty(ethWalletModel.getName()) ? "BIW-Wallet-" + i : ethWalletModel.getName());

            String addressVals = ethWalletModel.getAddress();
            StringBuilder sb = new StringBuilder();
            if (addressVals != null) {
                sb.append(addressVals.substring(0, 7));
                sb.append("...");
                sb.append(addressVals.substring((addressVals.length() - 5), addressVals.length()));
            }

            ETHWalletModel selectWallet = WalletDaoUtils.getCurrent();
            if (ethWalletModel.getAddress().equals(selectWallet.getAddress()) && ethWalletModel.getName().equals(selectWallet.getName())) {
                txtCurrentAccount.setVisibility(View.VISIBLE);
            } else {
                txtCurrentAccount.setVisibility(View.GONE);
            }

            addressView.setText(sb.toString());

            //点击item
            walletItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ethWalletModel.getAddress().equals(selectWallet.getAddress()) && ethWalletModel.getName().equals(selectWallet.getName())) {
                        //不处理
                    } else {


                        new ChangeWalletSureDialogView().showTips(AccountManageActivity.this, new ChangeWalletSureDialogView.OnCloseListener() {
                            @Override
                            public void closeClick() {

                            }

                            @Override
                            public void sureClick(String contentVal) {
                                setPwd(ethWalletModel);
                            }
                        });
                    }
                }
            });

            //管理按钮点击
            btnManage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ETHWalletModel selectWallet = WalletDaoUtils.getCurrent();

                    if (ethWalletModel.getAddress().equals(selectWallet.getAddress()) && ethWalletModel.getName().equals(selectWallet.getName())) {
                        //不处理
                        Intent detailIntent = new Intent(AccountManageActivity.this, WalletDetailActivity.class);
                        detailIntent.putExtra("walletName", ethWalletModel.getName());
                        detailIntent.putExtra("id", ethWalletModel.getId());
                        startActivity(detailIntent);
                    } else {


                        new ChangeWalletSureDialogView().showTips(AccountManageActivity.this, new ChangeWalletSureDialogView.OnCloseListener() {
                            @Override
                            public void closeClick() {

                            }

                            @Override
                            public void sureClick(String contentVal) {
                                setPwd(ethWalletModel);
                            }
                        });

//                        new CommonInputPwdDialogView().showTips(AccountManageActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
//                            @Override
//                            public void closeClick() {
//
//                            }
//
//                            @Override
//                            public void sureClick(String contentVal) {
//                                if (null == contentVal || contentVal.length() == 0) {
//                                    Toast.makeText(AccountManageActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
//                                    return;
//                                } else {
//                                    if (contentVal.equals(ethWalletModel.getPassword())) {
//                                        Intent detailIntent = new Intent(AccountManageActivity.this, WalletDetailActivity.class);
//                                        detailIntent.putExtra("walletName", ethWalletModel.getName());
//                                        detailIntent.putExtra("id", ethWalletModel.getId());
//                                        startActivity(detailIntent);
//                                    }else {
//                                        Toast.makeText(AccountManageActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        });

                    }
                }
            });

            walletLayout.addView(walletItem);
        }
    }


    private void setPwd(ETHWalletModel ethWalletModel) {
        new CommonInputPwdDialogView().showTips(AccountManageActivity.this, new CommonInputPwdDialogView.OnCloseListener() {
            @Override
            public void closeClick() {

            }

            @Override
            public void sureClick(String contentVal) {
                if (null == contentVal || contentVal.length() == 0) {
                    Toast.makeText(AccountManageActivity.this, getString(R.string.empty_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (contentVal.equals(ethWalletModel.getPassword())) {
                        WalletDaoUtils.setCurrentWallet(ethWalletModel);

//                        AtyContainer.getInstance().finishAllActivity();
//
//                        Intent detailIntent = new Intent(AccountManageActivity.this, XchainMainActivity.class);
//                        startActivity(detailIntent);


//                        XchainMainActivity.reStart(this);
                        restartMain();
                    } else {
                        Toast.makeText(AccountManageActivity.this, getString(R.string.pwd_check_wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void restartMain(){
        XchainMainThreeActivity.reStart(this);
        finish();
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
