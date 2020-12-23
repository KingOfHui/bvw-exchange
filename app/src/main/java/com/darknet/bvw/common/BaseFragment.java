package com.darknet.bvw.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.base.IView;
import com.darknet.bvw.view.CustomDialog;

public abstract class BaseFragment extends Fragment implements IView {

    protected Activity mActivity;
    private View mView;
    private CustomDialog dialog;//进度条
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mView = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
        initView(mView);
        initEvent();
        initDatas();
        return mView;
    }

    public abstract void initView(View view);

    public abstract int getLayoutId();

    public abstract void initDatas();

    public abstract void initEvent();

    public<V extends View> V findViewById(int id) {
        if(mView == null) return null;
        return mView.findViewById(id);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(requireActivity());
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override
    public void showLoading() {
        showDialog("");
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }
    public void showDialog(String progressTip) {
        if (requireActivity().isFinishing()) {
            return;
        }
        getDialog().show();
        if (progressTip != null) {
            getDialog().setTvProgress(progressTip);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
