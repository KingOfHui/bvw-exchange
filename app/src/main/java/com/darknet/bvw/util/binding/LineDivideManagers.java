package com.darknet.bvw.util.binding;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description
 * @Author dinghui.
 * @Date 2020/9/25 0025 11:09
 */
public class LineDivideManagers {
    public static LineManagerFactory both() {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.BOTH, 1);
    }

    public static LineManagerFactory horizontal() {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.HORIZONTAL, 1);
    }

    public static LineManagerFactory vertical() {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.VERTICAL, 1);
    }

    public static LineManagerFactory horizontal(@ColorInt int color) {
        return horizontal(1, color);
    }


    public static LineManagerFactory vertical(@ColorInt int color) {
        return vertical(1, color);
    }

    public static LineManagerFactory both(@ColorInt int color) {
        return both(1, color);
    }

    public static LineManagerFactory horizontal(int divideSize, @ColorInt int color) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.HORIZONTAL, divideSize, color);
    }

    public static LineManagerFactory vertical(int divideSize, @ColorInt int color) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.VERTICAL, divideSize, color);
    }

    public static LineManagerFactory both(int divideSize, @ColorInt int color) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.BOTH, divideSize, color);
    }

    interface LineManagerFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }
}