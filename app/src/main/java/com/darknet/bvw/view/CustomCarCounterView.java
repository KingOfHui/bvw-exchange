package com.darknet.bvw.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.darknet.bvw.R;
import com.darknet.bvw.util.ValueUtil;

import java.math.BigDecimal;

public class CustomCarCounterView extends FrameLayout {
    private static final String TAG = CustomCarCounterView.class.getSimpleName();

    private static final int STATE_ADD = 0; // 加
    private static final int STATE_SUM = 1; // 减
    private static final int STATE_CAN_SWITCH = 2;

    private static final BigDecimal DEFAULT_VALUE = BigDecimal.ZERO;
    private static final BigDecimal DEFAULT_STEP = BigDecimal.ONE;

    private BigDecimal mGoodsNumber = DEFAULT_VALUE; // 默认值
    private BigDecimal mStep = DEFAULT_STEP;  // 默认加减跨度

    private BigDecimal mMaxCount = new BigDecimal(10000);// 最大值
    private BigDecimal mMinCount = DEFAULT_VALUE; // 最小值  默认为0

    private boolean mCanNegative = false; //是否允许为负数 默认为false
    private boolean mCanSwitch = true;
    private boolean mCanShowModify = true;
    private boolean mShowOperateBtn = true;

    private TextView mTvNum;
    private ImageView mImgAdd;
    private ImageView mImgSum;

    @SuppressLint("HandlerLeak") private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_ADD:
                    checkAdd();
                    break;
                case STATE_SUM:
                    checkSub();
                    break;
                case STATE_CAN_SWITCH:
                    setCanAdd(mCanSwitch);
                    setCanSum(mCanSwitch);
                    break;
                default:
                    checkAdd();
                    checkSub();
                    break;
            }
        }
    };

    private UpdateNumberListener mUpdateNumberListener;

    public CustomCarCounterView(Context context) {
        this(context, null);
    }

    public CustomCarCounterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCarCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.view_car_commodity_count_operating,
                                               this, false);
        initView(rootView);
        initEvent();
        addView(rootView);

//        test();
    }

    public void setShowOperateBtn(boolean show) {
        mImgAdd.setVisibility(View.INVISIBLE);
        mImgSum.setVisibility(View.INVISIBLE);
        setCanSwitch(show);
        updateCountStyle();
    }

    @SuppressLint("SetTextI18n")
    private void updateCountStyle() {
        mTvNum.setText("x" + ValueUtil.stripTrailingZeros(mGoodsNumber));
    }

    private void test() {
        setMaxCount(new BigDecimal(8));
    }

    private void initView(View rootView) {
        mImgAdd = rootView.findViewById(R.id.img_add);
        mImgSum = rootView.findViewById(R.id.img_sub);
        mTvNum = rootView.findViewById(R.id.tv_number);
    }

    public void setCanShowModify(boolean can) {
        this.mCanShowModify = can;
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

        mTvNum.setOnClickListener(v -> {
            if (mCanSwitch) {
                showModifyCountDialog();
            }
        });
    }

    /**
     * 修改数量对话框
     */
    private void showModifyCountDialog() {
        // TODO: 2020/12/22 0022
    }

    /**
     * 是否允许加
     *
     * @param canAdd true or false
     */
    public void setCanAdd(boolean canAdd) {
        mImgAdd.setClickable(canAdd);
        mImgAdd.setBackgroundResource(canAdd ? R.drawable.img_number_add
                                              : R.drawable.img_number_add_uncheck);
    }

    /**
     * 是否允许减
     *
     * @param canSum true or false
     */
    public void setCanSum(boolean canSum) {
        mImgSum.setClickable(canSum);
        mImgSum.setBackgroundResource(canSum ? R.drawable.img_number_subtract
                                              : R.drawable.img_number_subtract_uncheck);
    }

    /**
     * 是否允许加减
     *
     * @param canSwitch true or false
     */
    public void setCanSwitch(boolean canSwitch) {
        this.mCanSwitch = canSwitch;
        setCanAdd(mCanSwitch);
        setCanSum(mCanSwitch);
//        mHandler.sendEmptyMessage(STATE_CAN_SWITCH);

    }

    /**
     * 是否可以小于0
     */
    public void setCanNegative(boolean canNegative) {
        this.mCanNegative = canNegative;
    }

    /**
     * @return 获取是否可以为负
     */
    public boolean getCanNegative() {
        return mCanNegative;
    }

    /**
     * 更新商品数量
     */
    public void updateNumber(boolean notify) {
        mTvNum.setText(ValueUtil.stripTrailingZeros(mGoodsNumber));
        if (mUpdateNumberListener != null) {
            if (notify) {
                mUpdateNumberListener.updateNumber(mGoodsNumber.stripTrailingZeros());
            }
        }
    }

    /**
     * 数量加
     */
    public void addNumber() {
        BigDecimal addResult = mGoodsNumber.add(getStep());
        if (addResult.compareTo(getMaxCount()) >= 0) {
            mGoodsNumber = getMaxCount();
        } else {
            mGoodsNumber = addResult;
        }
        if (mCanSwitch) {
            mHandler.sendEmptyMessage(-1);
        }
    }

    /**
     * 数量减
     */
    public void subNumber() {
        BigDecimal resultValue = mGoodsNumber.subtract(getStep());
        if (getCanNegative()) {// 可以为负数
            mGoodsNumber = resultValue;
        } else {
            if (resultValue.compareTo(DEFAULT_VALUE) < 0) {
                mGoodsNumber = getMinCount();
            } else {
                mGoodsNumber = resultValue;
            }
        }
        if (mCanSwitch) {
            mHandler.sendEmptyMessage(-1);
        }
    }

    /**
     * 每次加减设置的数量
     *
     * @param step 加减的跨度
     */
    public void step(BigDecimal step) {
        this.mStep = step;
    }

    /**
     * 获取的步长
     *
     * @return 步长
     */
    public BigDecimal getStep() {
        return mStep;
    }

    /**
     * 获取数量
     */
    public BigDecimal getNumber() {
        return mGoodsNumber.stripTrailingZeros();
    }

    public void setNumber(BigDecimal goodsNumber) {
        mGoodsNumber = goodsNumber;
        updateNumber(false);
    }

    public void setNumber(String goodsNumber) {
        if (!TextUtils.isEmpty(goodsNumber)) {
            BigDecimal number = new BigDecimal(goodsNumber);
            setNumber(number);
        }
    }

    public void setMinCount(BigDecimal minCount) {
        if (minCount != null) {
            this.mMinCount = minCount;
        }
        if (mCanSwitch) {
            mHandler.sendEmptyMessage(-1);
        }
    }

    public BigDecimal getMinCount() {
        return mMinCount;
    }

    public void setMaxCount(BigDecimal maxCount) {
        if (maxCount != null) {
            this.mMaxCount = maxCount;
        }

        if (mCanSwitch) {
            mHandler.sendEmptyMessage(-1);
        }
    }

    public BigDecimal getMaxCount() {
        return mMaxCount;
    }

    public void setUpdateNumberListener(UpdateNumberListener listener) {
        this.mUpdateNumberListener = listener;
    }

    public void revert() {
        mGoodsNumber = DEFAULT_VALUE;
        mStep = DEFAULT_STEP;
        mMinCount = DEFAULT_VALUE;
    }

    public void checkAdd() {
        setCanAdd(mGoodsNumber.compareTo(getMaxCount()) < 0);
    }

    public void checkSub() {
        setCanSum(mGoodsNumber.compareTo(getMinCount()) > 0);
    }

    /**
     * 更新商品数量监听器
     */
    public interface UpdateNumberListener {
        void updateNumber(BigDecimal number);
    }
}
