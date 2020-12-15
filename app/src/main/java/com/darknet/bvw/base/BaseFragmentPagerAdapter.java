package com.darknet.bvw.base;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用ViewPageAdapter
 * @param <T>
 */
public class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> fragments;
    private List<CharSequence> fragmentTitles;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentTitles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    public BaseFragmentPagerAdapter add(T fragment) {
        if (fragment != null) {
            fragments.add(fragment);
        }
        return this;
    }

    public BaseFragmentPagerAdapter add(T fragment, CharSequence fragmentTitle) {
        if (fragment != null) {
            fragments.add(fragment);
            fragmentTitles.add(fragmentTitle);
        }
        return this;
    }

    public BaseFragmentPagerAdapter addAll(List<T> fragments, List<CharSequence> fragmentTitles) {

        if (this.fragments != null && fragments.size() != 0) {
            this.fragments.addAll(fragments);
        }

        if (this.fragmentTitles != null && fragmentTitles.size() != 0) {
            this.fragmentTitles.addAll(fragmentTitles);
        }
        return this;
    }

    public List<T> getFragments() {
        return fragments;
    }

    public List<CharSequence> getFragmentTitles() {
        return fragmentTitles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
