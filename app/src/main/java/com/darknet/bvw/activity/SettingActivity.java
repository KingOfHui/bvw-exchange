package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.cq.library.utils.member.ActivityResult;
import com.darknet.bvw.BuildConfig;
import com.darknet.bvw.R;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.UpdateApkResponse;
import com.darknet.bvw.util.Language;
import com.darknet.bvw.util.UpdateAppHttpUtil;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.TypefaceTextView;
import com.darknet.bvw.view.UpdateDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateDialogFragment;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;

    private LinearLayout layLanguage;

    private LinearLayout layOrderExpire;

    private LinearLayout layAboutUs;

    private RelativeLayout layCheckUpdate;

    private Button btnExit;


    private TextView versionView;

    private String jumpUrl;



    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        layLanguage = findViewById(R.id.layLanguage);
        layOrderExpire = findViewById(R.id.layOrderExpire);
        layAboutUs = findViewById(R.id.layAboutUs);
        layCheckUpdate = findViewById(R.id.layCheckUpdate);
        btnExit = findViewById(R.id.btnExit);
        versionView = findViewById(R.id.setting_version_val);

        title.setText(getString(R.string.set_title));

        layBack.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        layLanguage.setOnClickListener(this);
        layCheckUpdate.setOnClickListener(this);
        layAboutUs.setOnClickListener(this);

        layCheckUpdate.setEnabled(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        checkUpdate();
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                finish();
                break;
            case R.id.layLanguage:
                SelectLanguageActivity.start(SettingActivity.this
                        , (resultCode, result) -> {
                            if(resultCode == RESULT_OK) {
                                XchainMainThreeActivity.reStart(SettingActivity.this);
                            }
                        });
                break;
            case R.id.layOrderExpire:
                break;
            case R.id.layAboutUs:
                Intent aboutIntent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.layCheckUpdate:
                showDialog();
                break;
            case R.id.btnExit:
//                AtyContainer.getInstance().finishAllActivity();
                Intent welIntent = new Intent(SettingActivity.this, WelcomeActivity.class);
                startActivity(welIntent);
                break;
        }
    }


//    private void setJumpAction(){


        private void showDialog(){
            new UpdateDialog(new UpdateDialog.UpdateItemClick() {
                @Override
                public void updateClick() {
                    Intent intent= new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://bitw.im/download.html");
                    intent.setData(content_url);
                    startActivity(intent);
                }

                @Override
                public void dismissClick() {

                }
            }).showTips(SettingActivity.this,"");
        }


//        Intent intent= new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse(jumpUrl);
//        intent.setData(content_url);
//        startActivity(intent);

//    }

    private void checkUpdate() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.UPDATE_URL)
                .tag(SettingActivity.this)
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
                                    UpdateApkResponse response = gson.fromJson(backVal, UpdateApkResponse.class);
                                    if (response != null) {
                                        if (response.getCode() == 0) {
                                            setUpdateData(response.getData());
                                        } else {
                                            Toast.makeText(SettingActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
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
                        try {
                            dismissDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void setUpdateData(UpdateApkResponse.UpdateModel updateData){

        if(updateData == null){
            return;
        }

        String version = updateData.getVersion();
        if (getVersionCode(BuildConfig.VERSION_NAME) < getVersionCode(version)) {
            Language language = Language.readFromConfig();
            jumpUrl = updateData.getAbroad_android_url();
            if (language == Language.CHINA) {
                jumpUrl = updateData.getInternal_android_url();
            }
//            showDialog(updateData);
//        }

//        jumpUrl = updateData.getUrl_address();

//        int curversioncode = getCurVersionCode(SettingActivity.this);
//
//        Log.e("versioncode","curversioncode="+curversioncode);
//
//        if (curversioncode < Integer.parseInt(updateData.getVersion_code())) {
//            updateApkDialog(response.getData());
            versionView.setText(getString(R.string.update_version_num)+" "+updateData.getVersion());
            layCheckUpdate.setEnabled(true);
        } else {
            versionView.setText(getString(R.string.update_newset_notice));
//            Toast.makeText(SettingActivity.this, getString(R.string.update_compare_sign), Toast.LENGTH_SHORT).show();
        }
    }
    private float getVersionCode(String version) {
        if (TextUtils.isEmpty(version)) {
            return 0;
        }

        int index = version.indexOf(".");
        if (index < 0) {
            return toFloat(version);
        }

        if (index == version.length() - 1) {
            return toFloat(version.substring(0, index));
        }

        String prefix = version.substring(0, index + 1);
        String suffix = version.substring(index + 1).replace(".", "");
        return toFloat(prefix + suffix);
    }

    public float toFloat(String number) {
        try {
            return Float.parseFloat(number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getCurVersionCode(Context ctx) {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }


    private void updateApkDialog(UpdateApkResponse.UpdateModel updateModel) {

//        Log.d(TAG, "onlyDownload() called with: view = [" + view + "]");
        UpdateAppBean updateAppBean = new UpdateAppBean();

        //设置 apk 的下载地址
        updateAppBean.setApkFileUrl(updateModel.getUrl_address());
        updateAppBean.setNewVersion("新");

        String content = updateModel.getContent();

        updateAppBean.setUpdateLog(content);

        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
            try {
                path = getExternalCacheDir().getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {
            path = getCacheDir().getAbsolutePath();
        }

        //设置apk 的保存路径
        updateAppBean.setTargetPath(path);
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());


//        UpdateAppManager.


        Bundle bundle = new Bundle();
        //添加信息，
//        fillUpdateAppData();
        bundle.putSerializable("update_dialog_values", updateAppBean);
//        if (mThemeColor != 0) {
//            bundle.putInt(THEME_KEY, mThemeColor);
//        }
//
//        if (mTopPic != 0) {
//            bundle.putInt(TOP_IMAGE_KEY, mTopPic);
//        }

        UpdateDialogFragment
                .newInstance(bundle)
//                .setUpdateDialogFragmentListener(mUpdateDialogFragmentListener)
                .show(((FragmentActivity) SettingActivity.this).getSupportFragmentManager(), "dialog");


    }


}
