package com.jingui.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.LinkedList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import jingui.android.utils.R;

public class ViewUtil {
    /**
     * 批量设置控件是否显示
     */
    public static void setVisibility(int visibility, View... views){
        if(views != null){
            for (View view : views) {
                if(view != null && view.getVisibility() != visibility){
                    view.setVisibility(visibility);
                }
            }
        }
    }

    public static void setVisibility(boolean isVisibility, View... views){
        setVisibility(isVisibility? View.VISIBLE: View.GONE, views);
    }

    /**
     * 批量设置点击事件
     */
    public static void setOnClickListener(View.OnClickListener listener, View... views){
        if(views != null){
            for (View view : views) {
                if(view != null){
                    view.setOnClickListener(listener);
                }
            }
        }
    }

    /**
     * 批量设置透明度
     */
    public static void setAlpha(float alpha, View... views){
        if(views != null){
            for (View view : views) {
                if(view != null){
                    view.setAlpha(alpha);
                }
            }
        }
    }

    /**
     * 设置 View 的高度
     */
    public static void setViewHeight(View view, int height){
        if(view == null)
            return;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp == null){
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        }else {
            lp.height = height;
        }
        view.setLayoutParams(lp);
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

    public static void setMarginTop(View view, int margin){
        setMargin(8, view, margin);
    }

    public static void setMarginBottom(View view, int margin){
        setMargin(2, view, margin);
    }

    public static void setMarginLeft(View view, int margin){
        setMargin(4, view, margin);
    }

    public static void setMarginRight(View view, int margin){
        setMargin(6, view, margin);
    }

    private static void setMargin(int type, View view, int margin){
        if(view == null) return;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(lp instanceof ViewGroup.MarginLayoutParams){
            switch (type){
                case 2:
                    ((ViewGroup.MarginLayoutParams) lp).bottomMargin = margin;
                    break;
                case 4:
                    ((ViewGroup.MarginLayoutParams) lp).leftMargin = margin;
                    break;
                case 6:
                    ((ViewGroup.MarginLayoutParams) lp).rightMargin = margin;
                    break;
                case 8:
                    ((ViewGroup.MarginLayoutParams) lp).topMargin = margin;
                    break;
            }
            view.setLayoutParams(lp);
        }
    }

    /**
     * 有些 View 比如 EditText 会将其中的 Context 进行一层包装, 不能直接强转 Activity ,使用该方法进行强转
     */
    public static Activity getActivity(Context context) {
        if(context == null)
            return null;
        // Gross way of unwrapping the Activity so we can get the FragmentManager
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        if(context != null)
            new IllegalStateException("The "+context.getClass().getName()+"'s is not an Activity.").printStackTrace();
        return null;
    }

    public static FragmentActivity getFragemntActivity(Context context){
        Activity activity = getActivity(context);
        if(activity instanceof FragmentActivity)
            return (FragmentActivity) activity;
        return null;
    }

    /**
     * 将view的上下文转换为Activity, 如果无法转换则返回null
     */
    public static Activity getActivity(View view){
        if(view == null)
            return null;
        return getActivity(view.getContext());
    }

    /**
     * 根据id查找View, 避免空指针异常和类型转换异常
     */
    public static <T extends View> T findViewById(ViewGroup parent, int id){
        if(parent == null)
            return null;
        try {
            return parent.findViewById(id);
        }catch (Exception e){
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取View父布局, 避免空指针异常
     */
    public static ViewParent getParent(View view){
        if(view == null)
            return null;
        return view.getParent();
    }

    public static void setTextSize(int sp, ViewGroup target, int ... textResId){
        if(target == null || textResId == null)
            return;

        for (int id : textResId) {
            TextView tv = findViewById(target, id);
            if(tv != null){
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
            }
        }
    }

    public static Context getContextFromView(View... views){
        if(views == null)
            return null;
        for (View view : views) {
            if(view != null)
                return view.getContext();
        }
        return null;
    }

    public static void removeViewFromParent(View target){
        try {
            if(target == null)
                return;

            ViewParent parent = target.getParent();
            if (parent instanceof ViewGroup){
                ((ViewGroup) parent).removeView(target);
            }
        }catch (Exception e){
        	e.printStackTrace();
        }
    }

    /**
     * 用于清除 EditText 的焦点, 解决了 {@code android.widget.EditText#clearFocus} 在部分设备上的兼容问题
     */
    public static void clearFocus(EditText editText){
        ViewParent vp = editText.getParent();
        if(vp instanceof View){
            View vg = (View) vp;
            vg.setFocusable(true);
            vg.setFocusableInTouchMode(true);
            vg.requestFocus();
        }
    }

    /**
     * 为一个 View 添加一个或多个焦点监听事件, 以做到解耦合
     */
    public static void addOnFocusChangeListener(View view, View.OnFocusChangeListener listener){
        View.OnFocusChangeListener lastListener = view.getOnFocusChangeListener();
        MultiFocusChangeListener ownerListener;
        if(lastListener == null){
            ownerListener = new MultiFocusChangeListener();
        }else if(lastListener instanceof MultiFocusChangeListener){
            ownerListener = (MultiFocusChangeListener) lastListener;
        }else {
            ownerListener = new MultiFocusChangeListener();
            ownerListener.addListener(lastListener);
        }
        ownerListener.addListener(listener);
        view.setOnFocusChangeListener(ownerListener);
    }

    public static void addOnClickListener(View view, View.OnClickListener listener){
        MultiClickListener multiClickListener;
        Object tag = view.getTag(R.string.KEY_MULTI_CLICK_LISTENER);
        if(tag instanceof MultiClickListener){
            multiClickListener = (MultiClickListener) tag;
        } else {
            multiClickListener = new MultiClickListener();
            view.setTag(R.string.KEY_MULTI_CLICK_LISTENER, multiClickListener);
        }
        multiClickListener.addListener(listener);
        view.setOnClickListener(multiClickListener);
    }

    public static void addFilter(EditText editText, InputFilter inputFilter){
        InputFilter[] filters = editText.getFilters();
        int size = DataUtil.getSize(filters);
        InputFilter[] newfilters = new InputFilter[size + 1];
        for (int i = 0; i < size; i++) {
            newfilters[i] = filters[i];
        }
        newfilters[size] = inputFilter;
        editText.setFilters(newfilters);
    }

    private static boolean nullFilter(CharSequence source) {
        if ("".equals(source.toString())) {
            return true;
        }
        return false;
    }

    /**
     * 控制 EditText 能输入几位小数
     * @param DECIMAL_DIGITS 小数位数
     * @param MAX_LENGTH 小数+整数长度
     */
    public static InputFilter get2NumPoint(final int DECIMAL_DIGITS, final int MAX_LENGTH) {
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
									   Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if (nullFilter(source)) return null;
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");//在点前后分开两段
                if (splitArray.length > 0) {
                    String intValue = splitArray[0];
                    int errorIndex = dValue.indexOf(".");
                    if (errorIndex == -1) {
                        errorIndex = dValue.length();
                    }
                    if (intValue.length() >= MAX_LENGTH - DECIMAL_DIGITS - 1 && dstart <= errorIndex) {
                        if (".".equals(source.toString())) {
                            return null;
                        }
                        return "";
                    }
                }
                if (splitArray.length > 1 && dstart >= splitArray[0].length() + 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                    if (diff > 0) {
                        try {
                            return source.subSequence(start, end - diff);
                        } catch (IndexOutOfBoundsException e) {
                            return source;
                        }
                    }
                }
                if (dest.length() == MAX_LENGTH - 1 && ".".equals(source.toString())) {
                    return "";
                }
                if (dest.length() >= MAX_LENGTH) {
                    return "";
                }
                return null;
            }
        };
        return lengthfilter;
    }

    public static Bitmap toBitmap(ScrollView scrollView) {
        if (scrollView != null) {
            int h = 0;
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();
                scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
            return bitmap;
        }
        return null;
    }

    private static class MultiClickListener implements View.OnClickListener {

        private List<View.OnClickListener> mListeners = new LinkedList<>();

        public void addListener(View.OnClickListener listener){
            mListeners.add(listener);
        }

        @Override
        public void onClick(View v) {
            for (View.OnClickListener listener : mListeners) {
                if(listener != null) listener.onClick(v);
            }
        }
    }
}