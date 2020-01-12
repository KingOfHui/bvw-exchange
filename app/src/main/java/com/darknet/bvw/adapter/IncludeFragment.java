package com.darknet.bvw.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;

/**
 * 作者：created by albert on 2019-12-23 21:24
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class IncludeFragment extends Fragment {


    private static String markId;

    public static IncludeFragment newInstance(String val) {

        IncludeFragment fragment = new IncludeFragment();
        markId = val;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_include, container, false);
        return view;
    }
}
