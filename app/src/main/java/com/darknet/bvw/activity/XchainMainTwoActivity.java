package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.MineFragment;
import com.darknet.bvw.activity.fragment.MoneyFragment;
import com.darknet.bvw.activity.fragment.MsgFragment;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.event.CloseViewEvent;
import com.darknet.bvw.model.event.PushEvent;
import com.darknet.bvw.model.response.UserInfoResponse;
import com.darknet.bvw.util.UserSPHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.next.easynavigation.view.EasyNavigationBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

//1.0
public class XchainMainTwoActivity extends BaseActivity {
    private EasyNavigationBar navigitionBar;

//    private String[] tabText = {"首页", "资产", "发现", "消息", "我的"};
    private String[] tabText = new String[3];
//    private int[] normalIcon = {R.mipmap.img_home_unpressed,
//            R.mipmap.img_fund_unpressed,
//            R.mipmap.img_fill, R.mipmap.img_message_unpressed, R.mipmap.img_mine_unpressed};

//    private int[] normalIcon = {
//            R.mipmap.img_fund_unpressed,R.mipmap.img_fill_unpressed, R.mipmap.img_message_unpressed, R.mipmap.img_mine_unpressed};

    private int[] normalIcon = {
            R.mipmap.img_fund_unpressed, R.mipmap.img_message_unpressed, R.mipmap.img_mine_unpressed};



    //选中时icon
//    private int[] selectIcon = {R.mipmap.img_home_pressed,
//            R.mipmap.img_fund_pressed,
//            R.mipmap.img_fill, R.mipmap.img_message_pressed, R.mipmap.img_mine_pressed};

//    private int[] selectIcon = {R.mipmap.img_fund_pressed,R.mipmap.img_fill, R.mipmap.img_message_pressed, R.mipmap.img_mine_pressed};

    private int[] selectIcon = {R.mipmap.img_fund_pressed,R.mipmap.img_message_pressed, R.mipmap.img_mine_pressed};


    List<Fragment> fragmentList = new ArrayList<>();


    @Override
    public void initView() {

        EventBus.getDefault().register(this);

        //发送关闭事件
        EventBus.getDefault().post(new CloseViewEvent());


        tabText[0]=getResources().getString(R.string.xmain_table_one);
//        tabText[1]=getResources().getString(R.string.xmain_table_find);
        tabText[1]=getResources().getString(R.string.xmain_table_two);
        tabText[2]=getResources().getString(R.string.xmain_table_three);

//        tabText[0]=getResources().getString(R.string.xmain_table_one);
//        tabText[1]=getResources().getString(R.string.xmain_table_find);
//        tabText[2]=getResources().getString(R.string.xmain_table_two);
//        tabText[3]=getResources().getString(R.string.xmain_table_three);

//       tabText = {getResources().getString(R.string.xmain_table_one),getResources().getString(R.string.xmain_table_two), getResources().getString(R.string.xmain_table_three)};


        navigitionBar = findViewById(R.id.navigitionBar);



//        Fragment homeFragment = new FundFragment();
        Fragment moneyFragment = new MoneyFragment();
//        Fragment findFragment = new FindFragment();
        Fragment academicFragment = new MsgFragment();
        Fragment mineFragment = new MineFragment();

//        fragmentList.add(homeFragment);
        fragmentList.add(moneyFragment);
//        fragmentList.add(findFragment);
        fragmentList.add(academicFragment);
        fragmentList.add(mineFragment);

        navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .iconSize(20)
                .tabTextSize(10)
                .tabTextTop(2)
                .fragmentList(fragmentList)
                .canScroll(true)
                .hasPadding(true)
                .navigationBackground(Color.parseColor("#171522"))
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .lineHeight(1)         //分割线高度  默认1px
                .lineColor(Color.parseColor("#171522"))
                .addIconSize(20)
                .addAsFragment(true)
                .normalTextColor(Color.parseColor("#807e8c"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#72f8db"))
                .fragmentManager(getSupportFragmentManager())
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int i) {
                        return false;
                    }
                })
                .mode(EasyNavigationBar.MODE_NORMAL)
                .build();



    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xchain_main;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {
        netCheck();
    }

    @Override
    public void configViews() {

    }


    private void netCheck(){

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();

        String msg = ""+System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg,privateKey);

//        Log.e("backVal","privateKey="+privateKey);
//        Log.e("backVal","msg="+msg);
//        Log.e("backVal","signVal="+signVal);

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL+ UrlPath.LOGIN_URL)
                .tag(XchainMainTwoActivity.this)
                .headers("Chain-Authentication",addressVals+"#"+msg+"#"+signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if(backVal != null){
                                Gson gson = new Gson();
                                UserInfoResponse response = gson.fromJson(backVal, UserInfoResponse.class);
                                if(response != null && response.getCode() == 0 ){

                                    if(response.getData() != null && response.getData().getNickname() != null){
                                        UserSPHelper.setParam(XchainMainTwoActivity.this, "nickName", response.getData().getNickname());
                                    }

                                }
                            }
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static void reStart(Context context) {
        Intent intent = new Intent(context, XchainMainTwoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddress(PushEvent nameEvent) {
        navigitionBar.selectTab(1);
    }

}
