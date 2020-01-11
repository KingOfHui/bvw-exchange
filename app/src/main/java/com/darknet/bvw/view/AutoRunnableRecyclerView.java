package com.darknet.bvw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class AutoRunnableRecyclerView extends RecyclerView {

    private static final long TIME_AUTO_POLL = 16;
    private AutoPollTask autoPollTask = new AutoPollTask(this);
    private boolean running; //标示是否正在自动轮询
    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false

    public AutoRunnableRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public AutoRunnableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoRunnableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        autoPollTask.setmYPixel(2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;//屏蔽事件
    }


    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoRunnableRecyclerView> mReference;
        private int mYPixel;

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoRunnableRecyclerView reference) {
            this.mReference = new WeakReference<>(reference);
        }

        public void setmYPixel(int mYPixel) {
            this.mYPixel = mYPixel;
        }

        @Override
        public void run() {
            AutoRunnableRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(0, mYPixel);
                recyclerView.postDelayed(recyclerView.autoPollTask, TIME_AUTO_POLL);
            }
        }
    }

    public void start() {
        if (running) stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }


    public void setScrollSpeed(int yPixel){
        autoPollTask.setmYPixel(yPixel);
    }
}
