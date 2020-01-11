package com.darknet.bvw.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.WordsAdapter;
import com.darknet.bvw.model.BtcDo;
import com.darknet.bvw.model.WordsModel;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CheckZjcActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;

//    private List<String> words;

    private List<WordsModel> listModels = new ArrayList<>();

    private GridView gridView;

//    private List<String> selectVals = new ArrayList<>();
    private List<WordsModel> selectModels = new ArrayList<>();

    WordsAdapter wordsAdapter;

    private EditText showWordView;
    private Button submitView;

    private String correctVal;

    private BtcDo btcDo;

    private String tnameVal;
    private String tpwdVal;

    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        tnameVal = getIntent().getStringExtra("usrName");
        tpwdVal = getIntent().getStringExtra("pwd");

        List<String> words = (List<String>) getIntent().getSerializableExtra("words");

        btcDo = (BtcDo) getIntent().getSerializableExtra("walletModel");

        title = findViewById(R.id.title);
        layBack = findViewById(R.id.layBack);
        gridView = findViewById(R.id.check_zjc_select_btn);
        showWordView = findViewById(R.id.check_zjc_content);
        submitView = findViewById(R.id.check_zjc_submit);

        title.setText(getString(R.string.zjc_beifen_title));


        StringBuffer sBuffer = new StringBuffer();

        for (int i = 0; i < words.size(); i++) {
            if (i == (words.size() - 1)) {
                sBuffer.append(words.get(i));
            } else {
                sBuffer.append(words.get(i) + ",");
            }

        }

        correctVal = sBuffer.toString();


        //打乱顺序
        shuffle2(words);

        for (int i = 0; i < words.size(); i++) {
            WordsModel wordsModel = new WordsModel();
            wordsModel.setSign(0);
            wordsModel.setContent(words.get(i));
            listModels.add(wordsModel);
        }

        layBack.setOnClickListener(this);
        submitView.setOnClickListener(this);

        wordsAdapter = new WordsAdapter(CheckZjcActivity.this, listModels);
        gridView.setAdapter(wordsAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WordsModel wordsModel = listModels.get(i);
                if (wordsModel.getSign() == 0) {
                    //代表是选择的
//                    selectVals.add(wordsModel.getContent());
                    selectModels.add(wordsModel);
                } else {
                    //取消选择
//                    selectVals.remove(wordsModel.getContent());
                    selectModels.remove(wordsModel);
                }
                setWordsContent();
                handleSelectWord(wordsModel);
            }
        });

    }

    private void setWordsContent() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < selectModels.size(); i++) {
            if (i == (selectModels.size() - 1)) {
                sb.append(selectModels.get(i).getContent());
            } else {
                sb.append(selectModels.get(i).getContent() + " ");
            }
        }

//        for (int i = 0; i < selectVals.size(); i++) {
//            if (i == (selectVals.size() - 1)) {
//                sb.append(selectVals.get(i));
//            } else {
//                sb.append(selectVals.get(i) + " ");
//            }
//        }
        showWordView.setText(sb.toString());
    }


    private void handleSelectWord(WordsModel wordsModel) {
        for (int i = 0; i < listModels.size(); i++) {
            WordsModel tempModel = listModels.get(i);
            if (tempModel.getContent().endsWith(wordsModel.getContent())) {
                if (tempModel.getSign() == 0) {
                    tempModel.setSign(1);
                } else {
                    tempModel.setSign(0);
                }
            }
        }
        wordsAdapter.notifyDataSetChanged();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_check_zjc;
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
                CheckZjcActivity.this.finish();
                break;
            case R.id.check_zjc_submit:
//                Toast.makeText(CheckZjcActivity.this, "22", Toast.LENGTH_SHORT).show();
                checkSubmitVal();
                break;
        }
    }


    private void checkSubmitVal() {
        String tempSelectWrods;

        StringBuffer stringBuffer = new StringBuffer();




        for (int i = 0; i < selectModels.size(); i++) {
            if (i == (selectModels.size() - 1)) {
                stringBuffer.append(selectModels.get(i).getContent());
            } else {
                stringBuffer.append(selectModels.get(i).getContent() + ",");
            }
        }

//        for (int i = 0; i < selectVals.size(); i++) {
//            if (i == (selectVals.size() - 1)) {
//                stringBuffer.append(selectVals.get(i));
//            } else {
//                stringBuffer.append(selectVals.get(i) + ",");
//            }
//        }

        tempSelectWrods = stringBuffer.toString();


//        Intent createIntent = new Intent(CheckZjcActivity.this, WalletCreateActivity.class);
//        createIntent.putExtra("walletModel",(Serializable)btcDo);

//        createIntent.putExtra("words",correctVal);
//        createIntent.putExtra("publicKey", publicKey);
//        createIntent.putExtra("addressVals", addressVals);
//        createIntent.putExtra("privateKey", privateKey);
//        startActivity(createIntent);


        //下面校验暂时取消

        if (tempSelectWrods == null || tempSelectWrods.trim().length() == 0) {
            Toast.makeText(CheckZjcActivity.this, getString(R.string.zjc_check_fail), Toast.LENGTH_SHORT).show();
        }

        if (correctVal.equals(tempSelectWrods)) {
            Toast.makeText(CheckZjcActivity.this, getString(R.string.zjc_check_successs), Toast.LENGTH_SHORT).show();


            //助记词，创建成功之后，即可保存数据
//            showDialog(getString(R.string.loading_wallet_tip));
//            ETHWalletModel walletModel = new ETHWalletModel();
//            walletModel.setAddress(btcDo.getAddress());
//            walletModel.setMnemonic(wordsVals);
//            walletModel.setName(walletName);
//            walletModel.setPassword(walletPasswrod);
////                walletModel.setCurrent(true);
//            walletModel.setKeyStoreVal(btcDo.getKeyStoreVal());
//            walletModel.setPrivateKey(btcDo.getPrivateKey());
//            walletModel.setPublicKey(btcDo.getPublicKey());
//            walletModel.setImportType(0);
//            walletModel.setCurrentSelect(1);
//
//            WalletDaoUtils.insertNewWallet(walletModel);
//            dismissDialog();


            Intent createIntent = new Intent(CheckZjcActivity.this, BackupPrivateKeyTwoActivity.class);
            createIntent.putExtra("walletModel", (Serializable) btcDo);
            createIntent.putExtra("words", tempSelectWrods);
            createIntent.putExtra("usrName",tnameVal);
            createIntent.putExtra("pwd",tpwdVal);
            startActivity(createIntent);


        } else {
            Toast.makeText(CheckZjcActivity.this, getString(R.string.zjc_check_fail), Toast.LENGTH_SHORT).show();
        }

    }


    private void shuffle2(List<String> list) {
        int size = list.size();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // 获取随机位置
            int randomPos = random.nextInt(size);
            // 当前元素与随机元素交换
            Collections.swap(list, i, randomPos);
        }
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
