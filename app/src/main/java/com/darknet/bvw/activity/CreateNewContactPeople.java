package com.darknet.bvw.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.request.AddContackPeopleRequest;
import com.darknet.bvw.model.response.BaseResponse;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.FailDialogView;
import com.darknet.bvw.view.SuccessDialogView;
import com.darknet.bvw.view.TypefaceTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateNewContactPeople extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;
    private EditText editXing, editMing, editAddress, editPhone, editEmail, editRemark;

    private TextView finishView;

    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);

        editXing = findViewById(R.id.editXing);
        editMing = findViewById(R.id.editMing);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editRemark = findViewById(R.id.editRemark);
        finishView = findViewById(R.id.add_people_finish_view);

        title.setText(getString(R.string.add_contack_create_new_people_title));

        layBack.setOnClickListener(this);
        finishView.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_new_contact_people;
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
                CreateNewContactPeople.this.finish();
                break;
            case R.id.add_people_finish_view:

                String remarkVal = editRemark.getText().toString();
                String addRessVal = editAddress.getText().toString();

                if (editRemark != null && editRemark.length() > 10) {
                    Toast.makeText(CreateNewContactPeople.this, getString(R.string.add_contact_remark_sign), Toast.LENGTH_LONG).show();
                    return;
                }

//                if(TxtUtil.isBTCValidAddress(addRessVal)){
//                    Toast.makeText(CreateNewContactPeople.this,getString(R.string.add_contack_wrong_wallet_address),Toast.LENGTH_LONG).show();
//                    return;
//                }
                saveData(remarkVal, addRessVal);
                break;
        }
    }

    private void saveData(String remarkVal, String addRessVal) {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        AddContackPeopleRequest tradeRequest = new AddContackPeopleRequest();
        tradeRequest.setAddress(addRessVal);
        tradeRequest.setRemark(remarkVal);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonVal = gson.toJson(tradeRequest);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonVal);
        showDialog(getString(R.string.load_data));

        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.ADD_PEOPLE_URL)
                .tag(CreateNewContactPeople.this)
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
                                    BaseResponse response = gson.fromJson(backVal, BaseResponse.class);
                                    if (response != null && response.getCode() == 0) {
                                        new SuccessDialogView().showTips(CreateNewContactPeople.this, getString(R.string.add_contack_address_success));
                                    } else {
                                        new FailDialogView().showTips(CreateNewContactPeople.this, getString(R.string.add_contack_address_fail));
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissDialog();
                    }
                });


//        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
//        String privateKey = walletModel.getPrivateKey();
//        String addressVals = walletModel.getAddress();
//
//        String msg = ""+System.currentTimeMillis();
//        String signVal = BitcoinjKit.signMessageBy58(msg,privateKey);
//
//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);
//
//        OkGo.<String>get(ConfigNetWork.JAVA_API_URL+ UrlPath.ADD_PEOPLE_URL)
//                .tag(CreateNewContactPeople.this)
//                .headers("Chain-Authentication",addressVals+"#"+msg+"#"+signVal)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> backresponse) {
//                        if (backresponse != null) {
//                            String backVal = backresponse.body();
//
//                            Log.e("backVal","backVal="+backVal);
//
//                            if (backVal != null) {
//                                Gson gson = new Gson();
//
//                            }
//                        }
//                    }
//                });
    }


}
