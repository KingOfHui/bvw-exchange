package com.darknet.bvw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;


import com.darknet.bvw.R;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/17 17:39
 * <br>desc: 圆角 TextView
 */
public class CornerTextView extends AppCompatTextView {

	private float mCornerLeftTop;
	private float mCornerRightTop;
	private float mCornerRightBottom;
	private float mCornerLeftBottom;
	private float mStrokeSize;
	private int mColor;
	private int mStartColor;
	private int mEndColor;
	private int mAngle;

	public CornerTextView(Context context) {
		this(context, null);
	}

	public CornerTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CornerTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CornerTextView);
		float corner = ta.getDimension(R.styleable.CornerTextView_corner, 0);
		mCornerLeftTop = ta.getDimension(R.styleable.CornerTextView_corner_left_top, corner);
		mCornerRightTop = ta.getDimension(R.styleable.CornerTextView_corner_right_top, corner);
		mCornerRightBottom = ta.getDimension(R.styleable.CornerTextView_corner_right_bottom, corner);
		mCornerLeftBottom = ta.getDimension(R.styleable.CornerTextView_corner_left_bottom, corner);
		mStrokeSize = ta.getDimension(R.styleable.CornerTextView_stroke_size, 0);
		mColor = ta.getColor(R.styleable.CornerTextView_color, Color.TRANSPARENT);
		mStartColor = ta.getColor(R.styleable.CornerTextView_startColor, -1);
		mEndColor = ta.getColor(R.styleable.CornerTextView_endColor, -1);
		mAngle = ta.getColor(R.styleable.CornerTextView_angle, Color.TRANSPARENT);
		ta.recycle();

		updateBackground();

	}

	private void updateBackground() {
		RoundRectShape shape = new RoundRectShape(
				new float[]{mCornerLeftTop, mCornerLeftTop, mCornerRightTop, mCornerRightTop, mCornerRightBottom, mCornerRightBottom, mCornerLeftBottom, mCornerLeftBottom}
				, new RectF(mStrokeSize, mStrokeSize, mStrokeSize, mStrokeSize),
				new float[]{mCornerLeftTop, mCornerLeftTop, mCornerRightTop, mCornerRightTop, mCornerRightBottom, mCornerRightBottom, mCornerLeftBottom, mCornerLeftBottom}
		);
		if(mStartColor == -1) {
			ShapeDrawable drawable = new ShapeDrawable(shape);
			Paint paint = drawable.getPaint();
			paint.setColor(mColor);
			paint.setStyle(Paint.Style.FILL);
			setBackgroundDrawable(drawable);
		}else {
			if(mAngle < 0){
				mAngle = 360 + mAngle;
			}
			GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.values()[mAngle / 45], new int[]{mStartColor, mEndColor});
			drawable.setCornerRadii(new float[]{mCornerLeftTop, mCornerLeftTop, mCornerRightTop, mCornerRightTop, mCornerRightBottom, mCornerRightBottom, mCornerLeftBottom, mCornerLeftBottom});
			setBackgroundDrawable(drawable);
		}
	}

	public void setColor(int color){
		mColor = color;
		updateBackground();
	}

	public void setCorner(int corner){
		mCornerLeftTop = corner;
		mCornerRightTop = corner;
		mCornerRightBottom = corner;
		mCornerLeftBottom = corner;
		updateBackground();
	}
}
