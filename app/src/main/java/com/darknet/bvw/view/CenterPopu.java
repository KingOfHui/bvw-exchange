package com.darknet.bvw.view;

import android.content.Context;

import com.darknet.bvw.R;
import com.darknet.bvw.util.EnvironmentUtil;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.impl.CenterListPopupView;

import androidx.annotation.NonNull;

public class CenterPopu extends CenterPopupView {
    private Context context;

    public CenterPopu(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_apply_leader;
    }

    @Override
    protected int getPopupWidth() {
        return getwidth();
    }

    private int getwidth() {
        return EnvironmentUtil.getScreenWidth(context) - 2 * EnvironmentUtil.dp2px(context, 58);
    }

}
