package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.util.QrCodeUtil;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.util.language.SPUtil;
import com.github.jokar.multilanguages.library.MultiLanguage;

public class ShareInviteActivity extends BaseActivity {


    private LinearLayout buyView;

    private TextView tilteView;
    private ImageView backView;
    private String tidVal;

    private ImageView zhenmaImgView;
    private TextView zhenmaView;

    private TextView copyView;
    private TextView saveView;

    public static void start(Context requireContext, String invite_code) {
        Intent intent = new Intent(requireContext, ShareInviteActivity.class);
        intent.putExtra("lid", invite_code);
        requireContext.startActivity(intent);
    }

    @Override
    public void initView() {

        tidVal = getIntent().getStringExtra("lid");

        buyView = (LinearLayout)this.findViewById(R.id.bid_buy_sub_view);
        tilteView = (TextView)this.findViewById(R.id.title);
        backView = (ImageView)this.findViewById(R.id.back_sign_view);

        zhenmaView = (TextView)this.findViewById(R.id.zhenma_val_view);
        zhenmaImgView = (ImageView)this.findViewById(R.id.zhenma_img_view);
        TextView code = findViewById(R.id.tv2);

        copyView = (TextView)this.findViewById(R.id.tv3);
//        saveView = (TextView)this.findViewById(R.id.zhenma_save_view);

        tilteView.setText(R.string.invite_friends);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        code.setText(tidVal);

        Bitmap bitmap = QrCodeUtil.createQRCode("https://bitw.im/download.html",200,200,null);
        zhenmaImgView.setImageBitmap(bitmap);
        zhenmaImgView.setOnLongClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri contentUrl = Uri.parse("https://bitw.im/download.html");
            intent.setData(contentUrl);
            startActivity(intent);
            return true;
        });

        copyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, tidVal);
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(ShareInviteActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
            }
        });
        int lanType = SPUtil.getInstance(this).getSelectLanguage();
        if (lanType == 1) {
        } else {
            findViewById(R.id.tv15).setVisibility(View.VISIBLE);
            findViewById(R.id.tv6).setVisibility(View.GONE);
            findViewById(R.id.tv16).setVisibility(View.VISIBLE);
            findViewById(R.id.tvRight2).setVisibility(View.VISIBLE);
        }

//
//        saveView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bitmap bitmap = QrCodeUtil.createQRCode(tidVal,200,200,null);
//                QrCodeUtil.saveImageToGallery(ShareInviteActivity.this,bitmap);
//            }
//        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_share_invite;
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
        TipHelper.Vibrate(ShareInviteActivity.this, new long[]{200, 300}, false);
    }

}
