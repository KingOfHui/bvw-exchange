package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.darknet.bvw.R;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;

    public int COUNT;
    private Context context;
    private String markIdVal;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context,String markId) {
        super(fm);
        this.context = context;
        titles = new String[]{context.getResources().getString(R.string.k_shendu_sign), context.getResources().getString(R.string.k_chengjiao_sign)};//,"简介"
        COUNT = titles.length;
        this.markIdVal = markId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DepthFragment depthFragment =DepthFragment.newInstance(markIdVal);
                return depthFragment;
            case 1:
                DealFragment depthFragmentTwo =DealFragment.newInstance(markIdVal);
                return depthFragmentTwo;
//                return DealFragment.newInstance();
            case 2:
                IncludeFragment includeFragment = IncludeFragment.newInstance(markIdVal);
                return includeFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}