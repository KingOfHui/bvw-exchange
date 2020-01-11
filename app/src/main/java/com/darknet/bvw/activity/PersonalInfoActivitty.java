package com.darknet.bvw.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.darknet.bvw.R;
import com.darknet.bvw.view.TypefaceTextView;

public class PersonalInfoActivitty extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;
    private TypefaceTextView title;


    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        title.setText("个人信息");
        layBack.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
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
                PersonalInfoActivitty.this.finish();
                break;
        }
    }
}
