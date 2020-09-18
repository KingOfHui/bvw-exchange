package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.view.TabLayoutHelper;
import com.darknet.bvw.viewmodel.MineralViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

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

    public static void startSelf(Context context) {
        context.startActivity(new Intent(context,IncomeRecordActivity.class));
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
    }

    @Override
    public void initDatas() {
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.GET_MINERAL_BONUS_LIST)
                .tag(MineralViewModel.class)
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if (backresponse != null) {
                            String backVal = backresponse.body();
                            if (backVal != null) {
                                Gson gson = new Gson();
                                try {
                                    BaseResponse<MineralBonusListResponse> status = gson.fromJson(backVal, new TypeToken<BaseResponse<MineralBonusListResponse>>(){}.getType());
                                    if (status != null && status.isSuccess()) {
                                        List<MineralBonusListResponse.ItemsBean> items = status.getData().getItems();

                                        List<MineralBonusListResponse.ItemsBean> itemsBeans = new ArrayList<>();
                                        List<MineralBonusListResponse.ItemsBean> itemsBeans1 = new ArrayList<>();
                                        List<MineralBonusListResponse.ItemsBean> itemsBeans2 = new ArrayList<>();
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
