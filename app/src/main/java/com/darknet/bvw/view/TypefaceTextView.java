package com.darknet.bvw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.darknet.bvw.R;

public class TypefaceTextView extends AppCompatTextView {
    public TypefaceTextView(Context context) {
        this(context, null);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypefaceTextView(context, attrs);
    }

    private void initTypefaceTextView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefaceTextView);
        String type = typedArray.getString(R.styleable.TypefaceTextView_typeface);
        if (null == type) {
            return;
        }
        Typeface typeface = null;
        switch (type) {
            case "PingFangBold":
                typeface = Typeface.createFromAsset(context.getAssets(), "font/PingFangBold.ttf");
                setTypeface(typeface);
                break;
            case "PingFangRegular":
                typeface = Typeface.createFromAsset(context.getAssets(), "font/PingFangRegular.ttf");
                setTypeface(typeface);
                break;
            case "DINProBold":
                typeface = Typeface.createFromAsset(context.getAssets(), "font/DINPro-Bold.otf");
                setTypeface(typeface);
                break;
            case "DINProRegular":
                typeface = Typeface.createFromAsset(context.getAssets(), "DINPro-Regular.otf");
                setTypeface(typeface);
                break;
            case "systemDefault":
                setTypeface(Typeface.DEFAULT);
                break;
            default:
                typeface = Typeface.createFromAsset(context.getAssets(), "font/PingFangRegular.ttf");
                setTypeface(typeface);
        }
        if (typedArray != null) {
            typedArray.recycle();
        }
        typeface = null;
    }
}
