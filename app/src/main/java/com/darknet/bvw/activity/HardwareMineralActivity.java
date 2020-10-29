package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityHardMineralBinding;
import com.darknet.bvw.databinding.ItemMineralBinding;
import com.darknet.bvw.model.Event;
import com.darknet.bvw.model.event.BidSuccessEvent;
import com.darknet.bvw.model.response.MineralListResponse;
import com.darknet.bvw.util.Language;
import com.darknet.bvw.view.BidDialogView;
import com.darknet.bvw.viewmodel.MineralViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @ClassName HardwareMineralActivity
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 11:36
 */
public class HardwareMineralActivity extends BaseBindingActivity<ActivityHardMineralBinding> {

    private MineralViewModel mViewModel;
    private Disposable mDi;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hard_mineral;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#181523"));
        }
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MineralViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.setLifecycleOwner(this);
        mBinding.ivImage.setImageResource(Language.readFromConfig() == Language.CHINA ? R.mipmap.img_mineral_banner_chinese : R.mipmap.img_mineral_banner_english);
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.mineral));
        mBinding.layoutTitle.titleRight.setText(getString(R.string.my_invite));
        mBinding.layoutTitle.titleRight.setOnClickListener(view -> mViewModel.getPublicAddress());
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
        MyAdapter adapter = new MyAdapter();
        mViewModel.getMineralListLive().observe(this, new Observer<MineralListResponse>() {
            @Override
            public void onChanged(MineralListResponse mineralListResponse) {
                if (mineralListResponse == null) {
                    mBinding.progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.mipmap.img_no_data), getString(R.string.mineral_list_no_data));
                    return;
                }
                List<MineralListResponse.ItemsBean> items = mineralListResponse.getItems();
                List<MineralListResponse.ItemsBean> itemsBeans = new ArrayList<>();
                if (items!=null) {
                    for (MineralListResponse.ItemsBean item : items) {
                        if (item.getPay_state() == 0 && item.getState() != 1) {
                            continue;
                        }
                        itemsBeans.add(item);
                    }
                }
                if (!itemsBeans.isEmpty()) {
                    adapter.setNewData(items);
                } else {
                    mBinding.progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.mipmap.img_no_data), getString(R.string.mineral_list_no_data));
                }
            }
        });
        mViewModel.dismissLoadingLive.observe(this, aBoolean -> {
            if (aBoolean) {
                dismissDialog();
                mBinding.refreshLayout.finishRefresh();
            }
        });
        mViewModel.isOpenBid.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    MyInviteActivity.startSelf(HardwareMineralActivity.this);
                } else {
                    new BidDialogView().showTips(HardwareMineralActivity.this, getString(R.string.find_invest_notice));
                }
            }
        });

        mBinding.rvMineral.setAdapter(adapter);
        mBinding.rvMineral.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.line_4E4A5E)));
        mBinding.rvMineral.addItemDecoration(decoration);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<MineralListResponse.ItemsBean> data = adapter.getData();
            MineralListResponse.ItemsBean itemsBean = data.get(position);
            MineralInfoActivity.startSelfForResult(this, itemsBean, mViewModel.getMineralStatusResponseLiveData().getValue(), 10000);
        });
        mBinding.tvIncomeRecord.setOnClickListener(view -> IncomeRecordActivity.startSelf(this, mViewModel.getMineralStatusResponseLiveData().getValue()));
        mBinding.executePendingBindings();
        mBinding.refreshLayout.setOnRefreshListener(refreshLayout -> requestData());
    }

    @Override
    public void initDatas() {
        requestData();
    }

    private void requestData() {
        showDialog(getString(R.string.loading));
        mViewModel.getMineralList();
        mViewModel.getMineralStatus();
//        Observable<Object> ob1 = Observable.create(emitter -> {
//            mViewModel.getMineralStatus();
//            emitter.onComplete();
//        });
//        Observable<Object> ob2 = Observable.create(emitter -> {
//            mViewModel.getMineralList();
//            emitter.onComplete();
//        });
//        mDi = ob1.concatWith(ob2).subscribe(o -> finishRefresh(), throwable -> finishRefresh());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void success(BidSuccessEvent nameEvent) {
        mViewModel.isOpenBid.setValue(true);
    }

    private void finishRefresh() {
        dismissDialog();
        mBinding.refreshLayout.finishRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000) {
            if (resultCode == RESULT_OK) {
                requestData();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDi != null) {
            mDi.dispose();
        }
        EventBus.getDefault().unregister(this);
    }

    public static class MyAdapter extends BaseDataBindingAdapter<MineralListResponse.ItemsBean, ItemMineralBinding> {

        public MyAdapter() {
            super(R.layout.item_mineral);
        }

        @Override
        protected void convert(ItemMineralBinding itemMineralBinding, MineralListResponse.ItemsBean item) {
            itemMineralBinding.setVm(item);
            Context context = itemMineralBinding.tvState.getContext();
            String text = context.getString(R.string.unknown);
            if (item.getMiner_type() == 2) {
                if (item.getPay_state() == 0 && item.getState() != 1) {
                    text = context.getString(R.string.no_miners);
                } else if (item.getPay_state() == 0 && item.getState() == 1) {
                    text = context.getString(R.string.wei_zhi_ya);
                } else if (item.getPay_state() != 0 && item.getState() != 1) {
                    text = context.getString(R.string.shut_down);
                } else if (item.getPay_state() != 0 && item.getState() == 1) {
                    text = context.getString(R.string.wa_kuang_zhong);
                }
            } else {
                text = item.getState() == 2 ? context.getString(R.string.gu_zhang_zhong) : item.getState() == 1 ? context.getString(R.string.wa_kuang_zhong) : context.getString(R.string.wei_kai_ji);
            }
            itemMineralBinding.tvState.setText(text);
        }
    }
}
