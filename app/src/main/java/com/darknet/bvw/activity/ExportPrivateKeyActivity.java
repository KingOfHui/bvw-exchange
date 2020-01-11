package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.view.TypefaceTextView;

/**
 * 导出私钥
 */
public class ExportPrivateKeyActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    private EditText editPassword;
    private TypefaceTextView editPrivateKey;

    private Long walletId;
    ETHWalletModel walletModel;

    private Button checkOutPrivateKey;

    @Override
    public void initView() {

        walletId = (Long) getIntent().getLongExtra("id", 0);

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        editPassword = findViewById(R.id.editPassword);
        editPrivateKey = findViewById(R.id.editPrivateKey);
        checkOutPrivateKey = findViewById(R.id.checkout_privateKey);

        title.setText(getString(R.string.wallet_detail_export_store));

        if (walletId != 0) {
            walletModel = WalletDaoUtils.getWalletById(walletId);
        }

        checkOutPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pwdContent = editPassword.getText().toString();
                if (null == pwdContent || pwdContent.trim().length() == 0) {
                    Toast.makeText(ExportPrivateKeyActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (walletModel.getPassword().equals(pwdContent)) {
                        editPrivateKey.setText(walletModel.getPrivateKey());
                        Toast.makeText(ExportPrivateKeyActivity.this, getString(R.string.long_click_copy_content), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ExportPrivateKeyActivity.this, getString(R.string.wrong_pwd), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        editPrivateKey.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, walletModel.getPrivateKey());
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(ExportPrivateKeyActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
                return false;
            }
        });


        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_export_private_key;
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

    }

    //震动
    private void rockAction() {
        TipHelper.Vibrate(ExportPrivateKeyActivity.this, new long[]{200, 300}, false);
    }


}
