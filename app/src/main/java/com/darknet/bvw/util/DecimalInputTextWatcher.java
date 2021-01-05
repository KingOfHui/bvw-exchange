package com.darknet.bvw.util;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Description: 小数位数限定
 */

public class DecimalInputTextWatcher implements TextWatcher {

    private static final String Period = ".";
    private static final String Zero = "0";

    /**
     * 需要设置该 DecimalInputTextWatcher 的 EditText
     */
    private EditText editText = null;

    /**
     * 默认  小数的位数   2 位
     */
    private static final int DEFAULT_DECIMAL_DIGITS = 2;

    private int decimalDigits;// 小数的位数
    private int totalDigits;//最大长度

    /**
     * @param editText      editText
     * @param totalDigits   最大长度
     * @param decimalDigits 小数的位数
     */
    public DecimalInputTextWatcher(EditText editText, int totalDigits, int decimalDigits) {
        if (editText == null) {
            throw new RuntimeException("editText can not be null");
        }
        this.editText = editText;
        if (totalDigits <= 0)
            throw new RuntimeException("totalDigits must > 0");
        if (decimalDigits <= 0)
            throw new RuntimeException("decimalDigits must > 0");
        this.totalDigits = totalDigits;
        this.decimalDigits = decimalDigits;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            String s = editable.toString();
            editText.removeTextChangedListener(this);
            //限制最大长度
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(totalDigits)});

            if (s.contains(Period)) {
                //超过小数位限定位数,只保留限定小数位数
                if (s.length() - 1 - s.indexOf(Period) > decimalDigits) {
                    s = s.substring(0,
                            s.indexOf(Period) + decimalDigits + 1);
                    editable.replace(0, editable.length(), s.trim());
                }
            }
            //如果首位输入"."自动补0
            if (s.trim().equals(Period)) {
                s = Zero + s;
                editable.replace(0, editable.length(), s.trim());
            }
            //首位输入0时,不再继续输入
            if (s.startsWith(Zero)
                    && s.trim().length() > 1) {
                if (!s.substring(1, 2).equals(Period)) {
                    editable.replace(0, editable.length(), Zero);
                }
            }
            editText.addTextChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setXiaoshuPriceLimit(int xiaoshuPriceLimit) {
        this.decimalDigits = xiaoshuPriceLimit;
    }
}