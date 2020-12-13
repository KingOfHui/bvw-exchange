package com.darknet.bvw.util.binding;

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

    interface LineManagerFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }
}