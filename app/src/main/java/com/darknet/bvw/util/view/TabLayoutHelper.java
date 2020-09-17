package com.darknet.bvw.util.view;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.darknet.bvw.R;
import com.github.fujianlian.klinechart.utils.ViewUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabLayoutHelper {
    public static void setupWithViewPager(@NonNull TabLayout tabLayout, ViewPager viewPager, @NonNull CharSequence ... texts){
        setupWithViewPager(tabLayout, viewPager, new ArrayList<>(Arrays.asList(texts)));
    }

    /**
     * 使用此方法代替 {@link TabLayout#setupWithViewPager(ViewPager)} , 用于添加选中时字体变大效果
     */
    public static void setupWithViewPager(@NonNull TabLayout tabLayout, ViewPager viewPager, @NonNull List<CharSequence> texts){
        setupWithViewPager(tabLayout, viewPager, texts, 18, 15);
    }

    public static void setupWithViewPager(@NonNull TabLayout tabLayout, ViewPager viewPager
            , @NonNull List<CharSequence> texts, int selectSizeSP, int unselectSizeSP){
        setupWithViewPager(tabLayout, viewPager, texts, selectSizeSP, unselectSizeSP, -3);
    }

    public static void setupWithViewPager(@NonNull TabLayout tabLayout, ViewPager viewPager
            , @NonNull List<CharSequence> texts, int selectSizeSP, int unselectSizeSP, int tabWidth){

        tabLayout.setupWithViewPager(viewPager);
        int selected = tabLayout.getSelectedTabPosition();
        for (int index = 0; index < texts.size(); index++) {
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            if(tab == null){
                tab = tabLayout.newTab();
                tabLayout.addTab(tab);
            }
            TextView text = (TextView) View.inflate(tabLayout.getContext(), R.layout.tab_layout_custom, null);
            if(tabWidth != -3){
                setViewWidth(tab.view, tabWidth);
            }
            text.setText(texts.get(index));
            if(selected == index){
                updateSelectView(text, selectSizeSP);
            }
            tab.setCustomView(text);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                updateSelectView(text, selectSizeSP);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                updateUnselectedView(text, unselectSizeSP);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private static void updateUnselectedView(TextView text, int unselectSizeSP) {
        if(text == null) return;
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, unselectSizeSP);
        text.setTextColor(Color.parseColor("#70FFFFFF"));
    }

    private static void updateSelectView(TextView text, int selectSizeSP) {
        if(text == null) return;
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectSizeSP);
        text.setTextColor(text.getResources().getColor(R.color._01FCDA));
    }

    public static void setViewWidth(View view, int width){
        if(view == null)
            return;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp == null){
            lp = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            lp.width = width;
        }
        view.setLayoutParams(lp);
    }
}
