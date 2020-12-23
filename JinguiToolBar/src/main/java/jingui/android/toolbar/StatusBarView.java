package jingui.android.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.jingui.lib.utils.DensityUtils;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/19 02:31
 * <br>desc: 用于空出状态栏的高度
 */
public class StatusBarView extends View {
	private static final int StatusBarHeight = DensityUtils.getStatusBarHeight();

	public StatusBarView(Context context) {
		this(context, null);
	}

	public StatusBarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StatusBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setPadding(0, StatusBarHeight, 0, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(StatusBarHeight, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
	}
}
