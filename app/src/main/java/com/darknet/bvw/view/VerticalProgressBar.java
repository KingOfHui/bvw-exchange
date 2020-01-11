package com.darknet.bvw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class VerticalProgressBar extends View {
    /**
     * 画笔
     */
    private Paint mPaint;

    private TextPaint mTextPaint;
    /**
     * 进度值
     */
    private int mProgress;
    /**
     * 宽度值
     */
    private int width;
    private int height;// 高度值
    /**
     * 画进度条的矩形
     */
    private RectF mRectF;

    private String mText = "梯度4000";


    private int oneColor = Color.rgb(1, 252, 218);
    private int twoColor = Color.rgb(56, 101, 111);

    /**
     * 最大进度
     */
    private int max = 100;
    private int mTextHeight;

    public VerticalProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(dp2px(15));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;// 宽度值
        height = getMeasuredHeight() - 1;// 高度值
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(twoColor);//第二层矩形颜色(背景层颜色)
        mRectF.set(0, 0, width, height);//第二层矩形(背景层)
        canvas.drawRect(mRectF, mPaint);//画背景层圆角矩形(盖在描边层之上)

        if (mProgress == 0)//进度为 0不画进度
            return;
        //第三层矩形(进度层)
        mRectF.set(0, height - mProgress / 100f * height, width, height);
        mPaint.setColor(oneColor);
        canvas.drawRect(mRectF, mPaint);//画第三层(进度层)圆角矩形(盖在背景层之上)
        canvas.save();
        drawText(canvas);

    }

    private void drawText(Canvas canvas) {


        float dy;
        if (mTextHeight>mRectF.height()){
            dy= mRectF.top-mTextHeight;
        }else {
            dy=mRectF.centerY() - (mTextHeight>>1);
        }

        canvas.translate(0, dy);
        StaticLayout sl = new StaticLayout(mText, mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
        sl.draw(canvas);
        canvas.restore();
    }


    /**
     * 设置progressbar进度
     */
    public void setProgress(int currentCount) {
        this.mProgress = currentCount > max ? max : currentCount;
        postInvalidate();
    }

    public void setText(String text) {
        mText = text;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (int) (fontMetrics.descent - fontMetrics.ascent);

        postInvalidate();
    }

    public void setMax(int maxProgress) {
        this.max = maxProgress;
    }


    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
