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

public class MyQrTwoActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    //    private SimpleDraweeView imgQr, imgHead;
    private TextView copyAddress;

//    private String addRessVal;

    private ImageView qrImg;
    private TypefaceTextView addRessView;

    private String mainAddress;
    private String moneyType;

    private TextView erweimaView;

    private TextView zhulianUnder;

    @Override
    public void initView() {
        mainAddress = getIntent().getStringExtra("mainAddress");
        moneyType = getIntent().getStringExtra("moneyType");

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);

//        imgQr = findViewById(R.id.imgQr);
//        imgHead = findViewById(R.id.imgHead);
//        nickName = findViewById(R.id.nickName);

        qrImg = findViewById(R.id.qr_img);
        addRessView = findViewById(R.id.qr_address_content);
        copyAddress = findViewById(R.id.qr_copy_address);

        erweimaView = findViewById(R.id.erwema_sign_viwe);
        zhulianUnder = findViewById(R.id.erwema_main_line_view);

//        title.setText(getString(R.string.qr_erweima_title_one));


        if (moneyType.equalsIgnoreCase("usdt")) {
            title.setText(moneyType + " " + getString(R.string.shoukuan_code) + " (ERC20)");
            erweimaView.setText(getResources().getString(R.string.qr_erweima_code_one) + "Ethereum ERC20 USDT" + getResources().getString(R.string.gonglian_sign) + moneyType + getResources().getString(R.string.qr_erweima_code_one_one));
            zhulianUnder.setText("Ethereum " + "ERC20 " + moneyType);
        } else {
            title.setText(moneyType + " " + getString(R.string.shoukuan_code) + " (" + moneyType + getResources().getString(R.string.gonglian_sign) + ")");
            erweimaView.setText(getResources().getString(R.string.qr_erweima_code_one) + moneyType + getResources().getString(R.string.gonglian_sign) + getResources().getString(R.string.qr_erweima_code_one_one));
            zhulianUnder.setText(moneyType + getResources().getString(R.string.gonglian_sign));
        }

//
//

        layBack.setOnClickListener(this);

        copyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, mainAddress);
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(MyQrTwoActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_qr_two;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//        addRessVal = walletModel.getAddress();


        addRessView.setText(mainAddress);
        Bitmap bitmap = QrCodeUtil.createQRCode(mainAddress, 200, 200, null);
        qrImg.setImageBitmap(bitmap);

    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layBack:
                MyQrTwoActivity.this.finish();
                break;
        }
    }


    //震动
    private void rockAction() {
        TipHelper.Vibrate(MyQrTwoActivity.this, new long[]{200, 300}, false);
    }


}
