package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

public class ExportKeyStoreActivity extends BaseActivity {

    private RelativeLayout layBack;
    private TypefaceTextView title;

    private Long walletId;
    ETHWalletModel walletModel;

    private EditText storeContentView;
    private Button shareView;

    @Override
    public void initView() {
        walletId = (Long) getIntent().getLongExtra("id", 0);

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);

        title.setText(getString(R.string.export_key_title));

        if (walletId != 0) {
            walletModel = WalletDaoUtils.getWalletById(walletId);
        }

        storeContentView = findViewById(R.id.store_content_view);
        shareView = findViewById(R.id.store_share);

        storeContentView.setText(walletModel.getKeyStoreVal());

        storeContentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, walletModel.getKeyStoreVal());
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(ExportKeyStoreActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();

                return false;
            }
        });


        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, walletModel.getKeyStoreVal());
                //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
                shareIntent = Intent.createChooser(shareIntent, getString(R.string.export_key_share));
                startActivity(shareIntent);
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
        return R.layout.activity_export_keystore;
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

    //震动
    private void rockAction() {
        TipHelper.Vibrate(ExportKeyStoreActivity.this, new long[]{200, 300}, false);
    }
}
