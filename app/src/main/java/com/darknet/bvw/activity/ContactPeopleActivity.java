package com.darknet.bvw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.adapter.ContactPeopleAdapter;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.AddressEvent;
import com.darknet.bvw.model.request.GetContactPeopleRequest;
import com.darknet.bvw.model.response.PeopleListResponse;
import com.darknet.bvw.util.TipHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.TypefaceTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ContactPeopleActivity extends BaseActivity implements ContactPeopleAdapter.OnItemClickListener {

    private RelativeLayout layBack;
    private Button btnRight;
    private TypefaceTextView title;

    private ContactPeopleAdapter contactPeopleAdapter;

    private RecyclerView mRecyclerView;
    //    private SmartRefreshLayout mRefreshLayout;
    private GridLayoutManager gridLayoutManager;


    private LinearLayout noDataLayout;
    private LinearLayout haveDataLayout;

    //0来自个人中心，1来自转账页面
    private int fromType;

    @Override
    public void initView() {

        fromType = getIntent().getIntExtra("type", 0);



        layBack = findViewById(R.id.layBack);
        btnRight = findViewById(R.id.btnRight);
        title = findViewById(R.id.title);
        btnRight.setText(getString(R.string.add_contack__people_right));
        title.setText(getString(R.string.add_contack__people_title));

        mRecyclerView = findViewById(R.id.people_rv);
//        mRefreshLayout = findViewById(R.id.refreshLayout);
        noDataLayout = findViewById(R.id.layNoData);
        haveDataLayout = findViewById(R.id.peo_ple_have_data);
        //开始下拉
//        mRefreshLayout.setEnableRefresh(true);//启用刷新
//        mRefreshLayout.setEnableLoadMore(false);//启用加载

        gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        contactPeopleAdapter = new ContactPeopleAdapter(this);
        mRecyclerView.setAdapter(contactPeopleAdapter);
        contactPeopleAdapter.setOnItemClickListener(this);


        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(ContactPeopleActivity.this, CreateNewContactPeople.class);
                startActivity(addIntent);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_people;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
//        getData();
//        setData();
    }

    @Override
    public void configViews() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    //获取地址列表
    private void getData() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        GetContactPeopleRequest tradeRequest = new GetContactPeopleRequest();
        tradeRequest.setPage(1);
        tradeRequest.setLimit(100);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);


        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.PEOPLE_LIST_URL)
                .tag(ContactPeopleActivity.this)
                .upRequestBody(requestBody)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
//                            Log.e("backVal", "backVal=" + backVal);


                            if (backVal != null) {

                                try {
                                    Gson gson = new Gson();
                                    PeopleListResponse response = gson.fromJson(backVal, PeopleListResponse.class);
                                    if (requestBody != null && response.getCode() == 0 && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                                        setData(response.getData().getItems());
                                    } else {
                                        setNoData();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    setNoData();
                                }

//                                Log.e("backVal", "backVal=" + response.toString());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        setNoData();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissDialog();
                    }
                });
    }

    private void setData(List<PeopleListResponse.PeopleModel> datas) {
        noDataLayout.setVisibility(View.GONE);
        haveDataLayout.setVisibility(View.VISIBLE);
        contactPeopleAdapter.addData(datas);
        contactPeopleAdapter.notifyDataSetChanged();
    }


    //设置数据
    private void setNoData() {
        noDataLayout.setVisibility(View.VISIBLE);
        haveDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(PeopleListResponse.PeopleModel peopleModel) {

        if(fromType == 0){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            ClipData clipData = ClipData.newPlainText(null, peopleModel.getAddress());
            // 把数据集设置（复制）到剪贴板
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(ContactPeopleActivity.this, getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
            rockAction();
        }else {
            AddressEvent addressEvent = new AddressEvent();
            addressEvent.setAddress(peopleModel.getAddress());
            EventBus.getDefault().post(addressEvent);
            finish();
        }
    }

    //震动
    private void rockAction() {
        TipHelper.Vibrate(ContactPeopleActivity.this, new long[]{200, 300}, false);
    }

}
