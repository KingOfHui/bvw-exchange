package com.darknet.bvw.player.view;//package com.zqh.player.view;
//
//import android.view.View;
//
///**
// * Created by wanghaofei on 2019/4/2.
// * e-mail : xxx@xx
// * time   : 2019/04/02
// * desc   :
// * version: 1.0
// */
//
//public class LoadingView extends View {
//
//    private static final float RADIUS = 25;  //小球半径
//
//    private CustomPoint currentPointBlue;  //蓝色的小球
//    private CustomPoint currentPointRed;   //红色的小球
//    private Paint mPaint;    //蓝色小球的画笔
//    private static final int BOUNDARY = 70;   //白色的边界长度
//
//    private ValueAnimator anim;   //动画
//
//    public CustomPropertyAnimationView(Context context) {
//        this(context, null);
//    }
//
//    public CustomPropertyAnimationView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CustomPropertyAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView();
//    }
//
//    private void initView() {
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setAntiAlias(true);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (currentPointBlue == null) {
//            currentPointBlue = new CustomPoint(getWidth() / 2, getHeight() / 2);
//            currentPointRed = new CustomPoint(getWidth() / 2, getHeight() / 2);
//            drawCircle(canvas);   //画圆
//            if (isFirst) {
//                startAnimation();   //开始动画
//                isFirst = false;
//            }
//        } else {
//            drawCircle(canvas);
//        }
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);
//        int width;
//        int height;
//        if (mode == MeasureSpec.EXACTLY) {   //表示确定的值或者MATCH_PARENT
//            width = size;
//        } else {   //表示WARP_CONTENT
//            width = (int) (2 * RADIUS + 2 * BOUNDARY + getPaddingLeft() + getPaddingRight());
//        }
//        mode = MeasureSpec.getMode(heightMeasureSpec);
//        size = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (mode == MeasureSpec.EXACTLY) {
//            height = size;
//        } else {   //表示WARP_CONTENT
//            height = (int) (4 * RADIUS + getPaddingTop() + getPaddingBottom());
//        }
//        setMeasuredDimension(width, height);
//    }
//
//    //外部调用的地方可以控制动画的开始、暂停与停止
//    public void startCustomAnim() {
//        if (!anim.isStarted() || anim.isPaused()) {
//            anim.start();
//        }
//    }
//
//    public void stopCustomAnim() {
//        if (anim.isStarted()) {
//            anim.end();
//        }
//    }
//
//    public void pauseCustomAnim() {
//        if (!anim.isPaused()) {
//            anim.pause();
//        }
//    }
//
//    //画圆
//    private void drawCircle(Canvas canvas) {
//        float blueX = currentPointBlue.getX();
//        float redX = Math.abs(currentPointBlue.getX() - getWidth() / 2) + getWidth() / 2;
//        currentPointRed.setX(redX + getWidth() / 2);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawCircle(blueX, getHeight() / 2, RADIUS, mPaint);
//        mPaint.setColor(Color.RED);
//        canvas.drawCircle(redX, getHeight() / 2, RADIUS, mPaint);
//    }
//
//    //开始动画
//    private void startAnimation() {
//        CustomPoint startPoint = new CustomPoint(getWidth() / 2, getHeight() / 2);
//        CustomPoint endPoint = new CustomPoint(getWidth() / 2 - 2 * RADIUS, getHeight() / 2);
//        anim = ValueAnimator.ofObject(new CustomPointEvaluator(), startPoint, endPoint, startPoint);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                currentPointBlue = (CustomPoint) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        anim.setDuration(600);
//        anim.setRepeatCount(Animation.INFINITE);
//    }
//}
//
//
//