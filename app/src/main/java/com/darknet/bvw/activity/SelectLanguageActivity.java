package com.darknet.bvw.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cq.library.utils.member.ActivityResult;
import com.cq.library.utils.member.IntentHelper;
import com.darknet.bvw.R;
import com.darknet.bvw.util.SharedPreferencesUtil;
import com.darknet.bvw.util.language.LocalManageUtil;

import androidx.fragment.app.FragmentActivity;

public class SelectLanguageActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    public static void start(FragmentActivity context, ActivityResult result) {
        Intent starter = new Intent(context, SelectLanguageActivity.class);
        IntentHelper.startActivityForResult(context, starter, result);
    }

    private RelativeLayout backView;
    private TextView titleView;

    private RadioGroup radioGroup;
    private int current = 0;

    @Override
    public void initView() {
        backView = findViewById(R.id.layBack);
        titleView = findViewById(R.id.title);
        titleView.setText(getResources().getString(R.string.language));
        radioGroup = findViewById(R.id.radioGroup);
        String select = (String) SharedPreferencesUtil.getData("select", "0");
        if (TextUtils.isEmpty(select)) {
            radioGroup.check(R.id.radioChinese);
            current++;
        }
        if ("0".equals(select)) {
//            radioGroup.check(R.id.radioFollowSystem);
            radioGroup.check(R.id.radioEnglish);
            current++;
        } else if ("1".equals(select)) {
            radioGroup.check(R.id.radioEnglish);
            current++;
        } else if ("2".equals(select)) {
            radioGroup.check(R.id.radioChinese);
            current++;
        }

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        radioGroup.setOnCheckedChangeListener(this);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_language;
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

//    private String getStystemLanguage() {
//        String language = getSystemLocale(this).getLanguage();
//        return language;
//
//    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);
        setResult(RESULT_OK, new Intent());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (current != 0) {
            switch (checkedId) {
                case R.id.radioFollowSystem:
//                    selectLanguage(2);
//                    String language = getStystemLanguage();
//                    System.out.println("选择时候的语言"+language);
//                    if ("zh".equals(language)) {
//                        selectLanguage(0);
//                    } else if ("en".equals(language)) {
//                        selectLanguage(1);
//                    } else {
//                        selectLanguage(1);
//                    }

                    selectLanguage(2);
                    SharedPreferencesUtil.putData("select", "0");
                    break;
                case R.id.radioEnglish:
                    selectLanguage(1);
                    SharedPreferencesUtil.putData("select", "1");
                    break;
                case R.id.radioChinese:
                    selectLanguage(0);
                    SharedPreferencesUtil.putData("select", "2");
                    break;
            }

        }
        current++;
    }

}