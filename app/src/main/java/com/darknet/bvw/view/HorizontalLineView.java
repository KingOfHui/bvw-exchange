package com.darknet.bvw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.darknet.bvw.R;

/**
 * @ClassName HorizontalLineView
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 19:44
 */
public class HorizontalLineView extends LinearLayout {

    private TextView mTvRight;

    public HorizontalLineView(Context context) {
        this(context,null);
    }

    public HorizontalLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.view_horizontal_line, this);
        TextView tvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);
        ImageView ivRight = view.findViewById(R.id.iv_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalLineView);
        String leftText = ta.getString(R.styleable.HorizontalLineView_left_text);
        String rightText = ta.getString(R.styleable.HorizontalLineView_right_text);
        int rightImageRes = ta.getResourceId(R.styleable.HorizontalLineView_right_image, R.mipmap.contacts_press);
        boolean visible = ta.getBoolean(R.styleable.HorizontalLineView_img_visible, false);
        tvLeft.setText(leftText);
        mTvRight.setText(rightText);
        ivRight.setVisibility(visible ? VISIBLE : GONE);
        ivRight.setImageResource(rightImageRes);
        ta.recycle();
    }

    public void setRightText(String rightText) {
        if (mTvRight != null) {
            mTvRight.setText(rightText);
        }
    }
}
