package com.darknet.bvw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.darknet.bvw.R;


/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/01/19
 *     desc   :
 *     version: 1.0.1
 * </pre>
 */
public class RItem extends ConstraintLayout {

    private TextView mTvTopTitle;
    private TextView mTvBottomInfo;
    private ImageView mIvRightArrow;
    private View mLineView;
    private TextView mTvRight;

    public RItem(Context context) {
        this(context,null);
    }

    public RItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.library_widget_ritem, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RItem);
        String topText = ta.getString(R.styleable.RItem_rTopTitle);
        String rightText = ta.getString(R.styleable.RItem_rRightText);
        int titleStyle = ta.getInt(R.styleable.RItem_rTopTitleTextStyle, 1);
        String bottomInfo = ta.getString(R.styleable.RItem_rBottomDescription);
        Drawable rightArrowDrawable = ta.getDrawable(R.styleable.RItem_rRightArrowIcon);
        boolean isShowRightArrow = ta.getBoolean(R.styleable.RItem_rShowRightArrow, true);
        boolean isShowBottomLine = ta.getBoolean(R.styleable.RItem_rShowBottomLine, false);
        ta.recycle();
        mTvTopTitle = findViewById(R.id.tv_title);
        mTvRight = findViewById(R.id.tvRight);
        mTvBottomInfo = findViewById(R.id.tv_bottom_info);
        mIvRightArrow = findViewById(R.id.iv_right_arrow);
        mLineView = findViewById(R.id.view_bottom_line);

        setTopTitle(topText);
        setRightText(rightText);
        setTopTitleStyle(titleStyle);
        setBottomInfo(bottomInfo);
        setRightArrowDrawable(rightArrowDrawable);
        showRightArrow(isShowRightArrow);
        showBottomLine(isShowBottomLine);

    }

    public void setRightText(String rightText) {
        if (rightText != null) {
            mTvRight.setText(rightText);
        }
    }

    public void showBottomLine(boolean isShowBottomLine) {
        mLineView.setVisibility(isShowBottomLine ? VISIBLE : GONE);
    }

    public void showRightArrow(boolean isShow) {
        mIvRightArrow.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setRightArrowDrawable(Drawable rightArrowDrawable) {
        if (rightArrowDrawable != null) {
            mIvRightArrow.setImageDrawable(rightArrowDrawable);
        }
    }

    public void setBottomInfo(String bottomInfo) {
        if (bottomInfo != null) {
            mTvBottomInfo.setText(bottomInfo);
        }
    }

    public void setTopTitleStyle(int titleStyle) {
        mTvTopTitle.setTypeface(Typeface.DEFAULT, titleStyle);
    }

    public void setTopTitle(String topText) {
        if (topText != null) {
            mTvTopTitle.setText(topText);
        }
    }
}
