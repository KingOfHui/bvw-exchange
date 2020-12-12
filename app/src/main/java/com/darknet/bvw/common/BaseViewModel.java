package com.darknet.bvw.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : dinghui
 * @date : 2020/7/3 0:58
 */
public class BaseViewModel extends AndroidViewModel{


    protected CompositeDisposable mDisposableContainer;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mDisposableContainer = new CompositeDisposable();
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposableContainer.dispose();
    }

    public void addDisposable(Disposable d) {
        mDisposableContainer.add(d);
    }
}
