package jingui.android.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jingui.lib.utils.DensityUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ToolBarContainer extends FrameLayout {

    private static final int StatusBarHeight = DensityUtils.getStatusBarHeight();
    private static final int ToolBarHeight = DensityUtils.dip2px(50);

    public static final int Height = StatusBarHeight + ToolBarHeight;

    public ToolBarContainer(@NonNull Context context) {
        this(context, null);
    }

    public ToolBarContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPadding(0, StatusBarHeight, 0, 0);
        if(getBackground() == null){
            setBackgroundResource(R.drawable.global_tool_bar_bg);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

}