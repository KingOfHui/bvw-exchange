package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.util.QrCodeUtil;
import com.darknet.bvw.util.TipHelper;

public class BidZhenMaActivity extends BaseActivity {


    private LinearLayout buyView;

    private TextView tilteView;
    private ImageView backView;
    private String tidVal;

    private ImageView zhenmaImgView;
    private TextView zhenmaView;

    private TextView copyView;
    private TextView saveView;

    @Override
    public void initView() {

        tidVal = getIntent().getStringExtra("lid");

        buyView = (LinearLayout)this.findViewById(R.id.bid_buy_sub_view);
        tilteView = (TextView)this.findViewById(R.id.title);
        backView = (ImageView)this.findViewById(R.id.back_sign_view);

        zhenmaView = (TextView)this.findViewById(R.id.zhenma_val_view);
        zhenmaImgView = (ImageView)this.findViewById(R.id.zhenma_img_view);

        copyView = (TextView)this.findViewById(R.id.zhenma_copy_view);
        saveView = (TextView)this.findViewById(R.id.zhenma_save_view);

        tilteView.setText(getString(R.string.find_bid_zhen_ma));
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        zhenmaView.setText(tidVal);

        Bitmap bitmap = QrCodeUtil.createQRCode(tidVal,200,200,null);
        zhenmaImgView.setImageBitmap(bitmap);

        copyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, tidVal);
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(BidZhenMaActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
            }
        });

        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = QrCodeUtil.createQRCode(tidVal,200,200,null);
                QrCodeUtil.saveImageToGallery(BidZhenMaActivity.this,bitmap);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bid_zhenma;
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
        TipHelper.Vibrate(BidZhenMaActivity.this, new long[]{200, 300}, false);
    }

}
