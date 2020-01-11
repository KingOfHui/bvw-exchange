package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.util.QrCodeUtil;
import com.darknet.bvw.util.TipHelper;

import java.math.BigDecimal;

/**
 * 订单详情
 */
public class TradeDetailActivity extends BaseActivity implements View.OnClickListener {


    private TextView faKuanView;
    private TextView shouKuanView;

    private TextView kuangFeiView;

    private TextView beiZhuView;

    private TextView jiaoYiHaoView;

    private TextView qukuaiView;

    private TextView jiaoyiTimeView;

    private ImageView imgView;

    private TextView numAccountView;

    TradeListResponse.TradeListModel tradeListModel;

    private RelativeLayout backView;

    private Button getUrlView;

    private TextView titleView;

    private TextView accountTypeView;

    private ImageView paySignView;

    String createUrl = "";

    @Override
    public void initView() {

        tradeListModel = (TradeListResponse.TradeListModel) getIntent().getSerializableExtra("tradeModel");

        faKuanView = findViewById(R.id.fakuan_view);
        shouKuanView = findViewById(R.id.shoukuan_view);
        kuangFeiView = findViewById(R.id.kuangongfei_view);
        beiZhuView = findViewById(R.id.beizhu_content_view);
        jiaoYiHaoView = findViewById(R.id.txtTradeNo);
        qukuaiView = findViewById(R.id.txtQk);
        jiaoyiTimeView = findViewById(R.id.txtTradeTime);
        imgView = findViewById(R.id.imgQr);
        backView = findViewById(R.id.layBack);
        titleView = findViewById(R.id.title);
        getUrlView = findViewById(R.id.get_url_view);

        paySignView = findViewById(R.id.sign_img_succ);


        titleView.setText(getString(R.string.trade_detail_title));
        numAccountView = findViewById(R.id.num_account_view);
        accountTypeView = findViewById(R.id.num_account_num_type);

        faKuanView.setText(tradeListModel.getFrom_address());
        shouKuanView.setText(tradeListModel.getTo_address());


        if (tradeListModel.getType() != null) {
            if (tradeListModel.getType().equals("3")) {

                if (tradeListModel.getService_fee() != null) {
                    if (tradeListModel.getService_fee().compareTo(BigDecimal.ZERO) == 0) {
                        kuangFeiView.setText("0 BVW");
                    } else {
                        kuangFeiView.setText(tradeListModel.getService_fee().stripTrailingZeros().toPlainString() + "BVW");
                    }
                } else {
                    kuangFeiView.setText("");
                }

            } else {
                if (tradeListModel.getFee().compareTo(BigDecimal.ZERO) == 0) {
                    kuangFeiView.setText("0 BVW");
                } else {
                    kuangFeiView.setText(tradeListModel.getFee().stripTrailingZeros().toPlainString() + "BVW");
                }
            }
        } else {
            if (tradeListModel.getFee().compareTo(BigDecimal.ZERO) == 0) {
                kuangFeiView.setText("0 BVW");
            } else {
                kuangFeiView.setText(tradeListModel.getFee().stripTrailingZeros().toPlainString() + "BVW");
            }
        }


        if (TextUtils.isEmpty(tradeListModel.getDemo())) {
            beiZhuView.setText(getString(R.string.trade_detail_no_data));
        } else {
            beiZhuView.setText(tradeListModel.getDemo());
        }
        jiaoyiTimeView.setText(tradeListModel.getCreate_at());

        qukuaiView.setText(tradeListModel.getHeight());

        numAccountView.setText(tradeListModel.getAmount().stripTrailingZeros().toPlainString() + "");
        accountTypeView.setText(tradeListModel.getSymbol());


        if (tradeListModel.getStatus().equals("failed")) {
            paySignView.setImageResource(R.mipmap.dialog_fail_view);
        } else {
            paySignView.setImageResource(R.mipmap.img_gou);
        }


        String addressVals = tradeListModel.getTx_hash();

//        StringBuilder sb = new StringBuilder();
//        if (addressVals != null) {
//            sb.append(addressVals.substring(0, 5));
//            sb.append("...");
//            sb.append(addressVals.substring((addressVals.length() - 5), addressVals.length()));
//        }
//        String tempAddrss = sb.toString();


        jiaoYiHaoView.setText(addressVals);


        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        String createUrl = ConfigNetWork.JAVA_API_URL + "BVW/tx/" + tradeListModel.getTx_hash();


        if (tradeListModel.getType().equals("3")) {
            createUrl = tradeListModel.getPay_tx_hash();
        } else {
            createUrl = tradeListModel.getTx_hash();
        }

        Bitmap bitmap = QrCodeUtil.createQRCode(createUrl, 200, 200, null);
        imgView.setImageBitmap(bitmap);

        getUrlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, createUrl);
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(TradeDetailActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
            }
        });


        jiaoYiHaoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jiaoiyVal = jiaoYiHaoView.getText().toString();

                if (!TextUtils.isEmpty(jiaoiyVal)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                    ClipData clipData = ClipData.newPlainText(null, jiaoiyVal);
                    // 把数据集设置（复制）到剪贴板
                    clipboard.setPrimaryClip(clipData);
                    Toast.makeText(TradeDetailActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                    rockAction();
                }
            }
        });

        faKuanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fakuanVal = faKuanView.getText().toString();

                if (!TextUtils.isEmpty(fakuanVal)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                    ClipData clipData = ClipData.newPlainText(null, fakuanVal);
                    // 把数据集设置（复制）到剪贴板
                    clipboard.setPrimaryClip(clipData);
                    Toast.makeText(TradeDetailActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                    rockAction();
                }
            }
        });

        shouKuanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shoukuanVal = shouKuanView.getText().toString();
                if (!TextUtils.isEmpty(shoukuanVal)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                    ClipData clipData = ClipData.newPlainText(null, shoukuanVal);
                    // 把数据集设置（复制）到剪贴板
                    clipboard.setPrimaryClip(clipData);
                    Toast.makeText(TradeDetailActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                    rockAction();
                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_detail;
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
        TipHelper.Vibrate(TradeDetailActivity.this, new long[]{200, 300}, false);
    }

}
