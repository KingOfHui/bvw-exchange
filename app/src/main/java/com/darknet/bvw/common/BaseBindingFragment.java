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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.darknet.bvw.util.ToastUtils;

import static android.app.Activity.RESULT_OK;

/**
 * @ClassName BaseBindingFragment
 * @Description
 * @Author dinghui
 * @Date 2020/12/11 0011 17:22
 */
public abstract class BaseBindingFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends Fragment {
    protected DB mDataBinding;
    protected VM mViewModel;

    private boolean isLoaded = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, setLayoutResId(), container, false);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = initViewModel();
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
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
    }

    private void onFragmentFirstVisible() {
        initData();
    }

    public abstract int setLayoutResId();

    protected abstract VM initViewModel();

    protected abstract void initData();

    protected final <T extends BaseViewModel> T getFragmentViewModel(Class<T> tClass) {
        T viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(tClass);
        return viewModel;
    }

    protected final <T extends BaseViewModel> T getActivityViewModel(Class<T> viewModelClass) {
        T viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(viewModelClass);
        return viewModel;
    }

    public void finishActivity() {
        requireActivity().finish();
    }

    public void finishActivityWithResultOk() {
        requireActivity().finish();
        requireActivity().setResult(RESULT_OK);
    }
}
