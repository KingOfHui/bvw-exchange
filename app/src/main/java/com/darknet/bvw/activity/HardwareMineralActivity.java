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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityHardMineralBinding;
import com.darknet.bvw.databinding.ItemMineralBinding;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.model.response.MineralStatusResponse;
import com.darknet.bvw.util.NoUnderlineClickSpan;
import com.darknet.bvw.util.SpanHelper;
import com.darknet.bvw.viewmodel.MineralViewModel;

import java.util.List;

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
        mBinding.layoutTitle.titleRight.setOnClickListener(view -> MyInviteActivity.startSelf(HardwareMineralActivity.this));
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
        MyAdapter adapter = new MyAdapter();
        mViewModel.getMineralListLive().observe(this, new Observer<MineralListResponse>() {
            @Override
            public void onChanged(MineralListResponse mineralListResponse) {
                for (int i = 0; i < 10; i++) {
                    MineralListResponse.ItemsBean bean = new MineralListResponse.ItemsBean();
                    bean.setPay_symbol("BTW/BTC" + i);
                    bean.setPower_btc_1_hour("123---" + i);
                    mineralListResponse.getItems().add(bean);
                }
                if (mineralListResponse.getItems() != null && !mineralListResponse.getItems().isEmpty()) {
                    adapter.setNewData(mineralListResponse.getItems());
                } else {
                    mBinding.progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.mipmap.img_no_data), "您暂未添加矿机");
                }
            }
        });

        mBinding.rvMineral.setAdapter(adapter);
        mBinding.rvMineral.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mBinding.rvMineral.addItemDecoration(decoration);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<MineralListResponse.ItemsBean> data = adapter.getData();
            MineralListResponse.ItemsBean itemsBean = data.get(position);
            MineralInfoActivity.startSelf(this,itemsBean);
        });
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
