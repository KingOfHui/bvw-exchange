package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.print.PrintJob;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityMyInviteBinding;
import com.darknet.bvw.databinding.ItemMyInviteBinding;
import com.darknet.bvw.model.response.InviteDataResponse;
import com.darknet.bvw.util.TimeBigToSmallComparator;
import com.darknet.bvw.viewmodel.InviteViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Collections;
import java.util.List;

/**
 * @author DH
 */
public class MyInviteActivity extends BaseBindingActivity<ActivityMyInviteBinding> {

    public static void startSelf(Context context) {
        context.startActivity(new Intent(context, MyInviteActivity.class));
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_invite;
    }

    @Override
    public void initView() {
        InviteViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(InviteViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.titleLayout.layBack.setOnClickListener((v) -> finish());
        mBinding.titleLayout.title.setText(getString(R.string.my_invite));
        mBinding.rvInviteList.setLayoutManager(new LinearLayoutManager(this));
        InviteAdapter adapter = new InviteAdapter();
        mBinding.rvInviteList.setAdapter(adapter);
        viewModel.getInviteStatus();
        viewModel.getInviteDataLive().observe(this, new Observer<InviteDataResponse>() {
            @Override
            public void onChanged(InviteDataResponse inviteDataResponse) {
                if (inviteDataResponse!=null) {
                    mBinding.setData(inviteDataResponse);
                    InviteDataResponse.LowerLevel1ListBean lowerLevel1List = inviteDataResponse.getLowerLevel1List();
                    if (lowerLevel1List != null) {
                        List<InviteDataResponse.LowerLevel1ListBean.ItemsBean> items = lowerLevel1List.getItems();
                        if (items != null && items.size() > 0) {
                            adapter.setNewData(items);
                        }
                        return;
                    }
                }
                mBinding.progressLayout.showEmpty(ContextCompat.getDrawable(mAppContext, R.mipmap.img_no_data), getString(R.string.my_invite_no_data));

            }
        });
        mBinding.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.getInviteStatus();
                mBinding.smartRefresh.finishRefresh();
            }
        });
    }

    @Override
    public void initDatas() {

    }

    private static class InviteAdapter extends BaseDataBindingAdapter<InviteDataResponse.LowerLevel1ListBean.ItemsBean, ItemMyInviteBinding> {
        private InviteAdapter() {
            super(R.layout.item_my_invite);
        }

        @Override
        protected void convert(ItemMyInviteBinding binding, InviteDataResponse.LowerLevel1ListBean.ItemsBean item) {
            binding.setData(item);
        }
    }
}
