package com.darknet.bvw.activity;

import android.content.Context;
import android.content.Intent;
import android.print.PrintJob;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityMyInviteBinding;
import com.darknet.bvw.databinding.ItemMyInviteBinding;
import com.darknet.bvw.model.response.InviteDataResponse;
import com.darknet.bvw.viewmodel.InviteViewModel;

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
        mBinding.titleLayout.title.setText("我的邀请");
        mBinding.rvInviteList.setLayoutManager(new LinearLayoutManager(this));
        InviteAdapter adapter = new InviteAdapter();
        mBinding.rvInviteList.setAdapter(adapter);
        viewModel.getInviteStatus();
        viewModel.getInviteDataLive().observe(this, new Observer<InviteDataResponse>() {
            @Override
            public void onChanged(InviteDataResponse inviteDataResponse) {
                mBinding.setData(inviteDataResponse);
                adapter.setNewData(inviteDataResponse.getLowerLevel1List().getItems());
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
