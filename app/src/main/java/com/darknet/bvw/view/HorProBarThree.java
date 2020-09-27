package com.darknet.bvw.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.darknet.bvw.R;

import java.text.DecimalFormat;

/**
 *
 */
public class HorProBarThree extends View {

    private Paint bgPaint;
    private Paint progressPaint;

    private Paint tipPaint;
    private Paint textPaint;
    private Paint lastPaint;
    /**
     * 起始,结束的数量
     */
    private Paint numPaint;

    private int mWidth;
    private int mHeight;
    private int mViewHeight;
    /**
     * 进度条的宽度
     */
    private int mProgressWidth;
    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前进度
     */
    private float currentProgress;

    /**
     * 进度动画
     */
    private ValueAnimator progressAnimator;

    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;

    /**
     * 进度条弧度
     */
    private int progressRound;

    /**
     * 百分比提示框画笔的宽度
     */
    private int tipPaintWidth;

    /**
     * 百分比提示框的高度
     */
    private int percentTipHeight;

    /**
     * 百分比提示框的宽度
     */
    private int percentTipWidth;

    /**
     * 画三角形的path
     */
    private Path path = new Path();
    /**
     * 三角形的高
     */
    private int triangleHeight;
    /**
     * 进度条距离提示框的高度
     */
    private int progressHeight;

    /**
     * 进度移动的距离
     */
    private float moveDis;

    private Rect textRect = new Rect();
    private String textString = "0";
    private String targetString = "1500";
    /**
     * 百分比文字字体大小
     */
    private int textPaintSize;

    /**
     * 进度条背景颜色
     */
//    private int bgColor = 0x0000FF;
    private int bgColor = Color.rgb(56, 101, 111);
    /**
     * 进度条颜色
     */
    private int progressColor = Color.rgb(88, 181, 250);

    private int[] colors = {Color.rgb(101, 226, 175), Color.rgb(88, 181, 250)};

    /**
     * 绘制提示框的矩形
     */
    private RectF rectF = new RectF();
    private RectF bgRectF = new RectF();
    private RectF progressRectF = new RectF();

    /**
     * 圆角矩形的圆角半径
     */
    private int roundRectRadius;

    /**
     * 进度监听回调
     */
    private ProgressListener progressListener;


    //进度条内的字体

    private float innerTextLeft;

    private Context contexttt;


    private int mDefaultProgresWidth=dp2px(180);


    private String mFirstText = "1:1";
    private String mSecondText = "1:1";
    private String mLastText = "0BTW";
    private boolean mNeedDrawProgressText=false;


    public HorProBarThree(Context context) {
        this(context, null);
    }

    public HorProBarThree(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        contexttt = context;
        initPaint();
    }



    public void setProgressColor(int[] colors) {
        this.colors = colors;
    }

    public void setTipColor(int color) {
        tipPaint.setColor(color);
    }

    /**
     * 初始化画笔宽度及view大小
     */
    private void init(Context context, AttributeSet attrs) {
        progressRound = dp2px(3);
        percentTipHeight = dp2px(15);
//        percentTipWidth = dp2px(30);
        tipPaintWidth = dp2px(1);
        triangleHeight = dp2px(3);
        roundRectRadius = dp2px(2);
        textPaintSize = sp2px(10);
//        progressHeight = dp2px(3);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorProBar);
        progressColor = ta.getColor(R.styleable.HorProBar_pro_color, Color.rgb(88, 181, 250));
//        mProgressWidth = ta.getDimensionPixelOffset(R.styleable.HorProBar_pro_width, dp2px(80));

        mProgressWidth = ta.getDimensionPixelOffset(R.styleable.HorProBar_pro_width, mDefaultProgresWidth);

        progressHeight = ta.getDimensionPixelOffset(R.styleable.HorProBar_pro_height, dp2px(3));
        //view真实的高度
        mViewHeight = percentTipHeight + tipPaintWidth + triangleHeight + progressRound + progressHeight;
        ta.recycle();
    }

    public void setTargetString(String target) {
//        LogUtil.logD("201810261453", target);
        targetString = target;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        bgPaint = getPaint(progressRound, bgColor, Paint.Style.FILL);
        progressPaint = getPaint(progressRound, progressColor, Paint.Style.FILL);
        progressPaint.setColor(contexttt.getResources().getColor(R.color._01FCDA));
        tipPaint = getPaint(tipPaintWidth,contexttt.getResources().getColor(R.color._01FCDA), Paint.Style.STROKE);

        initTextPaint();
        percentTipWidth = (int) textPaint.measureText(textString + "%") + dp2px(10);
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textPaintSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        numPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numPaint.setTextSize(textPaintSize);
//        numPaint.setColor(Color.BLACK);

        numPaint.setColor(contexttt.getResources().getColor(R.color._01FCDA));

//        tipPaint
        numPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);


        lastPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lastPaint.setTextSize(textPaintSize);
        lastPaint.setColor(Color.WHITE);
//        lastPaint.setColor(contexttt.getResources().getColor(R.color._01FCDA));

        lastPaint.setTextAlign(Paint.Align.CENTER);
        lastPaint.setAntiAlias(true);


    }

    /**
     * 统一处理paint
     *
     * @param strokeWidth
     * @param color
     * @param style
     * @return
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }

    /**
     * 测量宽度
     *
     * @param mode
     * @param width
     * @return
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mWidth = mProgressWidth + dp2px(30);
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                if (mProgressWidth == mDefaultProgresWidth) {
//                    mProgressWidth = mWidth - dp2px(20);
                    mProgressWidth = mWidth;
                }
                break;
            default:
                break;

        }
        return mWidth;
    }

    /**
     * 测量高度
     *
     * @param mode
     * @param height
     * @return
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
//                mHeight = mViewHeight + dp2px(10);
                mHeight = mViewHeight;
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
            default:
                break;
        }
        return mHeight;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        tipPaint.setColor(colors[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (moveDis < 0) {
            currentProgress = (mProgressWidth) * mProgress / 100;
            if (currentProgress < dp2px(2) && currentProgress != 0) {
                currentProgress = dp2px(2);
            }
            //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，
            //当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
            //moveDis是tip框移动的距离
//            if (currentProgress <= (mProgressWidth)) {
//                moveDis = currentProgress;
//            } else  /*if (currentProgress > mWidth-percentTipWidth)*/ {
//                moveDis = mProgressWidth;
//            }
            if (currentProgress <= mProgressWidth) {
                moveDis = ((mWidth - mProgressWidth - percentTipWidth) >> 1) + currentProgress;
            } else  /*if (currentProgress > mWidth-percentTipWidth)*/ {
                moveDis = (mWidth + mProgressWidth - percentTipWidth) / 2;
                Log.d("201904021445", "moveDis:" + moveDis + ";percentTipWidth" + percentTipWidth);
            }
            if (moveDis == 0) {
                moveDis = (mWidth - mProgressWidth - percentTipWidth) / 2;
            }
        }
//        LinearGradient sweepGradient = new LinearGradient(percentTipWidth >> 1, percentTipHeight, moveDis + (percentTipWidth >> 1), percentTipHeight, colors, null, Shader.TileMode.MIRROR);
//        progressPaint.setShader(sweepGradient);
        drawBgProgress(canvas);
        drawProgress(canvas);
        if (mNeedDrawProgressText) {
            drawTipView(canvas);
            drawText(canvas, textString);
        }
        drawInnerText(canvas);
//        drawTargetText(canvas);

    }


    public void setNeedDrawProgressText(boolean needDraw){
        mNeedDrawProgressText = needDraw;
        postInvalidate();
    }

    private void drawBgProgress(Canvas canvas) {
        bgRectF.left = (mWidth - mProgressWidth) / 2;
        bgRectF.top = percentTipHeight + triangleHeight;
        bgRectF.right = (mWidth + mProgressWidth) / 2;
        bgRectF.bottom = percentTipHeight + triangleHeight + progressHeight;
        canvas.drawRect(bgRectF, bgPaint);
//        canvas.drawRect(bgRectF, progressRound, progressRound, bgPaint);
        Log.d("201904021432", "drawBgProgress:" + mWidth + " /n");
    }

    private void drawProgress(Canvas canvas) {
        progressRectF.left = (mWidth - mProgressWidth) / 2;
        progressRectF.top = percentTipHeight + triangleHeight;
        if (currentProgress <= mProgressWidth) {
            progressRectF.right = currentProgress + ((mWidth - mProgressWidth) >> 1);
        } else {
            progressRectF.right = (mWidth + mProgressWidth) / 2;
        }
        progressRectF.bottom = percentTipHeight + triangleHeight + progressHeight;
//        canvas.drawRoundRect(progressRectF, progressRound, progressRound, progressPaint);
        canvas.drawRoundRect(progressRectF, 0, 0, progressPaint);
    }

    /**
     * 绘制进度上边提示百分比的view
     *
     * @param canvas
     */
    private void drawTipView(Canvas canvas) {


            drawRoundRect(canvas);
            drawTriangle(canvas);

    }


    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        rectF.set(moveDis, 0, percentTipWidth + moveDis, percentTipHeight);
        canvas.drawRoundRect(rectF, roundRectRadius, roundRectRadius, tipPaint);
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        path.moveTo(percentTipWidth / 2 - triangleHeight + moveDis, percentTipHeight);
        path.lineTo(percentTipWidth / 2 + moveDis, percentTipHeight + triangleHeight);
        path.lineTo(percentTipWidth / 2 + triangleHeight + moveDis, percentTipHeight);
        canvas.drawPath(path, tipPaint);
        path.reset();

    }

    public void setFirstText(String text) {
        mFirstText = text;
        invalidate();
    }

    public void setSecondText(String text) {
        mSecondText = text;
        invalidate();
    }

    public void setLastText(String text) {
        mLastText = text;
        invalidate();
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
//    private void drawText(Canvas canvas, String textString) {
//        textRect.left = (int) moveDis;
//        textRect.top = 0;
//        textRect.right = (int) (percentTipWidth + moveDis);
//        textRect.bottom = percentTipHeight;
//        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
//        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
//        //文字绘制到整个布局的中心位置
//        canvas.drawText(textString + "%", textRect.centerX(), baseline, textPaint);
//    }


    private void drawText(Canvas canvas, String textString) {
        textRect.left = (int) moveDis;
        textRect.top = 0;
        textRect.right = (int) (percentTipWidth + moveDis);
        textRect.bottom = percentTipHeight;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(textString + "%", textRect.centerX(), baseline, textPaint);

    }


    private void drawInnerText(Canvas canvas){
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        innerTextLeft = progressRectF.left + dp2px(10);
        drawDefaultText(canvas, mFirstText, innerTextLeft);
        drawDefaultText(canvas, mSecondText, innerTextLeft + dp2px(5) + textPaint.measureText(mFirstText));
        drawLastText(canvas, mLastText);
    }


    private void drawDefaultText(Canvas canvas, String textString, float left) {
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((progressRectF.bottom + progressRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(textString, left, baseline, textPaint);
    }

    private void drawLastText(Canvas canvas, String lastString) {
        lastPaint.setTextAlign(Paint.Align.RIGHT);
        Paint.FontMetricsInt fontMetrics = lastPaint.getFontMetricsInt();
        int baseline = (int) ((progressRectF.bottom + progressRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(lastString, bgRectF.right - dp2px(10), baseline, lastPaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawTargetText(Canvas canvas) {
//        canvas.drawText("0", getPaddingLeft() + percentTipWidth/2, mViewHeight + dp2px(8), numPaint);

//        float width = numPaint.measureText(targetString);
//        if (mProgress>0) {
        canvas.drawText(targetString, (percentTipWidth >> 1) + moveDis, mViewHeight + dp2px(8), numPaint);
//        }
    }

    /**
     * 进度移动动画  通过插值的方式改变移动的距离
     */
    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                //进度数值只显示整数，我们自己的需求，可以忽略
                textString = formatNum(/*format2Int(*/value);
                percentTipWidth = (int) (textPaint.measureText(textString + "%") + dp2px(10));
                //把当前百分比进度转化成view宽度对应的比例
                currentProgress = (mProgressWidth) * value / 100;
                if (currentProgress < dp2px(2) && currentProgress != 0) {
                    currentProgress = dp2px(2);
                }
                //进度回调方法
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }
                //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，
                //当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
                //moveDis是tip框移动的距离
                if (currentProgress <= (mProgressWidth)) {
                    moveDis = (mWidth - mProgressWidth - percentTipWidth) / 2 + currentProgress;
                } else  /*if (currentProgress > mWidth-percentTipWidth)*/ {
                    moveDis = (mWidth + mProgressWidth - percentTipWidth) / 2;
                }
                if (moveDis == 0) {
                    moveDis = (mWidth - mProgressWidth - percentTipWidth) / 2;
                }

                invalidate();
            }
        });
        progressAnimator.start();
    }


    /**
     * 设置进度条带动画效果
     *
     * @param progress
     * @return
     */
    public HorProBarThree setProgressWithAnimation(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    /**
     * 实时显示进度
     *
     * @param progress
     * @return
     */
    public HorProBarThree setCurrentProgress(float progress) {

            textString = formatNum(/*format2Int(*/progress);
            textPaint.setColor(contexttt.getResources().getColor(R.color._01FCDA));
            percentTipWidth = (int) (textPaint.measureText(textString + "%") + dp2px(10));
            mProgress = progress;
            currentProgress = (mProgressWidth) * progress / 100;
            if (currentProgress < dp2px(2) && currentProgress != 0) {
                currentProgress = dp2px(2);
            }
            if (mWidth == 0) {
                moveDis = -1;
            } else {
                if (currentProgress <= (mProgressWidth)) {
                    moveDis = (mWidth - mProgressWidth - percentTipWidth) / 2 + currentProgress;
                } else  /*if (currentProgress > mWidth-percentTipWidth)*/ {
                    moveDis = (mWidth + mProgressWidth - percentTipWidth) / 2;
                }
                if (moveDis == 0) {
                    moveDis = (mWidth - mProgressWidth - percentTipWidth) / 2;
                }
            }
            invalidate();

        return this;
    }

    /**
     * 开启动画
     */
    public void startProgressAnimation() {
        if (progressAnimator != null &&
                !progressAnimator.isRunning() &&
                !progressAnimator.isStarted())
            progressAnimator.start();
    }

    /**
     * 暂停动画
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator.pause();
        }
    }

    /**
     * 恢复动画
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeProgressAnimation() {
        if (progressAnimator != null)
            progressAnimator.resume();
    }

    /**
     * 停止动画
     */
    public void stopProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator.end();
        }
    }

    /**
     * 回调接口
     */
    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    /**
     * 回调监听事件
     *
     * @param listener
     * @return
     */
    public HorProBarThree setProgressListener(ProgressListener listener) {
        progressListener = listener;
        return this;
    }

    /**
     * 格式化数字(保留两位小数)
     *
     * @param money
     * @return
     */
    public static String formatNumTwo(double money) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money);
    }

    /**
     * 格式化数字(保留一位小数)
     *
     * @param money
     * @return
     */
    public static String formatNum(int money) {
        DecimalFormat format = new DecimalFormat("0");
        return format.format(money);
    }

    /**
     * 格式化数字(保留两位小数)
     *
     * @param money
     * @return
     */
    public static String formatNum(float money) {
        if (((int) money * 1000) == (int) (money * 1000)) {
            //如果是一个整数
            return String.valueOf((int) money);
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }
}
