package com.darknet.bvw.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacetemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacetemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //获取到当前 item 的 index
        int position = parent.getChildAdapterPosition(view);
        if (position % 3 == 0 || position % 3 == 2) {
            outRect.bottom = space;
        } else {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }
}
