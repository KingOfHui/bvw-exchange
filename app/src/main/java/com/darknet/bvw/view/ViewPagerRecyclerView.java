package com.darknet.bvw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 结局与ViewPager2滑动冲突问题
 */
public class ViewPagerRecyclerView extends RecyclerView {
    public ViewPagerRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ViewPagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float mLastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                return canScrollVertically(y >= mLastY ? -1 : 1);

            default:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("dhdhdh", "ACTION_MOVE");
                getParent().requestDisallowInterceptTouchEvent(canScrollVertically(y >= mLastY ? -1 : 1));
//                } else {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i("dhdhdh", "ACTION_UP");
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
