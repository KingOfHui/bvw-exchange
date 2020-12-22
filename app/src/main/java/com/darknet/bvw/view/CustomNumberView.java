package com.darknet.bvw.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.darknet.bvw.R;
import com.darknet.bvw.view.wrap.TextWatcherWrapper;

import java.math.BigDecimal;

public class CustomNumberView extends FrameLayout {
    private static final String TAG = CustomNumberView.class.getSimpleName();

    private BigDecimal mGoodsNumber = BigDecimal.ZERO;
    private BigDecimal mMaxCount = BigDecimal.valueOf(Integer.MAX_VALUE);
    private BigDecimal mMinCount = BigDecimal.valueOf(-1);
    private boolean canSwitch = true;

    private ImageView mImgAdd;
    private ImageView mImgSum;
    private EditText mEtNum;

    private UpdateNumberListener mUpdateNumberListener;

    public CustomNumberView(Context context) {
        this(context, null);
    }

    public CustomNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.view_number_count_operating,
                                               this,
                                               false);
        initView(rootView);
        initEvent();
        addView(rootView);
    }

    private void initView(View rootView) {
        mImgAdd = rootView.findViewById(R.id.img_add);
        mImgSum = rootView.findViewById(R.id.img_sub);
        mEtNum = rootView.findViewById(R.id.et_number);
    }

    private void initEvent() {
        mImgAdd.setOnClickListener(v -> {
            addNumber();
            updateNumber(true);
        });
        mImgSum.setOnClickListener(v -> {
            subNumber();
            updateNumber(true);
        });
        mEtNum.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                String numStr = s.toString().trim();
                if (TextUtils.isEmpty(numStr)) {
                    return;
                }
                mGoodsNumber = new BigDecimal(numStr);
                if (mUpdateNumberListener != null) {
                    mUpdateNumberListener.updateNumber(mGoodsNumber);
                }
            }
        });
    }

    public void setCanAdd(boolean canAdd) {
        mImgAdd.setClickable(canAdd);
        mImgAdd.setBackgroundResource(canAdd
                                              ? R.drawable.img_number_add
                                              : R.drawable.img_number_add_uncheck);

    }


    public void setCanSum(boolean canSum) {
        mImgSum.setClickable(canSum);
        mImgSum.setBackgroundResource(canSum
                                              ? R.drawable.img_number_subtract
                                              : R.drawable.img_number_subtract_uncheck);
    }


    public void setCanSwitch(boolean canSwitch) {
        Log.i(TAG, "setCanSwitch: " + canSwitch);
        setCanAdd(canSwitch);
        setCanSum(canSwitch);
    }

    /**
     * @param canEdit 设置EditText是否可编辑输入
     */
    public void setCanEdit(boolean canEdit) {
        Log.i(TAG, "setCanEdit: " + canEdit);
        mEtNum.setFocusable(canEdit);
        mEtNum.setFocusableInTouchMode(canEdit);
    }

    /**
     * 设置可输入小数，默认可输入整数
     */
    public void setEditInputTypeIsDecimal() {
        mEtNum.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }
    /**
     * 更新商品数量
     */
    public void updateNumber(boolean notify) {
        mEtNum.setText(mGoodsNumber.toPlainString());
        if (mUpdateNumberListener != null) {
            if (notify) {
                mUpdateNumberListener.updateNumber(mGoodsNumber);
            }
        }
    }

    public void addNumber() {
        mGoodsNumber = mGoodsNumber.add(BigDecimal.ONE);
    }

    public void subNumber() {
        if (!mMinCount.equals(BigDecimal.valueOf(-1))) {
            mGoodsNumber = mGoodsNumber.subtract(BigDecimal.ONE).compareTo(mMinCount) < 0 ? mMinCount : mGoodsNumber.subtract(BigDecimal.ONE);
        } else {
            mGoodsNumber = mGoodsNumber.subtract(BigDecimal.ONE).compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : mGoodsNumber.subtract(BigDecimal.ONE);
        }
    }


    /**
     * 获取商品数量
     *
     * @return
     */
    public BigDecimal getNumber() {
        return mGoodsNumber;
    }

    public void setNumber(BigDecimal goodsNumber) {
        mGoodsNumber = goodsNumber;
        updateNumber(false);
    }

    public void setNumber(String goodsNumber) {
        if (!TextUtils.isEmpty(goodsNumber)) {
            BigDecimal number =new BigDecimal(goodsNumber);
            setNumber(number.stripTrailingZeros());
        } else {
            setNumber(BigDecimal.ZERO);
        }
    }

    public void setMinCount(BigDecimal minCount) {
        this.mMinCount = minCount;
    }

    public void setMaxCount(BigDecimal maxCount) {
        this.mMaxCount = maxCount;
    }

    public void setUpdateNumberListener(UpdateNumberListener listener) {
        this.mUpdateNumberListener = listener;
    }

    /**
     * 更新商品数量监听器
     */
    public interface UpdateNumberListener {

        void updateNumber(BigDecimal number);
    }
}
