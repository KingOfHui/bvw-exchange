package com.darknet.bvw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.darknet.bvw.R;

import org.web3j.abi.datatypes.Int;

import java.math.BigDecimal;


public class CustomVerticalView extends ConstraintLayout {

    private TextView mTv_top;
    private TextView mTv_bottom;
    private TextView mTv_biw;

    public CustomVerticalView(Context context) {
        this(context, null);
    }

    public CustomVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CustomVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomVerticalView);
        String topText = obtainStyledAttributes.getString(R.styleable.CustomVerticalView_topStr);
        String topTextLittle = obtainStyledAttributes.getString(R.styleable.CustomVerticalView_topStrLittle);
        boolean visible = obtainStyledAttributes.getBoolean(R.styleable.CustomVerticalView_topStrLittleVisible, false);
        String bottomText = obtainStyledAttributes.getString(R.styleable.CustomVerticalView_bottom_textStr);
        obtainStyledAttributes.recycle();
        View view = View.inflate(context, R.layout.layout_custom_vertical_view, this);
        mTv_top = view.findViewById(R.id.tv_top);
        mTv_bottom = view.findViewById(R.id.tv_bottom);
        mTv_biw = view.findViewById(R.id.tv_biw);
        mTv_biw.setVisibility(visible?VISIBLE:GONE);
        setTopStr(topText);
        mTv_bottom.setText(bottomText);
    }


//    private int heigh = 0;
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (heigh == 0) {
//            for (int i = 0; i < getChildCount(); i++) {
//                View childView = getChildAt(i);
//                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//                heigh += childView.getMeasuredHeight();
//            }
//        }
//        setMeasuredDimension(widthMeasureSpec, heigh);
//    }

    public void setTopStr(String topStr) {
        if (topStr!=null) {
            mTv_top.setText(topStr);
        }
    }

    public void setTopStr(BigDecimal top) {
        if (top != null) {
            mTv_top.setText(top.toPlainString());
        }  else {
            mTv_top.setText("0");
        }
    }

    public void setBottomText(String text) {
        if (text != null) {
            mTv_bottom.setText(text);
        }
    }

/*
    fun setTopStr(topText:String?) {
//        tv_top.textSize = DisplayUtil.dip2px(context,8)
//        topText?.let {
//            if (it.length > 9) {
//                tv_top.textSize = DisplayUtil.dip2px(context,7)
//            }
//        }
        tv_top.text = topText ?:"0"
    }

    fun setTopStrLittle(text:String?) {

//        tv_biw.textSize = DisplayUtil.dip2px(context,4)
        tv_biw.text = text ?:"0"
    }

    fun setTopStr(top:BigDecimal?) {
//        tv_top.textSize = DisplayUtil.dip2px(context,10)
//        top?.let {
//            if (it.stripTrailingZeros().toPlainString().length > 7) {
//                tv_top.textSize = DisplayUtil.dip2px(context,7)
//            }
//        }
        tv_top.text = "${top?: BigDecimal.ZERO}"
    }*/

/*

    //控件默认的宽高
    var defaultWidth = 300
    var defaultHeight = 200

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val withSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT
            && layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT
        ) {
            setMeasuredDimension(defaultWidth, defaultHeight)
        } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(defaultWidth, heightSize)
        } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(withSize, defaultHeight)
        }
    }
*/

    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = 70 as Int //高度默认大小
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = 100 //宽度默认大小
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }*/
}