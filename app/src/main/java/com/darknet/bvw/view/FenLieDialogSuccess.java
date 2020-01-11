package com.darknet.bvw.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.FenLieOrderResponse;
import com.darknet.bvw.util.QrCodeUtil;
import com.darknet.bvw.util.TipHelper;

import java.text.NumberFormat;

public class FenLieDialogSuccess {

//    private static final int CHECK_ECG_DATA = 111;
//    //10秒检测一次
//    private Handler mHandlerTwo = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            if (msg.what == CHECK_ECG_DATA) {
//                EventBus.getDefault().post(new PushEvent());
//
//                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
//                    ad.dismiss();
//                }
//                ((Activity) context).finish();
//                return true;
//            }
//            return false;
//        }
//    });


    AlertDialog ad;
    Context context;

    public void showTips(final Context context, String content, FenLieOrderResponse.FLOrderItemModel orderItemModel) {

        this.context = context;
        try {
            //剪切板Data对象
            ad = new AlertDialog.Builder(context).create();
            ad.show();
            ad.setContentView(R.layout.dialog_fenlie_success);
            ad.setCanceledOnTouchOutside(true);
            //在弹框上显示软键盘，注意此行需要放到setContentView下面
            ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            TextView percentView = (TextView) ad.findViewById(R.id.fenlie_success_percent_view);
            TextView numView = (TextView) ad.findViewById(R.id.fenlie_success_num_view);
            TextView jiaoyihaoView = (TextView) ad.findViewById(R.id.fenlie_success_jiaoyihao_view);
            TextView qukuaiView = (TextView) ad.findViewById(R.id.fenlie_success_qukuai_view);
            TextView timeView = (TextView) ad.findViewById(R.id.fenlie_success_time_view);

            Button copyView = (Button) ad.findViewById(R.id.get_url_view);
            ImageView imgView = (ImageView) ad.findViewById(R.id.imgQr);


            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(0);
            float tempFloat = Float.parseFloat(orderItemModel.getBack_rate());
            String result = numberFormat.format(tempFloat * 100);
            percentView.setText(result+"%");

            numView.setText(orderItemModel.getBack_bvw() + "");

            String addressVals = orderItemModel.getTx_hash();
            StringBuilder sb = new StringBuilder();
            if(addressVals!=null){
                sb.append(addressVals.substring(0,5));
                sb.append("...");
                sb.append(addressVals.substring((addressVals.length()-5),addressVals.length()));
            }

            jiaoyihaoView.setText(sb.toString());
            qukuaiView.setText(orderItemModel.getHeight());
            timeView.setText(orderItemModel.getCreate_at());
//            mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 2000);

            Bitmap bitmap = QrCodeUtil.createQRCode(orderItemModel.getTx_hash(), 200, 200, null);
            imgView.setImageBitmap(bitmap);

            copyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(orderItemModel.getTx_hash())) {
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                        ClipData clipData = ClipData.newPlainText(null, orderItemModel.getTx_hash());
                        // 把数据集设置（复制）到剪贴板
                        clipboard.setPrimaryClip(clipData);
                        Toast.makeText(context, context.getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                        rockAction();
                    }
                }
            });

//            final Timer t = new Timer();
//            t.schedule(new TimerTask() {
//                public void run() {
//                    try {
//                        t.cancel();
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }, 2000);


//            shouKuanView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String shoukuanVal = shouKuanView.getText().toString();
//                    if (!TextUtils.isEmpty(shoukuanVal)) {
//                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
//                        ClipData clipData = ClipData.newPlainText(null, shoukuanVal);
//                        // 把数据集设置（复制）到剪贴板
//                        clipboard.setPrimaryClip(clipData);
//                        Toast.makeText(TradeDetailActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
//                        rockAction();
//                    }
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //震动
    private void rockAction() {
        TipHelper.Vibrate((Activity) context, new long[]{200, 300}, false);
    }


}
