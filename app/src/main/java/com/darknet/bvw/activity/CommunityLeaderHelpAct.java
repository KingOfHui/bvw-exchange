package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.view.CustomAttachPopup;
import com.darknet.bvw.view.TypefaceTextView;
import com.lxj.xpopup.XPopup;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CommunityLeaderHelpAct extends BaseActivity {

    @BindView(R.id.layBack)
    RelativeLayout layBack;

    @BindView(R.id.title)
    TypefaceTextView tvTitle;

    @BindView(R.id.title_right)
    TypefaceTextView titleRight;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CommunityLeaderHelpAct.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        tvTitle.setText(R.string.title_com_leader_help);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_community_leader_help;
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

    @OnClick({R.id.layBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                finish();
                break;
        }
    }

}
