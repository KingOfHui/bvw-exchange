package com.darknet.bvw.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.MoneyTypeAdapter;
import com.darknet.bvw.model.event.MoneyTypeEvent;
import com.darknet.bvw.model.response.MoneyTypeResponse;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MoneyTypeActivity extends BaseActivity implements MoneyTypeAdapter.ItemViewListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;

    private ListView listView;

    private MoneyTypeAdapter moneyTypeAdapter;

    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        listView = findViewById(R.id.money_type_listview);
        title.setText("选择币种");

        moneyTypeAdapter = new MoneyTypeAdapter(MoneyTypeActivity.this);
        listView.setAdapter(moneyTypeAdapter);
        moneyTypeAdapter.setItemViewListener(this);

        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Toast.makeText(MoneyTypeActivity.this,"...here..click..",Toast.LENGTH_SHORT).show();

                MoneyTypeResponse.MoneyTypeModel moneyTypeModel = (MoneyTypeResponse.MoneyTypeModel)adapterView.getItemAtPosition(i);
                MoneyTypeEvent typeEvent = new MoneyTypeEvent();
                typeEvent.setName(moneyTypeModel.getSymbol());
                EventBus.getDefault().post(typeEvent);
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_money_type;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
//        netCheck();
    }

    @Override
    public void configViews() {

    }


//    private void netCheck() {
//
//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//
//        String privateKey = walletModel.getPrivateKey();
//        String addressVals = walletModel.getAddress();
//
//        String msg = "" + System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
//
//        Log.e("backVal", "privateKey=" + privateKey);
//        Log.e("backVal", "msg=" + msg);
//        Log.e("backVal", "signVal=" + signVal);
//
//        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.MONEY_TYPE_URL)
//                .tag(MoneyTypeActivity.this)
//                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
//                            Log.e("backVal", "backVal=" + backVal);
//                            if (backVal != null) {
//                                Gson gson = new Gson();
//                                MoneyTypeResponse response = gson.fromJson(backVal, MoneyTypeResponse.class);
//                                if (response != null && response.getCode() == 0 && response.getData() != null) {
//                                    setListVal(response.getData());
//                                }
//                            }
//                        }
//                    }
//                });
//    }


    private void setListVal(List<MoneyTypeResponse.MoneyTypeModel> data) {
        moneyTypeAdapter.addData(data);

    }

    @Override
    public void itemClick(MoneyTypeResponse.MoneyTypeModel friendListModel) {


    }


}
