package com.darknet.bvw.activity.fragment;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.activity.BaseActivity;
import com.darknet.bvw.view.CustomDialog;

public class BaseFragment extends Fragment {

    private CustomDialog dialog;//进度条

    private BaseActivity baseActivity;

    public BaseFragment() {
    }

    public BaseFragment(BaseActivity activity) {
        this.baseActivity = activity;
    }

    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(baseActivity);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog(String progressTip) {

        try {
            getDialog().show();
            if (progressTip != null) {
                getDialog().setTvProgress(progressTip);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void dismissDialog() {

        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
