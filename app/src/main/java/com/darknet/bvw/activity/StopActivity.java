package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.darknet.bvw.R;

public class StopActivity extends BaseActivity {

//    private TextView titleContent;
//
//    private RelativeLayout backLayout;

//    private TextView versionName;

    private TextView stopSureView;

    @Override
    public void initView() {

        stopSureView = findViewById(R.id.stop_sure_btn);

//        titleContent = findViewById(R.id.title);
//        backLayout = findViewById(R.id.layBack);
//        titleContent.setText(getString(R.string.about_us_title));
//        versionName = findViewById(R.id.about_version_view);
//
//
//        backLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        versionName.setText("V" + getCurVersionName(this));


        stopSureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(StopActivity.this, XchainMainThreeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stop;
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

    public static String getCurVersionName(Context ctx) {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
