package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.util.QrCodeUtil;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.view.TypefaceTextView;

public class MyQrActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    //    private SimpleDraweeView imgQr, imgHead;
    private TextView copyAddress;

//    private String addRessVal;

    private ImageView qrImg;
    private TypefaceTextView addRessView;

    private String brcAddress;
    private String moneyType;
    private TextView erweimaSign;

    private TextView gonglianUnder;

    @Override
    public void initView() {
        brcAddress = getIntent().getStringExtra("brcAddress");
        moneyType = getIntent().getStringExtra("moneyType");

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
//        imgQr = findViewById(R.id.imgQr);
//        imgHead = findViewById(R.id.imgHead);
//        nickName = findViewById(R.id.nickName);
        qrImg = findViewById(R.id.qr_img);
        addRessView = findViewById(R.id.qr_address_content);

        copyAddress = findViewById(R.id.qr_copy_address);

        erweimaSign = findViewById(R.id.erweima_sign_view);

        gonglianUnder = findViewById(R.id.erweima_gonglian_under);
        gonglianUnder.setText("BTW"+getResources().getString(R.string.gonglian_sign)+moneyType);


        erweimaSign.setText(getResources().getString(R.string.qr_erweima_code_two) + moneyType +" "+ getResources().getString(R.string.qr_erweima_code_two_two));
//        title.setText(getString(R.string.qr_erweima_title_two));
        title.setText(moneyType + " " + getString(R.string.qr_erweima_title_three));
        erweimaSign.setVisibility(View.GONE);


        layBack.setOnClickListener(this);

        copyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, brcAddress);
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(MyQrActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_qr;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//        addRessVal = walletModel.getAddress();

        addRessView.setText(brcAddress);

        Bitmap bitmap = QrCodeUtil.createQRCode(brcAddress, 200, 200, null);
        qrImg.setImageBitmap(bitmap);

    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layBack:
                MyQrActivity.this.finish();
                break;
        }
    }


    //震动
    private void rockAction() {
        TipHelper.Vibrate(MyQrActivity.this, new long[]{200, 300}, false);
    }


}
