package com.darknet.bvw.util.view;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @ClassName TransitionGlobalLayoutListener
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 15:38
 */
//向上平移目标 View 一段距离, 以保证软键盘不会遮挡目标 view
public class TransitionGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private final int initTranslationY;
    private int mLastVisibleRectBottom = -1;

    private View mTarget;
    private View[] translationViews;

    public TransitionGlobalLayoutListener(View targetView, int initTranslationY, View... translationViews) {
        this.mTarget = targetView;
        this.translationViews = translationViews;
        this.initTranslationY = initTranslationY;
    }

    /**
     * @param targetView       目标 View , 通过计算软键盘高度, 将目标View 向上平移来保证不会被遮挡
     * @param translationViews 跟着 targetView 同时上移的View
     */
    public TransitionGlobalLayoutListener(View targetView, View... translationViews) {
        this.mTarget = targetView;
        this.initTranslationY = 0;
        this.translationViews = translationViews;
    }

    @Override
    public void onGlobalLayout() {
        if (mTarget == null)
            return;

        Rect r = new Rect();
        // NOTE: 获取软键盘弹出情况下屏幕可见高度, 包含状态栏高度
        mTarget.getWindowVisibleDisplayFrame(r);
        int screenVisibleHeight = r.bottom;

        View rootView = mTarget.getRootView();
        int[] stateBarHeight = new int[2];
        // NOTE: 利用跟布局距屏幕顶部的距离来计算状态栏的高度
        // NOTE: (注意, 之所以用此方式是因为沉浸式布局时状态栏高度应为0)
        rootView.getLocationOnScreen(stateBarHeight);
        // NOTE: 减去状态栏高度以得到真实的屏幕可见高度
        screenVisibleHeight -= stateBarHeight[1];
        if (screenVisibleHeight < 0) screenVisibleHeight = 0;

        if (screenVisibleHeight == mLastVisibleRectBottom) {
            return;
        }
        mLastVisibleRectBottom = screenVisibleHeight;
        //这里是屏幕高度
        // NOTE: 注意, 屏幕高度由于使用的是 root 的高度, 所以不包含状态栏高度
        final int screenHeight = rootView.getMeasuredHeight();
        if (screenVisibleHeight < screenHeight / 4 * 3) {
            //得到软键盘高度
            int keyboardHeight = screenHeight - screenVisibleHeight;
            int bottom = getBottomInScreen(mTarget) - stateBarHeight[1];
            //得到目标控件底部到屏幕底部的间距
            int marginBottomInScreen = screenHeight - bottom;
            if (marginBottomInScreen < 0) marginBottomInScreen = 0;
            //得到平移距离
            int translationY = keyboardHeight - marginBottomInScreen;
            if (translationY < 0) translationY = 0;
            //进行平移操作
            translation(-translationY);
        } else {
            translation(initTranslationY);
        }
    }

    private void translation(int translationY) {
        mTarget.setTranslationY(translationY);
        if (isEmpty(translationViews)) return;

        for (View view : translationViews) {
            if (view == null) continue;
            view.setTranslationY(translationY);
        }
    }

    private int getBottomInScreen(View view){
        int[] temp = new int[2];
        view.getLocationOnScreen(temp);
        return temp[1] + view.getHeight();
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }
}
