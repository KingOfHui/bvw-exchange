package com.darknet.bvw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.darknet.bvw.R;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class VerticalProgressBarFour extends View {
    /**
     * 画笔
     */
    private Paint mPaint;

    private TextPaint mTextPaint;
    private TextPaint mSecondTextPaint;
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

    private String mFirstText = "";
    private String mSecondText = "";

    private Context contexttt;

    private int bgColor = Color.rgb(56, 101, 111);

    /**
     * 最大进度
     */
    private int max = 100;
    private int mTextHeight;

    public VerticalProgressBarFour(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        contexttt = context;
        init();
    }

    public VerticalProgressBarFour(Context context, AttributeSet attrs) {
        super(context, attrs);
        contexttt = context;
        init();
    }

    public VerticalProgressBarFour(Context context) {
        super(context);
        contexttt = context;
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
        mPaint.setColor(bgColor);//第二层矩形颜色(背景层颜色)
        mRectF.set(0, 0, width, height);//第二层矩形(背景层)
        canvas.drawRect(mRectF, mPaint);//画背景层圆角矩形(盖在描边层之上)

        if (mProgress == 0)//进度为 0不画进度
            return;
        //第三层矩形(进度层)
        mRectF.set(0, height - mProgress / 100f * height, width, height);
//        mPaint.setColor(contexttt.getResources().getColor(R.color._8236FF));
        mPaint.setColor(contexttt.getResources().getColor(R.color._01FCDA));
        canvas.drawRect(mRectF, mPaint);//画第三层(进度层)圆角矩形(盖在背景层之上)
        drawFirsText(canvas);
    }

    private void drawFirsText(Canvas canvas) {

        canvas.save();
        float dy;
        if (mTextHeight > mRectF.height()) {
            dy = mRectF.top - mTextHeight;
        } else {
            dy = mRectF.centerY() - (mTextHeight >> 1);
        }
        canvas.translate(0, dy);
        StaticLayout sl = new StaticLayout(mSpannableStringBuilder, mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER,1.0f, 0.0f, true);
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

    private SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder();


//    public void setFirstText(String firstText, int firstTextSize, int firstColor) {
//        mSpannableStringBuilder.append(firstText).append("\n");
//        mFirstText = firstText;
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(firstTextSize), 0, firstText.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(firstColor), 0, firstText.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
//    }
//
//    public void setSecondText(String secondText, int secondTextSize, int secondColor) {
//        mSpannableStringBuilder.append(secondText);
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(secondTextSize), mFirstText.length(), mFirstText.length() + secondText.length()+10, SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(secondColor), mFirstText.length(), mFirstText.length() + secondText.length()+10, SPAN_EXCLUSIVE_EXCLUSIVE);
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = (int) ((fontMetrics.descent - fontMetrics.ascent)*2f);
//        postInvalidate();
//    }


    public void setFirstText(String firstText, int firstTextSize, int firstColor) {
        mSpannableStringBuilder.append(firstText).append("\n");
        mFirstText = firstText;
        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(firstTextSize), 0, firstText.length()+1, SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(firstColor), 0, firstText.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void setSecondText(String secondText, int secondTextSize, int secondColor) {
        mSpannableStringBuilder.append(secondText);
        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(secondTextSize), mFirstText.length(), mFirstText.length() + secondText.length()+1, SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(secondColor), mFirstText.length(), mFirstText.length() + secondText.length()+1, SPAN_EXCLUSIVE_EXCLUSIVE);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (int) ((fontMetrics.descent - fontMetrics.ascent) * 2f);
        mSecondText = mFirstText +secondText;
        postInvalidate();
    }


    public void setThirdText(String thirdText, int thirdTextSize, int thirdColor) {
        mSpannableStringBuilder.append("\n").append(thirdText);
        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(thirdTextSize), mSecondText.length()+1, mSecondText.length() + thirdText.length()+2, SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(thirdColor), mSecondText.length()+1, mSecondText.length() + thirdText.length()+2, SPAN_EXCLUSIVE_EXCLUSIVE);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (int) ((fontMetrics.descent - fontMetrics.ascent) * 2f);
        postInvalidate();
    }





//    public void setSecondText(String secondText, int secondTextSize, int secondColor) {
//        mSpannableStringBuilder.append(secondText).append("\n");
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(secondTextSize), mFirstText.length(), mFirstText.length() + secondText.length()+1, SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(secondColor), mFirstText.length(), mFirstText.length() + secondText.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
////        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
////        mTextHeight = (int) ((fontMetrics.descent - fontMetrics.ascent) * 2f);
////        postInvalidate();
//    }
//
//
//    public void setThirdText(String secondText, int secondTextSize, int secondColor) {
//        mSpannableStringBuilder.append(secondText);
//        mSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(secondTextSize), mFirstText.length(), mFirstText.length() + secondText.length()+1, SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(secondColor), mFirstText.length(), mFirstText.length() + secondText.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = (int) ((fontMetrics.descent - fontMetrics.ascent) * 2f);
//        postInvalidate();
//    }

    public void setMax(int maxProgress) {
        this.max = maxProgress;
    }


    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

}
