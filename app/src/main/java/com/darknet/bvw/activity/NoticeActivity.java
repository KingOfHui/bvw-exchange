package com.darknet.bvw.activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.darknet.bvw.R;
import com.darknet.bvw.model.response.NoticeResponse;

public class NoticeActivity extends BaseActivity {

    private TextView titleContent;

    private RelativeLayout backLayout;

    private NoticeResponse.NoticeData noticeData;

    private TextView contentView;


    @Override
    public void initView() {

        noticeData = (NoticeResponse.NoticeData) getIntent().getSerializableExtra("model");

        titleContent = findViewById(R.id.title);
        backLayout = findViewById(R.id.layBack);
        contentView = findViewById(R.id.notice_content);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (noticeData != null) {
            titleContent.setText(noticeData.getTitle());
            contentView.setText(noticeData.getContent());
            if (noticeData.getTitle().length() > 25) {
                titleContent.setTextSize(9f);
            } else if (noticeData.getTitle().length() > 18) {
                titleContent.setTextSize(13f);
            }
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice;
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
