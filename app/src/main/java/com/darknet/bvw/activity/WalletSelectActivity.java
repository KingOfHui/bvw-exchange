package com.darknet.bvw.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;

import com.cq.library.utils.member.ActivityResult;
import com.darknet.bvw.R;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.util.Language;
import com.darknet.bvw.view.TypefaceTextView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 主页选择钱包的方式
 */
public class WalletSelectActivity extends BaseActivity implements View.OnClickListener {

    //创建
    private Button createView;
    //引入
    private Button importView;

    private static final int REQUEST_WRITE_STORAGE = 112;
	private TypefaceTextView mTvLanguage;


    @Override
    protected void onResume() {
        super.onResume();
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features what required the permission
                } else {
//                    ToastUtils.showToast("The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission");
                }
            }
        }

    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_create_wallte;
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
    public void initView() {
        EventBus.getDefault().register(this);

        createView = (Button) this.findViewById(R.id.wallet_select_create_view);
        importView = (Button) this.findViewById(R.id.wallet_select_import_view);
		mTvLanguage = findViewById(R.id.tv_language);

		mTvLanguage.setText(Language.readFromConfig().name);
        createView.setOnClickListener(this);
        importView.setOnClickListener(this);
		mTvLanguage.setOnClickListener(v ->
				SelectLanguageActivity.start(WalletSelectActivity.this,
						(resultCode, result) -> WalletSelectActivity.this.restart()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_select_create_view:

//                Intent creatIntent = new Intent(WalletSelectActivity.this, WalletZjcActivity.class);
                Intent creatIntent = new Intent(WalletSelectActivity.this, WalletCreateActivity.class);
                startActivity(creatIntent);
                break;
            case R.id.wallet_select_import_view:
                Intent importIntent = new Intent(WalletSelectActivity.this, LeadInAccountActivity.class);
                startActivity(importIntent);

//                Intent importIntent = new Intent(WalletSelectActivity.this, TestWebView.class);
//                startActivity(importIntent);
//                Intent manageIntent = new Intent(WalletSelectActivity.this, AccountManageActivity.class);
////                Intent manageIntent = new Intent(WalletSelectActivity.this, XchainMainActivity.class);
//                startActivity(manageIntent);
                break;
        }
    }

    private void netCheck() {
        HttpParams paramsPost = new HttpParams();
        OkGo.<String>post("")
                .tag(WalletSelectActivity.this)
                .params(paramsPost)
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();

                            }
                        }
                    }
                });
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
