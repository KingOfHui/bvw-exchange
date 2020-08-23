package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.model.BtcDo;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.view.TypefaceTextView;
import com.darknet.bvw.wallet.BtcWalletUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhangchen
 * <p>
 * 你的钱包助记词activity
 */
public class WalletZjcActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    private Button btnNext;
    private TypefaceTextView createWords;
    private QMUIFloatLayout mWordsContent;

    //助记词
    private TextView wordsContentView;

    //助记词
    List<String> mnemonic;

//    private String privateKey;
//    private String publicKey;
//    private String addressVals;
    BtcDo btcDo;


    private String tnameVal;
    private String tpwdVal;


    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        tnameVal = getIntent().getStringExtra("usrName");
        tpwdVal = getIntent().getStringExtra("pwd");

        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        btnNext = findViewById(R.id.btnNext);
        createWords = findViewById(R.id.walletCreateWords);
        wordsContentView = findViewById(R.id.walletCreateWords);
        mWordsContent = findViewById(R.id.qfl_content);

        title.setText(getString(R.string.zjc_beifen_title));


//        if (pathArray.length <= 1) {
//            //内容不对
//            return null;
//        }
//        try {
//            Wallet wallet = WalletUtils.CreateWallet("wang","1234");
//            Log.e("wallet","walletModel="+wallet.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        btnNext.setOnClickListener(this);
        layBack.setOnClickListener(this);

        showDialog(getString(R.string.load_data));

        btcDo = BtcWalletUtils.create();

        dismissDialog();

        if(btcDo == null){
            return;
        }

        mnemonic = btcDo.getListZjc();

        StringBuffer wordsContent = new StringBuffer();
        mWordsContent.removeAllViews();
        for (int i = 0; i < mnemonic.size(); i++) {
            String tempWord = mnemonic.get(i);
            /*if (i == (mnemonic.size() - 1)) {
//                Log.e("wallet", "种子:" + tempWord);
                wordsContent.append(tempWord);
            } else {
                wordsContent.append(tempWord + " ");
            }*/
            View view = View.inflate(this,R.layout.view_typeface_text_view, null);;
            TextView tv = view.findViewById(R.id.walletCreateWords);
            tv.setText(tempWord);
            mWordsContent.addView(view);
        }

//        Log.e("wallet", "助记词:" + wordsContent.toString());

        wordsContentView.setText(wordsContent.toString());

//        wordsContentView.setOnTouchListener();


        mWordsContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, wordsContent.toString());
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(WalletZjcActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                rockAction();
                return false;
            }
        });


//        privateKey = btcDo.getPrivateKey();
//        publicKey = btcDo.getPublicKey();
//        addressVals = btcDo.getAddress();


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_zjc_v2;
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
                WalletZjcActivity.this.finish();
                break;
            case R.id.btnNext:
                Intent checkIntent = new Intent(WalletZjcActivity.this, CheckZjcActivity.class);
                checkIntent.putExtra("walletModel",(Serializable)btcDo);
                checkIntent.putExtra("words", (Serializable) mnemonic);
                checkIntent.putExtra("usrName",tnameVal);
                checkIntent.putExtra("pwd",tpwdVal);
                startActivity(checkIntent);
                break;
        }
    }


    //震动
    private void rockAction() {
        TipHelper.Vibrate(WalletZjcActivity.this, new long[]{200, 300}, false);
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
