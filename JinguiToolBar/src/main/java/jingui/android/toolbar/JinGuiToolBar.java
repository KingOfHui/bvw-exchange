package jingui.android.toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingui.lib.utils.BaseApplication;
import com.jingui.lib.utils.DensityUtils;
import com.jingui.lib.utils.ResourcesUtils;
import com.jingui.lib.utils.ViewUtil;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

/**
 * Created by guojiel on 2018/5/24.
 */

public class JinGuiToolBar extends ToolBarContainer implements View.OnClickListener {

    public static final int ID_LEFT_ICON = 100;
    public static final int ID_RIGHT_ICON_1 = 101;
    public static final int ID_RIGHT_ICON_2 = 102;
    public static final int ID_RIGHT_TEXT = 103;

    private final int IconSize = DensityUtils.dip2px(50);
    private final int Warp = FrameLayout.LayoutParams.WRAP_CONTENT;

    private ImageView mLeftIcon;
    private TextView mCenterTitle;
    private ImageView mRightIcon1;
    private ImageView mRightIcon2;
    private TextView mTvRightText;

    private Drawable mLeftIconDrawable;
    private String mCenterTitleText;
    private int mCenterTitleColor = Color.BLACK;
    private Drawable mRightIcon1Drawable;
    private Drawable mRightIcon2Drawable;
    private int mCenterTitleTextSize = DensityUtils.sp2px(getContext(), 20);

    private OnItemClickListener mOnItemClickListener;
    //0=normal, 1=bold, 2=italic
    private int centerTitleStyle = Typeface.BOLD;
    private String mRightText;
    private int mRightTextColor = Color.BLACK;
    private float mRightTextSize = DensityUtils.sp2px(BaseApplication.instance(), 15);
    private int mRightTextStyle = Typeface.NORMAL;

    public JinGuiToolBar(@NonNull Context context) {
        this(context, null);
    }

    public JinGuiToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mOnItemClickListener = new OnBackListener(ViewUtil.getActivity(getContext()));
        initAttrs(attrs);
        initView();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = getContext().obtainStyledAttributes(attrs, R.styleable.JinGuiToolBar);
            mLeftIconDrawable = a.getDrawable(R.styleable.JinGuiToolBar_leftIcon);
            mCenterTitleText = a.getString(R.styleable.JinGuiToolBar_centerTitleText);
            mCenterTitleColor = a.getColor(R.styleable.JinGuiToolBar_centerTitleColor, mCenterTitleColor);
            mRightIcon1Drawable = a.getDrawable(R.styleable.JinGuiToolBar_rightIcon1);
            mRightIcon2Drawable = a.getDrawable(R.styleable.JinGuiToolBar_rightIcon2);
            centerTitleStyle = a.getInt(R.styleable.JinGuiToolBar_centerTitleStyle, centerTitleStyle);
            mCenterTitleTextSize = a.getDimensionPixelSize(R.styleable.JinGuiToolBar_centerTitleTextSize, mCenterTitleTextSize);

            mRightText = a.getString(R.styleable.JinGuiToolBar_rightText);
            mRightTextColor = a.getColor(R.styleable.JinGuiToolBar_rightTextColor, mRightTextColor);
            mRightTextSize = a.getDimension(R.styleable.JinGuiToolBar_rightTextSize, mRightTextSize);
            mRightTextStyle = a.getInt(R.styleable.JinGuiToolBar_rightTextStyle, mRightTextStyle);

        }finally {
            if(a != null){
                a.recycle();
            }
        }
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        setLeftIcon(mLeftIconDrawable);

        mCenterTitle = new TextView(getContext());
        mCenterTitle.setTextColor(mCenterTitleColor);
        mCenterTitle.getPaint().setTextSize(mCenterTitleTextSize);
        mCenterTitle.setText(mCenterTitleText);
        mCenterTitle.getPaint().setFakeBoldText(true);
        mCenterTitle.setTypeface(Typeface.DEFAULT, centerTitleStyle);
        addView(mCenterTitle, createLayoutParams(Warp, Warp, Gravity.CENTER));

        setRightIcon1(mRightIcon1Drawable);
        setRightIcon2(mRightIcon2Drawable);
        setRightText(mRightText, mRightTextColor, mRightTextSize, mRightTextStyle);
    }

    public void enableRightText(boolean enable){
        ViewUtil.setVisibility(enable, mTvRightText);
    }

    public void setRightText(String rightText, int rightTextColor, float rightTextSize, int rightTextStyle) {
        if(mTvRightText == null){
            mTvRightText = new AppCompatTextView(getContext());
            mTvRightText.setOnClickListener(this);
            FrameLayout.LayoutParams params = createLayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT| Gravity.CENTER_VERTICAL);
            params.rightMargin = DensityUtils.dip2px(20);
            addView(mTvRightText, params);
        }

        mTvRightText.setText(rightText);
        mTvRightText.setTextColor(rightTextColor);
        mTvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        mTvRightText.setTypeface(Typeface.DEFAULT, rightTextStyle);
    }

    public void setLeftIcon(int resId){
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(getContext(), resId);
        }catch (Exception ignored){
        }
        setLeftIcon(drawable);
    }

    public void setLeftIcon(Drawable drawable){
        mLeftIconDrawable = drawable;
        if(mLeftIconDrawable != null){
            if(mLeftIcon == null){
                mLeftIcon = createLeftIcon();
                addView(mLeftIcon, createLayoutParams(IconSize, IconSize, Gravity.LEFT));
            }
        }
        if(mLeftIcon != null) {
            mLeftIcon.setImageDrawable(mLeftIconDrawable);
            mLeftIcon.setBackground(ResourcesUtils.getDrawable(jingui.android.utils.R.drawable.nim_touch_bg));
        }
    }

    private ImageView createLeftIcon() {
        return createRightIcon();
    }

    public void setRightIcon1(int resId){
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(getContext(), resId);
        }catch (Exception ignored){
        }
        setRightIcon1(drawable);
    }

    public void setRightIcon1(Drawable drawable){
        mRightIcon1Drawable = drawable;
        if(mRightIcon1Drawable != null){
            if(mRightIcon1 == null) {
                mRightIcon1 = createRightIcon();
                addView(mRightIcon1, createLayoutParams(IconSize, IconSize, Gravity.RIGHT));
            }
        }
        if(mRightIcon1 != null){
            mRightIcon1.setImageDrawable(mRightIcon1Drawable);
        }
    }

    public void setRightIcon2(int resId){
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(getContext(), resId);
        }catch (Exception ex){
        }
        setRightIcon2(drawable);
    }

    public void setRightIcon2(Drawable drawable){
        mRightIcon2Drawable = drawable;
        if(mRightIcon2Drawable != null){
            if(mRightIcon2 == null) {
                mRightIcon2 = createRightIcon();
                FrameLayout.LayoutParams params = createLayoutParams(IconSize, IconSize, Gravity.RIGHT);
                params.rightMargin = IconSize;
                addView(mRightIcon2, params);
            }
        }
        if(mRightIcon2 != null){
            mRightIcon2.setImageDrawable(mRightIcon2Drawable);
        }
    }

    public void setCenterTitle(CharSequence text){
        mCenterTitle.setText(text);
    }

    public void setCenterTitle(int resId){
        mCenterTitle.setText(resId);
    }

    private ImageView createRightIcon(){
        ImageView icon = new ImageView(getContext());
        icon.setOnClickListener(this);
        icon.setScaleType(ImageView.ScaleType.CENTER);
        return icon;
    }

    private FrameLayout.LayoutParams createLayoutParams(int w, int h, int g){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w, h, g);
        return params;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener == null){
            return;
        }
        if(v == mLeftIcon){
            if(mLeftIconDrawable != null) {
                mOnItemClickListener.onItemClick(ID_LEFT_ICON);
            }
        }else if(v == mRightIcon1){
            if(mRightIcon1Drawable != null) {
                mOnItemClickListener.onItemClick(ID_RIGHT_ICON_1);
            }
        }else if(v == mRightIcon2){
            if(mRightIcon2Drawable != null) {
                mOnItemClickListener.onItemClick(ID_RIGHT_ICON_2);
            }
        }else if(v == mTvRightText){
            mOnItemClickListener.onItemClick(ID_RIGHT_TEXT);
        }
    }

    /**
     * 别的地方不能给设置id
     */
    @Override
    public void setId(int id) {
    }

    public interface OnItemClickListener{
        void onItemClick(int id);
    }

    public static class OnBackListener implements OnItemClickListener {

        private WeakReference<Activity> mActivityWeakReference;

        public OnBackListener(Activity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onItemClick(int id) {
            if(id == ID_LEFT_ICON){
                Activity activity = mActivityWeakReference.get();
                if(activity == null) return;
                activity.finish();
            }
        }
    }
}
