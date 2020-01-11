package com.darknet.bvw.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.CheckSignResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class QianDaoDialog {

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
    TextView signPowView;
    TextView qianDaoView;

    public void showTips(final Context context, String content, QianDaoClick qianDaoClick) {

        this.context = context;
        try {
            //剪切板Data对象
            ad = new AlertDialog.Builder(context).create();
            ad.show();
            ad.setContentView(R.layout.dialog_qiandao_task);
            ad.setCanceledOnTouchOutside(true);
            //在弹框上显示软键盘，注意此行需要放到setContentView下面
            ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            qianDaoView = (TextView) ad.findViewById(R.id.qiandao_dialog_view);
            signPowView = (TextView)ad.findViewById(R.id.qiandao_yes_val_view);

//            contentView.setText(content);
            checkSign();

            qianDaoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, context.getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
                    qianDaoClick.qiandao();
//                    ad.dismiss();
                }
            });

//            mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 2000);


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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dissDia() {
        ad.dismiss();
    }


    public interface QianDaoClick {
        void qiandao();
    }


    private void checkSign() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.SIGN_CHECK_URL)
                .tag(context)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    CheckSignResponse response = gson.fromJson(backVal, CheckSignResponse.class);
                                    if (response != null) {
                                        if (response.getCode() == 0) {
                                            qianDaoView.setEnabled(false);
                                            qianDaoView.setText(context.getResources().getString(R.string.sign_success_notice));
                                        } else {

                                        }
                                        signPowView.setText(response.getData().getSignPower().stripTrailingZeros().toPlainString());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        dismissDialog();
                    }
                });
    }


    private void setSignData(CheckSignResponse.SignModel signModel) {

    }


}
