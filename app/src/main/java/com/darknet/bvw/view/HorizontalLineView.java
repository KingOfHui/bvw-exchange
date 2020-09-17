package com.darknet.bvw.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.darknet.bvw.R;
import com.darknet.bvw.util.TipHelper;

/**
 * @ClassName HorizontalLineView
 * @Description
 * @Author dinghui
 * @Date 2020/9/8 0008 19:44
 */
public class HorizontalLineView extends LinearLayout {

    private TextView mTvRight;
    private ImageView mIvRight;
    private TextView mTvLeft;

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
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);
        mIvRight = view.findViewById(R.id.iv_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalLineView);
        String leftText = ta.getString(R.styleable.HorizontalLineView_left_text);
        String rightText = ta.getString(R.styleable.HorizontalLineView_right_text);
        int rightImageRes = ta.getResourceId(R.styleable.HorizontalLineView_right_image, R.mipmap.contacts_press);
        boolean visible = ta.getBoolean(R.styleable.HorizontalLineView_img_visible, false);
        mTvLeft.setText(leftText);
        mTvRight.setText(rightText);
        mIvRight.setVisibility(visible ? VISIBLE : GONE);
        mIvRight.setImageResource(rightImageRes);
        ta.recycle();
    }

    public void setRightText(String rightText) {
        if (mTvRight != null) {
            mTvRight.setText(rightText);
        }
    }

    public void setRightImgClick() {
        if (mIvRight != null) {
            mIvRight.setOnClickListener(view -> {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, mTvRight.getText().toString().trim());
                // 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
                TipHelper.Vibrate(getContext(), new long[]{200, 300}, false);
            });
        }
    }
}
