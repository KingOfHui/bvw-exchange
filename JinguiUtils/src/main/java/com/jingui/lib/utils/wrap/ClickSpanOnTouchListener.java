package com.jingui.lib.utils.wrap;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import jingui.android.utils.R;


/**
 * Created by guoshiwen on 2019/4/12.
 * 设置此 OnTouchListener 可以使 ClickSpanner 生效, 使ClickSpan不会拦截TextView点击事件, 处理使用ellipsize属性后textView可以上下滚动问题
 */

public class ClickSpanOnTouchListener implements View.OnTouchListener{
    public static final int KEY_CLICK_SPAN_TOUCH = R.string.KEY_CLICK_SPAN_TOUCH;
    ClickSpanInvoker invoker = new ClickSpanInvoker();
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v instanceof TextView)
            return invoker.onTouch((TextView) v, event);
        return false;
    }
}