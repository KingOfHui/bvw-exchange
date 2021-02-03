package com.darknet.bvw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.darknet.bvw.R;

public class DrawableTextView extends AppCompatTextView {
    private Drawable drawableLeft = null, drawableTop = null, drawableRight = null,
            drawableBottom = null;
    private int drawableWidth, drawableHeight;
    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        int count = typedArray.getIndexCount();
        for(int i = 0; i < count; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.DrawableTextView_drawableRight:
                    drawableRight = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableLeft:
                    drawableLeft = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableTop:
                    drawableTop = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableBottom:
                    drawableBottom = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableWidth:
                    drawableWidth = typedArray.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.DrawableTextView_drawableHeight:
                    drawableHeight = typedArray.getDimensionPixelSize(attr,0);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
        if(null != drawableLeft){
            drawableLeft.setBounds(0,0, drawableWidth, drawableHeight);
        }
        if(null != drawableRight){
            drawableRight.setBounds(0,0, drawableWidth, drawableHeight);
        }
        if(null != drawableTop){
            drawableTop.setBounds(0,0, drawableWidth, drawableHeight);
        }
        if(null != drawableBottom){
            drawableBottom.setBounds(0,0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

}
