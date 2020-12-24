package com.darknet.bvw.common;

/**
 * @ClassName BaseBindingFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:47
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.darknet.bvw.R;
import com.darknet.bvw.base.IView;
import com.darknet.bvw.util.StatusBarUtil;
import com.darknet.bvw.view.CustomDialog;

import static android.app.Activity.RESULT_OK;

/**
 * @ClassName BaseBindingFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/11 0011 17:22
 */
public abstract class BaseBindingFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends Fragment implements IView {
    protected DB mDataBinding;
    protected VM mViewModel;
    private CustomDialog dialog;//进度条
    private boolean isLoaded = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, setLayoutResId(), container, false);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = initViewModel();
        mDataBinding.setVariable(BR.vm, mViewModel);
        initView();
        return mDataBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded && !isHidden()) {
            onFragmentFirstVisible();
            isLoaded = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (ChangeStatus()) {
            StatusBarUtil.setStatusBarColor(requireActivity(),
                    isVisibleToUser?R.color.appColorPrimary:R.color.color_bg_181523);
        }
    }

    public boolean ChangeStatus() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
    }

    protected abstract void initView();

    private void onFragmentFirstVisible() {
        initData();
    }

    public abstract int setLayoutResId();

    protected abstract VM initViewModel();

    protected abstract void initData();

    protected final <T extends BaseViewModel> T getFragmentViewModel(Class<T> tClass) {
        T viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(tClass);
        handleLoadingLive(viewModel);
        return viewModel;
    }

    protected final <T extends BaseViewModel> T getActivityViewModel(Class<T> viewModelClass) {
        T viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(viewModelClass);
        handleLoadingLive(viewModel);
        return viewModel;
    }

    public void handleLoadingLive(BaseViewModel viewModel) {
        viewModel.isLoadingLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showLoading();
                } else {
                    dismissDialog();
                }
            }
        });

    }
    public void finishActivity() {
        requireActivity().finish();
    }

    public void finishActivityWithResultOk() {
        requireActivity().finish();
        requireActivity().setResult(RESULT_OK);
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
