package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment2;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseFragment;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.databinding.ActivityIncomeRecordBinding;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.DictByKeyResponse;
import com.darknet.bvw.model.MineralBonusListResponse;
import com.darknet.bvw.util.EnvironmentUtil;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.view.TabLayoutHelper;
import com.darknet.bvw.viewmodel.MineralViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName IncomeRecordActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 15:37
 */
public class IncomeRecordActivity extends BaseBindingActivity<ActivityIncomeRecordBinding> {

    private IncomeRecordFragment mFragment;
    private IncomeRecordFragment2 mFragment1;
    private IncomeRecordFragment mFragment2;
    List<MineralBonusListResponse.ItemsBean> itemsBeans = new ArrayList<>();
    List<MineralBonusListResponse.ItemsBean> itemsBeans1 = new ArrayList<>();
    List<MineralBonusListResponse.ItemsBean> itemsBeans2 = new ArrayList<>();

    public static void startSelf(Context context) {
        context.startActivity(new Intent(context, IncomeRecordActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_record;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.income_record));
        BaseFragmentPagerAdapter<BaseFragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        mFragment = IncomeRecordFragment.newInstance(true);
        adapter.add(mFragment, getString(R.string.mineral_income));
        mFragment1 = IncomeRecordFragment2.newInstance(false);
        adapter.add(mFragment1, getString(R.string.tuijian_income));
        mFragment2 = IncomeRecordFragment.newInstance(false);
        adapter.add(mFragment2, getString(R.string.da_kuang_gong_jiang_li));
        mBinding.vpIncome.setOffscreenPageLimit(2);
        mBinding.vpIncome.setAdapter(adapter);
//        TabLayoutHelper.setupWithViewPager(mBinding.tabIncome, mBinding.vpIncome, adapter.getFragmentTitles(), 16, 14, EnvironmentUtil.getScreenWidth(this) / 3-20);
        mBinding.tabIncome.setupWithViewPager(mBinding.vpIncome);
        mBinding.tabIncome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int btwTotal = 0;
                int btcTotal = 0;
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans) {
                            btcTotal += itemsBean.getBonus_btc();
                            btwTotal += itemsBean.getBonus_miner();
                        }
                        break;
                    case 1:
                        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans1) {
                            btcTotal += itemsBean.getBonus_btc();
                            btwTotal += itemsBean.getBonus_miner();
                        }
                        break;
                    case 2:
                        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans2) {
                            btcTotal += itemsBean.getBonus_btc();
                            btwTotal += itemsBean.getBonus_miner();
                        }
                        break;
                    default:

                        break;
                }
                updateViewNum(btwTotal, btcTotal);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        int btwTotal = 0;
        int btcTotal = 0;
        for (MineralBonusListResponse.ItemsBean itemsBean : itemsBeans) {
            btcTotal += itemsBean.getBonus_btc();
            btwTotal += itemsBean.getBonus_miner();
        }
        updateViewNum(btwTotal, btcTotal);
    }

    private void updateViewNum(int btwTotal, int btcTotal) {
        mBinding.tvBtwTotal.setText(SpanHelper.start()
                .next(String.valueOf(btwTotal))
                .setTextColor(Color.parseColor("#01FCDA"))
                .setTextSize(30)
                .next(" ")
                .next("BTW")
                .setTextSize(15)
                .setTextColor(Color.WHITE)
                .get());
        mBinding.tvBtcTotal.setText(SpanHelper.start()
                .next(String.valueOf(btcTotal))
                .setTextColor(Color.parseColor("#01FCDA"))
                .setTextSize(30)
                .next(" ")
                .next("BTC")
                .setTextSize(15)
                .setTextColor(Color.WHITE)
                .get());
    }

    @Override
    public void initDatas() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Gson gson = new Gson();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", 20);
        map.put("page", 0);
        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(map));
        OkGo.<String>post(ConfigNetWork.JAVA_API_URL + UrlPath.GET_MINERAL_BONUS_LIST)
                .tag(MineralViewModel.class)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<MineralBonusListResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<MineralBonusListResponse>>() {
                                    }.getType());
                                    if (status != null && status.isSuccess()) {
                                        List<MineralBonusListResponse.ItemsBean> items = status.getData().getItems();

                                        if (items != null && items.size() > 0) {
                                            for (MineralBonusListResponse.ItemsBean item : items) {
                                                int bonus_miner = item.getBonus_miner();
                                                int bonus_refer = item.getBonus_refer();
                                                int bonus_big_node = item.getBonus_big_node();
                                                if (bonus_miner > 0) {
                                                    itemsBeans.add(item);
                                                }
                                                if (bonus_refer > 0) {
                                                    itemsBeans1.add(item);
                                                }
                                                if (bonus_big_node > 0) {
                                                    itemsBeans2.add(item);
                                                }
                                            }
                                        }
                                        mFragment.setList(itemsBeans);
                                        mFragment1.setList(itemsBeans1);
                                        mFragment2.setList(itemsBeans2);
                                    } else {
                                        Toast.makeText(getApplication(), "获取收益列表失败~", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }


}
