package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

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
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.BLACK);
        }
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MineralViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.setLifecycleOwner(this);
        mBinding.layoutTitle.backSignView.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.mineral));
        mBinding.layoutTitle.titleRight.setText(getString(R.string.my_invite));
        mBinding.layoutTitle.titleRight.setOnClickListener(view -> MyInviteActivity.startSelf(HardwareMineralActivity.this));
        mBinding.layoutTitle.titleRight.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.titleRight.setTextColor(Color.parseColor("#01FCDA"));
        MyAdapter adapter = new MyAdapter();
        mViewModel.getMineralListLive().observe(this, new Observer<MineralListResponse>() {
            @Override
            public void onChanged(MineralListResponse mineralListResponse) {
                if (mineralListResponse != null && mineralListResponse.getItems() != null && !mineralListResponse.getItems().isEmpty()) {
                    adapter.setNewData(mineralListResponse.getItems());
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
        mBinding.tvIncomeRecord.setOnClickListener(view -> IncomeRecordActivity.startSelf(this));
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
    }

    public static class MyAdapter extends BaseDataBindingAdapter<MineralListResponse.ItemsBean, ItemMineralBinding> {

        public MyAdapter() {
            super(R.layout.item_mineral);
        }

        @Override
        protected void convert(ItemMineralBinding itemMineralBinding, MineralListResponse.ItemsBean item) {
            itemMineralBinding.setVm(item);
            Context context = itemMineralBinding.tvState.getContext();
            itemMineralBinding.tvState.setText(item.getState() == 2 ? context.getString(R.string.gu_zhang_zhong) : item.getState() == 1 ? context.getString(R.string.wa_kuang_zhong) : context.getString(R.string.wei_kai_ji));
        }
    }
}
