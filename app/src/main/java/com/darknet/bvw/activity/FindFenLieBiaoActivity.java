package com.darknet.bvw.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.util.language.SPUtil;

public class FindFenLieBiaoActivity extends BaseActivity {

    private TextView titleContent;

    private RelativeLayout backLayout;

    private TextView versionName;

    private ImageView fenlieBiaoImg;

    @Override
    public void initView() {

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
        titleContent.setText(getString(R.string.fenlie_order_header_title_sign));
        versionName = findViewById(R.id.about_version_view);
        fenlieBiaoImg= findViewById(R.id.fenlie_biao_img_bg);


        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        int lanType = SPUtil.getInstance(FindFenLieBiaoActivity.this).getSelectLanguage();
        if (lanType == 0) {
            fenlieBiaoImg.setImageResource(R.mipmap.jishubiao_bg_img);
        } else if (lanType == 1) {
            fenlieBiaoImg.setImageResource(R.mipmap.jishubiao_bg_img_en);
        } else {
            fenlieBiaoImg.setImageResource(R.mipmap.jishubiao_bg_img_en);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_fenlie_biao;
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
