package com.darknet.bvw.util.binding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;

/**
 * @ClassName DividerLine
 * @Description
 * @Author dinghui
 * @Date 2020/11/25 0025 14:59
 */
public class DividerLine extends ItemDecoration {
    private Context mContext;
    private LineDrawMode mDrawMode;
    private int mDividerSize = 1;
    private Drawable mDividerDrawable;

    public DividerLine(Context context, LineDrawMode drawMode, int dividerSize) {
        mContext = context;
        mDrawMode = drawMode;
        this.mDividerSize = dividerSize;
        mDividerDrawable = new ColorDrawable(Color.parseColor("#4C587D"));
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        switch (mDrawMode) {
            case VERTICAL: {
                drawVertical(c, parent, state);
                break;
            }
            case HORIZONTAL:{
                drawHorizontal(c, parent, state);
                break;
            }
            case BOTH:{
                drawHorizontal(c, parent, state);
                drawVertical(c, parent, state);
                break;
            }
        }
    }

    private void drawVertical(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getRight() + params.rightMargin;
            int right = left + mDividerSize;
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private void drawHorizontal(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.topMargin;
            int right = child.getRight() - params.rightMargin;
            int bottom = top + mDividerSize;
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    /**
     * 分隔线绘制模式,水平，垂直，两者都绘制
     */
    enum LineDrawMode {
        HORIZONTAL, VERTICAL, BOTH
    }
}
