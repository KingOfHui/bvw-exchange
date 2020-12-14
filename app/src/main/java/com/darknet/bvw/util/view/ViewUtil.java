package com.darknet.bvw.util.view;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * @ClassName ViewUtil
 * @Description
 * @Author dinghui
 * @Date 2020/12/14 0014 10:44
 */
public class ViewUtil {
    /**
     * text 设置删除线
     * @param view
     */
    public static void setTextViewDeleteLine(TextView view) {
        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
    /**
     * text 取消删除线
     * @param view
     */
    public static void cancelTextViewDeleteLine(TextView view) {
        view.setPaintFlags(view.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    /**
     * text 设置下划线
     * @param view
     */
    public static void setTextViewUnderLine(TextView view) {
        view.setPaintFlags(view.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * text 取消下划线
     * @param view
     */
    public static void cancelTextViewUnderLine(TextView view) {
        view.setPaintFlags(view.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
    }

}
