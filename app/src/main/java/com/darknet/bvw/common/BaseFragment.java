package com.darknet.bvw.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
        initView(view);
        initEvent();
        initDatas();
        return view;
    }

    public abstract void initView(View view);

    public abstract int getLayoutId();

    public abstract void initDatas();

    public abstract void initEvent();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
