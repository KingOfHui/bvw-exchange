package com.darknet.bvw.bindingadapter;

import android.widget.TextView;

import androidx.databinding.InverseMethod;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.R;

public class MyInverseMethod {

    @InverseMethod("toText")
    public static String toText(TextView view, int status) {
        if (status == 2) {
            return MyApp.getInstance().getString(R.string.wa_kuang_zhong);
        }
        return MyApp.getInstance().getString(R.string.wei_zhi_ya);
    }
}
