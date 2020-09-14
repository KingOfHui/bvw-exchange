package com.darknet.bvw.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseBindQuickAdapter;
import com.darknet.bvw.base.BaseBindingViewHolder;
import com.darknet.bvw.base.BaseDataBindViewHolder;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityHardMineralBinding;
import com.darknet.bvw.databinding.ItemMineralBinding;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.model.response.MineralStatusResponse;
import com.darknet.bvw.util.NoUnderlineClickSpan;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.viewmodel.MineralViewModel;

/**
 * @ClassName HardwareMineralActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 11:36
 */
public class HardwareMineralActivity extends BaseBindingActivity<ActivityHardMineralBinding> {

    private MineralViewModel mViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hard_mineral;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.BLACK);
        }
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MineralViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.setLifecycleOwner(this);
        mBinding.layoutTitle.backSignView.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText("矿机");
        mBinding.layoutTitle.titleRight.setText("我的邀请");
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
//        mViewModel.getMineralStatusResponseLiveData().observe(this, new Observer<MineralStatusResponse>() {
//            @Override
//            public void onChanged(MineralStatusResponse response) {
//                mBinding.tvAverageCount.setText(getSpanString(response.getPower_24_hour_avg()," H/S"));
//                mBinding.tvBeforeCount.setText(getSpanString(response.getPower_24_hour_usd_bonus()," USDT"));
//                mBinding.tvBtcIncome.setText(getSpanString(response.getToday_btc_bonus()," BTC"));
//                mBinding.tvBtwIncome.setText(getSpanString(response.getToday_btw_bonus()," BTW"));
//                mBinding.tvTotalCount.setText(String.valueOf(response.getMiner_count()));
//                mBinding.tvWorkCount.setText(String.valueOf(response.getMiner_working_count()));
//            }
//        });
       /* mViewModel.isEmptyLive.observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                mBinding.progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.drawable.icon_empty_cn),"您暂未添加矿机");
            } else {
                mBinding.progressLayout.showContent();
            }
        });*/
        MyAdapter adapter = new MyAdapter();
        mViewModel.getMineralListLive().observe(this, new Observer<MineralListResponse>() {
            @Override
            public void onChanged(MineralListResponse mineralListResponse) {
                if (mineralListResponse.getItems() != null && !mineralListResponse.getItems().isEmpty()) {
                    adapter.setNewData(mineralListResponse.getItems());
                } else {
                    mBinding.progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.mipmap.img_no_data), "您暂未添加矿机");
                }
            }
        });

        mBinding.rvMineral.setAdapter(adapter);
        mBinding.rvMineral.setLayoutManager(new LinearLayoutManager(this));
    }

    private SpannableStringBuilder getSpanString(String s, String symbol) {
        return SpanHelper.start()
                .next(s)
                .setTextColor(Color.parseColor("#01FCDA"))
                .setTextSize(28)
                .next(symbol)
                .setTextSize(15)
                .setTextColor(Color.WHITE)
                .get();
    }

    @Override
    public void initDatas() {
        mViewModel.getMineralStatus();
        mViewModel.getMineralList();
    }

    public static class MyAdapter extends BaseDataBindingAdapter<MineralListResponse.ItemsBean, ItemMineralBinding> {

        public MyAdapter() {
            super(R.layout.item_mineral);
        }

        @Override
        protected void convert(ItemMineralBinding itemMineralBinding, MineralListResponse.ItemsBean item) {
            itemMineralBinding.setVm(item);
        }
    }
}
