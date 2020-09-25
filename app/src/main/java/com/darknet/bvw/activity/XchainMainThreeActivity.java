package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.darknet.bvw.BuildConfig;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.ExchangeFragment;
import com.darknet.bvw.activity.fragment.FindFragment;
import com.darknet.bvw.activity.fragment.FirstFragment;
import com.darknet.bvw.activity.fragment.MineFragment;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.model.event.PushEvent;
import com.darknet.bvw.model.event.TradeEvent;
import com.darknet.bvw.model.response.UpdateApkResponse;
import com.darknet.bvw.model.response.UserInfoResponse;
import com.darknet.bvw.util.Language;
import com.darknet.bvw.util.UserSPHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.view.SmartFragmentStatePagerAdapter;
import com.darknet.bvw.view.UpdateDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class XchainMainThreeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
//    private EasyNavigationBar navigitionBar;

    //    private String[] tabText = {"首页", "资产", "发现", "消息", "我的"};
//    private String[] tabText = new String[4];
//    private int[] normalIcon = {R.mipmap.img_home_unpressed,
//            R.mipmap.img_fund_unpressed,
//            R.mipmap.img_fill, R.mipmap.img_message_unpressed, R.mipmap.img_mine_unpressed};

//    private int[] normalIcon = {
//            R.mipmap.img_fund_unpressed, R.mipmap.img_fill_unpressed, R.mipmap.img_message_unpressed, R.mipmap.img_mine_unpressed};
//
//
//    private int[] selectIcon = {R.mipmap.img_fund_pressed, R.mipmap.img_fill, R.mipmap.img_message_pressed, R.mipmap.img_mine_pressed};

    List<Fragment> fragmentList = new ArrayList<>();


    private LinearLayout fristLayout, secLayout, /*thirdLayout,*/ fourLayout;
    private ImageView firstImg, secImg, /*thirdImg,*/ fourImg;
    private TextView firstTxt, secTxt, /*thirdTxt,*/ fourTxt;
    private ViewPager viewPager;

    private LinearLayout bomLayout;


    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        fristLayout = this.findViewById(R.id.main_first_layout);
        firstImg = this.findViewById(R.id.main_first_img);
        firstTxt = this.findViewById(R.id.main_first_txt);

        secLayout = this.findViewById(R.id.main_second_layout);
        secImg = this.findViewById(R.id.main_second_img);
        secTxt = this.findViewById(R.id.main_second_txt);

//        thirdLayout = this.findViewById(R.id.main_third_layout);
//        thirdImg = this.findViewById(R.id.main_third_img);
//        thirdTxt = this.findViewById(R.id.main_third_txt);

        fourLayout = this.findViewById(R.id.main_four_layout);
        fourImg = this.findViewById(R.id.main_four_img);
        fourTxt = this.findViewById(R.id.main_four_txt);

        viewPager= this.findViewById(R.id.main_viewpager);

        bomLayout = this.findViewById(R.id.main_bom_layout);

        //        Fragment homeFragment = new FundFragment();
        FirstFragment moneyFragment = new FirstFragment();
        Fragment findFragment = new FindFragment();
//        Fragment academicFragment = new MsgFragment();
//        Fragment academicFragment = new ExchangeFragment();
        Fragment mineFragment = new MineFragment();

//        fragmentList.add(homeFragment);
        fragmentList.add(moneyFragment);
        fragmentList.add(findFragment);
//        fragmentList.add(academicFragment);
        fragmentList.add(mineFragment);

        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(new SmartFragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });


//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragmentList.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragmentList.size();
//            }
//        });



        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
        tableFirst();


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        fristLayout.setOnClickListener(this);
        secLayout.setOnClickListener(this);
//        thirdLayout.setOnClickListener(this);
        fourLayout.setOnClickListener(this);




        //发送关闭事件
        EventBus.getDefault().post(new CloseViewEvent());


//        tabText[0] = getResources().getString(R.string.xmain_table_one);
//        tabText[1] = getResources().getString(R.string.xmain_table_find);
//        tabText[2] = getResources().getString(R.string.xmain_table_two);
//        tabText[3] = getResources().getString(R.string.xmain_table_three);

//       tabText = {getResources().getString(R.string.xmain_table_one),getResources().getString(R.string.xmain_table_two), getResources().getString(R.string.xmain_table_three)};


//        navigitionBar = findViewById(R.id.navigitionBar);




//        navigitionBar.titleItems(tabText)
//                .normalIconItems(normalIcon)
//                .selectIconItems(selectIcon)
//                .iconSize(20)
//                .tabTextSize(10)
//                .tabTextTop(2)
//                .fragmentList(fragmentList)
//                .hasPadding(true)
//                .navigationBackground(Color.parseColor("#171522"))
//                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
//                .lineHeight(1)         //分割线高度  默认1px
//                .lineColor(Color.parseColor("#171522"))
//                .addIconSize(20)
//                .canScroll(false)    //Viewpager能否左右滑动
//                .addAsFragment(true)
//                .normalTextColor(Color.parseColor("#807e8c"))   //Tab未选中时字体颜色
//                .selectTextColor(Color.parseColor("#72f8db"))
//                .fragmentManager(getSupportFragmentManager())
//                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
//                    @Override
//                    public boolean onTabClickEvent(View view, int i) {
//                        return false;
//                    }
//                })
//                .mode(EasyNavigationBar.MODE_NORMAL)
//                .build();





    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xchain_main_three;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        netCheck();
        checkUpdate();
    }

    @Override
    public void configViews() {

    }



    private void checkUpdate() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

        showDialog(getString(R.string.load_data));

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.UPDATE_URL)
                .tag(XchainMainThreeActivity.this)
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
                                            Toast.makeText(XchainMainThreeActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
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
        if (!BuildConfig.VERSION_NAME.equals(version)) {
            showDialog(updateData);
        }

    }


    private void showDialog(UpdateApkResponse.UpdateModel updateData){
        new UpdateDialog(new UpdateDialog.UpdateItemClick() {
            @Override
            public void updateClick() {
                Language language = Language.readFromConfig();
                String url = updateData.getAbroad_android_url();
                if (language == Language.CHINA) {
                    url = updateData.getInternal_android_url();
                }
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }

            @Override
            public void dismissClick() {

            }
        }).showTips(XchainMainThreeActivity.this,"");
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


    private void netCheck() {

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.LOGIN_URL)
                .tag(XchainMainThreeActivity.this)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();

                                try {
                                    UserInfoResponse response = gson.fromJson(backVal, UserInfoResponse.class);
                                    if (response != null && response.getCode() == 0) {

                                        if (response.getData() != null && response.getData().getNickname() != null) {
                                            UserSPHelper.setParam(XchainMainThreeActivity.this, "nickName", response.getData().getNickname());

                                            if (response.getData().getVip_level() != null && response.getData().getVip_level().equals("1")) {
                                                UserSPHelper.setParam(XchainMainThreeActivity.this, "viplever", "1");
                                                Log.e("viplever", "....yes...");
                                            } else {
                                                Log.e("viplever", "....no...");
                                                UserSPHelper.setParam(XchainMainThreeActivity.this, "viplever", "0");
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });
    }




//    private int[] normalIcon = {
//            R.mipmap.img_fund_unpressed, R.mipmap.img_fill_unpressed, R.mipmap.img_message_unpressed, R.mipmap.img_mine_unpressed};
//
//
//    private int[] selectIcon = {R.mipmap.img_fund_pressed, R.mipmap.img_fill, R.mipmap.img_message_pressed, R.mipmap.img_mine_pressed};


    private void tableFirst() {
        viewPager.setCurrentItem(0);
        firstImg.setImageResource(R.mipmap.img_home_pressed);
        secImg.setImageResource(R.mipmap.img_fill_unpressed);
//        thirdImg.setImageResource(R.mipmap.home_trade_normal);
        fourImg.setImageResource(R.mipmap.img_mine_unpressed);


        firstTxt.setTextColor(getResources().getColor(R.color._72f8db));
        secTxt.setTextColor(getResources().getColor(R.color._807e8c));
//        thirdTxt.setTextColor(getResources().getColor(R.color._807e8c));
        fourTxt.setTextColor(getResources().getColor(R.color._807e8c));
    }

    private void tableSec() {
        viewPager.setCurrentItem(1);
        firstImg.setImageResource(R.mipmap.img_home_unpressed);
        secImg.setImageResource(R.mipmap.img_fill);
//        thirdImg.setImageResource(R.mipmap.home_trade_normal);
        fourImg.setImageResource(R.mipmap.img_mine_unpressed);

        firstTxt.setTextColor(getResources().getColor(R.color._807e8c));
        secTxt.setTextColor(getResources().getColor(R.color._72f8db));
//        thirdTxt.setTextColor(getResources().getColor(R.color._807e8c));
        fourTxt.setTextColor(getResources().getColor(R.color._807e8c));
    }

/*    private void tableThird() {
        viewPager.setCurrentItem(2);
        firstImg.setImageResource(R.mipmap.img_home_unpressed);
        secImg.setImageResource(R.mipmap.img_fill_unpressed);
//        thirdImg.setImageResource(R.mipmap.home_trade_press);
        fourImg.setImageResource(R.mipmap.img_mine_unpressed);

        firstTxt.setTextColor(getResources().getColor(R.color._807e8c));
        secTxt.setTextColor(getResources().getColor(R.color._807e8c));
//        thirdTxt.setTextColor(getResources().getColor(R.color._72f8db));
        fourTxt.setTextColor(getResources().getColor(R.color._807e8c));
    }*/


    private void tableFour() {
        viewPager.setCurrentItem(2);
        firstImg.setImageResource(R.mipmap.img_home_unpressed);
        secImg.setImageResource(R.mipmap.img_fill_unpressed);
//        thirdImg.setImageResource(R.mipmap.home_trade_normal);
        fourImg.setImageResource(R.mipmap.img_mine_pressed);

        firstTxt.setTextColor(getResources().getColor(R.color._807e8c));
        secTxt.setTextColor(getResources().getColor(R.color._807e8c));
//        thirdTxt.setTextColor(getResources().getColor(R.color._807e8c));
        fourTxt.setTextColor(getResources().getColor(R.color._72f8db));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_first_layout:
                tableFirst();
                break;
            case R.id.main_second_layout:
                tableSec();
                break;
//            case R.id.main_third_layout:
//                tableThird();
//                break;
            case R.id.main_four_layout:
                tableFour();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                tableFirst();
                break;
            case 1:
                tableSec();
                break;
            case 2:
//                tableThird();
//                break;
//            case 3:
                tableFour();
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

    public static void reStart(Context context) {
        Intent intent = new Intent(context, XchainMainThreeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(PushEvent nameEvent) {
//        viewPager.setCurrentItem(2);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveTradeEvent(TradeEvent tradeEvent) {
        if(tradeEvent.getType() == 0){
            bomLayout.setVisibility(View.GONE);
        }else {
            bomLayout.setVisibility(View.VISIBLE);
        }
//        viewPager.setCurrentItem(2);
    }

}
