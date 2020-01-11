package com.darknet.bvw.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.darknet.bvw.R;
import com.darknet.bvw.view.TypefaceTextView;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layBack;

    private TypefaceTextView title;

    private EditText editAccountName, editPassword, editRepeatPassword;

    private Button btnNext;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                CreateAccountActivity.this.finish();
                break;
        }
    }

    @Override
    public void initView() {
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        editAccountName = findViewById(R.id.editAccountName);
        editPassword = findViewById(R.id.editPassword);
        editRepeatPassword = findViewById(R.id.editRepeatPassword);

        btnNext = findViewById(R.id.btnNext);
        title.setText(getString(R.string.create_aacoutn_view));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_account;
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
}
