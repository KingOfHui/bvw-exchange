package com.darknet.bvw.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.PrivateKeyFragment;
import com.darknet.bvw.activity.fragment.ZjcFragment;
import com.darknet.bvw.adapter.AccountViewPager;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.view.TypefaceTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangchen
 * <p>
 * 导入用户activity
 */
public class LeadInAccountActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, View.OnClickListener {
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private RadioButton radioZjc, radioKeyStore, radioPrivateKey;
    private AccountViewPager adapter;
    private List<Fragment> list;
    private int currentFragment;
    private RelativeLayout layBack;
    private TypefaceTextView title;

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        radioZjc = findViewById(R.id.radioZjc);
        radioKeyStore = findViewById(R.id.radioKeyStore);
        radioPrivateKey = findViewById(R.id.radioPrivateKey);
        viewPager = findViewById(R.id.viewPager);
        radioGroup = findViewById(R.id.radioGroup);
        layBack = findViewById(R.id.layBack);
        title = findViewById(R.id.title);
        title.setText(getString(R.string.lead_into_wallet));
        initFragment();
        adapter = new AccountViewPager(getSupportFragmentManager(), this, list);
        viewPager.setAdapter(adapter);

        layBack.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBack:
                LeadInAccountActivity.this.finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_leadin;
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

    public void initFragment() {
        list = new ArrayList<>();
        list.add(new ZjcFragment());
//        list.add(new KeyStoreFragment());
        list.add(new PrivateKeyFragment());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radioZjc:
                currentFragment = 0;
                break;
//            case R.id.radioKeyStore:
//                currentFragment = 1;
//                break;
            case R.id.radioPrivateKey:
                currentFragment = 1;
                break;
        }
        viewPager.setCurrentItem(currentFragment);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.radioZjc);
                break;
//            case 1:
//                radioGroup.check(R.id.radioKeyStore);
//                break;
            case 1:
                radioGroup.check(R.id.radioPrivateKey);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
