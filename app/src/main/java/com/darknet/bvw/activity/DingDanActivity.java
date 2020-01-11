package com.darknet.bvw.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darknet.bvw.R;

public class DingDanActivity extends BaseActivity {

    private TextView titleContent;

    private RelativeLayout backLayout;

    private TextView versionName;

    @Override
    public void initView() {

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
        titleContent.setText(getString(R.string.about_us_title));
        versionName = findViewById(R.id.about_version_view);


        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        versionName.setText("V" + getCurVersionName(this));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dingdan_list;
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
